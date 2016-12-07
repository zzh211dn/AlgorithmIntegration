/**
 * 
 */
package com.shanghaiUni.smooth;

/**
 * @author Administrator
 * 
 */
public class SmoothCommonFunction {
	static final double PRECISION=0.00001;
	static final int W=1;
	static double getaverage(double src[],int length)
	{
		double sum=0;
		for(int i=0;i<length;i++)
		{
			sum+=src[i];
		}
		sum/=length;
		return sum;
	}
	static double getS(double src[],double average,int length)
	{
		double S=0;
		for(int i=0;i<length;i++)
		{
			S+=((src[i]-average)*(src[i]-average));
		}
		S/=(length-1);
		return Math.sqrt(S);
	}
	 static void  process(double[] x_point,double[] y_point,double[] result,int N,int T) 
	{ 
			//double   x_y[N][2],matrix_A[N][T+1],trans_A[T+1][N],coef_A[T+1][T+1],coef_formu[T+1][T+2],y[T+1],   a[T+1]; 
			double[][] x_y=new double[N][2];
			double[][] matrix_A=new double[N][T+1];
			double[][] trans_A=new double[T+1][N];
			double[][] coef_A=new double[T+1][T+1];
			double[][] coef_formu=new double[T+1][T+2];
			double[] y=new double[T+1];
			//double[] a=new double[T+1];
			
			init(x_y	,x_point,   y_point,	N); 
			get_A(matrix_A	,	x_y,N,T,N); 
			//printf( "矩阵A为：\n "); 
			//print_array(matrix_A,N); 
			matrix_trans(matrix_A	,	trans_A,T,N); 
			mutiple(trans_A	,	matrix_A	,	coef_A,T,N); 
			//printf( "法矩阵为：\n "); 
			//print_array(coef_A,T+1); 
			get_y(trans_A	,	x_y	,	y	,	T+1,N); 
			cons_formula(coef_A	,	y	,	coef_formu,T); 
			convert(coef_formu	,	T+1,T); 
			compute(coef_formu	,	T+1	,	result,T); 
			//compute(coef_formu	,	T+1	,	a); 
			//*return(a); 
		} 
	 
		
	static	void  mutiple(double   a[][],double   b[][],double   c[][],int T,int N) 
	{ 
		double   res=0; 
		int   i,j,k; 
		for(i=0;i <T+1;i++) 
		{
			for(j=0;j <T+1;j++) 
			{ 
				res=0; 
				for(k=0;k <N;k++) 
				{ 
					res+=a[i][k]*b[k][j]; 
					c[i][j]=res; 
				}	
			}
		}
	} 

	static void  matrix_trans(double   a[][],double   b[][],int T,int N) 
	{ 
		int   i,j; 
		for(i=0;i <N;i++) 
		{ 
			for(j=0;j <T+1;j++) 
			{ 
				b[j][i]=a[i][j]; 
			} 
		} 
	} 

	static void  init(double   x_y[][],double   x[],double   y[],int   n) 
	{ 
		int   i; 
		for(i=0;i <n;i++) 
		{ 
			x_y[i][0]=x[i]; 
			x_y[i][1]=y[i]; 
		} 
	} 

	static void  get_A(double   matrix_A[][],double   x_y[][],int   n,int T,int N) 
	{ 
		int   i,j; 
		for(i=0;i <N;i++) 
		{ 
			for(j=0;j <T+1;j++) 
			{ 
				matrix_A[i][j]=W*Math.pow(x_y[i][0],j); 
			} 
		} 
	} 

	static void  print_array(double   array[][],int   n,int T) 
	{ 
		int   i,j; 
		for(i=0;i <n;i++) 
		{ 
			for(j=0;j <T+1;j++) 
			{ 
				System.out.print(array[i][j]);
				System.out.print(' ');
			} 
				System.out.println();
		} 
	} 

	static void  convert(double  argu[][],int   n,int T) 
	{ 
		int   i,j,k,p,t; 
		double   rate,temp; 
		for(i=1;i <n;i++) 
		{ 
			for(j=i;j <n;j++) 
			{ 
				if(argu[i-1][i-1]==0) 
				{ 
					for(p=i;p <n;p++) 
					{ 
						if(argu[p][i-1]!=0) 
								break; 
					} 
					if(p==n) 
					{ 
						System.out.println( "方程组无解! "); 
						return; 
					} 
					for(t=0;t <n+1;t++) 
					{ 
						temp=argu[i-1][t]; 
						argu[i-1][t]=argu[p][t]; 
						argu[p][t]=temp; 
					} 
				} 
				rate=argu[j][i-1]/argu[i-1][i-1]; 
				for(k=i-1;k <n+1;k++) 
				{ 
					argu[j][k]-=argu[i-1][k]*rate; 
					if(Math.abs(argu[j][k]) <=PRECISION) 
						argu[j][k]=0; 
				} 
			} 
		} 
	} 

	static	void  compute(double   argu[][],int   n,double   root[],int T) 
	{ 
		int   i,j; 
		double temp; 
		for (i=n-1;i>=0;i--)
		{
			temp=argu[i][n]; 
			for(j=n-1;j> i;j--) 
			{ 
				temp-=argu[i][j]*root[j]; 
			} 
			root[i]=temp/argu[i][i]; 
		}
	} 

	static	void  get_y(double   trans_A[][],double   x_y[][],double   y[],int   n,int N) 
	{ 
		int   i,j; 
		double   temp; 
		for(i=0;i <n;i++) 
		{ 
			temp=0; 
			for(j=0;j <N;j++) 
			{ 
				temp+=trans_A[i][j]*x_y[j][1];	
			} 
			y[i]=temp; 
		} 
	} 

	static	void  cons_formula(double   coef_A[][],double   y[],double   coef_form[][],int T) 
	{ 
		int   i,j; 
		for(i=0;i <T+1;i++) 
		{ 
			for(j=0;j <T+2;j++) 
			{ 
				if(j==T+1) 
					coef_form[i][j]=y[i]; 
				else 
					coef_form[i][j]=coef_A[i][j]; 
			} 
		} 
	} 

	static	void  print_root(double   a[],int   n,int N,int T) 
	{ 
		int   i,j; 
		System.out.println( N+"个点的"+T+"次拟合的多项式系数为:"); 
		for(i=0;i <n;i++) 
		{ 
			System.out.print( "a["+(i+1)+"]="+a[i]+",	 "); 
		} 
		System.out.println();
		System.out.println( "拟合曲线方程为:\ny(x)= "+a[0]); 
		for(i=1;i <n;i++) 
		{ 
			System.out.print( "   +   "+a[i]); 
			for(j=0;j <i;j++) 
			{ 
				System.out.print( "*X "); 
			} 
		} 
		System.out.println(); 
	} 
	static void  getaverage(double src[][],double dst[],int row,int col)
	{
		for(int i=0;i<col;i++)
		{
			dst[i]=0;
			for(int j=0;j<row;j++)
			{
				dst[i]+=src[j][i];
			}
			dst[i]/=row;
		}
	}
}
