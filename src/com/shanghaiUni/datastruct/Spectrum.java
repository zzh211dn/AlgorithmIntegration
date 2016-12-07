/**
 * 
 */
package com.shanghaiUni.datastruct;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.shanghaiUni.datastruct.Instance;

/**
 * @author Administrator
 * 光谱类
 */
public class Spectrum {
	/**
	 * 类别信息
	 */
	public String classInfo  = "1";
    public double target=0;
    /**
     * 编号
     */
    public String no;
    /**
     * 补充信息
     */
    Map<String,String> CommentInfo = new HashMap<String,String>();
	/**
     * 吸收峰强度
     */
    double[] spectrumValue; 
	/**
	 * 波数
	 */
	double[] WaveNum; 
    
	public Spectrum()
	{
		
	}
	public Spectrum(String filename) throws NumberFormatException, IOException
	{
		int length = 0;
		ArrayList<Double> xList;
		ArrayList<Double> yList;
		
		xList= new ArrayList<Double>();
		yList= new ArrayList<Double>();
		
		File file =new File(filename);
		int Index=file.getName().lastIndexOf('.', file.getName().length()-1);
		//System.out.println(Index);
		//System.out.println(file.getName());
		if (Index==-1)
		{
			no = file.getName();
		}
		else
		{
			no =file.getName().substring(0, Index);
		}
		
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
		
		
		spectrumValue=new double[length];		
		WaveNum=new double[length];		
		
		if (yList.get(0) < yList.get(length-1))
		{
			for (int i=0;i<length;i++)
			{
				WaveNum[i]=yList.get(i);
				spectrumValue[i]=xList.get(i);
			}
		}
		else
		{
			for (int i=length-1;i>=0;i--)
			{
				WaveNum[i]=yList.get(length-i-1);
				spectrumValue[i]=xList.get(length-i-1);
			}
		}
		xList.clear();
		yList.clear();		
	}
	public Spectrum(Instance instance)
	{
		no = instance.no;
		int spectlength;
		spectlength = instance.AttributeNum();
		
		this.spectrumValue = new double[spectlength];
		this.WaveNum =new double[spectlength];
		
		System.arraycopy(instance.x, 0, this.spectrumValue, 0, spectlength);
		
		int i;
		
		for (i = 0; i< spectlength; i++)
		{
			this.WaveNum[i] = Double.parseDouble(instance.xtitle[i]);			
		}
		
		if (instance.CommentInfo != null)
		{			
			for(i=0;i<instance.CommentInfo.length;i++)
			{
				this.CommentInfo.put(instance.CommentTitle[i], 
						instance.CommentInfo[i]);
			}
		}
		
		if (instance.y != null)
		{
			target = instance.y[0];			
		}
	}
	public Instance Transform(double start,double end)
	{
				
		int AttributeNum = 0;
		ArrayList<Double> x = new ArrayList<Double>();
		ArrayList<String> title =new ArrayList<String>();
		
		for(int i=0;i<WaveNum.length;i++)
		{
			if(WaveNum[i]>=start && WaveNum[i]<=end)
			{
				x.add(this.spectrumValue[i]);
				title.add(Double.toString(this.WaveNum[i]));
				AttributeNum+=1;
			}
		}
		
		if(AttributeNum>0)
		{
			return Transform(AttributeNum,x,title);
		}
		else
		{
			return null;
		}
		
	}
	public Instance Transform(double[] start,double[] end)
	{
				
		int AttributeNum = 0;
		ArrayList<Double> x = new ArrayList<Double>();
		ArrayList<String> title =new ArrayList<String>();
		
		for(int i=0;i<WaveNum.length;i++)
		{
			for(int j=0;j<start.length;j++)
			{
				if(WaveNum[i]>=start[j] && WaveNum[i]<=end[j])
				{
					if(!title.contains(Double.toString(this.WaveNum[i])))
					{
						x.add(this.spectrumValue[i]);
						title.add(Double.toString(this.WaveNum[i]));
						AttributeNum+=1;
					}
				}
			}
		}
		
		if(AttributeNum>0)
		{
			return Transform(AttributeNum,x,title);
		}
		else
		{
			return null;
		}
		
	}
	private Instance Transform(int AttributeNum,ArrayList<Double> x,
			ArrayList<String> title)
	{
		Instance OneInstance;
		OneInstance = new Instance(AttributeNum);
		
		for(int i=0;i<AttributeNum;i++)
		{
			OneInstance.x[i]=x.get(i);
			OneInstance.xtitle[i]=title.get(i);
		}
		
		OneInstance.no=this.no;
		
		return OneInstance;
	}
	public void TransformSelf(double start,double end)
	{
		ArrayList<Double> x = new ArrayList<Double>();
		ArrayList<Double> title =new ArrayList<Double>();
		int AttributeNum = 0;
		
		for(int i=0;i<WaveNum.length;i++)
		{
			if(WaveNum[i]>=start && WaveNum[i]<=end)
			{
				x.add(this.spectrumValue[i]);
				title.add(this.WaveNum[i]);
				AttributeNum+=1;
			}
		}
		
		this.spectrumValue =new double[AttributeNum];
		this.WaveNum=new double[AttributeNum];
		
		for(int i=0;i<AttributeNum;i++)
		{
			this.spectrumValue[i]=x.get(i);
			this.WaveNum[i]=title.get(i);
		}
	}
	/**
	 * 保留指定波数范围内的数值
	 * @param start 起始波数
	 * @param end 终止波数
	 */
	public void TransformSelf(double[] start,double end[])
	{
		ArrayList<Double> x = new ArrayList<Double>();
		ArrayList<Double> title =new ArrayList<Double>();
		int AttributeNum = 0;
		
		if(start.length!=end.length)
		{
			return;
		}
		
		for(int i=0;i<WaveNum.length;i++)
		{
			for(int j=0;j<start.length;j++)
			{
				if(WaveNum[i]>=start[j] && WaveNum[i]<=end[j])
				{
					if(!title.contains(this.WaveNum[i]))
					{
						x.add(this.spectrumValue[i]);
						title.add(this.WaveNum[i]);
						AttributeNum+=1;
					}
				}
			}
		}
		
		this.spectrumValue =new double[AttributeNum];
		this.WaveNum=new double[AttributeNum];
		
		for(int i=0;i<AttributeNum;i++)
		{
			this.spectrumValue[i]=x.get(i);
			this.WaveNum[i]=title.get(i);
		}
	}
    public Object clone()
    {
    	Spectrum NewSpectrum=new Spectrum();
    	NewSpectrum.classInfo=this.classInfo;
    	NewSpectrum.target=this.target;
    	NewSpectrum.no=this.no;
    	NewSpectrum.spectrumValue=new double[this.spectrumValue.length];
    	NewSpectrum.WaveNum=new double[this.WaveNum.length];
    	/*if ( this.CommentInfo != null)
    	{
    		NewSpectrum.CommentInfo=new String[this.CommentInfo.length];
    		System.arraycopy(this.CommentInfo, 0, 
        			NewSpectrum.CommentInfo, 0,  this.CommentInfo.length);
    	}
    	
    	if (this.CommentTitle != null)
    	{
    		NewSpectrum.CommentTitle =new String[this.CommentTitle.length];
    		System.arraycopy(this.CommentTitle, 0, 
        			NewSpectrum.CommentTitle, 0,  this.CommentTitle.length);
    	}*/
    	NewSpectrum.CommentInfo.clear();
    	NewSpectrum.CommentInfo.putAll(this.CommentInfo);
    	System.arraycopy(this.spectrumValue, 0, 
    			NewSpectrum.spectrumValue, 0,  this.spectrumValue.length);
    	System.arraycopy(this.WaveNum, 0, 
    			NewSpectrum.WaveNum, 0,  this.WaveNum.length);    	
    	
    	return NewSpectrum;
    
    }
    /**
     * 复制另一条光谱某段波长内的数值
     * @param Other 另一条光谱
     * @param start 起始波长
     * @param end 终结波长
     * @return
     */
    int CopyFromSpectrum(Spectrum Other, double start,double end)
    {
    	int i,j;    	
    	
    	for(i=0;i<Other.WaveNum.length;i++)
    	{
    		if(Other.WaveNum[i]>=start && Other.WaveNum[i]<=end)
    		{
    			for (j=0;j<this.WaveNum.length;j++)
    			{
    				if (this.WaveNum[j]==Other.WaveNum[i])
    				{
    					this.spectrumValue[j]=Other.spectrumValue[i];
    					break;
    				}
    			}
    		}
    	}
    	return 0;
    }
	public double[] getSpectrumValue() {
		return spectrumValue;
	}
	
	public double SpectrumValue(int index)
	{
		return this.spectrumValue[index];
	}
	
	public void SetSpectrumValue(int index, double value) {
		 spectrumValue[index] = value;
	}

	public void setSpectrumValue(double[] spectrumValue) {
		this.spectrumValue = spectrumValue;
	}

	public double[] getWaveNum() {
		return WaveNum;
	}

	public void setWaveNum(double[] waveNum) {
		WaveNum = waveNum;
	}
	public int Length()
	{
		return spectrumValue.length;	
	}
	/**
	 * 输出到CSV文件
	 * @param filename 文件名
	 * @return
	 * @throws IOException
	 */
	public boolean WriteToCSV(String filename) throws IOException
	{
		FileWriter fw = null;	
		
		fw = new FileWriter(filename);
		
		for (int i=0;i< this.WaveNum.length ;i++)
		{
			fw.write(Double.toString(this.WaveNum[i]) + ",");
			fw.write(Double.toString(this.spectrumValue[i]) + '\n');
		}
		
		fw.close();
		return true;
	}
	public String JSON()
	{
		StringBuilder out=new StringBuilder();
		
		for(int i=0;i<WaveNum.length;i++)
		{
			out.append("{");
			out.append("'wavenum':"+ Double.toString(WaveNum[i])+",");
			out.append("'strong':" + Double.toString(spectrumValue[i]));
			out.append("},");
		}
		
		return out.substring(0,out.toString().length()-1);
	}
	public void Dispose()
	{
		spectrumValue=new double[1];
		this.WaveNum=new double[1];
		CommentInfo.clear();
		no="";
	}
}
