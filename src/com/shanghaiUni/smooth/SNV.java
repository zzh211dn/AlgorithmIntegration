/**
 * 
 */
package com.shanghaiUni.smooth;

import com.shanghaiUni.common.PretreatmentSmoothOne;

/**
 * @author Administrator
 * 标准正则变换
 */
public class SNV extends PretreatmentSmoothOne {
	@ Override
	public int ComputeOneSpectrum(double[] OriginSpect,double[] ResultSpect, double[] OriginWave, int SpectLength) 
	{   
	    return computeSNV(OriginSpect, ResultSpect,SpectLength);
			
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
	private int computeSNV(double originSpect[],double computeSpect[],int spectNum)
	{
		if(originSpect==null)
			return -1;
		if(computeSpect==null)
			return -2;
		if(spectNum<=0)
			return -3;
		double average=SmoothCommonFunction.getaverage(originSpect,spectNum);
		double S=SmoothCommonFunction.getS(originSpect,average,spectNum);
		for (int i=0;i<spectNum;i++)
		{
			computeSpect[i]=(originSpect[i]-average)/S;
		}
		return 0;
	}
	@ Override
	public String CommentSelf(String[] Parameter)
	{
		return "标准正则变换\n";
	}
}
