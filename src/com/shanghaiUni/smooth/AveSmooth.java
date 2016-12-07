/**
 * 
 */
package com.shanghaiUni.smooth;

/**
 * @author Administrator
 * 平均窗口平滑
 */
public class AveSmooth extends WindowSmooth {
	@ Override
	public int ComputeOneSpectrum(double[] OriginSpect,double[] ResultSpect, double[] OriginWave, int SpectLength) 
	{
		if(OriginSpect==null)
			return -1;
		if(ResultSpect==null)
			return -2;
		if(SpectLength<=0)
			return -3;
		if(step<=0)
			return -4;
		boolean tag= step%2==0 ? true:false;
		int startIndex,endIndex;
		double sum;
		int temp=tag==true ? step/2-1 : step/2;
		for(int i=0;i<SpectLength;i++)
		{
			sum=0;
			if(i<step/2)
			{
				
				startIndex=0;
				endIndex=i+ temp;
			}
			else
			{
				startIndex=i-step/2;
				endIndex=startIndex+step;
				if (endIndex>=SpectLength)
				{
					endIndex=SpectLength-1;
				}
			}
			for(int j=startIndex;j<=endIndex;j++)
			{
				sum+=OriginSpect[j];
			}
			sum/=(endIndex-startIndex+1);
			ResultSpect[i]=sum;
		}
		return 0;
	}	
	@ Override
	public String CommentSelf(String[] Parameter)
	{
		return "平均窗口平滑\n" + super.CommentSelf(Parameter);
	}
	@ Override
	public String CheckParameter(String[] Parameter,String Info)
	{
		return super.CheckParameter(Parameter, "平均窗口平滑", Info);
	}
}
