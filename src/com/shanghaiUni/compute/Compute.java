/**
 * 
 */
package com.shanghaiUni.compute;

import com.shanghaiUni.common.*;
import com.shanghaiUni.datastruct.SpectrumInfoGroup;

/**
 * @author Administrator
 *
 */
public class Compute {
	public int ComputeSmooth(SpectrumInfoGroup ComputeSpect, String Method, String[] Parameter) throws InstantiationException, IllegalAccessException, ClassNotFoundException
	{
		Pretreatment OnePretreatment = null;
		OnePretreatment=(Pretreatment)Class.forName("com.shanghaiUni.smooth." + Method).newInstance();
		
		if(OnePretreatment.Init(Parameter))
		{
			return OnePretreatment.ComputeSmooth(ComputeSpect);
		}
		return 0;
	}
	public int ComputeSmooth(String[] filename)
	{
		return 0;
	}
	public  static String Parameter(String Method,String[] Parameter)
	{
		Pretreatment OnePretreatment = null;
		try {
			OnePretreatment=(Pretreatment)Class.forName("com.shanghaiUni.smooth." + Method).newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
		
		return OnePretreatment.TransformParameter(Parameter);
				
	}
}
