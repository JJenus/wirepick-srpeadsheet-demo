package com.datanucleus.spreadSheetDemo.model;

public class BulkUploadTemplate {
    private String filename;
    private byte[] data;


    public String getFilename() {
        return filename;
    }
    public void setFilename(String filename) {
        this.filename = filename;
    }
    public byte[] getData() {
        return data;
    }
    public void setData(byte[] data) {
        this.data = data;
    }
}
