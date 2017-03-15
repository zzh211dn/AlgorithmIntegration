package algorithm;
import algorithm.PLSpackage.PLS_method;
import algorithm.Regression.LinerRegression;
import algorithm.Regression.Residuals;
import javafx.stage.Stage;
import la.matrix.DenseMatrix;
import la.matrix.Matrix;

import java.io.IOException;
import java.util.*;

import static algorithm.PLSpackage.helpers.predict;


/**
 * Created by zzh on 2016/12/7.
 */
public class algorithmAPI {
    public Double[][] result ;
    public String Error = "";
    public String accuracy = "";
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
        Double[][] trianResult,testResult,validationResult;
        SVM3 svm = new SVM3();
        svm.trainSVM(trainPath,SVMtype,kenelType,  c, g,k);
        String  trainTest = trainPath+"\\trainFile";
        trianResult = svm.computeSVM(trainTest,1,trainPath+"\\trainResultFile");
        accuracy = svm.accuracy+"";
        Error = "模型训练准确率"+svm.accuracy+"\n"+"========================================"+"\n"+svm.crossAccuary+"\n"+"========================================";
        String testPath = trainPath+"\\testFile" ;
        String validationPath = trainPath+"\\" +
                "" +
                "" +
                "validationFile" ;
        testResult = svm.computeSVM(testPath,1,trainPath+"\\testResultFile");
        validationResult = svm.computeSVM(validationPath,2,trainPath+"\\validationResultFile");
        //Error = svm.getError();
        result.add(0,trianResult);
        result.add(1,testResult);
        result.add(2,validationResult);
        return result;
    }

    public Double[][] getPCAResult(Double[][] data,int r,int type){
        Matrix R;
        double[][] ddata = new double[data.length][data[0].length];
        for(int i=0;i<data.length;i++)
            for(int j=0;j<data[i].length;j++){
                if(data[i][j]==null)
                    System.out.println("i:"+i+"j:"+j);
                ddata[i][j] = data[i][j];
            }
        PCA pca =  new PCA(r);
        la.matrix.Matrix X = new DenseMatrix(ddata);
        if(type==1)
            R = pca.run(X, r);
        else
            R = pca.generate(X);
        Double[][] result = new Double[R.getData().length][R.getData()[0].length];
        for(int i=0;i<R.getData().length;i++)
            for(int j=0;j<R.getData()[i].length;j++){
                result[i][j] = R.getData()[i][j];
            }
        return result;
    }

    public Double[][] getResiduals(Double[][] data,Double[][] lable)
    {
        LinerRegression linerRegression = new LinerRegression();

        double[][] pridictLabel = linerRegression.getLinerRegression(Double2double(data),Double2double(lable));
        Residuals residuals  = new Residuals();
        Double[][] residualsResult = residuals.residuals(data,lable,double2Double(pridictLabel));
        return residualsResult;
    }



    /**
     * XLoading 值
     * @param var11
     */
    public void getXLoading( Matrix[] var11 )
    {
        int vectorCol = var11[0].getColumnDimension();
        int vectorRow = var11[0].getRowDimension();
        int lamdaCol =  var11[1].getColumnDimension();
        int lamdaRow = var11[1].getRowDimension();
        double[][] oneXloding = new double[vectorRow][2];
        double[][] twoXloding = new double[vectorRow][2];
        for(int i = 0;i<vectorRow;i++)
        {
            oneXloding[i][0] = i+1;
            System.out.println( " var11[0].getData()[i][0]"+var11[0].getData()[i][0]);
            twoXloding[i][0] = var11[0].getData()[i][0]*Math.sqrt(var11[1].getData()[0][0]);
        }

        for(int i = 0;i<vectorRow;i++)
        {
            oneXloding[i][1] = var11[0].getData()[i][0]*Math.sqrt(var11[1].getData()[0][0]);
            twoXloding[i][1] = var11[0].getData()[i][1]*Math.sqrt(var11[1].getData()[1][1]);
        }

        Double[][] oneXloding1 = double2Double(oneXloding);
        Double[][] twoXloding1 = double2Double(twoXloding);
        ArrayList oneXlodingAL = new ArrayList();
        ArrayList twoXlodingAL = new ArrayList();
        oneXlodingAL.add(oneXloding1);
        twoXlodingAL.add(twoXloding1);


        PictureAPI pictureAPI = new PictureAPI();
        try {
            Stage chartStage = new Stage();
            chartStage.setScene(pictureAPI.getChatingResult(oneXlodingAL,new String[]{"oneXLoading"},new String[]{"PCA1",""}));
            chartStage.show();

            Stage chartStage2 = new Stage();
            chartStage2.setScene(pictureAPI.getScatter2DResult(twoXlodingAL,new String[]{"twoXLoading"},new String[]{"PCA1","PCA2"}));
            chartStage2.show();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     *  计算分高归一化
     *  输入为读取的文件
     */
    public boolean getFenGaoNormalization(ArrayList<Double[][]> datas1,String dirName,String[] fileListName)
    {
        FenGaoNormalization fenGaoNormalization = new FenGaoNormalization();
        boolean flag = true;
        for(int i = 0;i<datas1.size();i++)
        {
            flag = fenGaoNormalization.fengaoNormalize(datas1.get(i),dirName,fileListName[i]);
            if (!flag)
                return false;
        }
        return true;
    }



    /**
     *  返回类型 key = [1.0, 2.0, 3.0, 3.0] String ；value = type 即聚类类别
     */
    public Double[] getKemeansResult(Double[][] data, int k ) {
        Kmeans callKmeans = new Kmeans();
        return callKmeans.computeKmeans(Double2double(data),k);
    }

    /**
     *  用pls降维
     */
    public  HashMap<String,Double[][]> getPLSDimReductionResult(Double[][] trainX,Double[][] trainY,int factors,Double[][] testX,Double[][] validationX){
        HashMap<String,Double[][]> result = new HashMap<>();
        PLS_method pls = new PLS_method(Double2double(trainX),Double2double(trainY),factors);
        Jama.Matrix x = new Jama.Matrix(Double2double(trainX));
        Jama.Matrix w = new Jama.Matrix(pls.getW());

        double[][] t = pls.getT();

        System.out.println("t:");
        for(int i =0;i<t.length;i++) {
            for (int j = 0; j < t[0].length; j++) {
                System.out.print(t[i][j]+"  ");
            }
            System.out.println();
        }

        double[][] W = pls.getW();

        System.out.println("w:");
        for(int i =0;i<W.length;i++) {
            for (int j = 0; j < W[0].length; j++) {
                System.out.print(W[i][j]+"  ");
            }
            System.out.println();
        }


        result.put("train",double2Double(x.times(w).getArray()));
        if(testX!=null)
        {
            Jama.Matrix testx = new Jama.Matrix(Double2double(testX));
            result.put("test",double2Double(testx.times(w).getArray()));
        }
        if(validationX!=null)
        {
            Jama.Matrix validationx = new Jama.Matrix(Double2double(validationX));
            result.put("validation",double2Double(validationx.times(w).getArray()));
        }
        return result;
    }

    /**
     *  用pls预测
     */
    public  HashMap<String,Double[][]> getPLSforestResult(Double[][] trainX,Double[][] trainY,Double[][] testX,Double[][] validationX,Double[][] validationY){
        HashMap<String,Double[][]> result = new HashMap<>();
        PLS_method pls = new PLS_method(Double2double(trainX),Double2double(trainY),trainX[0].length);
        double[][] trainPrediction = predict(pls, Double2double(trainX));
        result.put("train", double2Double(trainPrediction));

        double[][] meanPercentError = new double[2][1];//meanPercentError[0][0]  = trainmeanPercentError;meanPercentError[1][0] =  validationmeanPercentError

        for(int i=0; i < trainX.length; i++){
            double percentError = (Math.abs(trainPrediction[i][0]-trainY[i][0])/trainY[i][0] * 100);
            meanPercentError[0][0] += Math.abs(percentError);
        }
        meanPercentError[0][0] = meanPercentError[0][0]/trainX.length;
        meanPercentError[1][0] = -1;//代表没有数据

        if(testX!=null)
        {
            double[][] testPrediction = predict(pls, Double2double(testX));
            result.put("test",double2Double(testPrediction));
        }
        if(validationX!=null)
        {
            double[][]  validationxPrediction = predict(pls, Double2double(validationX));
            result.put("validation",double2Double(validationxPrediction));
            meanPercentError[1][0] = 0;
            for(int i=0; i < validationX.length; i++){
                double percentError = (Math.abs(validationxPrediction[i][0]-validationY[i][0])/validationY[i][0] * 100);
                meanPercentError[1][0] += Math.abs(percentError);
                //System.out.format("%5d|%11f|%13f|%15f\n", i, trainX[i][0], trainPrediction[i][0], trainMeanpercentError);
            }
            meanPercentError[1][0] = meanPercentError[1][0]/validationX.length;
        }
        result.put("trainMeanpercentError",double2Double(meanPercentError));
        return result;
    }


    /**
     * bpnn算法
     * @param  trainData 训练集；
     * @param  lable  训练集标签
     * @param  testData       测试集
     * @param  hiddenLayer    隐藏层，默认40
     * @param  iterateTimes    迭代次数，默认1000；
     */
    public ArrayList<Double[][]> getBPNNResult(Double[][] trainData,Double[][] lable,Double[][] testData,Double[][] validationData,int hiddenLayer,int iterateTimes)
    {
        ArrayList result = new ArrayList<Double[][]>();
        Double[][] trianResult,testResult,validationResult;
        BPNN bpnn = new BPNN();
        bpnn.trainBPNN(Double2double(trainData),Double2double(lable),hiddenLayer,iterateTimes);
        trianResult = bpnn.computeBPNN(Double2double(trainData));
        testResult = bpnn.computeBPNN(Double2double(testData));
        validationResult = bpnn.computeBPNN(Double2double(validationData));
        Error = bpnn.getError();
        result.add(0,trianResult);
        result.add(1,testResult);
        result.add(2,validationResult);
        return result;
    }
    /**
     * KNN算法
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
