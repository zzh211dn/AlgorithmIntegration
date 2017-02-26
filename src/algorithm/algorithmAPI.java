package algorithm;
import algorithm.PLSpackage.PLS_method;
import la.matrix.DenseMatrix;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.TreeMap;


/**
 * Created by zzh on 2016/12/7.
 */
public class algorithmAPI {
    public Double[][] result ;
    public String Error = "";
    public Double[][] double2Double(double[][] doubleData)
    {
        Double[][] result = new Double[doubleData.length][doubleData[0].length];
        for(int i = 0;i<result.length;i++) {
            for (int j = 0; j < result[0].length; j++) {

                result[i][j] = Double.parseDouble(String.format("%.4f", doubleData[i][j]));
//                System.out.print(result[i][j] + " ");
            }
        }
        return result;
    }

    public double[][] Double2double(Double[][] doubleData)
    {
        double[][] result = new double[doubleData.length][doubleData[0].length];
        for(int i = 0;i<result.length;i++) {
            for (int j = 0; j < result[0].length; j++) {
//                System.out.print(doubleData[i][j]+ " ");
                result[i][j] = Double.parseDouble(String.format("%.4f", doubleData[i][j]));
              //  System.out.print(result[i][j] + " ");
            }
        }
        return result;
    }

    public ArrayList<Double[][]> getSVMResult(String trainPath, int SVMtype, int kenelType, String c, String g,String k) throws IOException {
        ArrayList result = new ArrayList<Double[][]>();
        Double[][] trianResult,testResult;
        SVM3 svm = new SVM3();
        svm.trainSVM(trainPath,SVMtype,kenelType,  c, g,k);
        String  trainTest = trainPath+"\\trainFile";
        trianResult = svm.computeSVM(trainTest,1);
        Error = "模型训练准确率"+svm.accuracy+"\n"+"========================================"+"\n"+svm.crossAccuary+"\n"+"========================================";
        String testPath = trainPath+"\\testFile" ;
        testResult = svm.computeSVM(testPath,2);
        //Error = svm.getError();
        result.add(0,trianResult);
        result.add(1,testResult);
        return result;
    }

    public Double[][] getPCAResult(Double[][] data,int r){
        double[][] ddata = new double[data.length][data[0].length];
        for(int i=0;i<data.length;i++)
            for(int j=0;j<data[i].length;j++){
                ddata[i][j] = data[i][j];
            }

        la.matrix.Matrix X = new DenseMatrix(ddata);
        la.matrix.Matrix R = ml.subspace.PCA.run(X, r);
        Double[][] result = new Double[R.getData().length][R.getData()[0].length];
        for(int i=0;i<R.getData().length;i++)
            for(int j=0;j<R.getData()[i].length;j++){
                result[i][j] = R.getData()[i][j];
            }
        return result;
    }


    /**
     *  返回类型 key = [1.0, 2.0, 3.0, 3.0] String ；value = type 即聚类类别
     */
    public Double[] getKemeansResult(Double[][] data, int k ) {
        Kmeans callKmeans = new Kmeans();
        return callKmeans.computeKmeans(Double2double(data),k);
    }


    public Double[][] getPLSResult(Double[][] X,Double[][] Y,int factors){
        PLS_method pls = new PLS_method(Double2double(X),Double2double(Y),X[0].length-Y[0].length);
        return double2Double(pls.getW());
    }

    /**
     * bpnn算法
     * @param  trainData 训练集；
     * @param  lable  训练集标签
     * @param  testData       测试集
     * @param  hiddenLayer    隐藏层，默认40
     * @param  iterateTimes    迭代次数，默认1000；
     */
    public ArrayList<Double[][]> getBPNNResult(Double[][] trainData,Double[][] lable,Double[][] testData,int hiddenLayer,int iterateTimes)
    {
        ArrayList result = new ArrayList<Double[][]>();
        Double[][] trianResult,testResult;
        BPNN bpnn = new BPNN();
        bpnn.trainBPNN(Double2double(trainData),Double2double(lable),hiddenLayer,iterateTimes);
        trianResult = bpnn.computeBPNN(Double2double(trainData));
        testResult = bpnn.computeBPNN(Double2double(testData));
        Error = bpnn.getError();
        result.add(0,trianResult);
        result.add(1,testResult);
        return result;
    }
    /**
     * bpnn算法
     * @param  datas 训练集； 以行为文件单位
     * @param  Label  训练集标签   格式为 Label[i][0],只有一列
     * @param  testDatas   测试集 以行为文件单位
     * @param  k  距离
     * @return  Double[i][0] 只有一列，每一行代表一个文件的结果
     */
    public Double[][] getKNNResult(Double[][] datas, Double[][] testDatas, Double[][] Label, int k)
    {
        result = new Double[testDatas.length][1];
        KNN knn = new KNN();
        ArrayList<ArrayList<Double>> trainData = knn.Double2List(datas);
        ArrayList<String> trainLabel = knn.Double2StringList(Label);
        ArrayList<ArrayList<Double>> testData = knn.Double2List(testDatas);
        for(int i = 0;i<testData.size();i++)
        {
            result[i][0] = Double.valueOf(knn.knn(trainData,testData.get(i),trainLabel,k));
        }
        return result;
    }

}
