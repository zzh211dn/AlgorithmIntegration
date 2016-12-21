package model;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * 文件读写类
 */
public final class FileAction {



    /**
     * 读csv和txt文件方法
     *
     * @param file     文件
     * @param fileData 数据存储Map
     * @throws IOException
     */
    public void readCSVData(File file, HashMap<String, List<String[]>> fileData, String dirName) throws IOException {
        BufferedReader read = new BufferedReader(new FileReader(file));
        String line;
        String[] name = {"", dirName};
        List<String[]> dataList = new ArrayList<>();
        dataList.add(name);
        while ((line = read.readLine()) != null) {
            String[] subLine = line.split(",");

            dataList.add(subLine);
        }
        fileData.put(file.getName(), dataList);
    }

    /**
     * 读取zyz格式文件方法
     * @param file
     * @return
     * @throws IOException
     */
    public List<String[]> readZYZData(File file) throws IOException {
        List<String[]> zyzData = new LinkedList<>();
        BufferedReader read = new BufferedReader(new FileReader(file));
        String line;
        while ((line = read.readLine()) != null) {
            String[] subLine = line.split(",");
            zyzData.add(subLine);
        }
        return zyzData;
    }
    public ArrayList<Double[][]>openPCAFile(File file) throws IOException {
        ArrayList<Double[][]> picList = new ArrayList<>();
        BufferedReader read = new BufferedReader(new FileReader(file));
        String line;
        while ((line = read.readLine()) != null) {
            String[] subLine = line.split(",");
          //  picList.add(subLine);
        }


        return picList;
    }

    /**
     * 写文件方法
     *
     * @param file      写入文件
     * @param tableData 写入数据
     */
    public void saveData(File file, String[][] tableData) {

        try {
            FileWriter out = new FileWriter(file, false);
            for (int i = 0; i < tableData.length; i++) {
                for (int j = 0; j < tableData[i].length; j++) {
                    if (tableData[i][j] != null) {
                        out.write((tableData[i][j]));
                    } else
                        out.write((""));
                    if (j != tableData[i].length - 1)
                        out.write((","));
                }
                if (i != tableData.length - 1)
                    out.write(System.getProperty("line.separator"));
            }

            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}