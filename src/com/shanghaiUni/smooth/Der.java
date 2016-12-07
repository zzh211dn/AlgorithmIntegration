/**
 * 
 */
package com.shanghaiUni.smooth;

import com.shanghaiUni.common.PretreatmentSmoothBatchOld;

/**
 * @author Administrator
 * 导数
 */
public class Der extends PretreatmentSmoothBatchOld {
	private int DerType = 1;
	
	public boolean Init(String[] Parameter) 
	{
		DerType = Integer.parseInt(Parameter[0]);
		return true;
	}
	@ Override
	public boolean ComputeBatch(double[] OriginWave, double[] OriginSpect,double[] ResultSpect, int SpectNum, int SpectLength)
	{

	double x[], y[], dy[], ddy[];
    double t[], z[], dz[], ddz[]; 
    int i, j;

    x =new double[SpectLength];
    y=new double[SpectLength];
    dy =new double[SpectLength];
    ddy = new double[SpectLength];
    t = new double[SpectLength];
    z = new double[SpectLength];
    dz =new double[SpectLength];
    ddz =new double[SpectLength];

    for (j = 0 ;j < SpectLength ; j++)
    {
        x[j] = OriginWave[j];
        t[j] = OriginWave[j];
    }


   
    for (i = 0 ;i< SpectNum;i++)
    {
        for (j = 0 ;j < SpectLength; j++)
        {
            y[j] = OriginSpect[j * SpectNum + i];
            dy[j] = 0.0;
            ddy[j] = 0.0;
        }
        CInterpolateGetValueSpline1(SpectLength, x, y, dy, ddy, SpectLength, t, z, dz, ddz);
        switch (DerType)
        {

            case 1: //原始数据一阶导数
                for (j = 0;j < SpectLength; j++)
                {
                    ResultSpect[j * SpectNum + i] = dy[j];
                }
            	break;
            case 2: //原始数据二阶导数
                for (j = 0; j < SpectLength; j++)
                {
                    ResultSpect[j * SpectNum + i] = ddy[j];
                }
            	break;
        }
    		}
    return true;
	}
	// n     输入的光谱点数，必须有值
	// x[]   输入的光谱点对应的波长，必须有值
	// y[]   输入的光谱点对应的响应，必须有值
	// dy[]  输入的光谱点对应的一阶导数，可以为空，也可以给定值
	// ddy[] 输入的光谱点对应的二阶导数，可以为空，也可以给定值
	// m     输出的光谱点数，可以为原光谱点数，也可以自行定义，必须有值
	// t[]   输出的光谱点对应的波长，必须有值
	// z[]   输出的光谱点对应的响应，为空，计算后赋值
	// dz[]  输入的光谱点对应的一阶导数，为空，计算后赋值
	// ddz[] 输入的光谱点对应的二阶导数，为空，计算后赋值
	double CInterpolateGetValueSpline1(int n, double x[], double y[], double dy[], double ddy[], int m, double t[], double z[], double dz[], double ddz[])
	{
		int i,j;
		double h0,h1,alpha,beta,g; 
		double[] s;

		//初值
		s=new double[n];
		s[0]=dy[0];
		dy[0]=0.0;
		h0=x[1]-x[0];

		//初值再设
		//dy[0]=1.86548;
		//dy[n-1]=-0.046115;

		for(j=1;j<=n-2;j++)
		{
			h1=x[j+1]-x[j];
			alpha=h0/(h0+h1);
			beta=(1.0-alpha)*(y[j]-y[j-1])/h0;
			beta=3.0*(beta+alpha*(y[j+1]-y[j])/h1);
			dy[j]=-alpha/(2.0+(1.0-alpha)*dy[j-1]);
			s[j]=(beta-(1.0-alpha)*s[j-1]);
			s[j]=s[j]/(2.0+(1.0-alpha)*dy[j-1]);
			h0=h1;
		}

		for(j=n-2;j>=0;j--)
		{
			dy[j]=dy[j]*dy[j+1]+s[j];
		}

		for(j=0;j<=n-2;j++)
		{
			s[j]=x[j+1]-x[j];
		}

		for(j=0;j<=n-2;j++)
		{
			h1=s[j]*s[j];
			ddy[j]=6.0*(y[j+1]-y[j])/h1-2.0*(2.0*dy[j]+dy[j+1])/s[j];
		}

		h1=s[n-2]*s[n-2];
		ddy[n-1]=6.0*(y[n-2]-y[n-1])/h1+2.0*(2.0*dy[n-1]+dy[n-2])/s[n-2];
		g=0.0;

		for(i=0;i<=n-2;i++)
		{
			h1=0.5*s[i]*(y[i]+y[i+1]);
			h1=h1-s[i]*s[i]*s[i]*(ddy[i]+ddy[i+1])/24.0;
			g=g+h1;
		}

		for(j=0;j<=m-1;j++)
		{
			if(t[j]>=x[n-1])
				i=n-2;
			else
			{
				i=0;
				while(t[j]>=x[i+1])
					i=i+1;
			}

			h1=(x[i+1]-t[j])/s[i];
			h0=h1*h1;
			z[j]=(3.0*h0-2.0*h0*h1)*y[i];
			z[j]=z[j]+s[i]*(h0-h0*h1)*dy[i];
			dz[j]=6.0*(h0-h1)*y[i]/s[i];
			dz[j]=dz[j]+(3.0*h0-2.0*h1)*dy[i];
			ddz[j]=(6.0-12.0*h1)*y[i]/(s[i]*s[i]);
			ddz[j]=ddz[j]+(2.0-6.0*h1)*dy[i]/s[i];
			
			h1=(t[j]-x[i])/s[i];
			h0=h1*h1;

			z[j]=z[j]+(3.0*h0-2.0*h0*h1)*y[i+1];
			z[j]=z[j]-s[i]*(h0-h0*h1)*dy[i+1];
			dz[j]=dz[j]-6.0*(h0-h1)*y[i+1]/s[i];
			dz[j]=dz[j]+(3.0*h0-2.0*h1)*dy[i+1];
			ddz[j]=ddz[j]+(6.0-12.0*h1)*y[i+1]/(s[i]*s[i]);
			ddz[j]=ddz[j]-(2.0-6.0*h1)*dy[i+1]/s[i];
		}
		
		return(g);
	}
	@ Override
	public String TransformParameter(String[] Parameter)
	{
		try
		{
			if(Parameter[0].equals("Der1"))
			{
				return "Der"+"\n"+"1,";
			}
			else if (Parameter[0].equals("Der2"))
			{
				return "Der"+"\n"+"2,";				
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
		if(Parameter[0].equals("1"))
		{
			return "一阶导数\n" ;
		}
		else
		{
			return "二阶导数\n" ;
		}
		
	}
}
