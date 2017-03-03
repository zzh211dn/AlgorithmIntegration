package model;

import java.io.*;
import java.util.*;

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
    public void readCSVData(File file, LinkedHashMap<String, List<String[]>> fileData, String dirName) throws IOException {
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
    public  List<String[]>readZYZData(File file) throws IOException {
        List<String[]> zyzData = new LinkedList<>();
        BufferedReader read = new BufferedReader(new FileReader(file));
        String line;
        while ((line = read.readLine()) != null) {
            String[] subLine = line.split(",");
            zyzData.add(subLine);
        }
        return zyzData;
    }

    /**
     * 读取PCA结果文件
     * @param file
     * @return
     * @throws IOException
     */
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

    public void wirteTempSVM(Double[][] trainData,Double[][] label,Double[][] testData,Double[][] validationData,File file){
        try {
            File testFile = new File(file.getPath()+"\\testFile");
            File trainFile = new File(file.getPath()+"\\trainFile");
            File validationFile = new File(file.getPath()+"\\validationFile");
            FileWriter trainOut = new FileWriter(trainFile, false);
            for (int i = 0; i < trainData.length; i++) {
                trainOut.write(label[i][0]+" ");
                for (int j = 0; j < trainData[i].length; j++) {
                    trainOut.write(j+1+":"+trainData[i][j]);
                    if(j!=trainData[i].length-1)
                        trainOut.write(" ");
                    else
                        trainOut.write(System.getProperty("line.separator"));
                }
            }
            trainOut.close();

            FileWriter testOut = new FileWriter(testFile, false);
            for (int i = 0; i < testData.length; i++) {
                testOut.write(i+" ");
                for (int j = 0; j <testData[i].length; j++) {
                    testOut.write(j+1+":"+testData[i][j]);
                    if(j!=testData[i].length-1)
                        testOut.write(" ");
                    else
                        testOut.write(System.getProperty("line.separator"));
                }
            }
            testOut.close();

            FileWriter validationOut = new FileWriter(validationFile, false);
            for (int i = 0; i < validationData.length; i++) {
                validationOut.write(i+" ");
                for (int j = 0; j <validationData[i].length; j++) {
                    validationOut.write(j+1+":"+validationData[i][j]);
                    if(j!=validationData[i].length-1)
                        validationOut.write(" ");
                    else
                        validationOut.write(System.getProperty("line.separator"));
                }
            }
            validationOut.close();


        }catch (Exception e){
            e.printStackTrace();
        }

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