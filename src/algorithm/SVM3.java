package algorithm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class SVM3 {

    /**
     * JAVA test code for LibSVM
     *
     * @author yangliu
     * @throws IOException
     * @blog http://blog.csdn.net/yangliuy
     * @mail yang.liu@pku.edu.cn
     */
    String modelFile = null;

    public Double getAccuracy() {
        return accuracy;
    }

    String tPath;
    Double accuracy ;
    String crossAccuary;
    public void trainSVM(String trainPath, int SVMtype, int kenelType, String c, String g,String k) throws IOException {
        tPath = trainPath;
        String[] crossValidationTrainArgs = { "-v",k,"-s", SVMtype-1+"", "-t", kenelType-1+"", "-n", "0.01", "-c", c, "-g", g, trainPath+"\\trainFile"};
        modelFile = svm_train.main(crossValidationTrainArgs);
        crossAccuary = svm_train.getCrossaccuracy();
        String[] trainArgs={"-s", SVMtype-1+"", "-t", kenelType-1+"", "-n", "0.005", "-c", c, "-g", g, trainPath+"\\trainFile"};
        modelFile= svm_train.main(trainArgs);
    }

    public Double[][] computeSVM(String path,int type) throws IOException {
        String[] testArgs = {path, modelFile, "E:\\新建文件夹 (2)\\train-result1"};
        accuracy = svm_predict.main(testArgs);
        BufferedReader read = new BufferedReader(new FileReader( "E:\\新建文件夹 (2)\\train-result1"));
        String line  ="";
        ArrayList<Double> tempResult = new ArrayList<>();
        while ((line=read.readLine())!=null){
            tempResult.add(Double.valueOf(line));
        }
        read.close();
        Double[][] result = new Double[tempResult.size()][1];
        for(int i=0;i<tempResult.size();i++){
            result[i][0] = tempResult.get(i);
        }
        File mFile = new File(modelFile);
        File rFile = new File( "E:\\新建文件夹 (2)\\train-result1");
        if(type==2)
        mFile.delete();
        rFile.delete();
        return result;
    }
}