package algorithm.PLSpackage;

/**
 * Created by fish123 on 2016/12/13.
 */
public class Data {
    double[][] xTrain;
    double[][] yTrain;
    double[][] xTest;
    double[][] yTest;

    public Data(double[][] csv){
        gen_TrainTest(csv, 100);
    }

    private double[][] getInput(double[][] array){
        double[][] tableDouble = new double[array.length][array[0].length-1];
        for(int i=0; i<array.length; i++) {
            for(int j=0; j<array[0].length-1; j++) {
                tableDouble[i][j]= array[i][j];
            }
        }
        return tableDouble;
    }

    private double[][] getOutput(double[][] array){
        double[][] tableDouble = new double[array.length][1];
        for(int i=0; i<array.length; i++) {
            tableDouble[i][0]= array[i][array[0].length-1];
        }
        return tableDouble;
    }

    private void gen_TrainTest(double[][] array, double percent) {
        int count = (int) (array.length - Math.floor(array.length * percent));
        if (percent <= 0 || count > array.length || count <= 0) {
            xTrain = getInput(array);
            yTrain = getOutput(array);
            xTest = getInput(array);
            yTest = getOutput(array);
        } else {
            double[][] train = new double[count][array[0].length];
            double[][] test = new double[array.length - count][array[0].length];
            System.arraycopy(array, 0, train, 0, count);
            System.arraycopy(array, count, test, 0, array.length - count);

            xTrain = getInput(train);
            yTrain = getOutput(train);
            xTest = getInput(test);
            yTest = getOutput(test);
        }

    }
}
