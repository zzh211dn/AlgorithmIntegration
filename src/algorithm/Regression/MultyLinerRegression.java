package algorithm.Regression;

import la.matrix.DenseMatrix;
import la.matrix.Matrix;
import ml.options.Options;
import ml.regression.LinearRegression;
import ml.regression.Regression;
import ml.utils.Matlab;

import static ml.utils.Printer.display;
import static ml.utils.Printer.fprintf;
import static ml.utils.Time.tic;
import static ml.utils.Time.toc;

public class MultyLinerRegression{

    public double[][] getLinerRegression(double[][] data,double[][] lable)
    {
        Options options = new Options();
        options.maxIter = 600;
        options.lambda = 0.1;
        options.verbose = !true;
        options.calc_OV = !true;
        options.epsilon = 1e-5;

//        for(int i = 0;i<data.length;i++)
//        {
//            for(int j = 0;j<data[0].length;j++)
//            {
//                System.out.print(data[i][j]+",");
//            }
//            System.out.println();
//        }
        Regression LR = new LinearRegression(options);
        LR.feedData(data);
        LR.feedDependentVariables(lable);

        LR.train();
        Matrix Yt = LR.predict(data);


        return Yt.getData();

    }

    public static void main(String[] args) {
        double[][] data = {
                {1, 2, 3, 2,2},
                {4, 2, 3, 6,6},
                {5, 1, 4, 1,1},
                {5, 1, 4, 2,1}
//                {3.8889,4.3333,4.2778,4.3889},
//                { 4.4444,4.9722,4.6667,4.9167},
//                { 6.6389,7.0833,4.9722,5.4444},
//                {  3.8889,4.8611,4.75,4.6667},
//                { 3.9167,4.4444,3.8333,3.75},
//                {5.9167,6.3889,5.0833,5.8611},
//                {7.75,7.6389,5.0278,4.0556},
//                {5.1389,5.8333,5.4444,5.6111},
//                {6.1667,6.0556,4.2778,3.7222},
//                {6.0278,6.4444,5.1667,5.5556},
//                {8.4444,8.0278,5.1111,3.8333}
//                {4.9722,5.6111,4.7778,4.7778,3.9444,3.6944,4.4444,3.3889,3.8889,6.0833,3.75,3.7778}
        };

        double[][] depVars = {
                {1},{1}, {1},{1}
        };

        MultyLinerRegression linerRegression= new MultyLinerRegression();
        double[][] result = linerRegression.getLinerRegression(data,depVars);
        fprintf("Predicted dependent variables:\n");

        display(result);
    }
}