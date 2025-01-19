package com.chabiamin.dicomalbumsmanager.Model;

import java.io.File;
import java.util.ArrayList;

public class DicomAlbum {

    private final String name ;
    private final String Description  ;

    private final String creationDate ;

    private final ArrayList<DicomData> dicomDataArrayList ;

    private final File albumFile;



    public DicomAlbum(String name, String description, String creationDate, ArrayList<DicomData> dicomDataArrayList, File albumFile) {
        this.name = name;
        Description = description;
        this.creationDate = creationDate;
        this.dicomDataArrayList = dicomDataArrayList;
        this.albumFile = albumFile;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return Description;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public File getAlbumFile() {
        return albumFile;
    }

    public ArrayList<DicomData> getDicomDataArrayList() {
        return dicomDataArrayList;
    }

    @Override
    public String toString() {
        return "DicomAlbum{" +
                "name='" + name + '\'' +
                ", Description='" + Description + '\'' +
                ", creationDate='" + creationDate + '\'' +
                ", albumFile=" + albumFile +
                '}';
    }
}
