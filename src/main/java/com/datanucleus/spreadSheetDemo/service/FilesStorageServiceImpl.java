package com.datanucleus.spreadSheetDemo.service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Random;
import java.util.stream.Stream;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FilesStorageServiceImpl implements FilesStorageService {
    private final Path root = Paths.get("temp_uploads");

    public FilesStorageServiceImpl(){
        this.init();
    }

    @Override
    public void init() {
        try {
            if (!Files.exists(root)){
                Files.createDirectory(root);
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    private String getNextName(String fName, String ext, int count){
        String next = root.toString()+"/"+fName + String.format(" (%d).%s", count, ext);
        File f = new File(next);
        System.out.println(next);
        if (!f.isFile()){
            return fName + String.format(" (%d).%s", count, ext);
        }
        return getNextName(fName, ext, count + 1);
    }

    private String randomName(){
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 15;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        return buffer.toString();
    }

    @Override
    public String save(MultipartFile file) {
        String fileName = this.randomName() + "." + file.getOriginalFilename().split("\\.")[1];
        try {
            File f;
            do {
                f = new File(root.toString()+"/"+file.getOriginalFilename());
            } while (f.isFile());
            System.out.println(fileName);
            Files.copy(file.getInputStream(), this.root.resolve(fileName));

        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
        return root.toString()+"/"+fileName;
    }
    @Override
    public Resource load(String filename) {
        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    public File loadFile(String filename) {
        String next = root.toString()+"/"+filename;
        File f = new File(next);
        if (!f.isFile()){
            return f;
        }
        return null;
    }

    @Override
    public void deleteAll(){
        try {
            Files.walk(this.root)
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
        } catch (IOException e){
            throw new RuntimeException("failed to delete");
        }
        System.out.println(Files.exists(this.root));
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
        } catch (IOException e) {
            throw new RuntimeException("Could not load the files!");
        }
    }
}
