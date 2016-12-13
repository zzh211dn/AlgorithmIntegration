package algorithm.PLSpackage;

/**
 * Created by fish123 on 2016/12/13.
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import Jama.Matrix;
import Jama.SingularValueDecomposition;



public class helpers {
    public static double MACHEPS = 2E-16;
    public static Matrix pinv(Matrix x) {
        if (x.rank() < 1) {return null;}
        if (x.getColumnDimension() > x.getRowDimension()){ return pinv(x.transpose()).transpose();}
        SingularValueDecomposition svdX = new SingularValueDecomposition(x);
        double[] singularValues = svdX.getSingularValues();
        double tol = Math.max(x.getColumnDimension(),
                x.getRowDimension()) * singularValues[0] * MACHEPS;
        double[] singularValueRecip = new double[singularValues.length];
        for (int i = 0; i < singularValues.length; i++){
            singularValueRecip[i] = Math.abs(singularValues[i]) < tol ? 0 : (1.0 / singularValues[i]);
        }
        double[][] u = svdX.getU().getArray();
        double[][] v = svdX.getV().getArray();
        int min = Math.min(x.getColumnDimension(), u[0].length);
        double[][] inverse = new double[x.getColumnDimension()][x.getRowDimension()];
        for (int i = 0; i < x.getColumnDimension(); i++){
            for (int j = 0; j < u.length; j++){
                for (int k = 0; k < min; k++){
                    inverse[i][j] += v[i][k] * singularValueRecip[k] * u[j][k];
                }
            }
        }
        return new Matrix(inverse);
    }

    public static double[][] readCSV(String path){
        List<String[]> rowList = new ArrayList<String[]>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] lineItems = line.split(",");
                rowList.add(lineItems);
            }
            br.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        String[][] matrix = new String[rowList.size()][];
        for (int i = 0; i < rowList.size(); i++) {
            String[] row = rowList.get(i);
//            System.out.println("i:"+i+"    "+Arrays.toString(rowList.get(i)));
            matrix[i] = row;
        }
        double[][] tableDouble = new double[matrix.length][matrix[0].length];
        for(int i=0; i<matrix.length; i++) {
            for(int j=0; j<matrix[0].length; j++) {
                tableDouble[i][j]= Double.parseDouble(matrix[i][j]);
            }
        }
        return tableDouble;
    }

    public static double[][] predict(PLS_method method, double[][] X){
        Matrix Xmat = new Matrix(X);
        Matrix Wstar = new Matrix(method.getWstar());
        return Xmat.times(Wstar).getArray();
    }

    public static void reportAccuracy(double[][] known, double[][] predicted, boolean print) throws Exception{
        if (known.length != predicted.length){
            throw new Exception("Array Mismatch");
        }else{
            double meanpercentError = 0;
            if(print){
                System.out.println("     |  Known Y  | Predicted Y | Percent Error ");
                System.out.println("-----------------------------------------------");
            }
            for(int i=0; i < known.length; i++){
                double percentError = (Math.abs(predicted[i][0]-known[i][0])/known[i][0] * 100);
                meanpercentError += Math.abs(percentError);
                if(print){
                    System.out.format("%5d|%11f|%13f|%15f\n", i, known[i][0], predicted[i][0], percentError);
                }
            }
            if(print){
                System.out.println("-----------------------------------------------");
            }
            System.out.format("Mean absolute percentage error:       %f\n", meanpercentError/known.length);
        }
    }

}

