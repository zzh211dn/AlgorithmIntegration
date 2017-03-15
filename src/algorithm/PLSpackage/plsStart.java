package algorithm.PLSpackage;

/**
 * Created by fish123 on 2016/12/13.
 */

import java.io.File;

import static algorithm.PLSpackage.helpers.readCSV;
import static algorithm.PLSpackage.helpers.*;

public class plsStart {
    public static void main(String[] args) {
        String filePath = "C:\\Users\\fish123\\Desktop\\house.csv";
        double[][] csv = readCSV(filePath);
        Data data = new Data(csv);

        PLS_method method = new PLS_method(data.xTrain, data.yTrain, 20);
        double[][] WResult = method.getWstar();

        System.out.println("getWstar:");
        for(int i =0;i<WResult.length;i++) {
            for (int j = 0; j < WResult[0].length; j++) {
                System.out.print(WResult[i][j]+"  ");
            }
            System.out.println();
        }

        double[][] W = method.getT();

        System.out.println("t:");
        for(int i =0;i<W.length;i++) {
            for (int j = 0; j < W[0].length; j++) {
                System.out.print(W[i][j]+"  ");
            }
            System.out.println();
        }

        double[][] yPrediction = predict(method, data.xTest);

        System.out.println("xTest:");
        for(int i =0;i<data.xTest.length;i++) {
            for (int j = 0; j < data.xTest[0].length; j++) {
                System.out.print(data.xTest[i][j]+"  ");
            }
            System.out.println();
        }

        try {
            reportAccuracy(data.yTest, yPrediction, true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("done");

    }
}
