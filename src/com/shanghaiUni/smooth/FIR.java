/**
 * 
 */
package com.shanghaiUni.smooth;

import com.shanghaiUni.common.PretreatmentSmoothOne;

/**
 * @author Administrator
 * 有限脉冲的响应算法初始化计算滤波系数函数
 */
public class FIR extends PretreatmentSmoothOne {
	/**
	 *  滤波系数长度
	 */
	int order = 32;
	/**
	 * 滤波系数
	 */
	double[] H;
	@ Override
	public String TransformParameter(String[] Parameter)
	{
		try
		{		
			return "FIR\n"+Parameter[1]+",";			
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
		return "有限脉冲的响应算法\n" + 
			   "滤波系数长度 :" + Parameter[0];
	}
	@ Override
	public String CheckParameter(String[] Parameter,String Info)
	{
		int ps=Integer.parseInt(Parameter[0]);
		if(ps<3)
		{
			Info="有限脉冲的响应算法的滤波系数长度大于3";
		}
		else
		{
			Info="";
		}
		return Info;
	}
	@ Override
	public boolean Init(String[] Parameter)
	{
		try
		{
			if (Parameter.length >0)
			{
				order=Integer.parseInt(Parameter[0]);
			}
			H=new double[order+1];
			
			if (calH(100,1000,3,500,1)!= 0)
			{
				return false;
			}	
			
			return true;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
		
	}
	@ Override
	public int ComputeOneSpectrum(double[] OriginSpect,double[] ResultSpect, double[] OriginWave, int SpectLength) 
	{   
		if (OriginSpect==null)
		{
			return -1;
		}
		if(ResultSpect==null)
		{
			return -2;
		}
		if(SpectLength<=0)
		{
			return -3;
		}
		if(H==null)
		{
			return -4;
		}
		if(order<=0)
			return -5;
		for(int i=0;i<SpectLength;i++)
		{
			ResultSpect[i]=0;
			for(int j=i;j>=0 && j>=i-order;j--)
			{
				
				ResultSpect[i]+=OriginSpect[j]*H[i-j];
			}
		}
		return 0;
			
	}
	/**
	 * @param lowF 最低频率
	 * @param highF 最高频率
	 * @param filterType 滤波器类型
	 * @param samplingF 采样频率
	 * @param windowType 窗口类型
	 * @return
	 */
	private int calH(double lowF,double highF,int filterType,double samplingF,int windowType)
	{
		if(filterType >4 || filterType<1)
			return -1;
		if(H==null)
			return -2;
		if(filterType==3 || filterType==4)
		{
			if(lowF<0 || lowF>highF )
				return -3;
		}
		if(filterType==1 && lowF<0)
			return -3;
		if(filterType==2 && highF<=0)
			return -3;
		if(windowType<0 || windowType>=7)
			return -4;
		lowF/=samplingF;
		highF/=samplingF;
		firwin(order,filterType,lowF,highF,windowType,H);
		return 0;
			
	}
	private void firwin(int n,int band,double fln,double fhn,double wn,double h[])
	{
		int i,n2,mid;
		double s,pi,wc1,wc2=0,beta,delay;


		beta=0;
		/*if(wn==7)
		{
			printf("input beta parameter of Kaiser window ( 3 <beta  <10) \n");
			scanf("%lf",&beta);

		}*/
		pi=4.0*Math.atan(1.0);
		if((n%2)==0)
		{
			n2=n/2-1;
			mid=1;
		}
		else
		{
			n2=n/2;
			mid=0;
		}
		delay=n/2.0;	
		wc1=2.0*pi*fln;
		if(band>=3)
			wc2=2.0*pi*fhn;
		switch(band)
		{
		case 1: 
			for(i=0;i<=n2;i++)
			{
				s=i-delay;
				h[i]=(Math.sin(wc1*s)/(pi*s))*window((int)wn,n+1,i,beta);
				h[n-i]=h[i];	
			}
			if(mid==1)
				h[n/2]=wc1/pi;

			break;
		case 2:
			for(i=0;i<=n2;i++)
			{
				s=i-delay;
				h[i]=(Math.sin(pi*s)-Math.sin(wc1*s))/(pi*s);
				h[i]=h[i]*window((int)wn,n+1,i,beta);
				h[n-i]=h[i];
			}
			if(mid==1)
				h[n/2]=1.0-wc1/pi;
			break;
		case 3: 
			for(i=0;i<=n2;i++)
			{
				s=i-delay;
				h[i]=(Math.sin(wc2*s)-Math.sin(wc1*s))/(pi*s);
				h[i]=h[i]*window((int)wn,n+1,i,beta);
				h[n-i]=h[i];
			}
			if(mid==1)
				h[n/2]=(wc2-wc1)/pi;
			break;
		case 4: 
			for(i=0;i<=n2;i++)
			{
				s=i-delay;
				h[i]=(Math.sin(wc1*s)+Math.sin(pi*s)-Math.sin(wc2*s))/(pi*s);
				h[i]=h[i]*window((int)wn,n+1,i,beta);
				h[n-i]=h[i];
			}
			if(mid==1)
				h[n/2]=(wc1+pi-wc2)/pi;
			break;
		default: ;		
		}
	}
	private double window(int type,int n,int i,double beta)
	{
		int k;
		double pi,w;

		pi=4.0*Math.atan(1.0);
		w=1.0;
		switch(type)
		{
		case 1:
			w=1.0;
			break;
		case 2:
			k=(n-2)/10;
			if(i<=k)
				w=0.5*(1.0-Math.cos(i*pi/(k+1)));
			if(i>n-k-2)
				w=0.5*(1.0-Math.cos((n-i-1)*pi/(k+1)));	
			break;
		case 3:
			w=1.0-Math.abs(1.0-2*i/(n-1.0));
			break;
		case 4:
			w=0.5*(1.0-Math.cos(2*i*pi/(n-1)));
			break;
		case 5:
			w=0.54-0.46*Math.cos(2*i*pi/(n-1));
			break;
		case 6:
			w=0.42-0.5*Math.cos(2*i*pi/(n-1))+0.08*Math.cos(4*i*pi/(n-1));
			break;
		case 7:
			w=kaiser(i,n,beta);
			break;
		default: ;	  
		}
		return (w);
	}
	private double kaiser(int i,int n,double beta)
	{
		double a,w,a2,b1,b2,beta1;

		b1=bessel0(beta);
		a=2.0*i/(double)(n-1)-1.0;
		a2=a*a;
		beta1=beta*Math.sqrt(1.0-a2);
		b2=bessel0(beta1);
		w=b2/b1;
		return (w);
	}

	private double bessel0(double x )
	{
		int i;
		double d,y,d2,sum;

		y=x/2.0;
		d=1.0;
		sum=1.0;
		for(i=1;i<=25;i++)
		{
			d=d*y/i;
			d2=d*d;
			sum=sum+d2;
			if(d2<sum*(1.0e-8))
				break;
		}
		return (sum);
	}
}
