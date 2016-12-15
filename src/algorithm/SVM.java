package algorithm;

import org.encog.Encog;
import org.encog.ml.data.MLData;
import org.encog.ml.data.MLDataPair;
import org.encog.ml.data.MLDataSet;
import org.encog.ml.data.basic.BasicMLDataSet;
import org.encog.ml.svm.KernelType;
import org.encog.ml.svm.SVMType;
import org.encog.ml.svm.training.SVMTrain;
import org.encog.neural.NeuralNetworkError;

import java.util.ArrayList;

/**
 * Created by fish123 on 2016/12/14.
 */
public class SVM {
    org.encog.ml.svm.SVM svm;
    public void trainSVM(double[][] trainData,double[][] trainlabel,int SVMtype,int kenelType){
        org.encog.ml.svm.SVMType svm_type;
        switch(SVMtype) {
            case 1:
                svm_type = SVMType.NewSupportVectorRegression;
                break;
            case 2:
                svm_type = SVMType.NewSupportVectorClassification;
                break;
            case 3:
                svm_type = SVMType.SupportVectorOneClass;
                break;
            case 4:
                svm_type = SVMType.EpsilonSupportVectorRegression;
                break;
            case 5:
                svm_type = SVMType.NewSupportVectorRegression;
                break;
            default:
                throw new NeuralNetworkError("Invalid svm type");
        }
        org.encog.ml.svm.KernelType kernel_type;
        switch(kenelType) {
            case 1:
                kernel_type = KernelType.Linear;
                break;
            case 2:
                kernel_type = KernelType.Poly;
                break;
            case 3:
                kernel_type = KernelType.RadialBasisFunction;
                break;
            case 4:
                kernel_type = KernelType.Sigmoid;
                break;
            case 5:
                kernel_type = KernelType.Precomputed;
                break;
            default:
                throw new NeuralNetworkError("Invalid svm type");
        }

        svm = new org.encog.ml.svm.SVM(trainData[0].length,svm_type, kernel_type);

        // create training data
        MLDataSet trainingSet = new BasicMLDataSet(trainData,trainlabel);

        // train the SVM
        final SVMTrain train = new SVMTrain(svm, trainingSet);
        train.iteration();
        train.finishTraining();
    }


    public Double[][] computeSVM(double[][] testData)
    {
        double[][] dataTotest = new double[testData.length][testData[0].length];
        double[][] TLabel = new double[testData.length][1];
        for(int i=0;i<dataTotest.length;i++){
            TLabel[i][0] = Math.random();
        }

        MLDataSet testSet = new BasicMLDataSet(dataTotest,TLabel);
        // test the SVM
        System.out.println("SVM Results:");
        int index = 0;

        ArrayList<Double> result = new ArrayList<>();
        String content = "";
        for(MLDataPair pair: testSet ) {
            final MLData output = svm.compute(pair.getInput());
            content+= "predist=" + output.getData(0)+"\r\n";
            result.add(output.getData(0));
            index++;
        }

        Double[][] returnResult = new Double[result.size()][1];
        for(int i = 0;i<result.size();i++)
        {
            returnResult[i][1] = result.get(i);
        }
        return returnResult;

    }


}