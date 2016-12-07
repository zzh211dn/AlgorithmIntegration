/**
 * 
 */
package com.shanghaiUni.smooth;

import com.shanghaiUni.common.PretreatmentSmoothOne;

/**
 * @author 上海大学
 * @version 1.0
 * 标准差标准化
 */
public class ZeroMeanNorm extends PretreatmentSmoothOne {
	@ Override
	public int ComputeOneSpectrum(double[] OriginSpect,double[] ResultSpect, double[] OriginWave, int SpectLength) 
	{   
	    return zeroMeanNormalization(OriginSpect, ResultSpect,SpectLength);
			
	}

	/**
	 * @param data 单个光谱原始数据
	 * @param result 单个光谱计算数据
	 * @param length 单个光谱长度
	 * @Return:                                       
	 *      0,正确返回，数据计算成功        
	 *      -1,错误返回，原始数据为空  
	 *      -2，错误返回，传入的返回指针为空
	 *      -3，错误返回，每数据长度小于或等于0 
	 */
	private int zeroMeanNormalization(double data[], double result[], int length) {
		if (data == null)
			return -1;
		if (length <= 0)
			return -2;
		if (result == null)
			return -3;
		double mu = calAverage(data, length);
		double sigma = calSigma(data, length);
		for (int i = 0; i < length; i++) {
			result[i] = (data[i] - mu) / sigma;
		}
		return 0;
	}
	private double calAverage(double data[],int length)
	{
		double sum=0;
		for(int i=0;i<length;i++)
		{
			sum+=data[i];
		}
		sum/=length;
		return sum;
	}
	
	private double calSigma(double data[],int length)
	{
		double mu=calAverage(data,length);
		double sigma=0;
		for (int i=0;i<length;i++)
		{
			sigma+=(data[i]-mu)*(data[i]-mu);
		}
		sigma/=length;
		return Math.sqrt(sigma);
	}
}
