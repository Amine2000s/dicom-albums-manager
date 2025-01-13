package com.chabiamin.dicomalbumsmanager.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class fileUtils {


    public static byte[] readFiles(String filePath) throws FileNotFoundException {

        List<String> lines = new ArrayList<>();
        try(FileInputStream inputStream = new FileInputStream(new File(filePath))){

            long fileSize = inputStream.getChannel().size();
            byte[] fileBytes = new byte[(int)fileSize];

            inputStream.read(fileBytes);

            return fileBytes;
            
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

}
