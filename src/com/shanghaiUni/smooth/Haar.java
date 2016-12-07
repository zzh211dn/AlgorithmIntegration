/**
 * 
 */
package com.shanghaiUni.smooth;

import com.shanghaiUni.common.PretreatmentSmoothOne;

/**
 * @author Administrator
 * Haar小波
 */
public class Haar extends PretreatmentSmoothOne {
	/* (non-Javadoc)
	 * @see com.shanghaiUni.common.PretreatmentSmoothOne#ComputeOneSpectrum(double[], double[], double[], int)
	 */
	@ Override
	public int ComputeOneSpectrum(double[] OriginSpect,double[] ResultSpect, double[] OriginWave, int SpectLength)
	{
		return haarWavelet(OriginSpect,ResultSpect,SpectLength);
				
	}
	
	/************************************************************************/
	/**
	 * @param originSpect 原始数据
	 * @param computeSpect 计算结果
	 * @param spectNum 数据长度
	 * @return
	 */
	private int haarWavelet(double originSpect[],double computeSpect[],int spectNum)
	{
		if(originSpect==null)
			return -1;
		if(computeSpect==null)
			return -2;
		if(spectNum<=0)
			return -3;
		double[] Tempcompute=new double[spectNum/2];
		for(int i=0;i<spectNum/2;i++)
		{
			double d=originSpect[i*2]-originSpect[i*2+1];
			Tempcompute[i]=(originSpect[i*2]-d/2)*Math.sqrt(2);
			//computeSpect[i]=(originSpect[i*2]-d/2)*Math.sqrt(2);
			//computeSpect[2*i+1]=computeSpect[2*i];
		}
		for(int i=0;i<spectNum/2;i++)
		{
			computeSpect[2*i]=Tempcompute[i];
			computeSpect[2*i+1]=Tempcompute[i];
		}
		if(spectNum%2==1)
		{
			computeSpect[computeSpect.length-1]=Tempcompute[Tempcompute.length-1];
		}
		Tempcompute=null;
		return 0;
	}
	@ Override
	public String CommentSelf(String[] Parameter)
	{
		return "Haar小波\n";
	}
}
