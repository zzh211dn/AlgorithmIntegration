/**
 * 
 */
package com.shanghaiUni.smooth;

import com.shanghaiUni.common.PretreatmentSmoothOne;

/**
 * @author Administrator
 *
 */
public class WindowSmooth extends PretreatmentSmoothOne {
	/**
	 * 窗口步长
	 */
	protected int step = 5;
	@ Override
	public boolean Init(String[] Parameter)
	{
		try
		{
			if (Parameter.length==1)
			{
				step = Integer.parseInt(Parameter[0]);
			}
			return true;
		}
		catch (NumberFormatException ex)
		{
			return false;
		}
		
	}
	@ Override
	public String TransformParameter(String[] Parameter)
	{
	   try
	   {
		   return Parameter[0]+"\n"+Parameter[1]+",";
	   }
	   catch (Exception e)
	   {
		   e.printStackTrace();
		   return "";
	   }
	}
	@ Override
	public String CommentSelf(String[] Parameter)
	{
		return "窗口步长:" + Parameter[0];
	}	
	public String CheckParameter(String[] Parameter,String prefix,String Info)
	{
		int ps=Integer.parseInt(Parameter[0]);
		
		if(ps<3)
		{
			Info=prefix + "窗口步长输入大于3的整数";
			return Info;
		}
		else
		{
			return "";
		}
	}
}
