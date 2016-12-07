/**
 * 
 */
package com.shanghaiUni.smooth;

import com.shanghaiUni.common.PretreatmentSmoothOne;

/**
 * @author Administrator
 * 直线相减
 */
public class SLS extends PretreatmentSmoothOne {
    @Override
	public int ComputeOneSpectrum(double[] OriginSpect,double[] ResultSpect, double[] OriginWave, int SpectLength) 
	{   
	    return ComputeSLS(OriginSpect, ResultSpect,OriginWave,SpectLength);
			
	}
	/**
	 * @param originSpect 单个光谱原始数据
	 * @param computeSpect 计算结果返回
	 * @param OriginSet 每个光谱的横坐标
	 * @param spectNum 每个光谱中数据个数
	 * @return 
	 * 0,正确返回，数据计算成功
	 *	-1,错误返回，原始数据为空
	 *	-2，错误返回，传入的返回指针为空
	 *	-3，错误返回，每数据长度小于或等于5
	 *	-4，错误返回，光谱横坐标数组为空
	 */
	private int   ComputeSLS(double originSpect[],double computeSpect[],double OriginSet[],int spectNum)
	{
		final int T=1;
		if(originSpect==null)
			return -1;
		if(computeSpect==null)
			return -2;
		if(spectNum<=5)
			return -3;
		if(OriginSet==null)
			return -4;
		double   result[]=new double[T+1];
		SmoothCommonFunction.process(OriginSet,originSpect,result,spectNum,T);
		for(int i=0;i<spectNum;i++)
		{
			computeSpect[i]=originSpect[i]-(result[0]+result[1]*OriginSet[i]);
		}
		return 0;
	}
	@ Override
	public String CommentSelf(String[] Parameter)
	{
		return "直线相减\n";
	}
}
