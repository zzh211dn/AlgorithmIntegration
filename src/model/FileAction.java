package model;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 文件读写类
 */
public final class FileAction  {


    /**
     * 读文件方法
     * @param file 文件
     * @param fileData 数据存储Map
     * @throws IOException
     */
    public void readData(File file,HashMap<String,List<String[]>> fileData,String dirName) throws IOException {
        BufferedReader read = new BufferedReader(new FileReader(file));
        String line;
        String[] name = {"",dirName};
        List<String[]> dataList = new ArrayList<>();
        dataList.add(name);
        while((line=read.readLine())!=null){
            String[] subLine = line.split(",");
            dataList.add(subLine);
        }
        fileData.put(file.getName(),dataList);
    }

    public void saveData(File file,String[][] tableDta){



    }

}