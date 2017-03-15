package algorithm.Regression;

/**
 * Created by fish123 on 2017/3/14.
 */
public class Residuals {

    public Double[][] residuals(Double[][] X,Double[][] Y,Double[][] predictY)
    {
        Double[][] residualsX = new Double[X.length][X[0].length];
        Double[][] residualsY = new Double[Y.length][Y[0].length];

        Double[][] matrixMinusY = matrixMinus(Y,predictY);// calculate y-y'

        /**
         * 求y-y'的平方和
        * */
        double sum = 0;
        double mean = 0;
        for(int i = 0;i<matrixMinusY[0].length;i++)
        {
            for(int j = 0;j<matrixMinusY.length;j++)
            {
                sum = matrixMinusY[j][i]*matrixMinusY[j][i]+sum;
            }
        }
        /**
         * 残差=（y-y'）*(n-p-1)/sum(y-y')^2
         * */
        for(int i = 0;i<matrixMinusY[0].length;i++)//列
        {
            for(int j = 0;j<matrixMinusY.length;j++)//行
            {
                residualsY[j][i] = matrixMinusY[j][i]*(Y.length-X[0].length-1)/sum;
            }
        }
        return  residualsY;

    }

    /**
     * 集合相减
     *
     * */
    public Double[][] matrixMinus(Double[][] first,Double[][] second)
    {
        Double[][] result = new Double[first.length][first[0].length];
        for(int i = 0;i<result.length;i++)
        {
            for(int j = 0;j<result[0].length;j++)
            {
                result[i][j] = first[i][j]-second[i][j];
            }
        }
        return result;
    }

}
