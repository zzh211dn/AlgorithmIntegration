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

    public Double[][] getSVMResult(Double[][] trainData,Double[][] trainlabel,int svm_type,int kener_type ,Double[][] testData)
    {
        SVM svm = new SVM();
        svm.trainSVM(Double2double(trainData),Double2double(trainlabel),svm_type,kener_type);
        result = svm.computeSVM((Double2double(testData)));
        return result;
    }


    public Double[][] getPCAResult(Double[][] data) {
        PCA callPCA = new PCA();
        result = double2Double(callPCA.computePCA(Double2double(data)).getArray());
        return result;
    }

    /**
     *  杩斿洖 key = [1.0, 2.0, 3.0, 3.0] String 绫诲瀷锛寁alue = type
     */
    public TreeMap<String,Integer> getKemeansResult(Double[][] data,int k ) {
        Kmeans callKmeans = new Kmeans();
        return callKmeans.computeKmeans(Double2double(data),k);
    }

    /**
     * bpnn鐩存帴浼犲叆鐗瑰緛闆嗭紝label锛屾祴璇曢泦锛岄殣钘忓眰鏁帮紝杩唬娆℃暟銆?
     * 杩斿洖棰勬祴鍒嗙被缁撴灉
     */
    public Double[][] getBPNNResult(Double[][] trainData,Double[][] lable,Double[][] testData,int hiddenLayer,int iterateTimes)
    {
        BPNN bpnn = new BPNN();
        bpnn.trainBPNN(Double2double(trainData),Double2double(lable),hiddenLayer,iterateTimes);
        result = bpnn.computeBPNN(Double2double(testData));
        return result;
    }



}
