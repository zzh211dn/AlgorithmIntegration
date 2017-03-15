package algorithm;

import la.matrix.DenseMatrix;
import la.matrix.Matrix;
import ml.subspace.DimensionalityReduction;
import ml.utils.ArrayOperator;
import ml.utils.Matlab;
import ml.utils.Printer;

public class PCA extends DimensionalityReduction {
    public static Matrix[] xloading ;
    public static Matrix E;

    public static void main(String[] args) {
        double[][] data = new double[][]{
                {3.8889D,4.4443998D,6.6388998D,3.8889D,3.9166999D,5.9166999D,7.75D,5.1388998D,6.1666999D,6.0278001D,8.4443998D,4.9721999D},
                {4.3333001D,4.9721999D,7.0833001D,4.8611002D,4.4443998D,6.3888998D,7.6388998D,5.8333001D,6.0556002D,6.4443998D,8.0277996D,5.6111002D},
                {4.2778001D,4.6666999D,4.9721999D,4.75D,3.8333001D,5.0833001D,5.0278001D,5.4443998D,4.2778001D,5.1666999D,5.1111002D,4.7778001D},
                {4.3888998D,4.9166999D,5.4443998D,4.6666999D,3.75D,5.8611002D,4.0556002D,5.6111002D,3.7221999D,5.5556002D,3.8333001D,4.7778001D},
                {4.3611002D,4.6388998D,4.9166999D,4D,3.8889D,5.75D,3.8055999D,5.3056002D,3.6944001D,5.3888998D,3.6389D,3.9444001D},
                { 4.6943998D,4.3056002D,3.8055999D,3.1389D,5.1388998D,4.9721999D,4.3888998D,3.5833001D,5.5833001D,4.6943998D,4.7778001D,3.6944001D},
                {4.1943998D,4.3056002D,4.9166999D,4.4721999D,3.8333001D,5.2221999D,4.3333001D,4.7221999D,3.5D,4.7221999D,4.4166999D,4.4443998D},
                {2.9166999D,3.2221999D,3.5D,3.6944001D,3.0555999D,2.3611D,3.4721999D,3.25D,2.9166999D,2.4721999D,3.6666999D,3.3889D},
                {3.1944001D,2.9721999D,2.6944001D,3.5278001D,3.9166999D,2.1389D,3.8333001D,2.6389D,4.3056002D,2.75D,3.9444001D,3.8889D},
                {5.3333001D,5.7221999D,6.0556002D,5.3056002D,5.4443998D,6.5278001D,6.0278001D,6.1388998D,6.0278001D,6.2221999D,6.0278001D,6.0833001D},
                {4.1111002D,4.3056002D,3.5278001D,4.8056002D,4.1666999D,3.6944001D,3.3889D,4.2221999D,4.1388998D,3.6666999D,3.1944001D,3.75D},
                {3.8333001D,3.8333001D,3.8889D,4.5278001D,3.8055999D,3.3333001D,4.4443998D,3.75D,3.4166999D,3.1944001D,4.2778001D,3.7778001D}

        };
        double[][] data2 = new double[][]{{1.12, 2.05, 3.12}, {5.56, 6.28, 8.94}, {10.2, 8.0, 20.5}};

                Matrix X = (new DenseMatrix(data)).transpose();
//        Matrix X = (new DenseMatrix(data2));
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

    public static Matrix generate(Matrix T){
        return T.mtimes(E);
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
        System.out.println("X:"+ X);

        Matrix var9 = X.transpose();
        Matrix var10 = var9.mtimes(X);

        Matrix[] var11=Matlab.eigs(var10, r, "lm");
        xloading = var11;
        E = Matlab.eigs(var10, r, "lm")[0];
        return X.mtimes(E);
    }
}