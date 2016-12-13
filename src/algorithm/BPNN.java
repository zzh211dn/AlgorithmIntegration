package algorithm;

import org.encog.engine.network.activation.ActivationLinear;
import org.encog.engine.network.activation.ActivationSigmoid;
import org.encog.ml.data.MLData;
import org.encog.ml.data.MLDataPair;
import org.encog.ml.data.MLDataSet;
import org.encog.ml.data.basic.BasicMLDataSet;
import org.encog.ml.train.MLTrain;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.BasicLayer;
import org.encog.neural.networks.training.Train;
import org.encog.neural.networks.training.propagation.resilient.ResilientPropagation;
import org.encog.util.Format;
import org.encog.util.simple.EncogUtility;

import java.util.Iterator;

import static org.encog.util.simple.EncogUtility.formatNeuralData;

/**
 * Created by fish123 on 2016/12/13.
 */
public class BPNN {

    public String trainToError(MLTrain train, double error, int maxIteration) {
        int epoch = 1;
        System.out.println("Beginning training...");
        String res = "";
        do {
            train.iteration();
            ++epoch;
        } while(train.getError() > error && !train.isTrainingDone() && epoch< maxIteration);
        res = ("Iteration #" + Format.formatInteger(epoch) + " Error:" + Format.formatPercent(train.getError()) + " Target Error: " + Format.formatPercent(error));
        train.finishTraining();
        return res;
    }


    public BasicNetwork network= new BasicNetwork();
    public void trainBPNN(double[][] feature,double[][] lable,int hiddenLayer,int iterateTimes)
    {
        network.addLayer(new BasicLayer(new ActivationLinear(), true,feature[0].length));
        network.addLayer(new BasicLayer(new ActivationSigmoid(), true,Integer.valueOf(hiddenLayer)));
        network.addLayer(new BasicLayer(new ActivationLinear(), true,1));
        network.getStructure().finalizeStructure();
        network.reset();

        MLDataSet trainingSet = new BasicMLDataSet(feature, lable);

        final Train train = new ResilientPropagation(network, trainingSet);

        Integer maxIteration = Integer.valueOf(iterateTimes);
        // Evaluate the neural network.
        String errorRes = trainToError(train, 0.00001,maxIteration);
        System.out.println("nexgt=================");
    }

    public void computeBPNN(double[][] pridictDataSet)
    {
        double[][] testLabel = new double[pridictDataSet.length][1];
        MLDataSet testingSet = new BasicMLDataSet(pridictDataSet, testLabel);

        EncogUtility.evaluate(network, testingSet);
        String content = "";

        Iterator it = testingSet.iterator();
        while(it.hasNext()) {
            MLDataPair pair = (MLDataPair)it.next();
            MLData output = network.compute(pair.getInput());
            content = content+("Actual=" + formatNeuralData(output)+"\r\n");
        }
    }


}