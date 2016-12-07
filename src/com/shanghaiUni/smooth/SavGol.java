/**
 * 
 */
package com.shanghaiUni.smooth;

import java.math.BigDecimal;

import com.shanghaiUni.common.PretreatmentSmoothBatchOld;

/**
 * @author Administrator
 *
 */
public class SavGol extends PretreatmentSmoothBatchOld {

	/**
	 * 点数
	 */
	private int nu;
    /**
     * 阶数0， 平滑；1，一阶；2，二阶
     */
    private int ld;
    /**
     * m=2，平滑；m=3，导数
     */
    private int m;
    private double TINY=1.0e-20;
    @ Override
    public boolean Init(String[] Parameter) 
    {
    	try
    	{
    		nu = Integer.parseInt(Parameter[0]);
    		m = Integer.parseInt(Parameter[1]);
    		ld = Integer.parseInt(Parameter[2]);
    		return true;
    	}
    	catch (Exception ex)
    	{
    		ex.printStackTrace();
    		return false;
    	}
    }
    @ Override
    public String TransformParameter(String[] Parameter)
	{
		try
		{
			if(Parameter[0].equals("SavGol1"))
			{
				return "SavGol"+"\n"+
						Parameter[1]+",2,0,";			
			}
			else if (Parameter[0].equals("SavGol2"))
			{
				return "SavGol"+"\n"+
						Parameter[1]+",3,1,";	
			}
			else if (Parameter[0].equals("SavGol3"))
			{
				return "SavGol"+"\n"+
						Parameter[1]+",3,2,";	
			}
			else
			{
				return "";
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return "";
		}    	
	}
    @ Override
	public String CommentSelf(String[] Parameter)
	{
    	if(Parameter[1].equals("2"))
		{
			return "SavGol平滑"+"\n"+
					"点数： " + Parameter[0];			
		}
		else 
		{
			if (Parameter[2].equals("1"))
			{
				return "SavGol一阶导数"+"\n"+
						"点数： " + Parameter[0];
			}
			else
			{
				return "SavGol二阶导数"+"\n"+
						"点数： " + Parameter[0];
			}
		}		
	}
    @ Override
	public boolean ComputeBatch(double[] waveNum, double[] originSet,double[] resultSet, int SpectNum, int SpectLength)
	{
		
		SavGolMethod(originSet,resultSet,SpectNum,SpectLength,(nu - 1) / 2, (nu - 1) / 2, m, ld);
		return true;
	}
    @ Override
	public String CheckParameter(String[] Parameter,String Info)
	{
		int ps=Integer.parseInt(Parameter[0]);
		ld=Integer.parseInt(Parameter[2]);
		String prefix="SavGol";
		
		if(ld==0)
		{
			prefix+="平滑";
		}
		else if(ld==1)
		{
			prefix+="一阶导数";
		}
		else
		{
			prefix+="二阶导数";
		}
		
		Info+=CheckPositive(ps,prefix + "点数",Info);
		Info+=CheckOddNumber(ps,prefix + "点数",Info);
			
		return Info;
	}
	/**
	 * @param OriginSet
	 * @param SGampleSet
	 * @param NumSample
	 * @param NumValue
	 * @param nl 左边的点
	 * @param nr 右边的点
	 * @param m 几次项
	 * @param ld ld=0平滑，ld=1一阶导数，ld=2二阶导数
	 * @return
	 */
	private int SavGolMethod(double[] OriginSet,double[] SGampleSet,int NumSample,int NumValue,int nl,int nr,int m,int ld)
	{
		long i,j,k;

		int np=nl+nr+1;
		//long ld=1;
		//long m=4;
		float[] coef;
		double sum=0;

		//nl=2;
		//nr=2;
		np=nl+nr+1;
		coef=new float[1000];
		//m=3;
		SavGolCoef(coef,np,nl,nr,ld,m);

		/*
		BigDecimal b;
		for(k=nl+1;k>=1;k--)
		{
			b= new BigDecimal(coef[(int)k]);
			coef[(int)k]=b.setScale(7,BigDecimal.ROUND_HALF_UP).floatValue();
			//System.out.println(Float.toString(coef[(int)k]));
		}
		for(k=0;k<nr;k++)
		{
			b= new BigDecimal(coef[(int)(np-k)]);
			coef[(int)(np-k)]=b.setScale(7,BigDecimal.ROUND_HALF_UP).floatValue();
			//System.out.println(Float.toString(coef[(int)(np-k)]));
		}*/
		
		for(i=0;i<NumSample;i++)
		{
			for(j=nl;j<NumValue-nr;j++)
			{
				sum=0;
				for(k=nl+1;k>=1;k--)
				{
					sum+=coef[(int) k]*OriginSet[(int) ((j-k+1)*NumSample+i)];
				}
				for(k=0;k<nr;k++)
				{
					sum+=coef[(int) (np-k)]*OriginSet[(int) ((j+k+1)*NumSample+i)];
				}
				SGampleSet[(int) (j*NumSample+i)]=sum;
			}
		}
		
		return 1;
	}
	private void SavGolCoef(float[] c,int np,int nl,int nr,int ld,int m)
	{
		int imj,ipj,j,k,kk,mm;
		int[] indx;
		float d,fac,sum;
		float[] a;
		float[] b;
		
		//nrerror("bad args in savgol");
		indx=new int[(int) (m+2)];
		//a=new double[(m+2)*(m+2)];
		//b=new double[m+2];
		a=new float[(int) ((m+2)*(m+2))];
		b=new float[(int) (m+2)];
		//memset(a,0,sizeof(double)*(m+2)*(m+2));
		//memset(b,0,sizeof(double)*(m+2));
		int i;
		
		for (i =0; i < m+2; i++)
		{
			for (j=0; j<m+2; j++)
			{
				a[(int)(i*(m+2)+j)]=0;
			}
			b[i]=0;
			indx[i]=0;
		}
		
		for (ipj=0;ipj<=(m << 1);ipj++) 
		{
			
			if (ipj == 0) 
			{
	           sum = 1;
			}
	        else
	        {
	           sum = 0;
	        }
	           
			for (k=1;k<=nr;k++) 
			{
				sum += Math.pow((double)k,(double)ipj);				
			}
			for (k=1;k<=nl;k++) 
			{
				sum += Math.pow((double)-k,(double)ipj);
			}
			mm=FMIN(ipj,2*m-ipj);
			for (imj = -mm;imj<=mm;imj+=2) 
			{
				a[(int)((1+(ipj+imj)/2)*(m+2)+1+(ipj-imj)/2)]=sum;
			}
		}
		d=1;
		d=ludcmp(a,m+1,indx,d);
		for (j=1;j<=m+1;j++) 
		{
			b[j]=(float) 0.0;
		}
		b[(int) (ld+1)]=1;
		lubksb(a,m+1,indx,b);
		for (kk=1;kk<=np;kk++) 
		{
			c[(int) kk]=0;			
		}
		for (k = -nl;k<=nr;k++) 
		{
			sum=b[1];
			fac=1;
			for (mm=1;mm<=m;mm++) sum += b[(int) (mm+1)]*(fac *= k);
			kk=((np-k) % np)+1;
			c[(int) kk]=sum;
		}
		
	}
	private void lubksb(float[] a,int n,int[] indx, float[] b)
	{
		int i,ii=0,ip,j;
		float sum;

		for (i=1;i<=n;i++) 
		{
			ip=indx[i];
			sum=b[ip];
			b[ip]=b[i];
			if (ii == 0)
			{
				for (j=ii;j<=i-1;j++) 
				{
					sum -= a[i*(n+1)+j]*b[j];
				}
			}
			else if (sum == 0) 
			{
				ii=i;
			}
			b[i]=sum;
		}
		for (i=n;i>=0;i--) 
		{
			sum=b[i];
			for (j=i+1;j<=n;j++) 
			{
				sum -= a[i*(n+1)+j]*b[j];
			}
			//a[i*(n+1)+i]=1;
			if (a[i*(n+1)+i]==sum)
			{
				b[i]=1;
			}
			else
			{
				b[i]=sum/a[i*(n+1)+i];
			}
		}
	}
	private float ludcmp(float[] a,int n,int[] indx,float d)
	{
		int i,imax,j,k;
		float big,dum,sum,temp;
		float[] vv;

		imax=-9999;
		vv=new float[n+1];
		d=1;
		for (i=1;i<=n;i++) 
		{
			big=0;
			for (j=1;j<=n;j++)
				//if ((temp=fabs(a[i][j])) > big) big=temp;
			if ((temp=Math.abs(a[i*(n+1)+j])) > big) 
			{
				big=temp;				
			}
			if (big == 0.0) 
			{
				
			}
			vv[i]=(float) (1.0/big);
		}
		for (j=1;j<=n;j++) 
		{
			for (i=1;i<j;i++) 
			{
				//sum=a[i][j];
				sum=a[i*(n+1)+j];
				for (k=1;k<i;k++) 
				{
					sum -= a[i*(n+1)+k]*a[k*(n+1)+j];
				}
				a[i*(n+1)+j]=sum;
			}
			big=(float) 0.0;
			for (i=j;i<=n;i++) 
			{
				sum=a[i*(n+1)+j];
				for (k=1;k<j;k++)
				{
					sum -= a[i*(n+1)+k]*a[k*(n+1)+j];
				}
				a[i*(n+1)+j]=sum;
				if ( (dum=vv[i]*Math.abs(sum)) >= big) 
				{
					big=dum;
					imax=i;
				}
			}
			if (j != imax) 
			{
				for (k=1;k<=n;k++) 
				{
					dum=a[imax*(n+1)+k];
					a[imax*(n+1)+k]=a[j*(n+1)+k];
					a[j*(n+1)+k]=dum;
				}
				d = -d;
				vv[imax]=vv[j];
			}
			indx[j]=imax;
			if (a[j*(n+1)+j] == 0.0)
			{
				a[j*(n+1)+j]=(float)TINY;				
			}
			if (j != n) 
			{
				dum=(float) (1.0/(a[j*(n+1)+j]));
				for (i=j+1;i<=n;i++) 
				{
					a[i*(n+1)+j]*=dum;
				}
			}
		}
		return d;
	}
	private int FMIN(int a,int b)
	{
		if (a>b) 
		{
			return b;			
		}
		else 
		{
			return a;
		}
	}
	
}
