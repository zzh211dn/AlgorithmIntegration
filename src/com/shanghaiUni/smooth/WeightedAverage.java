/**
 * 
 */
package com.shanghaiUni.smooth;

/**
 * @author Administrator
 * 加权平均平滑
 */
public class WeightedAverage extends WindowSmooth {
	double[] weight={0.1,0.2,0.4,0.2,0.1};
	@ Override
	public int ComputeOneSpectrum(double[] OriginSpect,double[] ResultSpect, double[] OriginWave, int SpectLength) 
	{
		step = 5;
		if(OriginSpect==null)
			return -1;
		if(ResultSpect==null)
			return -2;
		if(SpectLength<=0)
			return -3;
		if(step<=0)
			return -4;
		if(weight==null)
			return -5;
		double totalWeight=0;
		for(int i=0;i<step;i++)
		{
			totalWeight+=weight[i];
		}
		if(totalWeight-1>0.00000001)
			return -6;
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
			for(int j=startIndex;j<endIndex;j++)
			{
				sum+=weight[j-startIndex]*OriginSpect[j];
			}
			for(int j=endIndex-startIndex+1;j<step;j++)
			{
				sum+=weight[j]*OriginSpect[endIndex];
			}
			//sum/=(endIndex-startIndex+1);
			ResultSpect[i]=sum;
		}
		return 0;
	}
	@ Override
	public String CommentSelf(String[] Parameter)
	{
		return "加权平均平滑\n";
	}
}
