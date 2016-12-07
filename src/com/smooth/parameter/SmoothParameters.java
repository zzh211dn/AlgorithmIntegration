/**
 * 
 */
package com.smooth.parameter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * @author Administrator
 *
 */
@SuppressWarnings("serial")
public class SmoothParameters extends ArrayList<SmoothParameter>
{
	public SmoothParameters()
	{	
		
		//this.add(new SmoothParameter("1","2"));
		
	}
	
	public boolean Init(String Path) throws UnsupportedEncodingException
	{
		
//		String filename=this.getClass().getResource("/").getPath()+"SmoothMethod.csv";
//		System.out.print(this.getClass().getResourceAsStream("/dou/SmoothMethod.csv"));
	
		BufferedReader br;			
		
		try {
//			br = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/dou/SmoothMethod.csv")));
			String[] a = new String[]{"空,no,no,无需参数,0","一阶导数,Der1,Der,无需参数,0","二阶导数,Der2,Der,无需参数,0","SavGol平滑,SavGol1,SavGol,点数(奇数),31","SavGol一阶导数,SavGol2,SavGol,点数(奇数),17","SavGol二阶导数,SavGol3,SavGol,点数(奇数),17","平均窗口平滑,AveSmooth,AveSmooth,窗口步长(输入大于3的整数),5","中位值平滑,MedSmooth,MedSmooth,窗口步长(输入大于3的整数),5","常偏移量消除,COE,COE,无需参数,0","有限脉冲的响应算法,FIR,FIR,滤波系数长度(长度大于3),32","多元散射矫正,MCS,MCS,无需参数,0"
					,"多点归并平滑,MergerSmooth,MergerSmooth,无需参数,0","直线相减,SLS,SLS,无需参数,0","标准正则变换,SNV,SNV,无需参数,0","标准差标准化,ZeroMeanNorm,ZeroMeanNorm,无需参数,0","加权平均平滑,WeightedAverage,WeightedAverage,无需参数,0","BSpline小波,BSpline,BSpline,滤波器长度(大于101的奇数),101","DB4小波,DB4,DB4,无需参数,0","DOG小波,DOG,DOG,采样点数,100","Haar小波,Haar,Haar,无需参数,0","Marr小波,Marr,Marr,滤波器长度(大于281的奇数),281","Morlet小波,Morlet,Morlet,离散化取样点数，一般取11以上,11"};
			String line=null;
			try 
			{
				for(int i = 0;i<a.length;i++)//(line=br.readLine())!=null)
				{
					line = a[i];
					String arr[]=line.split(",");
					System.out.print(line);
					SmoothParameter OneParamter;
					//System.out.println(arr.length);
					//System.out.println(line);
					OneParamter=new SmoothParameter(arr);
					this.add(OneParamter);
					//i++;
					//System.out.println(i);
				}
			} 
			catch (Exception e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	public String GetClassMethod(String MethodName)
	{
		int i;
		
		for(i=0;i<this.size();i++)
		{
			if (this.get(i).getMethodname().equals(MethodName))
			{
				return this.get(i).getMethodclass();
			}
		}
		return "";
	}
	public String GetMethodValue(String MethodName)
	{
		int i;
		
		for(i=0;i<this.size();i++)
		{
			if (this.get(i).getMethodname().equals(MethodName))
			{
				return this.get(i).getMethodvalue();
			}
		}
		return "";
	}
	public String GetMethodComment(String MethodName)
	{
		int i;
		
		for(i=0;i<this.size();i++)
		{
			if (this.get(i).getMethodname().equals(MethodName))
			{
				return this.get(i).getMethodcomment();
			}
		}
		return "无";
	}
	public String GetMethodName(String MethodValue)
	{
		int i;
		
		for(i=0;i<this.size();i++)
		{
			if (this.get(i).getMethodvalue().equals(MethodValue))
			{
				return this.get(i).getMethodname();
			}
		}
		return "";
	}
	public String GetMethodNumValue(String MethodClass)
	{
		String arr[]=MethodClass.split(",");
		String result="";
		for(int i=0;i<arr.length;i++)
		{
			for(int j=0;j<this.size();j++)
			{
			if(this.get(j).getMethodvalue().equals(arr[i]))
			{
				result+=this.get(j).getValue()+",";
				break;
			}
			}
		}
		
		return result;
	}
	public String GetMethodOneValue(String MethodName)
	{
		for(int j=0;j<this.size();j++)
		{
			if(this.get(j).getMethodname().equals(MethodName))
			{
				return get(j).getValue();
			
			}
		}
		return "0";
	}
	
}
