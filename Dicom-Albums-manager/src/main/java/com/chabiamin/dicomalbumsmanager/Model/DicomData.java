package com.chabiamin.dicomalbumsmanager.Model;

public class DicomData {

    private final String fileName;
    private final String patientId;
    private final String modality;
    private final String studyDate;

    public DicomData(String fileName, String patientId, String modality, String studyDate) {
        this.fileName = fileName;
        this.patientId = patientId;
        this.modality = modality;
        this.studyDate = studyDate;
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
}
