package algorithm.PLSpackage;

/**
 * Created by fish123 on 2016/12/13.
 */

import java.io.File;

import static algorithm.PLSpackage.helpers.readCSV;
import static algorithm.PLSpackage.helpers.*;

public class plsStart {
    public static void main(String[] args) {
        String filePath = "C:\\Users\\fish123\\Desktop\\housing.csv";
        double[][] csv = readCSV(filePath);
        Data data = new Data(csv);

        PLS_method method = new PLS_method(data.xTrain, data.yTrain, 20);
        double[][] yPrediction = predict(method, data.xTest);
        try {
            reportAccuracy(data.yTest, yPrediction, true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("done");

    }
}
