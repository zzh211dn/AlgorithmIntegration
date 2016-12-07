/**
 * 
 */
package com.shanghaiUni.smooth;

import com.shanghaiUni.common.PretreatmentSmoothOne;

/**
 * @author Administrator
 * BSpline小波
 */
public class BSpline extends PretreatmentSmoothOne {
	/**
	 * 采样最小数值
	 */
	double min=-6;
	/**
	 * 采样最大值
	 */
	double max=6;
	/**
	 * 频率阶数
	 */
	double M=2;
	/**
	 * 带宽
	 */
	double FB=1;
	/**
	 * 频率中心
	 */
	double FC=0.5;
	/**
	 * 滤波器的长度
	 */
	int waveLength=101;
	@ Override
	public boolean Init(String[] Parameter) 
    {
		try
		{			
			waveLength=Integer.parseInt(Parameter[0]);
			min=Double.parseDouble(Parameter[1]);
			max=Double.parseDouble(Parameter[2]);
			M=Double.parseDouble(Parameter[3]);
			FB=Double.parseDouble(Parameter[4]);
			FC=Double.parseDouble(Parameter[5]);	
			
			if(waveLength%2==0)
			{
				waveLength+=1;
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
	public String TransformParameter(String[] Parameter)
	{
		 return "BSpline"+"\n"+ 
				 Parameter[1]+"," +
				 Double.toString(min) +"," +
				 Double.toString(max) + "," +
				 Double.toString(M) + "," +
				 Double.toString(FB) +"," +
				 Double.toString(FC) + "," ;
					
	}	
	/**
	 * @param OriginSpect 光谱的原始数据
	 * @param ResultSpect 计算结果
	 * @param OriginWave 光谱的波长
	 * @param SpectLength 光谱长度
	 * @return
	 */
	@ Override
	public int ComputeOneSpectrum(double[] OriginSpect,double[] ResultSpect, double[] OriginWave, int SpectLength)
	{
		return BSplineWavelet(OriginSpect,ResultSpect,SpectLength,waveLength,
				min,max,M,FB,FC);
	}
	/**
	 * @param originSpect 原始数据
	 * @param computeSpect 计算结果
	 * @param spectNum 数据长度
	 * @param waveLength 滤波器的长度,该长度是一个奇数，以方便取样对称
	 * @param min 采样最小数值
	 * @param max 采样最大值
	 * @param M 频率阶数
	 * @param FB 带宽
	 * @param FC 频率中心
	 * @return
	 * 0,正确返回，数据计算成功
	 *	-1,错误返回，原始数据为空
	 *	-2，错误返回，传入的返回指针为空
	 *	-3，错误返回，传入数据长度小于或等于0
	 *	-4，错误返回，传入计算小波函数参数错误
	 *	-5，错误返回，传入离散点数不对称，即不为奇数
	 *	-6，错误返回，传入原始数据长度小于告知长度
	 *	-7，错误返回，传入返回结果长度小于告知长度
	 */
	private int BSplineWavelet(double originSpect[],double computeSpect[],int spectNum,int waveLength,
			double min,double max,double M,double FB,double FC)
	{
		if(originSpect==null)
			return -1;
		if(computeSpect==null)
			return -2;
		if(spectNum<=0)
			return -3;
			
		
		//double real[]=new double[waveLength];
		
		//double sqrt2=Math.sqrt(2);
		double real[]=new double[waveLength];
		double imag[]=new double[waveLength];
		if(!calSpline(real,imag,min,max,M,FB,FC))
			return -4;
		//double real[]={sqrt2/4,sqrt2/2,sqrt2/4};
		return wavelet(real,imag,originSpect,computeSpect,spectNum);

	}
	

	private boolean calSpline(double real[],double imag[],double min,double max,double M,double fb,double fc)
	{
		
		if(real.length!=imag.length)
			return false;
		int numPoints=real.length;
		double temp1=Math.sqrt(fb);
		double step=(max-min)/numPoints;
		for(int i=0;i<numPoints;i++)
		{
			double nowValue=min+i*step;
			double temp2=Math.pow(sinc(fb*nowValue/M), M);
			double temp3Real=Math.cos(2*Math.PI*fc*nowValue);
			double temp3Imag=Math.sin(2*Math.PI*fc*nowValue);
			real[i]=temp1*temp2*temp3Real*step;
			imag[i]=temp1*temp2*temp3Imag*step;
		}
		
		return true;
	}
	
	double sinc(double x)
	{
		double temp=Math.PI*x;
		return Math.sin(temp)/temp;
	}
	
	private int wavelet(double real[],double imag[],double data[],double result[],int dataLength)
	{

		double resultReal[]=new double [result.length];
		double resultImag[]=new double [result.length];
		int ret1=Morlet.filter(real, data, resultReal, dataLength);
		if(ret1!=0)
			return ret1;
		int ret2=Morlet.filter(imag, data, resultImag, dataLength);
		if(ret2!=0)
			return ret2;
		for(int i=0;i<result.length;i++)
		{
			result[i]=Math.sqrt(resultReal[i]*resultReal[i]+resultImag[i]*resultImag[i]);
		}
		return 0;

	}
	@ Override
	public String CommentSelf(String[] Parameter)
	{
		return "BSpline小波\n" + 
				"滤波器的长度:" + Parameter[0];
	}
	@ Override
	public String CheckParameter(String[] Parameter,String Info)
	{
		int ps=Integer.parseInt(Parameter[0]);
		
		if(ps<101||ps%2==0)
		{
			Info="BSpline小波滤波器的长度必须是大于101的正奇数";
			return Info;
		}		
		else
		{
			return "";
		}
	}
}
