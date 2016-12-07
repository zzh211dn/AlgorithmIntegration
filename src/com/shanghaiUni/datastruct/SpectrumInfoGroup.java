/**
 * 
 */
package com.shanghaiUni.datastruct;

import com.shanghaiUni.datastruct.Instance;
import com.shanghaiUni.datastruct.Instances;
import com.shanghaiUni.datastruct.TitleType;
import com.shanghaiUni.Excel.ExcelOperation;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/*
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
*/
/**
 * @author Administrator
 * 光谱集合
 * 2013.04.27 修改过
 */
@SuppressWarnings("serial")
public class SpectrumInfoGroup extends ArrayList<Spectrum>
implements Cloneable {
	 String NoTitle  = "序号";
	 String ClassTitle  = "类别信息";
	 String TargetTitle  = "目标值";
	 String CommentTitle  = null;
	 double[] difference=null;
	 double[] waveNum=null; 
	 private ArrayList<Double> Wave=new ArrayList<Double>();
	 private ArrayList<Integer> thisWave=new ArrayList<Integer>();
	 private ArrayList<Integer> otherWave=new ArrayList<Integer>();
	 
	 public Object clone()
	 {
		 SpectrumInfoGroup NewSpectrumInfoGroup=new SpectrumInfoGroup();
		 NewSpectrumInfoGroup.NoTitle=this.NoTitle;
		 NewSpectrumInfoGroup.ClassTitle=this.ClassTitle;
		 NewSpectrumInfoGroup.TargetTitle=this.TargetTitle;
		 NewSpectrumInfoGroup.CommentTitle=this.CommentTitle;
		 
		 for(Spectrum One:this)
		 {
			 NewSpectrumInfoGroup.add((Spectrum)One.clone());
		 }
		 
		 return NewSpectrumInfoGroup;
	  }
	 
	 public boolean add(Instance instance)
	 {
		 if (instance.titletype != TitleType.DoubleType)
		 {
			 return false;
		 }
		 
		 Spectrum NewSpectrum =new Spectrum(instance);
		 
		 this.add(NewSpectrum);
		 
		 return true;
	 }
	 
	 public Instances getInstances()
	 {
		 Instances NewInstance = new Instances();
		 int i;
		 int SpectLength;
		 for(Spectrum One:this)
		 {
			 Instance OneInstance;
			 SpectLength = One.Length();
			 OneInstance =new Instance(SpectLength);
			 for (i =0;i< SpectLength; i++)
			 {
				 OneInstance.xtitle[i] = Double.toString(One.WaveNum[i]);
			 }
			 System.arraycopy(One.spectrumValue, 0, OneInstance.x, 0, SpectLength);
			 NewInstance.addInstance(OneInstance);
		 }
		 return NewInstance;		 
	 }
	 /**
	  * 转换集合内光谱吸收峰强度为一维数组
	 * @return 光谱波数不一致时为null
	 */
	public double[] GetTrainSet()
	 {
		 double TrainSet[];
		 
		 int AttributeNum = this.get(0).Length();
		 int SpectrumNum = this.size();
		 int i,j,k;
		 
		 for (i=0;i<SpectrumNum;i++)
		 {
			 if (AttributeNum != this.get(i).Length())
			 {
				 return null;
			 }
		 }
		 
		 TrainSet = new double[AttributeNum * SpectrumNum];
		 
		 for (i=0;i<SpectrumNum;i++)
		 {
			 
			 k=i*AttributeNum;			 
			 for (j=0;j<AttributeNum;j++)
			 {
				 TrainSet[k+j]=this.get(i).SpectrumValue(j);
			 }
		 }
		 return TrainSet;
	 }
	 /**
	 * 复制光谱集合
	 * @param Other 另一个光谱集合
	 * @param start 起始波数
	 * @param end 终止波数
	 * @return
	 */
	public int CopyFromSpectrumGroup(SpectrumInfoGroup Other,double start, double end)
	 {
		 int i,j;
		 
		 for(j=0;j<Other.size();j++)
		 {
			 for(i=0;i<this.size();i++)
			 {			 
				 if(Other.get(j).no.equals(this.get(i).no))
				 {
					 this.get(i).CopyFromSpectrum(Other.get(j), start, end);
					 break;
				 }
			 }
		 }
		 return 0;
	 }
	 /**
	  * 计算标准差
	 * @return
	 */
	public boolean ComputeDifference()
	 {
		 if(this.size()<2) 
		 {
			 return false;
		 }
		 
		 if (difference==null)
		 {
			 int WaveLength;
			 WaveLength=this.get(0).Length();
			 for(Spectrum One:this)
			 {
				 if(WaveLength!=One.Length())
				 {
					 return false;
				 }
			 }
		 
			 difference=new double[WaveLength];
			 waveNum=new double[WaveLength];
		 
			 double[] array=new double[this.size()];
			 int i,j=0;
		 
			 for(i=0;i<this.get(0).Length();i++)
			 {
				 for(j=0;j<this.size();j++)
				 {
					 array[j]=this.get(j).SpectrumValue(i);				 
				 }
				 difference[i]=getStandardDevition(array,this.size());
				 waveNum[i]=this.get(0).WaveNum[i];
			 }
		 
			 array=null;
		 }
		 return true;
	 }
	 private double getAverage(double[] array,int num)
	 {
	        double sum = 0;
	        for(int i = 0;i < num;i++)
	        {
	            sum += array[i];
	        }
	        return (double)(sum / num);
	 }
	 private double getStandardDevition(double[] array,int num)
	 {
	        double sum = 0;
	        double Average=getAverage(array,num);
	        for(int i = 0;i < num;i++){
	            sum += (array[i] -Average) * (array[i] -Average);
	        }
	        return Math.sqrt((sum / (num - 1)));
	   }
	 public int GetDiffenceOfWave(SpectrumInfoGroup other)
	 {
		 int i,j;
		 
		 Wave.clear();
		 thisWave.clear();
		 otherWave.clear();
		 
		 for(i=0;i<this.waveNum.length;i++)
		 {
			 for(j=0;j<other.waveNum.length;j++)
			 {
				 if(this.waveNum[i]==other.waveNum[j])
				 {
					 Wave.add(this.waveNum[i]);
					 thisWave.add(i);
					 otherWave.add(j);
					 break;
				 }
			 }
		 }
		 
		 return Wave.size();
	 }
	 public boolean CompareDiffence(SpectrumInfoGroup other,double wavenum[],double diff1[],double diff2[])
	 {
		 int i;
		 if(Wave.size()>0)
		 {
			 int wavelength=Wave.size();			 
			 for(i=0;i<wavelength;i++)
			 {
				 wavenum[i]=Wave.get(i);
				 diff1[i]=this.difference[thisWave.get(i)];
				 diff2[i]=other.difference[otherWave.get(i)];
			 }
		 }
		 else
		 {
			 return false;
		 }
			 
			
		 return true;
	 }
	 public boolean ReadCommentInfo(String Excelfile) throws FileNotFoundException, IOException
	 {
		 Workbook workbook = ExcelOperation.getWorkbook(Excelfile);
		 
		 //HSSFWorkbook workbook = ExcelOperation.getWorkbook(Excelfile);
		 ReadSheetInfo(workbook,"检测指标",0);
		 ReadSheetInfo(workbook,"光谱信息",1);
		workbook=null;
	    return true;
	}
	 /**
	 * @param workbook
	 * @param sheetname 表格名称
	 * @param colindex 光谱文件名所在列
	 */
	private void ReadSheetInfo(Workbook workbook,String sheetname,int colindex)
	 {
		 int startRow=0;
		 int i,j;
		 
		 /*for(i=0;i<workbook.getNumberOfSheets();i++)
		 {
			 System.out.println(workbook.getSheetAt(i).getSheetName());
		 }*/
		Sheet sheet = workbook.getSheet(sheetname);
		 //HSSFSheet sheet = workbook.getSheet(sheetname);
		ArrayList<String> title=new ArrayList<String>();
		int colnum=0;
			 
		if (sheet!=null)
		{
			Row row = sheet.getRow(startRow);
			//HSSFRow row = sheet.getRow(startRow);
			//HSSFCell cell=null;
			Cell cell=null;	 
			//读取列标题
			while(true) 
			{
				cell= row.getCell(colnum);
				if (ExcelOperation.isEmpty(cell)) 
				{
					break;
				}
				else
				{
					title.add(cell.getStringCellValue());
					colnum+=1;
				}				 
			}
			
			int index=-1;
			row=null;
			String no;
			//读出行
			
			index=-1;
			for(startRow=1;startRow<=sheet.getLastRowNum();startRow++)
			{
				index=-1;
				row = sheet.getRow(startRow);				
				no=row.getCell(colindex).getStringCellValue().replace(".csv", "");
				no=no.replace(".CSV", "");
				for(j=0;j<this.size();j++)
				{
					if(this.get(j).no.equals(no))
					{
						index=j;
						break;
					}
				}
				if(index!=-1)
				{
					for(i=colindex+1;i<colnum;i++)
					{
						cell= row.getCell(i);
						if(this.get(j).CommentInfo.containsKey(title.get(i)))
						{
							String value=this.get(j).CommentInfo.get(title.get(i))+ ";" + cell.getStringCellValue();
							this.get(j).CommentInfo.remove(title.get(i));
							this.get(j).CommentInfo.put(title.get(i), value);
						}
						else
						{
							this.get(j).CommentInfo.put(title.get(i), cell.getStringCellValue());
						}
					}
				}
			}
				 
		}	
	 }
	public String JSON()
	{
		StringBuilder out= new StringBuilder();
		
		out.append("[");
		for(Spectrum one: this)
		{
			out.append(one.JSON());
		}
		out.append("]");
		return out.toString();
	}
	public void Dispose()
	{
		for(Spectrum One: this)
		{
			One.Dispose();
		}
		this.clear();
	}
}
