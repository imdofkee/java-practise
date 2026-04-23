package com.osherovskaya.labs.database.model;

public class FIle {
    private int ID;
    private String filename;
    private int sizeInKB;

    public FIle(int id, String name, int size) {
        this.ID = id;
        this.filename = name;
        this.sizeInKB = size;
    }

    //Геттеры и сеттеры
    public int getFileID() {
        return ID;
    }

    public void setFileID(int id) {
        this.ID = id;
    }

    public String getFileName() {
        return filename;
    }

    public void setFileName(String name) {
        this.filename = name;
    }

    public int getFileSize() {
        return sizeInKB;
    }

    public void setFileSIze(int size) {
        this.sizeInKB = size;
    }
}
