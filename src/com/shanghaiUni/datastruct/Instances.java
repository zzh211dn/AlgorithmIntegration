/**
 * 
 */
package com.shanghaiUni.datastruct;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Administrator
 *
 */
public class Instances {
	public ArrayList<Instance> instance ;
	public int InstanceNum;	
	
	public Instances()
	{
		instance = new ArrayList<Instance>();
		InstanceNum=0;
	}
	public int addInstance(String filename)
	{
		Instance newinstance;
		newinstance=new Instance();
		
		try {
			newinstance.InitFromCSVForSpectrum(filename);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
		instance.add(newinstance);
		InstanceNum++;
		return 0;
	}
	public int addInstance(Instance instance)
	{
		this.instance.add(instance);
		InstanceNum++;
		return 0;
	}
	public ArrayList<Instance> getInstance() {
		return instance;
	}
	public void setInstance(ArrayList<Instance> instance) {
		this.instance = instance;
	}
	public String addInstanceBatch(String path) throws IOException
	{
		File file = new File(path);
		if(!file.exists())
		{
			return "目录不存在";
		}
		
		boolean LinuxPath=false;
		
		if(path.indexOf("/")!=-1)
		{
			LinuxPath=true;
			//System.out.println("LinuxPath: " +Boolean.toString(LinuxPath));
		}
		else
		{
			//System.out.println("LinuxPath: " +Boolean.toString(LinuxPath));
		}
		
		if(file.isDirectory()&&file.list().length!=0)
		{
			for(int i=0;i<file.list().length;i++)
			{
				 File childfile=new File(file.list()[i]);
				 if(childfile.getName().endsWith(".csv")||
					childfile.getName().endsWith(".CSV"))
				 {
					 Instance newinstance;
					 newinstance=new Instance(); 
					 if(LinuxPath)
					 {
						 if(path.endsWith("/"))
						 {
							 newinstance.InitFromCSVForSpectrum(path + file.list()[i]);
						 }
						 else
						 {
							 newinstance.InitFromCSVForSpectrum(path +"/"+file.list()[i]);
						 }
					 }
					 else
					 {
						 newinstance.InitFromCSVForSpectrum(path +"\\"+file.list()[i]);
					 }
					 instance.add(newinstance);
					 InstanceNum++;
				 }
			}
			return "成功";
		}
		else
		{
			return "文件不存在";
		}
	}
}
