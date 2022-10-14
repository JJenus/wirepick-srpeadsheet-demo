package com.datanucleus.spreadSheetDemo.controller;

import com.datanucleus.spreadSheetDemo.model.BulkUploadTemplate;
import com.datanucleus.spreadSheetDemo.model.CountryModel;
import com.datanucleus.spreadSheetDemo.model.util.CityUploadTemp;
import com.datanucleus.spreadSheetDemo.model.util.RegionUploadTemp;
import com.datanucleus.spreadSheetDemo.model.util.StateUploadTemp;
import com.datanucleus.spreadSheetDemo.service.BulkUploadService;
import com.datanucleus.spreadSheetDemo.service.FilesStorageService;
import com.datanucleus.spreadSheetDemo.util.SpreadSheet.SpreadSheetReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("spread-sheet")
public class SpreadSheet {
    @Autowired
    SpreadSheetReader spreadSheetReader;
    @Autowired
    BulkUploadService bulkUploadService;

    @Autowired
    FilesStorageService storageService;

    private final HttpHeaders header = new HttpHeaders();

    @PostMapping("/read")
    public ResponseEntity<?> uploadFiles(@RequestParam("file") MultipartFile file) {
        String message = "";

        try {
            String fileName = storageService.save(file);
            message = "Uploaded the files successfully: " + fileName;
            spreadSheetReader = new SpreadSheetReader(fileName);
            return ResponseEntity.status(HttpStatus.OK).body(spreadSheetReader.printSheet(0));
        } catch (Exception e) {
            System.out.println(e);
            message = "Fail to upload files!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }
    }

    @PostMapping(value = "/bulk-upload/countries")
    public ResponseEntity<Resource> uploadCountry(@RequestParam("file") MultipartFile excelVO) {
        BulkUploadTemplate template = bulkUploadService.uploadCountries(excelVO);
        if (template != null) {
            setHttpHeader(template.getFilename());
            ByteArrayResource resource = new ByteArrayResource(template.getData());
            return ResponseEntity.ok().headers(header).contentLength(template.getData().length)
                    .contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(resource);
        } else {
            return null;
        }
    }

    @PostMapping(value = "/bulk-upload/regions")
    public ResponseEntity<Resource> uploadRegion(RegionUploadTemp temp) {
        BulkUploadTemplate template = bulkUploadService.uploadRegions(temp);
        if (template != null) {
            setHttpHeader(template.getFilename());
            ByteArrayResource resource = new ByteArrayResource(template.getData());
            return ResponseEntity.ok().headers(header).contentLength(template.getData().length)
                    .contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(resource);
        } else {
            return null;
        }
    }

    @PostMapping(value = "/bulk-upload/cities")
    public ResponseEntity<Resource> uploadRegion(CityUploadTemp temp) {
        BulkUploadTemplate template = bulkUploadService.uploadCities(temp);
        if (template != null) {
            setHttpHeader(template.getFilename());
            ByteArrayResource resource = new ByteArrayResource(template.getData());
            return ResponseEntity.ok().headers(header).contentLength(template.getData().length)
                    .contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(resource);
        } else {
            return null;
        }
    }

    @PostMapping(value = "/bulk-upload/states")
    public ResponseEntity<Resource> uploadRegion(StateUploadTemp temp) {
        BulkUploadTemplate template = bulkUploadService.uploadStates(temp);
        if (template != null) {
            setHttpHeader(template.getFilename());
            ByteArrayResource resource = new ByteArrayResource(template.getData());
            return ResponseEntity.ok().headers(header).contentLength(template.getData().length)
                    .contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(resource);
        } else {
            return null;
        }
    }

    @GetMapping("/bulk-upload-template/countries")
    public ResponseEntity<?> uploadFiles() {
        BulkUploadTemplate template = bulkUploadService.generateCountryTemplate();
        if (template != null) {
            setHttpHeader(template.getFilename());

            ByteArrayResource resource = new ByteArrayResource(template.getData());
            return ResponseEntity.ok().headers(header).contentLength(template.getData().length)
                    .contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(resource);
        } else {
            return null;
        }
    }

    @GetMapping("/bulk-upload-template/states/country/{country}")
    public ResponseEntity<?> uploadState(@PathVariable("country") String country) {
        BulkUploadTemplate template = bulkUploadService.generateStateTemplate(country);
        if (template != null) {
            setHttpHeader(template.getFilename());

            ByteArrayResource resource = new ByteArrayResource(template.getData());

            return ResponseEntity.ok().headers(header).contentLength(template.getData().length)
                    .contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(resource);
        } else {
            return null;
        }
    }

    @GetMapping("/bulk-upload-template/regions/country/{country}")
    public ResponseEntity<?> generateRegion(@PathVariable("country") String country) {
        BulkUploadTemplate template = bulkUploadService.generateRegionTemplate(country);
        if (template != null) {
            setHttpHeader(template.getFilename());

            ByteArrayResource resource = new ByteArrayResource(template.getData());

            return ResponseEntity.ok().headers(header).contentLength(template.getData().length)
                    .contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(resource);
        } else {
            return null;
        }
    }

    @GetMapping("/bulk-upload-template/cities/state/{state}")
    public ResponseEntity<?> generateCity(@PathVariable("state") String state) {
        BulkUploadTemplate template = bulkUploadService.generateCityTemplate(state);
        if (template != null) {
            setHttpHeader(template.getFilename());

            ByteArrayResource resource = new ByteArrayResource(template.getData());

            return ResponseEntity.ok().headers(header).contentLength(template.getData().length)
                    .contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(resource);
        } else {
            return null;
        }
    }

    private void setHttpHeader(String fileName){
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"");
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");
    }
}
