/**
 * 
 */
package com.shanghaiUni.smooth;

import com.shanghaiUni.common.PretreatmentSmoothOne;

/**
 * @author Administrator
 *  常偏移量消除
 */
public class COE extends PretreatmentSmoothOne {
	@ Override
	public int ComputeOneSpectrum(double[] OriginSpect,double[] ResultSpect, double[] OriginWave, int SpectLength) 
	{   
	    return ComputeCOE(OriginSpect, ResultSpect,SpectLength);
			
	}
	/**
	 * @param originSpect 单个光谱原始数据
	 * @param computeSpect 计算结果返回
	 * @param spectNum 每个光谱中数据个数
	 * @return 
	 * 0,正确返回，数据计算成功
	 *	-1,错误返回，原始数据为空
	 *	-2，错误返回，传入的返回指针为空
	 *	-3，错误返回，每数据长度小于或等于0
	 */
	private int ComputeCOE(double originSpect[],double computeSpect[],int spectNum)
	{
		if(originSpect==null)
			return -1;
		if(computeSpect==null)
			return -2;
		if(spectNum<=5)
			return -3;
		double constant=0;
		for(int i=0;i<5;i++)
		{
			constant+=originSpect[i];
		}
		constant/=5;
		for(int i=0;i<spectNum;i++)
			computeSpect[i]=originSpect[i]-constant;
		return 0;
	}
	@ Override
	public String CommentSelf(String[] Parameter)
	{
		return "常偏移量消除\n" ;
	}
}
