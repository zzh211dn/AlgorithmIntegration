package com.shanghaiUni.commonMethod;

import java.io.File;
import java.util.ArrayList;

public class FileOperation {
	public static ArrayList<String> GetFilesName(String path,String extendname)
	{
		ArrayList<String> filesList = new ArrayList<String>();
		File file = new File(path);
		if(!file.exists())
		{
			return filesList;
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
				 if(childfile.getName().endsWith(extendname.toLowerCase())||
					childfile.getName().endsWith(extendname.toUpperCase()))
				 {
					 
					 if(LinuxPath)
					 {
						 if(path.endsWith("/"))
						 {
							 filesList.add(path + file.list()[i]);
						 }
						 else
						 {
							 filesList.add(path +"/"+file.list()[i]);
						 }
					 }
					 else
					 {
						filesList.add(path +"\\"+file.list()[i]);
					 }
					 
				 }
			}			
		}
		return filesList;
	}
	public static boolean delAllFile(String path) {
	       boolean flag = false;
	       File file = new File(path);
	       if (!file.exists()) {
	         return flag;
	       }
	       if (!file.isDirectory()) {
	         return flag;
	       }
	       String[] tempList = file.list();
	       File temp = null;
	       for (int i = 0; i < tempList.length; i++) {
	          if (path.endsWith(File.separator)) {
	             temp = new File(path + tempList[i]);
	          } else {
	              temp = new File(path + File.separator + tempList[i]);
	          }
	          if (temp.isFile()) {
	             temp.delete();
	          }
	          if (temp.isDirectory()) {
	             delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件
	             delFolder(path + "/" + tempList[i]);//再删除空文件夹
	             flag = true;
	          }
	       }
	       return flag;
	     }
	
	public static void delFolder(String folderPath) 
	{
		try 
		{
			delAllFile(folderPath); //删除完里面所有内容
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			myFilePath.delete(); //删除空文件夹
		} 
		catch (Exception e) 
		{
			e.printStackTrace(); 
		}
	}	
}
