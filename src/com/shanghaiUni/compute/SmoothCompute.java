/**
 * 
 */
package com.shanghaiUni.compute;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.shanghaiUni.common.*;
import com.shanghaiUni.commonMethod.FileOperation;
import com.shanghaiUni.datastruct.Instances;
import com.shanghaiUni.datastruct.Spectrum;
import com.shanghaiUni.datastruct.SpectrumInfoGroup;

/**
 * @author Administrator
 * 光谱预处理计算类（供外部调用）
 */
public class SmoothCompute {
	private SmoothMethodStruct[] SmoothMethods; 
	private int SmoothMethodLength=0;
	/**
	 * 预处理光谱
	 * @param ComputeSpect 光谱集合
	 * @param Method 方法名
	 * @param Parameter 参数
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */	
	private int ComputeSmooth(SpectrumInfoGroup ComputeSpect, String Method, String[] Parameter) throws InstantiationException, IllegalAccessException, ClassNotFoundException
	{
		Pretreatment OnePretreatment = null;
		OnePretreatment=(Pretreatment)Class.forName("com.shanghaiUni.smooth." + Method).newInstance();
		
		if(OnePretreatment.Init(Parameter))
		{
			return OnePretreatment.ComputeSmooth(ComputeSpect);
		}
		return 0;
	}
	/**
	 * 校对参数
	 * @param Method 预处理类名
	 * @param Parameter 参数数组
	 * @return 正确为空
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	private String CheckParameter(String Method, String[] Parameter) throws InstantiationException, IllegalAccessException, ClassNotFoundException
	{
		Pretreatment OnePretreatment = null;
		OnePretreatment=(Pretreatment)Class.forName("com.shanghaiUni.smooth." + Method).newInstance();
		
		String Info="";
		
		Info = OnePretreatment.CheckParameter(Parameter, Info);
		return Info;		
	}	
	/**
	 * 得到方法的说明
	 * @param parameterfilename 参数的模型文件
	 * @return
	 * @throws IOException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	public static String  GetComment(String parameterfilename) throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException 
	{
		
		BufferedReader br=null;
		
		try
		{
			br=new BufferedReader(new InputStreamReader(new FileInputStream(parameterfilename)));
			String line=null;
			line=br.readLine().trim();
			int i, length;
			length = Integer.parseInt(line);			
			String arr[];
			StringBuilder comment=new StringBuilder();
			String Method;
			
			for(i=0;i<length;i++)
			{
				Method=br.readLine().trim();
				line = br.readLine().trim();
				arr = line.split(",");
				Pretreatment OnePretreatment = null;
				OnePretreatment=(Pretreatment)Class.forName("com.shanghaiUni.smooth." + Method).newInstance();
				if(OnePretreatment != null)
				{
					comment.append(OnePretreatment.CommentSelf(arr)+"\n");
				}
			}
			
			return comment.toString();
		}	
		catch (FileNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();			
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}		
		finally
		{
			if(br != null)
			{
				br.close();
			}
		}
		
		return "";
	}
	/**
	 * 预处理光谱
	 * @param ComputeSpect 要计算的光谱
	 * @param parameterfilename 参数文件
	 * @param Transform 是否重整光谱
	 * @return 0 成功
	 * @throws IOException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * 给Web使用
	 */
	public int Compute(SpectrumInfoGroup ComputeSpect, String parameterfilename,boolean Transform) throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException
	{
		
		if (ReadParameterFile(parameterfilename))
		{
			ArrayList<Integer> MethodsIndex = new ArrayList<Integer>();
			MethodsIndex.add(0);
			double start;
			double end;
			int i;
			int j,k;
			
			start=SmoothMethods[0].start;
			end= SmoothMethods[0].end;
			for (i=1;i<SmoothMethodLength;i++)
			{
				if(start != SmoothMethods[i].start || 
						end != SmoothMethods[i].end)
				{
					MethodsIndex.add(i);
					start = SmoothMethods[i].start;
					end = SmoothMethods[i].end;
				}
			}
			
			i=0;			
			//System.out.printf("1");
			if (MethodsIndex.size()>=1)
			{
				
				SpectrumInfoGroup[] TempComputeSpect;
				TempComputeSpect = new SpectrumInfoGroup[MethodsIndex.size()];
				while(i<MethodsIndex.size())
				{
					//System.out.printf("2");
					//System.out.println(start);
					start = SmoothMethods[MethodsIndex.get(i)].start;
					end = SmoothMethods[MethodsIndex.get(i)].end;
					//System.out.println(start);
					//System.out.println(end);
					TempComputeSpect[i] = new SpectrumInfoGroup();
					for (Spectrum One:ComputeSpect)
					{
						if (One.getWaveNum()[0]<=start && 
								One.getWaveNum()[One.Length()-1]>=end)					
						{						
							TempComputeSpect[i].add((Spectrum)One.clone());
						}
					}	
					
					if (TempComputeSpect[i].size() > 0)						
					{
						if (i+1>=MethodsIndex.size())
						{
							k=SmoothMethodLength;
						}
						else
						{
							k=MethodsIndex.get(i+1);
						}
							
						for(j=MethodsIndex.get(i);j<k;j++)
						{								
							ComputeSmooth(TempComputeSpect[i],SmoothMethods[j].Method,
									SmoothMethods[j].Parameter);
						}
					}
					
					i++;
				}
				
				for(i=0;i<MethodsIndex.size();i++)
				{
					ComputeSpect.CopyFromSpectrumGroup(TempComputeSpect[i], 
							SmoothMethods[MethodsIndex.get(i)].start, 
							SmoothMethods[MethodsIndex.get(i)].end);
				}
				
			}
			if (Transform && SmoothMethods != null)
			{
				
				double[] startwave=new double[SmoothMethods.length];
				double[] endwave=new double[SmoothMethods.length];
				for(j=0;j<startwave.length;j++)
				{
					startwave[j]=SmoothMethods[j].start;
					endwave[j]=SmoothMethods[j].end;
				}
				for(Spectrum OneSpectrum:ComputeSpect)
				{
					OneSpectrum.TransformSelf(startwave, endwave);
					i++;
				}			
			}
			
		}
		return 0;
	}
	/**
	 * 预处理光谱
	 * @param csvfilename 光谱文件
	 * @param modelfilename 模型文件（参数文件）
	 * @return 数据集（供下一流程使用）
	 * @throws NumberFormatException
	 * @throws IOException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException	 
	 */
	public Instances Compute(String[] csvfilename, String modelfilename) throws NumberFormatException, IOException, InstantiationException, IllegalAccessException, ClassNotFoundException
	{
		SpectrumInfoGroup ComputeSpect;
		ComputeSpect = new SpectrumInfoGroup();
		
		ArrayList<Integer> AttributeNum = new ArrayList<Integer>();
		int i=0,j=0;
		
		for(String filename: csvfilename)
		{
			Spectrum One;
			One =new Spectrum(filename);
			ComputeSpect.add(One);
		}
		
		Compute(ComputeSpect,modelfilename,false);		
		
		Instances instances = new Instances();
		
		if (SmoothMethods != null)
		{
			
			double[] start=new double[SmoothMethods.length];
			double[] end=new double[SmoothMethods.length];
			for(j=0;j<start.length;j++)
			{
				start[j]=SmoothMethods[j].start;
				end[j]=SmoothMethods[j].end;
			}
			for(Spectrum OneSpectrum:ComputeSpect)
			{
				instances.addInstance(OneSpectrum.Transform(start,end));
				if ( !AttributeNum.contains(instances.instance.get(i).AttributeNum()))
				{
					AttributeNum.add(instances.instance.get(i).AttributeNum());
				}
				i++;
			}			
		}
		
		if (AttributeNum.size()==1)
		{
			return instances;
		}
		else
		{
			return null;
		}
	}	
	/**
	 * 预处理光谱
	 * @param path 光谱文件所在文件夹名
	 * @param modelfilename 模型文件（参数文件）
	 * @return 计算信息和预处理后的光谱所在文件夹名
	 * @throws NumberFormatException
	 * @throws IOException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * 流程使用
	 */
	public String[] Compute(String path, String modelfilename) throws NumberFormatException, IOException, InstantiationException, IllegalAccessException, ClassNotFoundException
	{
		String[] ReturnValue=new String[2];
		ReturnValue[0]="";
		SpectrumInfoGroup ComputeSpect;
		ComputeSpect = new SpectrumInfoGroup();
		
		ArrayList<String> filesname = FileOperation.GetFilesName(path,".csv");
		
		
		String computepath=Long.toString(System.currentTimeMillis());
		for(String filename: filesname)
		{
			Spectrum One;
			One =new Spectrum(filename);
			ComputeSpect.add(One);
		}
		
		String currentcomputepath=null;
		
		boolean UnixTemp=false;
		if (path.endsWith("\\"))
		{
			currentcomputepath=path + computepath;
		}
		else if(path.endsWith("/"))
		{
			UnixTemp=true;
			currentcomputepath=path+"/" + computepath;
		}
		else
		{
			currentcomputepath=path+"\\" + computepath;
		}
		
		File OneComputepath=new File(currentcomputepath);
		if(OneComputepath.exists())
		{
			FileOperation.delFolder(currentcomputepath);
		}
		OneComputepath.mkdir();
		Compute(ComputeSpect,modelfilename,true);		
		
		for(Spectrum one:ComputeSpect)
		{
			if(UnixTemp)
			{
				one.WriteToCSV(currentcomputepath + "/" + one.no + ".csv");
			}
			else
			{
				one.WriteToCSV(currentcomputepath + "\\" + one.no + ".csv");
			}
		}
		ReturnValue[0]="成功";
		ReturnValue[1]=currentcomputepath;
		return ReturnValue;
	}
	/**
	 * 读取参数文件
	 * @param parameterfilename 参数文件
	 * @return
	 * @throws IOException
	 */
	private boolean ReadParameterFile(String parameterfilename) throws IOException
	{
		BufferedReader br=null;
		boolean returnvalue=false;
		try 
		{
			br=new BufferedReader(new InputStreamReader(new FileInputStream(parameterfilename)));
			String line=null;
			line=br.readLine().trim();
			int i, j, length;
			length = Integer.parseInt(line);
			int arrLength;
			String arr[];
			
			if (length>0)
			{
				SmoothMethodLength = length;
				SmoothMethods= new SmoothMethodStruct[length];
				for (i=0;i<length;i++)
				{
					SmoothMethods[i]=new SmoothMethodStruct();
					SmoothMethods[i].Method = br.readLine().trim();
					line = br.readLine().trim();
					arr = line.split(",");
					arrLength=arr.length;					
					SmoothMethods[i].start= Double.parseDouble(arr[arrLength-2]);
					SmoothMethods[i].end= Double.parseDouble(arr[arrLength-1]);
					
					if (arrLength > 2)
					{
						SmoothMethods[i].Parameter=new String[arrLength-2];
						for(j=0;j<arrLength-2;j++)
						{
							SmoothMethods[i].Parameter[j]=arr[j];
						}
					}
				}
				returnvalue=true;
			}		
			
		} 
		catch (FileNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			if(br != null)
			{
				br.close();
			}
		}		
	
		return returnvalue;
	}
	/**
	 * 检查参数是否正确
	 * @param ComputeSpect 要预处理的光谱
	 * @param parameterfilename 参数文件
	 * @return 错误信息，若正确为空白字符“”
	 * @throws IOException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	public String CheckParameter(SpectrumInfoGroup ComputeSpect, String parameterfilename) throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException
	{
		
		double start=Double.MIN_VALUE;
		double end=Double.MAX_VALUE;		
		
		for(Spectrum One:ComputeSpect)
		{
			if(start<One.getWaveNum()[0])
			{
				start=One.getWaveNum()[0];
			}
			if(end>One.getWaveNum()[One.Length()-1])
			{
				end=One.getWaveNum()[One.Length()-1];
			}
		}
		
		return CheckParameter(start,end,parameterfilename,true);
	}
	/**
	 * 检查参数是否正确
	 * @param start 光谱起始波数
	 * @param end 光谱终止波数
	 * @param parameterfilename 参数文件
	 * @param IsWeb 是否以Web格式输出错误信息
	 * @return 错误信息，若正确为空白字符“”
	 * @throws NumberFormatException
	 * @throws IOException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	public String CheckParameter(double start,double end, String parameterfilename,boolean IsWeb) throws NumberFormatException, IOException, InstantiationException, IllegalAccessException, ClassNotFoundException
	{
		int i;
		if (ReadParameterFile(parameterfilename))
		{
			ArrayList<Integer> MethodsIndex = new ArrayList<Integer>();
			MethodsIndex.add(0);
						
			
			StringBuilder error=new StringBuilder();
			String info="";
			for (i=0;i<SmoothMethodLength;i++)
			{				
				
				if(start>this.SmoothMethods[i].end || 
						end<this.SmoothMethods[i].start)
				{
					if(IsWeb)
					{
						error.append("<br>第"+Integer.toString(i+1)+"个参数波长范围超过数据波长范围。</br>");
					}
					else
					{
						error.append("第"+Integer.toString(i+1)+"个参数波长范围超过数据波长范围。\n");
					}
				}
				info="";
				info=CheckParameter(this.SmoothMethods[i].Method,this.SmoothMethods[i].Parameter);
				if(!info.equals(""))
				{
					if(IsWeb)
					{
						error.append("<br>第"+Integer.toString(i+1)+"个参数错误："+info + "。</br>");
					}
					else
					{
						error.append("第"+Integer.toString(i+1)+"个参数错误："+info + "。\n");
					}
				}
			}
			
			return error.toString();
		}
		return "读取参数文件失败";
	}
}
