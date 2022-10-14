package com.datanucleus.spreadSheetDemo.service;

import com.datanucleus.spreadSheetDemo.model.*;
import com.datanucleus.spreadSheetDemo.model.util.CityUploadTemp;
import com.datanucleus.spreadSheetDemo.model.util.RegionUploadTemp;
import com.datanucleus.spreadSheetDemo.model.util.StateUploadTemp;
import com.datanucleus.spreadSheetDemo.repository.CityRepository;
import com.datanucleus.spreadSheetDemo.repository.CountryRepository;
import com.datanucleus.spreadSheetDemo.repository.RegionRepository;
import com.datanucleus.spreadSheetDemo.repository.StateRepository;
import com.datanucleus.spreadSheetDemo.util.SpreadSheet.SpreadSheetReader;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class BulkUploadService {
    @Autowired
    private SpreadSheetReader reader;
    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private StateRepository stateRepository;
    @Autowired
    private RegionRepository regionRepository;
    @Autowired
    private CityRepository cityRepository;

    public BulkUploadTemplate generateCountryTemplate(){
        return reader.generateCountryTemplate("tem");
    }

    public BulkUploadTemplate generateStateTemplate(String country){
        return reader.generateStateTemplate(country);
    }

    public BulkUploadTemplate generateRegionTemplate(String country) {
        return reader.generateRegionTemplate(country);
    }

    public BulkUploadTemplate generateCityTemplate(String state) {
        return reader.generateCityTemplate(state);
    }

    public BulkUploadTemplate uploadCountries(MultipartFile file) {
        // save the Excel file into the filesystem, the data in DeviceUpload table
        log.info("UploadTemplate started for " + file.getOriginalFilename());
        XSSFWorkbook workbook = null;
        int manufDeviceIdCol = 0;

        List<ResultStatus> statusList = new ArrayList<ResultStatus>();
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();

                // Create the file on server
                File serverFile = null;
                try {
                    serverFile = new File("/temp_uploads/" + file.getOriginalFilename());
                    String contentType = file.getContentType(); // application/vnd.openxmlformats-officedocument.spreadsheetml.sheet
                    BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                    stream.write(bytes);
                    stream.close();
                }catch(Exception e) {
                    log.error("Error saving uploaded file " + e.getMessage());
                }
                System.out.println("Server File Location=" + serverFile.getAbsolutePath());

                ByteArrayInputStream in = new ByteArrayInputStream(bytes);
                workbook = new XSSFWorkbook(in);
                XSSFSheet sheet = workbook.getSheetAt(0);
                //XSSFSheet  sheet = workbook.getSheetAt("Device upload");
                int rowNum = -1;
                for (Row row : sheet) {
                    rowNum++;
                    if(rowNum <= 1 || row.getCell(0) == null) {continue;} // skip first 2 Rows (header)

                    CountryModel country = new CountryModel();
                    country.setCountryName(row.getCell(0).getStringCellValue());
                    country.setDescription(row.getCell(1).getStringCellValue());
                    country.setCurrency(row.getCell(2).getStringCellValue());
                    country.setCurrencySymbol(row.getCell(3).getStringCellValue());
                    country.setUsExchangeRate (String.valueOf(row.getCell(4).getNumericCellValue()));
                    country.setUkExchangeRate(String.valueOf(row.getCell(5).getNumericCellValue()));
                    country.setEuExchangeRate(String.valueOf(row.getCell(6).getNumericCellValue()));

                    countryRepository.save(country);
                    int n = rowNum-1;
                    statusList.add(new ResultStatus(n, ResultStatus.SUCCESS, country.getCountryName()));
                }
                return reader.generateResultSheet(statusList, file.getOriginalFilename());
            } catch (Exception e) {
                log.error("Extraction: ", e);
                return reader.generateError(e.getMessage());
            }finally {
                try{
                    workbook.close();
                }catch(Exception e){

                }
            }
        }
        //todo replace null with Excel
        return null;
    }

    public BulkUploadTemplate uploadCities(CityUploadTemp temp) {
        // save the Excel file into the filesystem, the data in DeviceUpload table
        log.info("UploadTemplate started for " + temp.getFile().getOriginalFilename());
        MultipartFile file = temp.getFile();
        XSSFWorkbook workbook = null;

        List<ResultStatus> statusList = new ArrayList<ResultStatus>();
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();

                // Create the file on server
                File serverFile = null;
                try {
                    serverFile = new File("/temp_uploads/" + file.getOriginalFilename());
                    BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                    stream.write(bytes);
                    stream.close();
                }catch(Exception e) {
                    log.error("Error saving uploaded file " + e.getMessage());
                }
                System.out.println("Server File Location=" + serverFile.getAbsolutePath());

                ByteArrayInputStream in = new ByteArrayInputStream(bytes);
                workbook = new XSSFWorkbook(in);
                XSSFSheet sheet = workbook.getSheetAt(0);
                //XSSFSheet  sheet = workbook.getSheetAt("Device upload");
                int rowNum = -1;
                for (Row row : sheet) {
                    rowNum++;
                    if(rowNum <= 1 || row.getCell(0) == null) {continue;} // skip first 2 Rows (header)

                    CityModel city = new CityModel();
                    city.setCityName(row.getCell(0).getStringCellValue());
                    city.setDescription(row.getCell(1).getStringCellValue());

                    Optional<StateModel> findState = stateRepository.findById(temp.getStateId());
                    if (findState.isPresent()){
                        city.setStateName(findState.get().getStateName());
                        city.setCountryName(findState.get().getCountryName());

                        city.setCountryId(String.valueOf(findState.get().getCountryId()));
                        city.setRegionId(String.valueOf(findState.get().getRegionId()));
                        city.setStateId(String.valueOf(temp.getStateId()));
                    } else{
                        statusList.add(new ResultStatus(rowNum-1, ResultStatus.FAILED, String.format("Country id: %d not found", temp.getCountryId())));
                        continue;
                    }

                    cityRepository.save(city);
                    int n = rowNum-1;
                    statusList.add(new ResultStatus(n, ResultStatus.SUCCESS, city.getCityName()));
                }
                return reader.generateResultSheet(statusList, file.getOriginalFilename());
            } catch (Exception e) {
                log.error("Extraction: ", e);
                return reader.generateError(e.getMessage());
            }finally {
                try{
                    workbook.close();
                }catch(Exception e){

                }
            }
        }
        //todo replace null with Excel
        return null;
    }

    public BulkUploadTemplate uploadRegions(RegionUploadTemp temp) {
        // save the Excel file into the filesystem, the data in DeviceUpload table
        log.info("UploadTemplate started for " + temp.getFile().getOriginalFilename());
        MultipartFile file = temp.getFile();
        XSSFWorkbook workbook = null;

        List<ResultStatus> statusList = new ArrayList<ResultStatus>();
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();

                // Create the file on server
                File serverFile = null;
                try {
                    serverFile = new File("/temp_uploads/" + file.getOriginalFilename());
                    String contentType = file.getContentType(); // application/vnd.openxmlformats-officedocument.spreadsheetml.sheet
                    BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                    stream.write(bytes);
                    stream.close();
                }catch(Exception e) {
                    log.error("Error saving uploaded file " + e.getMessage());
                }
                System.out.println("Server File Location=" + serverFile.getAbsolutePath());

                ByteArrayInputStream in = new ByteArrayInputStream(bytes);
                workbook = new XSSFWorkbook(in);
                XSSFSheet sheet = workbook.getSheetAt(0);
                //XSSFSheet  sheet = workbook.getSheetAt("Device upload");
                int rowNum = -1;
                for (Row row : sheet) {
                    rowNum++;
                    if(rowNum <= 1 || row.getCell(0) == null) {continue;} // skip first 2 Rows (header)

                    Region region = new Region();
                    String regionName = row.getCell(0).getStringCellValue();
                    region.setRegionName(regionName);
                    region.setDescription(row.getCell(1).getStringCellValue());

                    Optional<CountryModel> findCountry = countryRepository.findById(temp.getCountryId());
                    if (findCountry.isPresent()){
                        region.setCountryName(findCountry.get().getCountryName());
                    } else{
                        statusList.add(new ResultStatus(rowNum-1, ResultStatus.FAILED, String.format("Country id: %d not found", temp.getCountryId())));
                        continue;
                    }

                    region.setCountryId(String.valueOf(temp.getCountryId()));

                    regionRepository.save(region);
                    int n = rowNum-1;
                    statusList.add(new ResultStatus(n, ResultStatus.SUCCESS, regionName));
                }
                return reader.generateResultSheet(statusList, file.getOriginalFilename());
            } catch (Exception e) {
                log.error("Extraction: ", e);
                return reader.generateError(e.getMessage());
            }finally {
                try{
                    workbook.close();
                }catch(Exception e){

                }
            }
        }
        //todo replace null with Excel
        return null;
    }

    public BulkUploadTemplate uploadStates(StateUploadTemp temp) {
        // save the Excel file into the filesystem, the data in DeviceUpload table
        log.info("UploadTemplate started for " + temp.getFile().getOriginalFilename());
        MultipartFile file = temp.getFile();
        XSSFWorkbook workbook = null;

        List<ResultStatus> statusList = new ArrayList<ResultStatus>();
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();

                // Create the file on server
                File serverFile = null;
                try {
                    serverFile = new File("/temp_uploads/" + file.getOriginalFilename());
                    BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                    stream.write(bytes);
                    stream.close();
                }catch(Exception e) {
                    log.error("Error saving uploaded file " + e.getMessage());
                }
                System.out.println("Server File Location=" + serverFile.getAbsolutePath());

                ByteArrayInputStream in = new ByteArrayInputStream(bytes);
                workbook = new XSSFWorkbook(in);
                XSSFSheet sheet = workbook.getSheetAt(0);
                //XSSFSheet  sheet = workbook.getSheetAt("Device upload");
                int rowNum = -1;
                for (Row row : sheet) {
                    rowNum++;
                    if(rowNum <= 1 || row.getCell(0) == null) {continue;} // skip first 2 Rows (header)

                    StateModel state = new StateModel();
                    String stateName = row.getCell(0).getStringCellValue();
                   state.setStateName(stateName);
                    state.setDescription(row.getCell(1).getStringCellValue());

                    Optional<CountryModel> findCountry = countryRepository.findById(temp.getCountryId());
                    if (findCountry.isPresent()){
                        Optional<Region> findRegion = regionRepository.findById(temp.getRegionId());
                        if (findRegion.isPresent()) {
                            state.setCountryName(findCountry.get().getCountryName());
                            state.setRegionId(String.valueOf(temp.getRegionId()));
                        }
                        else {
                            statusList.add(new ResultStatus(rowNum-1, ResultStatus.FAILED, String.format("Region id: %d not found", temp.getRegionId())));
                            continue;
                        }
                    } else{
                        statusList.add(new ResultStatus(rowNum-1, ResultStatus.FAILED, String.format("Country id: %d not found", temp.getCountryId())));
                        continue;
                    }

                    state.setCountryId(String.valueOf(temp.getCountryId()));

                    stateRepository.save(state);
                    int n = rowNum-1;
                    statusList.add(new ResultStatus(n, ResultStatus.SUCCESS, stateName));
                }
                return reader.generateResultSheet(statusList, file.getOriginalFilename());
            } catch (Exception e) {
                log.error("Extraction: ", e);
                return reader.generateError(e.getMessage());
            }finally {
                try{
                    workbook.close();
                }catch(Exception e){

                }
            }
        }
        //todo replace null with Excel
        return null;
    }
}
