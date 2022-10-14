package com.datanucleus.spreadSheetDemo.model;

public class ResultStatus{
    private int rowNum;
    private String status;
    private String comment;

    public final static String FAILED = "FAILED";
    public final static String SUCCESS = "SUCCESS";
    public final static String UNCLASSIFIED = "UNCLASSIFIED";

    public ResultStatus(int rowNum, String status, String comment) {
        this.rowNum = rowNum;
        this.status = status;
        this.comment = comment;
    }

    public int getRowNum() {
        return rowNum;
    }

    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}

