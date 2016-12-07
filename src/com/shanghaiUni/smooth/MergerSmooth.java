/**
 * 
 */
package com.shanghaiUni.smooth;

import com.shanghaiUni.common.PretreatmentSmoothOne;

/**
 * @author Administrator
 * 多点归并平滑
 */
public class MergerSmooth extends PretreatmentSmoothOne {
	@ Override
	public int ComputeOneSpectrum(double[] OriginSpect,double[] ResultSpect, double[] OriginWave, int SpectLength)
	{
		if(OriginSpect==null)
			return -1;
		if(ResultSpect==null)
			return -2;
		if(SpectLength<=0)
			return -3;
		double sum=0;
		for(int i=0;i<SpectLength;i++)
		{
			sum=0;
			for(int j=0;j<=i;j++)
			{
				sum+=OriginSpect[i];
			}
			sum/=(i+1);
			ResultSpect[i]=sum;
		}
		return 0;
	}
	@ Override
	public String CommentSelf(String[] Parameter)
	{
		return "多点归并平滑\n";
	}
}
