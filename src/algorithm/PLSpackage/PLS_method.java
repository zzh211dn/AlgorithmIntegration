package algorithm.PLSpackage;

/**
 * Created by fish123 on 2016/12/13.
 */
import Jama.Matrix;
/*
 * http://hellofrobenius.blogspot.com/2014/09/partial-least-squares-regression-in-java.html
 	Algorithm:
		Let X be the input matrix,
		Let Y be the output matrix,
		Let P be the loadings matrix for X, and let pi denote the i-th column of P;
		Let Q be the loadings matrix for Y, and let qi denote the i-th column of Q;
		Let T be the score matrix for X, and ti denote the i-th column of T;
		Let U be the score matrix for Y, and ui denote the i-th column of U;
		Let W be the PLS weight matrix, and d wi denote the i-th column of W; and
		Let B be a diagonal matrix of diagonal coefficients bi

		Then:
			For each factor i to be calculated:
				A) Initially choose ui as the largest column vector in X (having the largest sum of squares), but you can choose the first column as well.

				B) While (ti has not converged to a desired precision)
					1) wi proportional to X'ui     (estimate X weights)
					2) ti proportional to Xwi      (estimate X factor scores)
					3) qi proportional to Y'ti     (estimate Y weights)
					4) ui = Yqi      (estimate Y scores)

				C) bi = t'u        (compute prediction coefficient b)
				D) pi = X't        (estimate X factor loadings)
				E) X = X – tp'     (deflate X)
*/


public class PLS_method {
    //T Scores on X
    private double[][] T;
    public double[][] getT(){return T;}
    //U Scores on Y
    private double[][] U;
    public double[][] getU(){return U;}
    //P Loadings on X (Components extracted from data)
    private double[][] P;
    public double[][] getP(){return P;}
    //C (ndarray g x g): Diagonal matrix of regression coefficients
    private double[][] C;
    public double[][] getC(){return C;}
    //W  PLS weight matrix
    private double[][] W;
    public double[][] getW(){return W;}
    //B (ndarray n x m): Final regression matrix
    private double[] B;
    public double[] getB(){return B;}
    //W* Hermitian transpose
    private double[][] Wstar;
    public double[][] getWstar(){return Wstar;}

    // Nipals
    public PLS_method(final double[][] X, final double[][] Y, int factors){
        // Initialize and prepare the data
        int rows = X.length;
        int xcols = X[0].length;
        int ycols = Y[0].length;

        double[][] E = new double[rows][xcols];
        E = X;
        double[][] F = new double[rows][ycols];
        F = Y;

        T = new double[rows][factors];
        U = new double[rows][factors];
        P = new double[xcols][factors];
        C = new double[ycols][factors];
        W = new double[xcols][factors];
        B = new double[factors];

        double[] varX = new double[factors];
        double[] varY = new double[factors];

        // Initialize the algorithm
        boolean stop = false;
        for (int factor = 0; factor < factors && !stop; factor++){
            // Select t as the largest column from X,
            int lE = largest(E);
            double[] t = new double[rows];
            for (int i = 0; i < rows; i++){
                t[i] = E[i][lE];
            }
            // Select u as the largest column from Y.
            int lF = largest(F);
            double[] u = new double[rows];
            for (int i = 0; i < rows; i++){
                u[i] = F[i][lF];
            }
            // Will store weights for X and Y
            double[] w = new double[xcols];
            double[] c = new double[ycols];

            double norm_t = Euclidean(t);

            // Iteration region
            while (norm_t > 1e-14){
                // Store initial t to check convergence
                double[] t0 = t;
                // Step 1. Estimate w (X weights): w proportional to E'*u
                //   (in Abdi's paper, X is referred as E).
                // 1.1. Compute w = E'*u;
                w = new double[xcols];
                for (int j = 0; j < w.length; j++){
                    for (int i = 0; i < u.length; i++){
                        w[j] = w[j] + E[i][j] * u[i];
                    }
                }
                // 1.2. Normalize w (w = w/norm(w))
                double Ew = Euclidean(w);
                for (int i = 0; i < w.length; i++){
                    w[i] = w[i]/Ew;
                }
                // Step 2. Estimate t (X factor scores): t proportional to E*w
                //   (in Abdi's paper, X is referred as E).
                // 2.1. Compute t = E*w
                t = new double[rows];
                for (int i = 0; i < t.length; i++){
                    for (int j = 0; j < w.length; j++){
                        t[i] = t[i] + E[i][j] * w[j];
                    }
                }
                // 2.2. Normalize t: t = t/norm(t)
                double Et = Euclidean(t);
                for (int i = 0; i < t.length; i ++){
                    t[i] = t[i]/Et;
                }
                // Step 3. Estimate c (Y weights): c  F't
                //   (in Abdi's paper, Y is referred as F).
                // 3.1. Compute c = F'*t0;
                c = new double[ycols];
                for (int j = 0; j < c.length; j++){
                    for (int i = 0; i < t.length; i++){
                        c[j] = c[j] + F[i][j] * t[i];
                    }
                }
                // 3.2. Normalize q: c = c/norm(q)
                double Ec = Euclidean(c);
                for (int i = 0; i < c.length; i++){c[i] = c[i]/Ec;}
                // Step 4. Estimate u (Y scores): u = F*q
                //   (in Abdi's paper, Y is referred as F).
                // 4.1. Compute u = F*q;
                u = new double[rows];
                for (int i = 0; i < u.length; i++){
                    for (int j = 0; j < c.length; j++){
                        u[i] = u[i] + F[i][j] * c[j];
                    }
                }
                // Recalculate norm of the difference
                norm_t = 0.0;
                for (int i = 0; i < t.length; i++){
                    double d = (t0[i] - t[i]);
                    norm_t += d * d;
                }
                norm_t = Math.sqrt(norm_t);
            }
            // End iteration

            // Compute the value of b which is used to
            // predict Y from t as b = t'u [Abdi, 2010]
            double b = 0;
            for (int i = 0; i < t.length; i++){
                b = b + t[i] * u[i];
            }
            // Compute factor loadings for X as p = E'*t [Abdi, 2010]
            double[] p = new double[xcols];
            for (int j = 0; j < p.length; j++){
                for (int i = 0; i < rows; i++){
                    p[j] = p[j] + E[i][j] * t[i];
                }
            }
            // Perform deflaction of X and Y
            for (int i = 0; i < t.length; i++){
                // Deflate X as X = X - t*p';
                for (int j = 0; j < p.length; j++){
                    E[i][j] = E[i][j] - t[i] * p[j];
                }
                // Deflate Y as Y = Y - b*t*q';
                for (int j = 0; j < c.length; j++){
                    F[i][j] = F[i][j]- b * t[i] * c[j];
                }
            }

            // Calculate explained variances
            varY[factor] = b * b;
            double temp = 0;
            for (int i = 0; i < p.length; i++){
                temp = temp + p[i] * p[i];
            }
            varX[factor] = temp;
            // Save iteration results
            for (int i = 0; i < t.length; i++){T[i][factor] = t[i];}
            for (int i = 0; i < p.length; i++){P[i][factor] = p[i];}
            for (int i = 0; i < u.length; i++){U[i][factor] = u[i];}
            for (int i = 0; i < c.length; i++){C[i][factor] = c[i];}
            for (int i = 0; i < w.length; i++){W[i][factor] = w[i];}
            B[factor] = b;

        }

        // Calculate the coefficient vector

        Matrix Pmat = new Matrix(P);
        double[][] tempB = new double[B.length][B.length];
        for (int i = 0; i < B.length; i++){
            tempB[i][i] = B[i];
        }
        Matrix Bmat = new Matrix(tempB);
        Matrix PmatI = helpers.pinv(Pmat.transpose());
        PmatI = PmatI.times(Bmat);
        Matrix Cmat = new Matrix(C);
        PmatI = PmatI.times(Cmat.transpose());

        Wstar = PmatI.getArray();
    }
    private double Euclidean(double[] vect){
        double result = 0;
        for (int i = 0; i < vect.length; i++){
            result = result + Math.pow(vect[i], 2);
        }
        result = Math.sqrt(result);
        return result;
    }

    private int largest(double[][] matrix){
        int rows = matrix.length;
        int cols = matrix[0].length;
        int index = 0;
        double max = 0;
        for (int i = 0; i < cols; i++){
            double squareSum = 0.0;
            for (int j = 0; j < rows; j++){
                squareSum = squareSum + matrix[j][i] * matrix[j][i];
            }
            if (squareSum > max){
                max = squareSum;
                index = i;
            }
        }
        return index;
    }
}