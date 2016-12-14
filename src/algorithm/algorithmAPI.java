package algorithm;
import java.util.TreeMap;


/**
 * Created by zzh on 2016/12/7.
 */
public class algorithmAPI {
    public Double[][] result ;

    public Double[][] double2Double(double[][] doubleData)
    {
        Double[][] result = new Double[doubleData.length][doubleData[0].length];
        for(int i = 0;i<result.length;i++) {
            for (int j = 0; j < result[0].length; j++) {

                result[i][j] = Double.parseDouble(String.format("%.4f", doubleData[i][j]));
                System.out.print(result[i][j] + " ");
            }
        }
        return result;
    }

    public double[][] Double2double(Double[][] doubleData)
    {
        double[][] result = new double[doubleData.length][doubleData[0].length];
        for(int i = 0;i<result.length;i++) {
            for (int j = 0; j < result[0].length; j++) {

                result[i][j] = Double.parseDouble(String.format("%.4f", doubleData[i][j]));
                System.out.print(result[i][j] + " ");
            }
        }
        return result;
    }

    public Double[][] getSVM(Double[][] data)
    {
       return null;
    }

    public Double[][] getPCAResult(Double[][] data) {
        PCA callPCA = new PCA();
        result = double2Double(callPCA.computePCA(Double2double(data)).getArray());
        return result;
    }

    /*
    *  返回 key = [1.0, 2.0, 3.0, 3.0] String 类型，value = type
    */
    public TreeMap<String,Integer> getKemeansResult(Double[][] data,int k ) {
        Kmeans callKmeans = new Kmeans();
        return callKmeans.computeKmeans(Double2double(data),k);
    }
    //bpnn直接传入特征集，label，测试集，
    public Double[][] getBPNN(Double[][] feature,Double[][] lable,Double[][] pridictDataSet,int hiddenLayer,int iterateTimes)
    {
        BPNN bpnn = new BPNN();
        bpnn.trainBPNN(Double2double(feature),Double2double(lable),hiddenLayer,iterateTimes);
        result = bpnn.computeBPNN(Double2double(pridictDataSet));
        return result;
    }


}
