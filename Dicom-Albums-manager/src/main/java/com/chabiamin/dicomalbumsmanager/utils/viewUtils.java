package com.chabiamin.dicomalbumsmanager.utils;

import javafx.scene.control.ListView;

import java.io.File;
import java.util.ArrayList;

import static com.chabiamin.dicomalbumsmanager.utils.fileUtils.getAlbumsWithMetadata;

public class  viewUtils {


    public static void refreshListView(ListView list , File directory){
        ArrayList<File> fileList = getAlbumsWithMetadata(directory);
        if(list.getItems()!=null){
            list.getItems().clear();
        }
        list.getItems().addAll(fileList);
        }
}
