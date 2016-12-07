package com.shanghaiUni.common;
import com.shanghaiUni.datastruct.Spectrum;
/**
 * @author Administrator
 *
 */
/**
 * @author Administrator
 * 预处理单个光谱基类
 */
public class PretreatmentSmoothOne extends Pretreatment {
	double[] OriginSpect = null;
	double[] ResultSpect = null;
	double[] OriginWave = null;
	
	/* (non-Javadoc)
	 * @see com.shanghaiUni.common.Pretreatment#ComputeOneSmooth(com.shanghaiUni.common.Spectrum)
	 */	
	@ Override
	public int ComputeOneSmooth(Spectrum OneSpect) 
	{
		int ResultValue;
		
		 OriginSpect=new double[OneSpect.Length()];
		 ResultSpect=new double[OneSpect.Length()];
		 OriginWave=new double[OneSpect.Length()];
		
		
		System.arraycopy(OneSpect.getSpectrumValue(), 0, 
				OriginSpect, 0, OneSpect.Length());
		System.arraycopy(OneSpect.getWaveNum(), 0, 
				OriginWave, 0, OneSpect.Length());
		
		ResultValue = ComputeOneSpectrum(OriginSpect, ResultSpect, OriginWave, OneSpect.Length());
		
		if (ResultValue == 0 )
		{
			System.arraycopy(ResultSpect,0, OneSpect.getSpectrumValue(),0, ResultSpect.length);
		}            
		
		return ResultValue;
	}
	/**
	 * 计算单个光谱
	 * @param OriginSpect 光谱的原始数据
	 * @param ResultSpect 计算结果
	 * @param OriginWave 光谱的波长
	 * @param SpectLength 光谱长度
	 * @return
	 */
	public int ComputeOneSpectrum(double[] OriginSpect,double[] ResultSpect, double[] OriginWave, int SpectLength)
	{
		return 0;
	}
}
