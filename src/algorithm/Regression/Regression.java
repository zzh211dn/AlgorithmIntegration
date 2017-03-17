package algorithm.Regression;

import la.matrix.DenseMatrix;
import la.matrix.Matrix;
import ml.options.Options;
import ml.utils.Matlab;

public abstract class Regression {
    public int ny = 0;
    public int p = 0;
    public int n = 0;
    public Matrix X = null;
    public Matrix Y = null;
    public Matrix W = null;
    public double epsilon;
    public int maxIter;

    public Regression() {
        this.epsilon = 1.0E-6D;
        this.maxIter = 600;
    }

    public Regression(double epsilon) {
        this.epsilon = epsilon;
        this.maxIter = 600;
    }

    public Regression(int maxIter, double epsilon) {
        this.epsilon = epsilon;
        this.maxIter = maxIter;
    }

    public Regression(Options options) {
        this.epsilon = options.epsilon;
        this.maxIter = options.maxIter;
    }

    public void feedData(Matrix X) {
        this.X = X;
        this.p = X.getColumnDimension();
        this.n = X.getRowDimension();
        if(this.Y != null && X.getRowDimension() != this.Y.getRowDimension()) {
            System.err.println("The number of dependent variable vectors and the number of data samples do not match!");
            System.exit(1);
        }

    }

    public void feedData(double[][] data) {
        this.feedData((Matrix)(new DenseMatrix(data)));
    }

    public void feedDependentVariables(Matrix Y) {
        this.Y = Y;
        this.ny = Y.getColumnDimension();
        if(this.X != null && Y.getRowDimension() != this.n) {
            System.err.println("The number of dependent variable vectors and the number of data samples do not match!");
            System.exit(1);
        }

    }

    public void feedDependentVariables(double[][] depVars) {
        this.feedDependentVariables((Matrix)(new DenseMatrix(depVars)));
    }

    public abstract void train();

    public abstract void train(Matrix var1);

    public abstract Matrix train(Matrix var1, Matrix var2);

    public abstract Matrix train(Matrix var1, Matrix var2, Matrix var3);

    public Matrix predict(Matrix Xt) {
        if(Xt.getColumnDimension() != this.p) {
            System.err.println("Dimensionality of the test data doesn\'t match with the training data!");
            System.exit(1);
        }
        double[] x = ((LinearRegression)this).B;//Matlab.repmat(new DenseMatrix(((LinearRegression)this).B, 2), Xt.getRowDimension(), 1);
        for(int i =0;i<x.length;i++) {
            System.out.println("x:" + x[i]);
        }

        return this instanceof LinearRegression ?Xt.mtimes(this.W).plus(Matlab.repmat(new DenseMatrix(((LinearRegression)this).B, 2), Xt.getRowDimension(), 1)):Xt.mtimes(this.W);
    }

    public Matrix predict(double[][] Xt) {
        return this.predict((Matrix)(new DenseMatrix(Xt)));
    }

    public abstract void loadModel(String var1);

    public abstract void saveModel(String var1);
}
