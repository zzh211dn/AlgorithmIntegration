/**
 * 
 */
package com.shanghaiUni.common;

import com.shanghaiUni.datastruct.SpectrumInfoGroup;
import com.shanghaiUni.datastruct.Spectrum;

/**
 * @author Administrator
 * 预处理基类
 */
public abstract class Pretreatment {	
	/**
	 * 初始化参数
	 * @param Parameter 参数数组
	 * @return
	 */
	public boolean Init(String[] Parameter)
	{
		return true;
	}
	public int ComputeSmooth(SpectrumInfoGroup ComputeSpect)
	{
		int ResultValue=0;
		for(Spectrum One:ComputeSpect)
		{
			ResultValue=ComputeOneSmooth(One);
			if (ResultValue!=0)
			{
				break;
			}
		}
		return ResultValue;
	}
	
	/**
	 * @param OneSpect 处理单根光谱
	 * @return
	 */
	public int ComputeOneSmooth(Spectrum OneSpect) 
	{
	    return 0;
	}
	/**
	 * 参数转换
	 * @param Parameter 参数数组
	 * @return
	 */
	public String TransformParameter(String[] Parameter)
	{
		return Parameter[0]+"\n";
	}
	/**
	 * 参数的文字说明
	 * @param Parameter 参数数组
	 * @return
	 */
	public String CommentSelf(String[] Parameter)
	{
		return "";
	}
	/**
	 * 验证参数是否正确
	 * @param Parameter 参数
	 * @param Info 错误信息提示
	 * @return 通过是true,不通过是false,错误信息在info
	 */
	public String CheckParameter(String[] Parameter,String Info)
	{
		return "";
	}
	protected String CheckPositive(int Parameter,String prefix,String Info)
	{
		if(Parameter<1)
		{
			Info=prefix + "必须是正整数";
			return Info;
		}
		else
		{
			return Info;
		}
	}
	protected String CheckOddNumber(int Parameter,String prefix,String Info)
	{
		if(Parameter%2==0)
		{
			Info=prefix + "必须是正奇数";
			return Info;
		}
		else
		{
			return "";
		}
	}
}
