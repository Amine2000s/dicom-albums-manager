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
import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;
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
    }
    private ObservableList<DicomData> fetchDicomData(String directoryPath) {
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
                            String modality = attributes.getString(0x00080060);  // Modality
                            String studyDate = attributes.getString(0x00080020); // Study Date

                            dataList.add(new DicomData(dicomFile.getName(), patientId, modality, studyDate));

                            dis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return FXCollections.observableArrayList(dataList);
    }

}
