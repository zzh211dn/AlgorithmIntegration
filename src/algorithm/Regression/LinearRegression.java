package algorithm.Regression;
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import la.matrix.DenseMatrix;
import la.matrix.Matrix;
import la.matrix.SparseMatrix;
import ml.options.Options;
import ml.utils.ArrayOperator;
import ml.utils.Matlab;
import ml.utils.Printer;
import ml.utils.Time;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class LinearRegression extends Regression {
    private double lambda;
    private boolean calc_OV;
    private boolean verbose;
    public double[] B;

    public static void main(String[] args) {
        double[][] data = new double[][]{{1.0D, 2.0D, 3.0D, 2.0D}, {4.0D, 2.0D, 3.0D, 6.0D}, {5.0D, 1.0D, 4.0D, 1.0D}};
        double[][] depVars = new double[][]{{3.0D, 2.0D}, {2.0D, 3.0D}, {1.0D, 4.0D}};
        Options options = new Options();
        options.maxIter = 600;
        options.lambda = 0.1D;
        options.verbose = false;
        options.calc_OV = false;
        options.epsilon = 1.0E-5D;
        ml.regression.LinearRegression LR = new ml.regression.LinearRegression(options);
        LR.feedData(data);
        LR.feedDependentVariables(depVars);
        Time.tic();
        LR.train();
        Printer.fprintf("Elapsed time: %.3f seconds\n\n", new Object[]{Double.valueOf(Time.toc())});
        Printer.fprintf("Projection matrix:\n", new Object[0]);
        Printer.display(LR.W);
        Printer.fprintf("Bias vector:\n", new Object[0]);
        Printer.display(((ml.regression.LinearRegression)LR).B);
        Matrix Yt = LR.predict(data);
        Printer.fprintf("Predicted dependent variables:\n", new Object[0]);
        Printer.display(Yt);
    }

    public LinearRegression() {
    }

    public LinearRegression(double epsilon) {
        super(epsilon);
    }

    public LinearRegression(int maxIter, double epsilon) {
        super(maxIter, epsilon);
    }

    public LinearRegression(Options options) {
        super(options);
        this.lambda = options.lambda;
        this.calc_OV = options.calc_OV;
        this.verbose = options.verbose;
    }

    private double train(Matrix X, double[] y, double[] w0, double b0) {
        double[] w = w0;
        double b = b0;
        double[] y_hat = new double[this.n];
        double[] e = new double[this.n];
        double[] OFVs = null;
        boolean debug = false;
        byte blockSize = 10;
        if(this.calc_OV && this.verbose) {
            OFVs = ArrayOperator.allocate1DArray(this.maxIter + 1, 0.0D);
            double cnt = 0.0D;
            cnt = this.computeOFV(y, w0, b0);
            OFVs[0] = cnt;
            Printer.fprintf("Iter %d: %.10g\n", new Object[]{Integer.valueOf(0), Double.valueOf(cnt)});
        }

        int var38 = 0;
        double ofv_old = 0.0D;
        double ofv_new = 0.0D;
        int wj_new;
        int i;
        double xj1;
        double var46;
        double var47;
        double var48;
        if(X instanceof SparseMatrix) {
            int[] data = ((SparseMatrix)X).getIc();
            int[] b_new = ((SparseMatrix)X).getIr();
            int[] s = ((SparseMatrix)X).getJc();
            int[] ofv = ((SparseMatrix)X).getJr();
            double[] v1 = ((SparseMatrix)X).getPr();
            int[] valCSRIndices = ((SparseMatrix)X).getValCSRIndices();

            for(int v2 = 0; v2 < this.n; ++v2) {
                double s1 = b;

                for(int xj = ofv[v2]; xj < ofv[v2 + 1]; ++xj) {
                    i = data[xj];
                    s1 += w[i] * v1[valCSRIndices[xj]];
                }

                y_hat[v2] = s1;
                e[v2] = y[v2] - s1;
            }

            do {
                ofv_old = 0.0D;
                if(debug) {
                    ofv_old = this.computeOFV(y, w, b);
                    Printer.printf("f(b): %f\n", new Object[]{Double.valueOf(ofv_old)});
                }

                var46 = (b * (double)this.n + ArrayOperator.sum(e)) / ((double)this.n + this.lambda);

                for(wj_new = 0; wj_new < this.n; ++wj_new) {
                    e[wj_new] -= var46 - b;
                }

                b = var46;
                if(debug) {
                    ofv_new = this.computeOFV(y, w, var46);
                    Printer.printf("b updated: %f\n", new Object[]{Double.valueOf(ofv_new)});
                    if(ofv_old < ofv_new) {
                        Printer.errf("Error when updating b\n", new Object[0]);
                    }
                }

                for(wj_new = 0; wj_new < this.p; ++wj_new) {
                    ofv_old = 0.0D;
                    var48 = 0.0D;
                    xj1 = 0.0D;

                    for(int wj_new1 = s[wj_new]; wj_new1 < s[wj_new + 1]; ++wj_new1) {
                        int i1 = b_new[wj_new1];
                        double k = v1[wj_new1];
                        var48 += k * k;
                        xj1 += k * e[i1];
                    }

                    double var50 = (w[wj_new] * var48 + xj1) / (var48 + this.lambda);
                    int var52;
                    if(Double.isInfinite(var50)) {
                        byte var51 = 1;
                        var52 = var51 + 1;
                    }

                    for(var52 = s[wj_new]; var52 < s[wj_new + 1]; ++var52) {
                        int i2 = b_new[var52];
                        double xj2 = v1[var52];
                        e[i2] -= (var50 - w[wj_new]) * xj2;
                    }

                    w[wj_new] = var50;
                    if(debug) {
                        ofv_new = this.computeOFV(y, w, b);
                        Printer.printf("w[%d] updated: %f\n", new Object[]{Integer.valueOf(wj_new), Double.valueOf(ofv_new)});
                        if(ofv_old < ofv_new) {
                            Printer.errf("Error when updating w[%d]\n", new Object[]{Integer.valueOf(wj_new)});
                        }
                    }
                }

                ++var38;
                if(this.verbose) {
                    if(this.calc_OV) {
                        var47 = this.computeOFV(y, w, b);
                        OFVs[var38] = var47;
                        if(var38 % blockSize == 0) {
                            Printer.fprintf(".Iter %d: %.8g\n", new Object[]{Integer.valueOf(var38), Double.valueOf(var47)});
                        } else {
                            Printer.fprintf(".", new Object[0]);
                        }
                    } else if(var38 % blockSize == 0) {
                        Printer.fprintf(".Iter %d\n", new Object[]{Integer.valueOf(var38)});
                    } else {
                        Printer.fprintf(".", new Object[0]);
                    }
                }
            } while(var38 < this.maxIter);
        } else if(X instanceof DenseMatrix) {
            double[][] var39 = X.getData();

            for(int var40 = 0; var40 < this.n; ++var40) {
                double var42 = b + ArrayOperator.innerProduct(w, var39[var40]);
                y_hat[var40] = var42;
                e[var40] = y[var40] - var42;
            }

            do {
                ofv_old = 0.0D;
                if(debug) {
                    ofv_old = this.computeOFV(y, w, b);
                    Printer.printf("f(b): %f\n", new Object[]{Double.valueOf(ofv_old)});
                }

                double var41 = (b * (double)this.n + ArrayOperator.sum(e)) / ((double)this.n + this.lambda);

                int var43;
                for(var43 = 0; var43 < this.n; ++var43) {
                    e[var43] -= var41 - b;
                }

                b = var41;
                if(debug) {
                    ofv_new = this.computeOFV(y, w, var41);
                    Printer.printf("b updated: %f\n", new Object[]{Double.valueOf(ofv_new)});
                    if(ofv_old < ofv_new) {
                        Printer.errf("Error when updating b\n", new Object[0]);
                    }
                }

                for(var43 = 0; var43 < this.p; ++var43) {
                    ofv_old = 0.0D;
                    double var44 = 0.0D;
                    var46 = 0.0D;

                    for(wj_new = 0; wj_new < this.n; ++wj_new) {
                        var48 = var39[wj_new][var43];
                        var44 += var48 * var48;
                        var46 += var48 * e[wj_new];
                    }

                    var47 = (w[var43] * var44 + var46) / (var44 + this.lambda);
                    if(Double.isInfinite(var47)) {
                        byte var49 = 1;
                        i = var49 + 1;
                    }

                    for(i = 0; i < this.n; ++i) {
                        xj1 = var39[i][var43];
                        e[i] -= (var47 - w[var43]) * xj1;
                    }

                    w[var43] = var47;
                    if(debug) {
                        ofv_new = this.computeOFV(y, w, b);
                        Printer.printf("w[%d] updated: %f\n", new Object[]{Integer.valueOf(var43), Double.valueOf(ofv_new)});
                        if(ofv_old < ofv_new) {
                            Printer.errf("Error when updating w[%d]\n", new Object[]{Integer.valueOf(var43)});
                        }
                    }
                }

                ++var38;
                if(this.verbose) {
                    if(this.calc_OV) {
                        double var45 = this.computeOFV(y, w, b);
                        OFVs[var38] = var45;
                        if(var38 % blockSize == 0) {
                            Printer.fprintf(".Iter %d: %.8g\n", new Object[]{Integer.valueOf(var38), Double.valueOf(var45)});
                        } else {
                            Printer.fprintf(".", new Object[0]);
                        }
                    } else if(var38 % blockSize == 0) {
                        Printer.fprintf(".Iter %d\n", new Object[]{Integer.valueOf(var38)});
                    } else {
                        Printer.fprintf(".", new Object[0]);
                    }
                }
            } while(var38 < this.maxIter);
        }

        return b;
    }

    public void train() {
        double[][] ws = ArrayOperator.allocate2DArray(this.ny, this.p, 0.0D);
        this.B = ArrayOperator.allocate1DArray(this.ny, 0.0D);

        for(int k = 0; k < this.ny; ++k) {
            this.B[k] = this.train(this.X, Matlab.full(this.Y.getColumnVector(k)).getPr(), ws[k], this.B[k]);
        }

        this.W = (new DenseMatrix(ws)).transpose();
    }

    public void train(Matrix W0) {
        double[][] ws = W0.transpose().getData();
        this.B = ArrayOperator.allocate1DArray(this.ny, 0.0D);

        for(int k = 0; k < this.ny; ++k) {
            this.B[k] = this.train(this.X, Matlab.full(this.Y.getColumnVector(k)).getPr(), ws[k], this.B[k]);
        }

        this.W = (new DenseMatrix(ws)).transpose();
    }

    public Matrix train(Matrix X, Matrix Y) {
        String Method = "Linear Regression";
        System.out.printf("Training %s...\n", new Object[]{Method});
        double[][] ws = ArrayOperator.allocate2DArray(this.ny, this.p, 0.0D);
        this.B = ArrayOperator.allocate1DArray(this.ny, 0.0D);

        for(int k = 0; k < this.ny; ++k) {
            this.B[k] = this.train(X, Matlab.full(Y.getColumnVector(k)).getPr(), ws[k], this.B[k]);
        }

        this.W = (new DenseMatrix(ws)).transpose();
        return this.W;
    }

    private double computeOFV(double[] y, double[] w, double b) {
        double ofv = 0.0D;
        ofv += this.lambda * b * b;
        ofv += this.lambda * ArrayOperator.innerProduct(w, w);
        int[] ic = ((SparseMatrix)this.X).getIc();
        int[] jr = ((SparseMatrix)this.X).getJr();
        double[] pr = ((SparseMatrix)this.X).getPr();
        int[] valCSRIndices = ((SparseMatrix)this.X).getValCSRIndices();

        for(int r = 0; r < this.n; ++r) {
            double s = b;

            for(int e = jr[r]; e < jr[r + 1]; ++e) {
                int j = ic[e];
                s += w[j] * pr[valCSRIndices[e]];
                if(Double.isNaN(s)) {
                    byte a = 1;
                    int var18 = a + 1;
                }
            }

            double var17 = y[r] - s;
            ofv += var17 * var17;
        }

        return ofv;
    }

    public Matrix train(Matrix X, Matrix Y, Matrix W0) {
        double[][] ws = W0.transpose().getData();
        this.B = ArrayOperator.allocate1DArray(this.ny, 0.0D);

        for(int k = 0; k < this.ny; ++k) {
            this.B[k] = this.train(X, Matlab.full(Y.getColumnVector(k)).getPr(), ws[k], this.B[k]);
        }

        this.W = (new DenseMatrix(ws)).transpose();
        return this.W;
    }

    public void loadModel(String filePath) {
        try {
            ObjectInputStream e = new ObjectInputStream(new FileInputStream(filePath));
            this.W = (Matrix)e.readObject();
            this.B = (double[])e.readObject();
            e.close();
            System.out.println("Model loaded.");
        } catch (FileNotFoundException var3) {
            var3.printStackTrace();
            System.exit(1);
        } catch (IOException var4) {
            var4.printStackTrace();
        } catch (ClassNotFoundException var5) {
            var5.printStackTrace();
        }

    }

    public void saveModel(String filePath) {
        File parentFile = (new File(filePath)).getParentFile();
        if(parentFile != null && !parentFile.exists()) {
            parentFile.mkdirs();
        }

        try {
            ObjectOutputStream e = new ObjectOutputStream(new FileOutputStream(filePath));
            e.writeObject(this.W);
            e.writeObject(this.B);
            e.close();
            System.out.println("Model saved.");
        } catch (FileNotFoundException var4) {
            var4.printStackTrace();
            System.exit(1);
        } catch (IOException var5) {
            var5.printStackTrace();
        }

    }
}
