package com.datanucleus.spreadSheetDemo.util.SpreadSheet;

import com.datanucleus.spreadSheetDemo.model.BulkUploadTemplate;
import com.datanucleus.spreadSheetDemo.model.ResultStatus;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Service
public class SpreadSheetReader {
    private final Workbook workbook;

    public SpreadSheetReader(String fileLocation) throws IOException {
        FileInputStream file = new FileInputStream(fileLocation);
        this.workbook = new XSSFWorkbook(file);
    }

    public SpreadSheetReader() {
        this.workbook = new XSSFWorkbook();
    }

    public BulkUploadTemplate generateCountryTemplate(String country){
        XSSFWorkbook workbook = new XSSFWorkbook ();
        XSSFSheet sheet = workbook.createSheet("Countries upload");

        // style
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 12);
        headerStyle.setFont(font);

        Row nameRow = sheet.createRow(0);
        Cell companyNameCell = nameRow.createCell(0);
        companyNameCell.setCellValue("Bulk Upload Countries");
        companyNameCell.setCellStyle(headerStyle);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 6));
        CellUtil.setAlignment(companyNameCell, HorizontalAlignment.CENTER);

        Row headerRow = sheet.createRow(1);

        createCell(sheet, headerRow, 0, "Name", 6000, headerStyle);

        createCell(sheet, headerRow, 1, "Description", 4000, headerStyle);

        createCell(sheet, headerRow, 2, "Currency", 5000, headerStyle);

        createCell(sheet, headerRow, 3, "Currency Symbol", 5000, headerStyle);

        createCell(sheet, headerRow, 4, "US Exchange Rate", 5000, headerStyle);

        createCell(sheet, headerRow, 5, "UK Exchange Rate", 5000, headerStyle);

        createCell(sheet, headerRow, 6, "EU Exchange Rate", 5000, headerStyle);

        String filename = "country-upload-template-"+ getCurrentTime() + ".xlsx";
        BulkUploadTemplate template = saveExcel("/temp_uploads/", workbook, filename);
        return template;
    }

    public BulkUploadTemplate generateStateTemplate(String country){
        XSSFWorkbook workbook = new XSSFWorkbook ();
        XSSFSheet sheet = workbook.createSheet("States upload for "+country);

        // style
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 12);
        headerStyle.setFont(font);

        Row nameRow = sheet.createRow(0);
        Cell companyNameCell = nameRow.createCell(0);
        companyNameCell.setCellValue("Bulk Upload States for "+country);
        companyNameCell.setCellStyle(headerStyle);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));
        CellUtil.setAlignment(companyNameCell, HorizontalAlignment.CENTER);

        Row headerRow = sheet.createRow(1);

        createCell(sheet, headerRow, 0, "Name", 8000, headerStyle);

        createCell(sheet, headerRow, 1, "Description", 7000, headerStyle);

        createCell(sheet, headerRow, 2, "Region", 7000, headerStyle);

        createCell(sheet, headerRow, 3, "Country", 7000, headerStyle);


        String filename = "state-upload-template-"+ getCurrentTime() + ".xlsx";
        BulkUploadTemplate template = saveExcel("/temp_uploads/", workbook, filename);
        return template;
    }

    public BulkUploadTemplate generateRegionTemplate(String country) {
        XSSFWorkbook workbook = new XSSFWorkbook ();
        XSSFSheet sheet = workbook.createSheet("Region upload for "+country);

        // style
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 12);
        headerStyle.setFont(font);

        Row nameRow = sheet.createRow(0);
        Cell companyNameCell = nameRow.createCell(0);
        companyNameCell.setCellValue("Bulk Upload Regions for "+country);
        companyNameCell.setCellStyle(headerStyle);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 2));
        CellUtil.setAlignment(companyNameCell, HorizontalAlignment.CENTER);

        Row headerRow = sheet.createRow(1);

        createCell(sheet, headerRow, 0, "Name", 8000, headerStyle);

        createCell(sheet, headerRow, 1, "Description", 7000, headerStyle);

        createCell(sheet, headerRow, 2, "Country", 7000, headerStyle);

        String filename = "region-upload-template-"+ getCurrentTime() + ".xlsx";
        BulkUploadTemplate template = saveExcel("/temp_uploads/", workbook, filename);
        return template;
    }

    public BulkUploadTemplate generateCityTemplate(String state) {
        XSSFWorkbook workbook = new XSSFWorkbook ();
        XSSFSheet sheet = workbook.createSheet("City upload for "+state);

        // style
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 12);
        headerStyle.setFont(font);

        Row nameRow = sheet.createRow(0);
        Cell companyNameCell = nameRow.createCell(0);
        companyNameCell.setCellValue("Bulk Upload Regions for "+state);
        companyNameCell.setCellStyle(headerStyle);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 2));
        CellUtil.setAlignment(companyNameCell, HorizontalAlignment.CENTER);

        Row headerRow = sheet.createRow(1);

        createCell(sheet, headerRow, 0, "Name", 8000, headerStyle);

        createCell(sheet, headerRow, 1, "Description", 7000, headerStyle);

        createCell(sheet, headerRow, 2, "State", 7000, headerStyle);

        String filename = "city-upload-template-"+ getCurrentTime() + ".xlsx";
        BulkUploadTemplate template = saveExcel("/temp_uploads/", workbook, filename);
        return template;
    }

    public BulkUploadTemplate generateResultSheet(List<ResultStatus> statusList, String fileName){
        XSSFWorkbook statusbook = null;
        try {
            statusbook = new XSSFWorkbook ();
            XSSFSheet  sheet = statusbook.createSheet("Upload Status");
            CellStyle successStyle = statusbook.createCellStyle();
            //	successStyle.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex());
            //	successStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            XSSFFont font = ((XSSFWorkbook) statusbook).createFont();
            font.setFontName("Arial");
            font.setFontHeightInPoints((short) 12);
//			font.setBold(true);
            successStyle.setFont(font);
            int rowNum = 0;

            Row row = sheet.createRow(rowNum++);
            CellStyle headerStyle = statusbook.createCellStyle();
            headerStyle.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            Cell numCell = row.createCell(0);
            numCell.setCellValue("Row #");
            numCell.setCellStyle(headerStyle);

            Cell statusCell = row.createCell(1);
            statusCell.setCellValue("Name");
            statusCell.setCellStyle(headerStyle);

            Cell commentCell = row.createCell(2);
            commentCell.setCellValue("Status");
            commentCell.setCellStyle(headerStyle);

            for(ResultStatus result : statusList) {
                Row rowx = sheet.createRow(rowNum++);

                Cell numCellx = rowx.createCell(0);
                numCellx.setCellValue(result.getRowNum());
                numCellx.setCellStyle(successStyle);

                Cell statusCellx = rowx.createCell(2);
                statusCellx.setCellValue(result.getStatus());
                statusCellx.setCellStyle(successStyle);

                Cell commentCellx = rowx.createCell(1);
                commentCellx.setCellValue(result.getComment());
                commentCellx.setCellStyle(successStyle);
            }
            sheet.setColumnWidth(0, 2000);
            sheet.setColumnWidth(1, 6000);
            sheet.setColumnWidth(2, 6000);

            BulkUploadTemplate template = saveExcel("/temp_uploads/", statusbook, fileName + "-upload-status-" + getCurrentTime() + ".xlsx");
            template.setFilename(fileName + "_status.xlsx");
            return template;
        } catch (Exception e) {
            log.error("Error " + e.getMessage());
            return null;// new UpdateStatus(UpdateStatus.ERROR, e.getMessage() );
        }finally {
            try{
                statusbook.close();
            }catch(Exception e){

            }
        }
    }

    private void createCell(XSSFSheet sheet, Row row, int col, String name, int cellWidth, CellStyle style) {
        Cell headerCell = row.createCell(col);
        sheet.setColumnWidth(col, cellWidth);
        headerCell.setCellValue(name);
        headerCell.setCellStyle(style);
    }

    private String getCurrentTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-mm-ss");
        return formatter.format(new Date());
    }

    private BulkUploadTemplate saveExcel(String folder, XSSFWorkbook workbook, String filename) {
        try {
            FileOutputStream outputStream = new FileOutputStream(folder+filename);
            workbook.write(outputStream);
            workbook.close();
        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
        }

        try {
            BulkUploadTemplate template = new BulkUploadTemplate();
            template.setFilename(filename);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            template.setData(out.toByteArray());
            return template;
        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
        }
        return null;
    }

    public BulkUploadTemplate generateError(String errorStr) {
        XSSFWorkbook workbook = new XSSFWorkbook ();
        XSSFSheet  sheet = workbook.createSheet("Device upload");

        // style
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 12);
//		font.setBold(true);
        headerStyle.setFont(font);


        Row nameRow = sheet.createRow(0);
        Cell companyNameCell = nameRow.createCell(0);
        companyNameCell.setCellValue(errorStr);
        companyNameCell.setCellStyle(headerStyle);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 12));
        CellUtil.setAlignment(companyNameCell, HorizontalAlignment.CENTER);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        String time = format.format(new Date());
        return saveExcel("/tmp/greensky/", workbook, "error-"+time + ".xlsx");
    }

    public Map<Integer, List<String>> printSheet(int sheetIndex){
        Sheet sheet = workbook.getSheetAt(sheetIndex);

        Map<Integer, List<String>> data = new HashMap<>();
        int i = 0;
        for (Row row : sheet) {
            data.put(i, new ArrayList<String>());
            for (Cell cell : row) {
                switch (cell.getCellType()) {
                    case STRING:
                        data.get(new Integer(i)).add(cell.getRichStringCellValue().getString());
                        break;
                    case NUMERIC:
                        if (DateUtil.isCellDateFormatted(cell)) {
                            data.get(i).add(cell.getDateCellValue() + "");
                        } else {
                            data.get(i).add(cell.getNumericCellValue() + "");
                        }
                        break;
                    case BOOLEAN:
                        data.get(i).add(cell.getBooleanCellValue() + "");
                        break;
                    case FORMULA:
                        data.get(i).add(cell.getCellFormula() + "");
                        break;
                    default: data.get(new Integer(i)).add(" ");
                }
            }
            i++;
        }
        return data;
    }
}
