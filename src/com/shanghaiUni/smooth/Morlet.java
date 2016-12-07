/**
 * 
 */
package com.shanghaiUni.smooth;

import com.shanghaiUni.common.PretreatmentSmoothOne;

/**
 * @author Administrator
 * Morlet小波
 */
public class Morlet extends PretreatmentSmoothOne {

	/**
	 * 离散化取样点数，一般取11以上
	 */
	int DiscretizationNum=11;
	/**
	 * 小波角速度，与周期对应，一般(matlab中)取5
	 */
	int w0=5;
	/* (non-Javadoc)
	 * @see com.shanghaiUni.common.Pretreatment#Init(java.lang.String[])
	 */
	@ Override
    public boolean Init(String[] Parameter) 
    {
		try
		{
			DiscretizationNum=Integer.parseInt(Parameter[0]);
			if(DiscretizationNum<11)
			{
				DiscretizationNum=11;
			}
			w0=Integer.parseInt(Parameter[1]);
		}
		catch (Exception ex)
    	{
    		ex.printStackTrace();
    		return false;
    	}
		return true;
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
		return morletWavelet(OriginSpect,ResultSpect,SpectLength,DiscretizationNum,w0);
	}
	/**
	 * @param originSpect 原始数据
	 * @param computeSpect 计算结果
	 * @param spectNum 原始数据和计算结果数据长度
	 * @param waveLength 离散化取样点数
	 * @param w0 小波角速度
	 * @return
	 */
	private int morletWavelet(double originSpect[],double computeSpect[],int spectNum,int waveLength,double w0)
	{
		if(originSpect==null)
			return -1;
		if(computeSpect==null)
			return -2;
		if(spectNum<=0)
			return -3;
		double real[]=new double[waveLength];
		double imag[]=new double[waveLength];
		if(calMorlet(real,imag,w0)==false)
			return -4;
		
		int ret=wavelet(real,imag,originSpect,computeSpect,spectNum);
		
		return ret;
	}
	private boolean calMorlet(double real[],double imag[],double w0)
	{
		if(real.length!=imag.length)
			return false;
		if(real.length <=50)
			return false;
		
		double step=(double)10/real.length;//从-5到5采样。
		for(int i=0;i<real.length/2;i++)
		{
			double temp1=/*(step/Math.sqrt(2*Math.PI))**/Math.exp(-(i*step)*(i*step)/2);
			double temp2=Math.cos(i*step*w0);
			//double temp3=Math.sin(i*step*w0);
			real[i+real.length/2]=temp1*temp2*step;
			//imag[i+real.length/2]=temp1*temp3;
			real[real.length/2-i]=real[real.length/2+i];
			//imag[real.length/2-i]=imag[real.length/2+i];
		}
		
		return true;
	}
	
	public static int filter(double kernel[],double data[],double result[],int dataLength)
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
	
	private int wavelet(double real[],double imag[],double data[],double result[],int dataLength)
	{
		double resultReal[]=new double [result.length];
		double resultImag[]=new double [result.length];
		int ret1=filter(real, data, resultReal, dataLength);
		if(ret1!=0)
			return ret1;
		int ret2=filter(imag, data, resultImag, dataLength);
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
		return "Morlet小波\n" + 
				"离散化取样点数: " + Parameter[0];
	}
	@ Override
    public String TransformParameter(String[] Parameter)
	{	
		return "Morlet"+"\n"+
				Parameter[1]+"," + 
				Integer.toString(w0)+ ",";
		
	}
	@ Override
	public String CheckParameter(String[] Parameter,String Info)
	{
		int ps=Integer.parseInt(Parameter[0]);
		
		if(ps<11 || ps %2==0)
		{
			Info="Morlet小波的离散化取样点数必须大于11的正奇数";
			return Info;
		}
		else
		{
			return "";
		}
	}
}
