package com.chabiamin.dicomalbumsmanager.Model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.io.File;

public class DicomData {

    private final BooleanProperty isSelected;
    private final String fileName;
    private final String patientId;
    private final String modality;
    private final String studyDate;
    private final File file;
    public DicomData(boolean isSelected, String fileName, String patientId, String modality, String studyDate, File file) {
        this.isSelected = new SimpleBooleanProperty(isSelected);
        this.fileName = fileName;
        this.patientId = patientId;
        this.modality = modality;
        this.studyDate = studyDate;
        this.file = file;
    }

    public boolean isIsSelected() {
        return isSelected.get();
    }

    public BooleanProperty isSelectedProperty() {
        return isSelected;
    }

    public File getFile() {
        return file;
    }

    public BooleanProperty isselectedProperty() {
        return isSelected;
    }

    public boolean isSelected() {
        return isSelected.get();
    }

    public void setSelected(boolean selected) {
        this.isSelected.set(selected);
    }

    public String getFileName() {
        return fileName;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getModality() {
        return modality;
    }

    public String getStudyDate() {
        return studyDate;
    }

    public boolean isSelected(Object o) {
        return isSelected.get();

    }

    @Override
    public String toString() {
        return "DicomData{" +
                "isSelected=" + isSelected +
                ", fileName='" + fileName + '\'' +
                ", patientId='" + patientId + '\'' +
                ", modality='" + modality + '\'' +
                ", studyDate='" + studyDate + '\'' +
                '}';
    }
}
