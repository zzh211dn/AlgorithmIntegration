/**
 * 
 */
package com.shanghaiUni.smooth;

import com.shanghaiUni.common.PretreatmentSmoothOne;

/**
 * @author Administrator
 *
 */
public class MaxMinNorm extends PretreatmentSmoothOne {
	@ Override
	public int ComputeOneSpectrum(double[] OriginSpect,double[] ResultSpect, double[] OriginWave, int SpectLength) 
	{   
	    return minMaxNormalization(OriginSpect, ResultSpect,SpectLength);
			
	}
	/**
	 * @param data 单个光谱原始数据
	 * @param result 单个光谱计算后数据
	 * @param length 单个光谱长度
	 * @return 计算结果返回
	 * 0,正确返回，数据计算成功        
	 *      -1,错误返回，原始数据为空  
	 *      -2，错误返回，传入的返回指针为空
	 *      -3，错误返回，每数据长度小于或等于0
	 */
	private int minMaxNormalization(double data[],double result[],int length)
	{
		if(data==null)
			return -1;
		if(length<=0)
			return -2;
		if(result==null)
			return -3;
		double minMax=calMinMax(data,length);
		for (int i=0;i<length;i++)
		{
			result[i]=data[i]/minMax;
		}
		return 0;
	}
	private double calMinMax(double data[],int length)
	{
		double max=data[0];
		double min=data[0];
		for(int i=0;i<length;i++)
		{
			if(data[i]>max)
				max=data[i];
			if(data[i]<min)
				min=data[i];
		}
		return max-min;
	}
	@ Override
	public String CommentSelf(String[] Parameter)
	{
		return "极差标准化\n" ;
	}
}
