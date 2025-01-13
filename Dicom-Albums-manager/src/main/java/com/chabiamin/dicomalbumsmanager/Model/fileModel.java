package com.chabiamin.dicomalbumsmanager.Model;

public class fileModel {

    private String FileName;
    private String PatientID;
    private String Modality;
    private String StudyDate;

    public fileModel(String fileName, String patientID, String modality, String studyDate) {
        FileName = fileName;
        PatientID = patientID;
        Modality = modality;
        StudyDate = studyDate;
    }

    public String getFileName() {
        return FileName;
    }

    public void setFileName(String fileName) {
        FileName = fileName;
    }

    public String getPatientID() {
        return PatientID;
    }

    public void setPatientID(String patientID) {
        PatientID = patientID;
    }

    public String getModality() {
        return Modality;
    }

    public void setModality(String modality) {
        Modality = modality;
    }

    public String getStudyDate() {
        return StudyDate;
    }

    public void setStudyDate(String studyDate) {
        StudyDate = studyDate;
    }

    @Override
    public String toString() {
        return "fileModel{" +
                "FileName='" + FileName + '\'' +
                ", PatientID='" + PatientID + '\'' +
                ", Modality='" + Modality + '\'' +
                ", StudyDate='" + StudyDate + '\'' +
                '}';
    }
}
