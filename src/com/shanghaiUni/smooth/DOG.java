/**
 * 
 */
package com.shanghaiUni.smooth;

import com.shanghaiUni.common.PretreatmentSmoothOne;

/**
 * @author Administrator
 * DOG小波
 */
public class DOG extends PretreatmentSmoothOne {
	/**
	 *  第一个高斯核方差
	 */
	double sigma1=2;
	/**
	 * 第二个高斯核方差
	 */
	double sigma2=8;
	/**
	 * 采样上限
	 */
	double max=7;
	/**
	 * 采样下限
	 */
	double min=-7;
	/**
	 * 采样点数
	 */
	int waveLength=100;
	@ Override
	public boolean Init(String[] Parameter) 
    {
		try
		{
			sigma1=Double.parseDouble(Parameter[0]);
			sigma2=Double.parseDouble(Parameter[1]);
			max=Double.parseDouble(Parameter[2]);
			min=Double.parseDouble(Parameter[3]);
			waveLength=Integer.parseInt(Parameter[4]);			
			return true;			
		}
		catch (Exception ex)
    	{
    		ex.printStackTrace();
    		return false;
    	}		
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
		return DOGWavelet(OriginSpect,ResultSpect,SpectLength,sigma1,sigma2,
				max,min,waveLength);
	}
	/**
	 * @param originSpect 原始数据
	 * @param computeSpect 计算结果
	 * @param spectNum 数据长度
	 * @param sigma1 第一个高斯核方差
	 * @param sigma2 第二个高斯核方差
	 * @param max 采样上限
	 * @param min 采样下限
	 * @param waveLength 采样点数
	 * @return
	 * 0,正确返回，数据计算成功
		-1,错误返回，原始数据为空
		-2，错误返回，传入的返回指针为空
		-3，错误返回，传入数据长度小于或等于0
		-4，错误返回，传入计算小波函数参数错误,sigma2应该比sigma1大
		-6，错误返回，传入原始数据长度小于告知长度
		-7，错误返回，传入返回结果长度小于告知长度
	 */
	private int DOGWavelet(double originSpect[],double computeSpect[],int spectNum,
			double sigma1,double sigma2,double max,double min,int waveLength)
	{
		if(originSpect==null)
			return -1;
		if(computeSpect==null)
			return -2;
		if(spectNum<=0)
			return -3;

		
		double real[]=new double[waveLength];
		if(calDOG(real,sigma1,sigma2,waveLength,max,min)==false)
			return -4;
		return wavelet(real,originSpect,computeSpect,spectNum);
	}
	private boolean calDOG(double real[],double sigma1,double sigma2,int waveLength,double max,double min)
	{
		if(sigma2<sigma1)
			return false;
		double step=(max-min)/waveLength;
		if(real.length<=0)
			return false;

		for(int i=0;i<waveLength;i++)
		{
			double nowValue=i*step+min;
			double temp1=1/(sigma1*Math.sqrt(2*Math.PI))*Math.exp(-nowValue*nowValue/(2*sigma1*sigma1));
			double temp2=1/(sigma2*Math.sqrt(2*Math.PI))*Math.exp(-nowValue*nowValue/(2*sigma2*sigma2));
			real[i]=(temp1-temp2)*step;
		}
		return true;
	}
	
	private int filter(double kernel[],double data[],double result[],int dataLength)
	{
	
		if(data.length<dataLength)
			return -6;
		if(result.length<dataLength)
			return -7;
		double newData[]=new double[kernel.length-1+dataLength];
		for(int i=0;i<newData.length;i++)
		{
			if(i<kernel.length/2 )
				newData[i]=data[0];
			else if(i>=kernel.length/2+dataLength)
				newData[i]=data[dataLength-1];
			else
				newData[i]=data[i-kernel.length/2];
				
		}
		for(int i=0;i<dataLength;i++)
		{
			result[i]=0;
			for(int j=-kernel.length/2;j<kernel.length/2;j++)
			{
				result[i]+=newData[kernel.length/2+j+i]*kernel[j+kernel.length/2];
			}
		}
		return 0;
	}
	
	private int wavelet(double real[],double data[],double result[],int dataLength)
	{
		return filter(real, data, result, dataLength);
	}
	 @ Override
	 public String TransformParameter(String[] Parameter)
	{
		 return "DOG"+"\n"+"2,8,7,-7," +
					Parameter[1]+",";
	}	 
	@ Override
	public String CommentSelf(String[] Parameter)
	{
		return "DOG小波\n" + 
				"采样点数 :" + Parameter[4];
	}
	@ Override
	public String CheckParameter(String[] Parameter,String Info)
	{
		int ps=Integer.parseInt(Parameter[4]);
		if(ps<100)
		{
			Info="DOG小波采样点数要大于100";
		}
		else
		{
			Info="";
		}
		return Info;
	}
}
