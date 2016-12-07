package com.shanghaiUni.datastruct;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.File;
import java.util.ArrayList;

public class Instance {
	public double[] x = null;
	public double[] y = null;
	/**
	 * 
	 */
	public String[] xtitle = null;
	public String[] ytitle = null;
	public String[] CommentInfo = null; 
	public String[] CommentTitle = null;	
	
	public TitleType titletype;
	public String no;
	
	public Instance()
	{
		
	}
	
	public Instance(int AttributeNum)
	{		
		Init(AttributeNum);
	}
	public int AttributeNum()
	{
		if ( x != null)
		{
			return x.length;			
		}
		else
		{
			return 0;
		}
	}
	public void SetAttributeNum(int AttributeNum)
	{
		Init(AttributeNum);
	}
	
	private void Init(int AttributeNum)
	{
		if (AttributeNum > 0)
		{
			x = new double[AttributeNum];
			xtitle =new String[AttributeNum];
			
		}
	}
	public boolean InitFromCSVForSpectrum(String filename) throws IOException
	{
		int length = 0;
		ArrayList<Double> xList;
		ArrayList<Double> yList;
		
		xList= new ArrayList<Double>();
		yList= new ArrayList<Double>();
		
		File file =new File(filename);
		
		no = file.getName();
		
		BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
		String line=null;
		while((line=br.readLine())!=null)
		{
			String arr[]=line.split(",");
			if(arr.length==2)
			{
				yList.add(Double.parseDouble(arr[0]));
				xList.add(Double.parseDouble(arr[1]));
				length++;
			}
		}
		br.close();
				
		titletype=TitleType.DoubleType;
		
		x=new double[length];		
		xtitle=new String[length];		
		
		for (int i=0;i<length;i++)
		{
			xtitle[i]=yList.get(i).toString();
			x[i]=xList.get(i);
		}
		xList.clear();
		yList.clear();
		return true;
	}
	public boolean WriteToCSV(String filename) throws IOException
	{
		FileWriter fw = null;	
		
		fw = new FileWriter(filename);
		
		if (Write(x,xtitle,fw) && 
		Write(y,ytitle,fw) &&
		Write(CommentInfo,CommentTitle,fw) )
		{
		
			fw.close();
			return true;
		}
		else
		{
			fw.close();
			return false;
		}
		
	}
	private boolean Write(String[] value, String[] title, FileWriter fw) throws IOException
	{
		if ( value != null)
		{
			fw.write(Integer.toString(value.length)+ '\n');
		}
		else
		{
			fw.write(Integer.toString(0) + '\n');
			return true;
		}
		
		int i;
		
		for (i=0;i< value.length - 1;i++)
		{
			fw.write(value[i] + ",");
		}
		fw.write(value[i] + '\n');
		
		for (i=0;i< title.length - 1;i++)
		{
			fw.write(title[i] + ",");
		}
		fw.write(title[i] + '\n');
		return true;
	}
	private boolean Write(double[] value, String[] title, FileWriter fw) throws IOException
	{
		
		
		if ( value != null)
		{
			fw.write(Integer.toString(value.length)+ '\n');
		}
		else
		{
			fw.write(Integer.toString(0) + '\n');
			return true;
		}
		
		int i;
		
		for (i=0;i< value.length - 1;i++)
		{
			fw.write(Double.toString(value[i]) + ",");
		}
		fw.write(Double.toString(value[i]) + '\n');
		
		for (i=0;i< title.length - 1;i++)
		{
			fw.write(title[i] + ",");
		}
		fw.write(title[i] + '\n');
		return true;
	}
	public boolean ReadFromCSV(String filename) throws IOException
	{
				
		File file =new File(filename);
		
		no = file.getName();
		
		BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
		
		
		Read(x,xtitle,br);
		Read(y,ytitle,br);
		Read(CommentInfo,CommentTitle,br);
		br.close();
				
		titletype=TitleType.StringType;
		
		
		return true;
	}
	private boolean Read(double[] value, String[] title, BufferedReader br) throws NumberFormatException, IOException
	{
		String line=null;
		int length = Integer.parseInt(br.readLine().trim());
		
		if (length==0)
		{
			return true;
			
		}
		
		if (length<0)
		{
			return false;
		}
		value = new double[length];
		title = new String[length];
		
		
		
		line=br.readLine();
		String[] arr=line.split(",");
		
		int i;
		
		for(i=0;i<length;i++)
		{
			x[i] = Double.parseDouble(arr[i]);
		}
		
		line=br.readLine();
		arr=line.split(",");
		
		for(i=0;i<length;i++)
		{
			xtitle[i] = arr[i];
		}
		
		return true;
	}
	private boolean Read(String[] value, String[] title, BufferedReader br) throws NumberFormatException, IOException
	{
		String line=null;
		int length = Integer.parseInt(br.readLine().trim());
		if (length==0)
		{
			return true;
		}
		
		if (length<0)
		{
			return false;
		}
		value = new String[length];
		title = new String[length];
		
		
		
		line=br.readLine();
		String[] arr=line.split(",");
		
		int i;
		
		for(i=0;i<length;i++)
		{
			x[i] = Double.parseDouble(arr[i]);
		}
		
		line=br.readLine();
		arr=line.split(",");
		
		for(i=0;i<length;i++)
		{
			xtitle[i] = arr[i];
		}
		
		return true;
	}

	public double[] getX() {
		return x;
	}

	public void setX(double[] x) {
		this.x = x;
	}

	public String[] getXtitle() {
		return xtitle;
	}

	public void setXtitle(String[] xtitle) {
		this.xtitle = xtitle;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}
	public String JSON()
	{
		StringBuilder outstring=new StringBuilder();
		outstring.append("{'No':'"+no+"'");
		
		int i;
		for(i=0;i<xtitle.length;i++)
		{
			outstring.append(",'" + xtitle[i]+"':'"+Double.toString(x[i])+"'");
		}
		outstring.append("}");
		return outstring.toString();
	}
}


