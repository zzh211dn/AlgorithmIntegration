/**
 * 
 */
package com.shanghaiUni.smooth;

import com.shanghaiUni.common.PretreatmentSmoothOne;

/**
 * @author Administrator
 * daubechiesWavelet4阶小波变换
 */
public class DB4 extends PretreatmentSmoothOne {
	@ Override
	public int ComputeOneSpectrum(double[] OriginSpect,double[] ResultSpect, double[] OriginWave, int SpectLength)
	{
		return daubechiesWavelet(OriginSpect,ResultSpect,SpectLength);
				
	}
	
	/**
	 * @param originSpect 原始数据
	 * @param computeSpect 计算结果数据
	 * @param spectNum 原始数据长度
	 * @return 
	 * 0,正确返回，数据计算成功
	 *	-1,错误返回，原始数据为空
	 *	-2，错误返回，传入的返回指针为空
	 *	-3，错误返回，传入数据长度小于或等于0
	 */
	private int daubechiesWavelet(double []originSpect,double []computeSpect,int spectNum)
	{
		if(originSpect==null)
			return -1;
		if(computeSpect==null)
			return -2;
		if(spectNum<=0)
			return -3;
		final double sqt3=Math.sqrt(3),sqt2=4*Math.sqrt(2);
		final double h0=(1+sqt3)/sqt2,
		h1=(3+sqt3)/sqt2,
		h2=(3-sqt3)/sqt2,
		h3=(1-sqt3)/sqt2;
		//final double g0=h3,g1=-h2,g2=h1,g3=h0;
		
		int half=spectNum>>1;
		for(int i=0;i<half;i++)
		{
			computeSpect[2*i]=originSpect[(i*2)%spectNum]*h0
			+originSpect[(2*i+1)%spectNum]*h1
			+originSpect[(2*i+2)%spectNum]*h2
			+originSpect[(2*i+3)%spectNum]*h3;
			computeSpect[2*i+1]=computeSpect[2*i];
			/*computeSpect[i+half]=+originSpect[(2*i)%spectNum]*g0
			+originSpect[(2*i+1)%spectNum]*g1
			+originSpect[(2*i+2)%spectNum]*g2
			+originSpect[(2*i+3)%spectNum]*g3;*/
		}
		if(spectNum%2!=0)
			computeSpect[spectNum-1]=computeSpect[spectNum-2];
		return 0;
	}	
	@ Override
	public String CommentSelf(String[] Parameter)
	{
		return "DB4阶小波变换\n" ;
	}
}
