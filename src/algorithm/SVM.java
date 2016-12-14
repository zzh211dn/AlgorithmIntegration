package algorithm;

import org.encog.Encog;
import org.encog.ml.data.MLData;
import org.encog.ml.data.MLDataPair;
import org.encog.ml.data.MLDataSet;
import org.encog.ml.data.basic.BasicMLDataSet;
import org.encog.ml.svm.KernelType;
import org.encog.ml.svm.SVMType;
import org.encog.ml.svm.training.SVMTrain;

/**
 * Created by fish123 on 2016/12/14.
 */
public class SVM {

    public void trainSVM(double[][] trainData,double[][] trainlabel,double[][] testData){
//        SVM svm = new SVM(testDatas.get(0).size(), SVMType.NewSupportVectorRegression, KernelType.Linear);

        double[][] dataTotest = new double[testData.length][testData[0].length];

        org.encog.ml.svm.SVM svm = new org.encog.ml.svm.SVM(testData[0].length,false);

        // create training data
        MLDataSet trainingSet = new BasicMLDataSet(trainData,trainlabel);

        // train the SVM
        final SVMTrain train = new SVMTrain(svm, trainingSet);
        train.iteration();
        train.finishTraining();

        double[][] TLabel = new double[testData.length][1];
        for(int i=0;i<dataTotest.length;i++){
            TLabel[i][0] = Math.random();
        }


        MLDataSet testSet = new BasicMLDataSet(dataTotest,TLabel);
        // test the SVM
        System.out.println("SVM Results:");
        int index = 0;
        String content = "";
        for(MLDataPair pair: testSet ) {
            final MLData output = svm.compute(pair.getInput());
            content+= "predist=" + output.getData(0)+"\r\n";
            index++;
        }

        Encog.getInstance().shutdown();
    }

}
