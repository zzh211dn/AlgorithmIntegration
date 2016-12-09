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

public final class FileAction extends Application {




    private HashMap<String,List<String[]>> fileData =  new HashMap<>();

    @Override
    public void start(Stage stage) {


        final FileChooser fileChooser = new FileChooser();
        configureFileChooser(fileChooser);
        List<File> fileList = fileChooser.showOpenMultipleDialog(stage);
        for(int i=0;i<fileList.size();i++){
            try {
                readData(fileList.get(i));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println(fileData.size());

        //  stage.setScene(new Scene(rootGroup));
        //   stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

    private static void configureFileChooser(
            final FileChooser fileChooser) {
        fileChooser.setTitle("文件选择");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("csv格式", "*.csv"),
                new FileChooser.ExtensionFilter("txt格式", "*.txt")
        );
    }


    private void readData(File file) throws IOException {
        BufferedReader read = new BufferedReader(new FileReader(file));
        read.read();
        String line;
        List<String[]> dataList = new ArrayList<>();
        while((line=read.readLine())!=null){
            System.out.println(line);
            String[] subLine = line.split(",");
            System.out.println(subLine[0]);
            dataList.add(subLine);
        }
        fileData.put(file.getName(),dataList);
    }

    public HashMap<String, List<String[]>> getFileData() {
        return fileData;
    }
}