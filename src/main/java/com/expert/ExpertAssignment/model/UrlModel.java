package com.expert.ExpertAssignment.model;

public class UrlModel {

    private String url;
    private String fileName;

    public UrlModel(String url, String fileName) {
        this.url = url;
        this.fileName = fileName;
    }

    public UrlModel() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return "UrlModel{" +
                "url='" + url + '\'' +
                ", fileName='" + fileName + '\'' +
                '}';
    }
}
