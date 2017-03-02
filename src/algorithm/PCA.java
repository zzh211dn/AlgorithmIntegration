package algorithm;

import la.matrix.DenseMatrix;
import la.matrix.Matrix;
import ml.subspace.DimensionalityReduction;
import ml.utils.ArrayOperator;
import ml.utils.Matlab;
import ml.utils.Printer;

public class PCA extends DimensionalityReduction {
    public static Matrix[] xloading ;

    public static void main(String[] args) {
        double[][] data = new double[][]{{0.0D, 2.0D, 3.0D, 4.0D}, {2.0D, 0.0D, 4.0D, 5.0D}, {3.0D, 4.1D, 5.0D, 6.0D}, {2.0D, 7.0D, 1.0D, 6.0D},{0.0D, 8.0D, 7.0D, 4.0D}};
        Matrix X = (new DenseMatrix(data)).transpose();
        byte r = 3;
        Matrix R = run(X, r);
        Printer.disp("Original Data:");
        Printer.disp(X);
        Printer.disp("Reduced Data:");
        Printer.disp(R);
    }

    public PCA(int r) {
        super(r);
    }

    public void run() {
        this.R = run(this.X, this.r);
        Printer.disp("Reduced Data:");
        Printer.disp(this.R);
    }

    public static Matrix run(Matrix X, int r) {
        int n = Matlab.size(X, 1);
        double[] S = Matlab.sum(X).getPr();
        ArrayOperator.divideAssign(S, (double)n);
        X = X.copy();
        int d = X.getColumnDimension();
        double s = 0.0D;

        for(int XT = 0; XT < n; ++XT) {
            for(int Psi = 0; Psi < d; ++Psi) {
                s = S[Psi];
                if(s != 0.0D) {
                    X.setEntry(XT, Psi, X.getEntry(XT, Psi) - s);
                }
            }
        }

        Matrix var9 = X.transpose();
        Matrix var10 = var9.mtimes(X);

        Matrix[] var11=Matlab.eigs(var10, r, "lm");
        System.out.println(var11[0]);
        xloading = var11;
        return X.mtimes(Matlab.eigs(var10, r, "lm")[0]);
    }
}