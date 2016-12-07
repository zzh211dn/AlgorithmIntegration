/**
 * 
 */
package com.shanghaiUni.smooth;

import com.shanghaiUni.common.PretreatmentSmoothOne;

/**
 * @author Administrator
 * Marr小波
 */
public class Marr extends PretreatmentSmoothOne {
	/**
	 * 滤波器长度，为保证取样对称，取样长度为奇数
	 */
	int FilterNum=281;
	/**
	 * 采样上限
	 */
	double max=7;
	/**
	 * 采样下限
	 */
	double min=-7;
	@ Override
	public boolean Init(String[] Parameter) 
    {
		try
		{
			FilterNum=Integer.parseInt(Parameter[0]);
			if(FilterNum%2==0)
			{
				FilterNum+=1;				
			}
			
			max=Double.parseDouble(Parameter[1]);
			min=Double.parseDouble(Parameter[2]);
		}
		catch (Exception ex)
    	{
    		ex.printStackTrace();
    		return false;
    	}
		return true;
    }
	@ Override
    public String TransformParameter(String[] Parameter)
	{
		try
		{			
				return "Marr"+"\n"+
						Parameter[1]+"," +
						Double.toString(max)+ "," +
						Double.toString(min)+ ",";		
			
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
		return "Marr小波\n" + 
				"滤波器长度: " + Parameter[0];	
	}
	@ Override
	public String CheckParameter(String[] Parameter,String Info)
	{
		int ps=Integer.parseInt(Parameter[0]);		
		
		if(ps<281 || ps%2==0)
		{
			Info="Marr小波的滤波器长度必须是大于281的正奇数";
		}
		else
		{
			Info="";
		}
		
		return Info;
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
		return MarrWavelet(OriginSpect,ResultSpect,SpectLength,FilterNum,min,max);
	}
	
	/************************************************************************/
	/**
	 * @param originSpect 原始数据
	 * @param computeSpect 原始数据和计算结果数据长度
	 * @param spectNum 计算结果
	 * @param waveLength 滤波器长度
	 * @param min 采样下限
	 * @param max 采样上限
	 * @return
	 * 0,正确返回，数据计算成功
		-1,错误返回，原始数据为空
		-2，错误返回，传入的返回指针为空
		-3，错误返回，传入数据长度小于或等于0
		-4，错误返回，传入计算小波函数参数错误
		-5，错误返回，传入离散点数不对称，即不为奇数
		-6，错误返回，传入原始数据长度小于告知长度
		-7，错误返回，传入返回结果长度小于告知长度
	 */
	private int MarrWavelet(double originSpect[],double computeSpect[],int spectNum,int waveLength,double min,double max)
	{
		if(originSpect==null)
			return -1;
		if(computeSpect==null)
			return -2;
		if(spectNum<=0)
			return -3;
		//int waveLength=281;
		double real[]=new double[waveLength];
		if(calMarr(real,waveLength,min,max)==false)
			return -4;
		return wavelet(real,originSpect,computeSpect,spectNum);
	}
	private boolean calMarr(double real[],int waveLength,double min,double max)
	{
		//final int waveLength=281;
		double step=(max-min)/waveLength;
		
		for(int i=0;i<waveLength;i++)
		{
			double nowValue=i*step+min;
			//double temp1=2*(1-nowValue*nowValue)/Math.sqrt(2*Math.PI);
			double temp1=(1-nowValue*nowValue);
			double temp2=Math.exp(-nowValue*nowValue/2);
			real[i]=temp1*temp2*step;
		}
		return true;
	}
	
	private int filter(double kernel[],double data[],double result[],int dataLength)
	{
		if(kernel.length % 2 ==0)
			return -5;
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
		int ret=filter(real, data, result, dataLength);
		
		return ret;
	}
	
	
}
