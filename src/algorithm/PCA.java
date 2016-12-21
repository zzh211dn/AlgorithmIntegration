package algorithm;

import java.text.DecimalFormat;
import java.util.Date;

import Jama.Matrix;

public class PCA {
    /**
     * 将原始数据标准化
     * */
    public double[][] Standardlizer(double[][] x){
        int n=x.length;		//二维矩阵的行号
        int p=x[0].length;	//二维矩阵的列号
        double[] average=new double[p];	//每一列的平均值
        double[][] result=new double[n][p];	//标准化后的向量
        double[] var=new double[p];      //方差
        //取得每一列的平均值
        for(int k=0;k<p;k++){
            double temp=0;
            for(int i=0;i<n;i++){
                temp+=x[i][k];
            }
            average[k]=temp/n;
        }
        //取得方差
        for(int k=0;k<p;k++){
            double temp=0;
            for(int i=0;i<n;i++){
                temp+=(x[i][k]-average[k])*(x[i][k]-average[k]);
            }
            var[k]=temp/(n-1);
        }
        //获得标准化的矩阵
        for(int i=0;i<n;i++){
            for(int j=0;j<p;j++){
                result[i][j]=(double) ((x[i][j]-average[j])/Math.sqrt(var[j]));
            }
        }
        return result;

    }
    /**
     * 计算样本相关系数矩阵
     * @param x 处理后的标准矩阵
     * @return 系数矩阵
     * */
    public double[][] CoefficientOfAssociation(double[][] x){
        int n=x.length;		//二维矩阵的行号
        int p=x[0].length;	//二维矩阵的列号
        double[][] result=new double[p][p];//相关系数矩阵
        for(int i=0;i<p;i++){
            for(int j=0;j<p;j++){
                double temp=0;
                for(int k=0;k<n;k++){
                    temp+=x[k][i]*x[k][j];
                }
                result[i][j]=temp/(n-1);
            }
        }
        return result;

    }
    /**
     * 计算相关系数矩阵的特征值
     * @param x 相关系数举证
     * @return 矩阵特征值
     * */
    public double[][] FlagValue(double[][] x){
        //定义一个矩阵
        Matrix A = new Matrix(x);
        //由特征值组成的对角矩阵
        Matrix B=A.eig().getD();
        double[][] result=B.getArray();
        return result;


    }
    /**
     * 计算相关系数矩阵的特征向量
     * @param x 相关系数举证
     * @return 矩阵特向量
     * */
    public double[][] FlagVector(double[][] x){
        //定义一个矩阵
        Matrix A = new Matrix(x);
        //由特征向量组成的对角矩阵
        Matrix B=A.eig().getV();
        double[][] result=B.getArray();

        return result;


    }
    /**
     * 假设阈值是90%，选取最大的前几个
     * @param x 特征值
     * @return 选取前N个主成分
     * */
    public int[] SelectPrincipalComponent(double[][] x){
        int n=x.length;		//二维矩阵的行号,列号
        double[] a = new double[n];
        int[] result = new int[n];
        int k=0;
        double temp = 0;
        int m=0;
        double total=0;
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                if(i==j){
                    a[k]=x[i][j];
                }
            }
            k++;
        }
        double[] temp1=new double[a.length];

        System.arraycopy(a, 0, temp1, 0, a.length);
        for(int i=0;i<n;i++){
            temp=temp1[i];
            for(int j=i;j<n;j++){
                if(temp<=temp1[j]){
                    temp=temp1[j];
                    temp1[j]=temp1[i];
                }

                temp1[i]=temp;
            }
        }
        for(int i=0;i<n;i++){
            temp=a[i];
            for(int j=0;j<n;j++){
                if(a[j]>=temp){
                    temp=a[j];

                    k=j;

                }
                result[m]=k;

            }
            a[k]=-1000;

            m++;
        }
        for(int i=0;i<n;i++){
            total+=temp1[i];
        }
        System.out.println("total-------------"+total);
        int sum=1;
        temp=temp1[0];
        for(int i=0;i<n;i++){
            if(temp/total<=1){
                temp+=temp1[i+1];
                sum++;
            }
        }
        int[] end=new int[sum];
        System.arraycopy(result, 0, end, 0, sum);

        return end;

    }
    /**
     * 取得主成分
     * @param x 特征向量
     * @param y 选取的主成分
     *
     * @return 主成分矩阵
     * */
    public double[][] PrincipalComponent(double[][] x,int[] y){
        int n=x.length;
        double[][] Result=new double[n][y.length];
        int k=y.length-1;
        for(int i=0;i<y.length;i++){
            for(int j=0;j<n;j++){
                Result[j][i]=x[j][y[k]];
            }
            k--;
        }
        return Result;
    }


    public Matrix computePCA(double [][]data){
        Date date=new Date();
        double[][] Standard=this.Standardlizer(data);
        System.out.println("数据标准化完毕...");
        double[][] Assosiation=this.CoefficientOfAssociation(Standard);
        System.out.println("(m,n):("+Assosiation.length+","+Assosiation[0].length+")");
        System.out.println("样本相关系数矩阵计算完毕...");
        double[][] FlagValue=this.FlagValue(Assosiation);
        System.out.println("(m,n):("+FlagValue.length+","+FlagValue[0].length+")");
        System.out.println("样本相关系数矩阵的特征值计算完毕...");
        double[][] FlagVector=this.FlagVector(Assosiation);
        System.out.println("(m,n):("+FlagVector.length+","+FlagVector[0].length+")");
        System.out.println("样本相关系数矩阵的特征向量计算完毕...");
        int[] xuan=this.SelectPrincipalComponent(FlagValue);
        System.out.println("(size):("+xuan.length+")");
        System.out.println("获取主成分前N个...");
        double[][] result=this.PrincipalComponent(FlagVector, xuan);
        System.out.println("(m,n):("+result.length+","+result[0].length+")");
        System.out.println("主成分矩阵生成完毕...");
        Matrix A=new Matrix(data);
        Matrix B=new Matrix(result);
        Matrix C=A.times(B);
        System.out.println("(m,n):("+C.getRowDimension()+","+C.getColumnDimension()+")");
        Date date1=new Date();
        long diff=date1.getTime()-date.getTime();
        System.out.println("计算总时间："+(diff/1000.0/60.0));
        return C;
    }

    public static void main(String args[]){
        double [][]data={{149.3,4.2,108.1,10,12,13,1,2,1.1,1.2,149.3,4.2,108.1,10,12,13,1,2,1.1,1.2},
                {161.2,4.1,114.8,3,5,8,2,3,1.1,1.2,161.2,4.1,114.8,3,5,8,2,3,1.1,1.2},
                {171.5,3.1,123.2,20,21,22,4,5,1.1,1.2,171.5,3.1,123.2,20,21,22,4,5,1.1,1.2},
                {175.5,3.1,126.9,7,8,9,6,7,1.1,1.2,175.5,3.1,126.9,7,8,9,6,7,1.1,1.2},
                {180.8,1.1,132.1,14,15,16,8,9,1.1,1.2,180.8,1.1,132.1,14,15,16,8,9,1.1,1.2},
                {190.7,2.2,137.7,10,6,5,10,6,1.1,1.2,190.7,2.2,137.7,10,6,5,10,6,1.1,1.2},
                {202.1,2.1,146.0,100,101,102,20,10,1.1,1.2,202.1,2.1,146.0,100,101,102,20,10,1.1,1.2},
                {212.4,5.6,154.1,4,5,9,12,23,1.1,1.2,212.4,5.6,154.1,4,5,9,12,23,1.1,1.2},
                {226.1,5.0,162.3,2,3,5,11,22,1.1,1.2,226.1,5.0,162.3,2,3,5,11,22,1.1,1.2},
                {231.9,5.1,164.3,5,8,0,12,32,1.1,1.2,231.9,5.1,164.3,5,8,0,12,32,1.1,1.2},
                {239.0,0.7,167.6,6,8,7,14,25,1.1,1.2,239.0,0.7,167.6,6,8,7,14,25,1.1,1.2}};
        PCA test = new PCA();
        Matrix C=test.computePCA(data);
        C.print(0,3);
        double[][] c = C.getArray();
        Double[][] res = new Double[c.length][c[0].length];
        DecimalFormat var4 = new DecimalFormat("#.####");
        for(int i = 0;i<c.length;i++)
        {
            for(int j=0;j<c[0].length;j++) {
                res[i][j] = Double.parseDouble(var4.format(c[i][j]));
                System.out.print(res[i][j] + " ");
            }
            System.out.println();
        }
        //System.out.println(B.eig().getD().get(0,0));
    }
}