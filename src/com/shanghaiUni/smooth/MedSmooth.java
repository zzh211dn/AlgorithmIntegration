/**
 * 
 */
package com.shanghaiUni.smooth;

/**
 * @author Administrator
 * 中位值平滑
 */
public class MedSmooth extends WindowSmooth {
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
		//sum+=1.0;
		int temp=tag==true ? step/2-1 : step/2;
		for(int i=0;i<SpectLength;i++)
		{
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
			int num=endIndex-startIndex+1;
			double segment[]=new double[num];
			for(int j=startIndex;j<=endIndex;j++)
			{
				segment[j-startIndex]=OriginSpect[j];
			}
			InsertSort(segment,num);
			
			ResultSpect[i]=segment[num/2];
		}
		return 0;
	}
	void InsertSort(double a[],int n)           //折半插入排序   
	{
		int i,j,low,high,m;  
		double temp;
		for(i=1;i<n;i++)   
		{   
			temp=a[i];   
			low=0;high=i-1;   
			while(low<=high)   
			{   
				m=(low+high)/2;   
				if(temp<a[m])   high=m-1;   
				else   low=m+1;   
			}   
			for(j=i-1;j>=low;j--)   a[j+1]=a[j];   
			a[low]=temp;   
		}   
	} 
	@ Override
	public String CommentSelf(String[] Parameter)
	{
		return "中位值平滑\n" + 
				super.CommentSelf(Parameter);
	}
	@ Override
	public String CheckParameter(String[] Parameter,String Info)
	{
		return super.CheckParameter(Parameter, "中位值平滑", Info);
	}
}
