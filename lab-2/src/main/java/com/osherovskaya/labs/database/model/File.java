package com.osherovskaya.labs.database.model;

public class File {
    private int ID;
    private String filename;
    private int sizeInKB;

    public File(int id, String name, String size) {
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
