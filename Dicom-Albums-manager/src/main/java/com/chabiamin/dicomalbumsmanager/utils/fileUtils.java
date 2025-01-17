package com.chabiamin.dicomalbumsmanager.utils;

import com.chabiamin.dicomalbumsmanager.Model.DicomData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.dcm4che3.io.DicomInputStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.chabiamin.dicomalbumsmanager.Controller.fileManagement.dicomDataList;

public class fileUtils {


    public static byte[] readFile(String filePath) throws IOException {
        try (FileInputStream inputStream = new FileInputStream(filePath)) {
            long fileSize = inputStream.getChannel().size();
            byte[] fileBytes = new byte[(int) fileSize];
            inputStream.read(fileBytes);
            return fileBytes;
        }
    }
    public static List<DicomData> readDicomeFile(String filePath) throws IOException {
        /*List<DicomData> dicomDataList = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(new File(filePath));
             DicomInputStream dis = new DicomInputStream(fis)) {

            Attributes dataset = dis.readDataset(-1, -1);

            dataset.forEach((tag, vr, value) -> {
                String tagString = tag.toString();
                String valueString = value.toString();
                dicomDataList.add(new DicomData());
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

        return dicomDataList;*/
        return null;
    }
    public static ObservableList<DicomData> fetchDicomData(String directoryPath) {
        List<DicomData> dataList = new ArrayList<>();

        try {
            Files.walk(Paths.get(directoryPath))
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().toLowerCase().endsWith(".dcm"))
                    .forEach(path -> {
                        try {
                            File dicomFile = path.toFile();
                            DicomInputStream dis = new DicomInputStream(dicomFile);
                            org.dcm4che3.data.Attributes attributes = dis.readDataset(-1, -1);

                            String patientId = attributes.getString(0x00100020); // Patient ID
                            String studyDate = attributes.getString(0x00080020); // Study Date
                            String BirthDate = attributes.getString(0x00100030); // Birth Date
                            String Sex = attributes.getString(0x00100040); // SEX
                            String Age = attributes.getString(0x00101010); // Age at the time of study
                            String EthnicGroup = attributes.getString(0x00102160); // Study Date
                            String modality = attributes.getString(0x00080060);  // Modality
                            String protocolName = attributes.getString(0x00181030); // Study Date
                            /*attributes we will use them later on */
                            dataList.add(new DicomData(false, dicomFile.getName(), patientId, modality, studyDate, dicomFile));

                            dis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(dicomDataList!=null)dicomDataList.clear();
        dicomDataList.addAll(dataList);
        return FXCollections.observableArrayList(dataList);
    }
    public static ArrayList<DicomData> filterDicomData(
            ArrayList<DicomData> dicomDataArrayList ,
            String patientId ,
            String modality,
            LocalDate studyDate,
            String age,
            LocalDate birthDate,
            String studyInstance
            ){

        ArrayList<DicomData> filtredList = new ArrayList<>();

        for(DicomData file : dicomDataArrayList){
            if ((patientId == null || patientId.isEmpty() || patientId.equals(file.getPatientId())) &&
                    (modality == null || modality.isEmpty() || modality.equals(file.getModality())) &&
                    (studyDate == null || studyDate.toString().equals(file.getStudyDate()))) {
                filtredList.add(file);
                System.out.println("file is accepted , will be putted in list ");
                System.out.println(file.toString());
            }else{
                System.out.println("not selected ");
                System.out.println(file.toString());
            }
        }
        return filtredList;
    }


    public static File[] getAvailableDirectories(String directory) {
        // Here, we return a list of directories within a specific folder (e.g., user home or album folder)

        File homeDir = new File(directory);

        // Check if the directory exists and is indeed a directory
        if (homeDir.exists() && homeDir.isDirectory()) {
            // List all files in the directory
            return homeDir.listFiles(File::isDirectory);

        } else {
            System.out.println("The provided path is not a valid directory.");
            return new File[0];
        }


    }
/*
    public static void openDirectoryChooser(Stage stage) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Choose Album Directory");
        File selectedDirectory = directoryChooser.showDialog(stage);

        if (selectedDirectory != null) {
            directoryPathField.setText(selectedDirectory.getAbsolutePath());
        }
    }*/



    public static ArrayList<File> getAlbumsWithMetadata(File selectedDirectoryy) {
        ArrayList<File> albumsWithMetadata = new ArrayList<>();
        //File rootDir = new File(rootDirectory);

        // Check if the directory exists and is a directory
        if (selectedDirectoryy.exists() && selectedDirectoryy.isDirectory()) {
            // List all directories inside the root directory
            File[] allFiles = selectedDirectoryy.listFiles(File::isDirectory);

            // Iterate over all directories
            if (allFiles != null) {
                for (File album : allFiles) {
                    // Check if metadata.json exists in the album directory
                    File metadataFile = new File(album, "metadata.json");
                    if (metadataFile.exists() && metadataFile.isFile()) {
                        albumsWithMetadata.add(album);  // Add to list if metadata.json is found
                    }
                }
            }
        }
        return albumsWithMetadata;
    }
}
