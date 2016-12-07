package com.smooth.gui;

import java.io.File;
import java.io.IOException;

import com.shanghaiUni.compute.SmoothCompute;
import com.shanghaiUni.datastruct.Spectrum;
import com.shanghaiUni.datastruct.SpectrumInfoGroup;

public class SmoothProcess {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	public  static String CheckParameter(String SpectPath, String filename) throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException
	{
		
		
		String parameterfilename = null;
		
		parameterfilename = filename;
		
		if(parameterfilename==null)
		{
			return "找不到参数文件";
		}
		
		String info="";
		
		
		
		SmoothCompute smoothcompute=new SmoothCompute();
		
		
		SpectrumInfoGroup spectrumgroup=null;		
		spectrumgroup=LoadSpectrum(SpectPath);
		
		if(spectrumgroup==null)
		{
			return "找不到光谱文件";
		}
			
		info=smoothcompute.CheckParameter(spectrumgroup, parameterfilename);
		
		spectrumgroup.Dispose();
		
		return info;
	}
	public static SpectrumInfoGroup LoadSpectrum(String SpectrumPath) throws NumberFormatException, IOException
	{
		SpectrumInfoGroup spectrumgroup;
		spectrumgroup=new SpectrumInfoGroup();
		File file = new File(SpectrumPath);
		if(!file.exists())
		{
			return null;
		}
		
		if(file.isDirectory()&&file.list().length!=0)
		{
			for(int i=0;i<file.list().length;i++)
			{
				 File childfile=new File(file.list()[i]);
				 if(childfile.getName().endsWith(".csv")||
					childfile.getName().endsWith(".CSV"))
				 {
					  spectrumgroup.add(new Spectrum(SpectrumPath +"/"+file.list()[i]));
				 }				 
				   
			 }			
		 }
		return spectrumgroup;
	}
}
