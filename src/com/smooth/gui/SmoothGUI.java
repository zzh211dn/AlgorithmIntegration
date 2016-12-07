/**
 * 
 */
package com.smooth.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileWriter;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.shanghaiUni.compute.Compute;
import com.shanghaiUni.compute.SmoothCompute;
import com.smooth.parameter.SmoothParameter;
import com.smooth.parameter.SmoothParameters;
/**
 * @author Administrator
 *
 */
public class SmoothGUI extends JFrame 
 implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5935849088295284388L;
	private JTextField fileField;
	private JButton chooseSrcButton;
	private JButton computeButton;
	private static SmoothGUI process;
	private JPanel controlPanel;
	private JPanel parameterPanel;
	private Container container;
	private SmoothParameters smoothParameters;
	private boolean InitComplete; 
	private int ControlNum;
	/**
	 * @param args
	 */
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		SmoothGUI smoothGUI=new SmoothGUI();
//
//	}
	public SmoothGUI()
	{
		this.container = getContentPane();
		Toolkit kit=Toolkit.getDefaultToolkit();
		int screenWidth=kit.getScreenSize().width;
		int screenHeight=kit.getScreenSize().height;
		this.setSize(400, (int)(screenHeight*0.8));
		this.controlPanel = new JPanel();
		
		this.chooseSrcButton = new JButton("浏览文件夹");
		this.chooseSrcButton.addActionListener(this);
		
		this.fileField=new JTextField(30);
		
		this.computeButton = new JButton("计算");
		this.computeButton.setEnabled(false);
		this.computeButton.addActionListener(new ComputeListener());
		
		this.controlPanel.add(fileField);
		this.controlPanel.add(chooseSrcButton);
		this.controlPanel.add(computeButton);
	    
		this.container.add(this.controlPanel,BorderLayout.SOUTH);
		
		parameterPanel = new JPanel();
		GridBagLayout layout = new GridBagLayout();
		
		parameterPanel.setLayout(layout);
		parameterPanel.add(new JLabel("预处理方法"), new GBC(0,0).setAnchor(GBC.WEST));
		parameterPanel.add(new JLabel("参数"),new GBC(1,0).setAnchor(GBC.WEST));
		parameterPanel.add(new JLabel("波长范围"),new GBC(2,0).setAnchor(GBC.WEST));
		parameterPanel.add(new JLabel(),new GBC(3,0).setAnchor(GBC.WEST));
		parameterPanel.add(new JLabel(),new GBC(4,0).setAnchor(GBC.WEST));
		
		smoothParameters = new SmoothParameters();
		
		try
		{
			smoothParameters.Init("Config\\");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		ControlNum = 5;
		for(int i = 0;i < ControlNum;i++)
		{
			MakeCombo(i);
		}
		this.container.add(parameterPanel,BorderLayout.CENTER);
		
		InitComplete=true;
		
//	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setLocationByPlatform(true);
	    this.setTitle("光谱预处理程序 2016");
	    pack();
	    setVisible(true);	   
	    System.out.println();
	}
	/**
	 * 加载参数选择框
	 * @param i 序号
	 */
	private void MakeCombo(int i)
	{
		JComboBox param =new JComboBox();
		param.setName("Smooth" + Integer.toString(i));		
		
		JTextField txtpara = new JTextField(8);
		txtpara.setName("Parameter" + Integer.toString(i));
		txtpara.setText("0");
		txtpara.setEnabled(false);
		
		JTextField wavebegin = new JTextField(8);
		wavebegin.setName("wavebegin" + Integer.toString(i));
		wavebegin.setText("3999");
		
		JTextField waveend = new JTextField(8);
		waveend.setName("waveend" + Integer.toString(i));
		waveend.setText("8000");
		
		param.addItemListener(new ComboClick());
		
		parameterPanel.add(param,new GBC(0,i+1).setAnchor(GBC.WEST));
		parameterPanel.add(txtpara,new GBC(1,i+1).setAnchor(GBC.WEST));
		parameterPanel.add(wavebegin,new GBC(2,i+1).setAnchor(GBC.WEST));
		parameterPanel.add(new JLabel("到"),new GBC(3,i+1).setAnchor(GBC.WEST));
		parameterPanel.add(waveend,new GBC(4,i+1).setAnchor(GBC.WEST));
		
		int j;
		
		
		for(SmoothParameter one:smoothParameters)
		{
			param.addItem(one.getMethodname());

		}
		
		
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
	     JFileChooser fileChooser = new JFileChooser();
	     //fileChooser.setCurrentDirectory("");
	     fileChooser.setDialogTitle("打开文件夹");
	     fileChooser.setMultiSelectionEnabled(false);
         fileChooser.setFileSelectionMode(1);
         
         int flag = fileChooser.showOpenDialog(process);
         if (flag == 0) {
        	 this.fileField.setText(fileChooser.getSelectedFile().getPath());
             this.computeButton.setEnabled(true);        
           }
         else
         {
        	 this.computeButton.setEnabled(false);
         }
           
        }
	
	/**
	 * 下拉框事件
	 * @author Administrator
	 *
	 */
	public class ComboClick implements ItemListener
	{

		@Override
		public void itemStateChanged(ItemEvent e) {
			// TODO Auto-generated method stub
			if(InitComplete && e.getStateChange() == 1)
			{
				String AllName[] = e.getSource().toString().split(",");
				
				String Index = AllName[0].replace("javax.swing.JComboBox[Smooth", "");
				
				Component Point = null;
				for(Component one:parameterPanel.getComponents())
				{
					if(one.getName() != null && one.getName().equals("Parameter" + Index))
					{
						Point = one;
					}
				}
				
				if(Point != null)
				{
					JTextField PointText = (JTextField) Point;
					String MethodValue = smoothParameters.GetMethodOneValue(e.getItem().toString());
					
					PointText.setText(MethodValue);
					PointText.setToolTipText(smoothParameters.GetMethodComment(e.getItem().toString()));
					if (MethodValue.equals("0"))
					{
						PointText.setEnabled(false);
					}
					else
					{
						PointText.setEnabled(true);
					}
				}
			}
		}
		
	}
	
	/**
	 * 计算按钮事件
	 * @author Administrator
	 *
	 */
	public class ComputeListener implements ActionListener
	{

		private <T extends Component> int FindControl(T one, T[] group, String Name)
		{
			int i;
			for(i = 0; i < ControlNum ; i++ )
			{
				if(one.getName().equals(Name + Integer.toString(i)))
				{
					group[i] = one;
					return 0;
				}
			}
			
			return 1;
		}
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			// TODO Auto-generated method stub
			JComboBox currentsmooth[] = new JComboBox[ControlNum];
			JTextField currentpara[] = new JTextField[ControlNum];
			JTextField wavebegin[] = new JTextField[ControlNum];
			JTextField waveend[] = new JTextField[ControlNum];
			
			int i;
			int value = -1;
			for(Component one:parameterPanel.getComponents())
			{
				value = -1;
				if(one instanceof JComboBox)
				{
					FindControl((JComboBox)one, currentsmooth,"Smooth");
				}
				
				if(one instanceof JTextField)
				{
					value = FindControl((JTextField)one, currentpara,"Parameter");
					if(value == 1)
					{
						value = FindControl((JTextField)one, wavebegin,"wavebegin");
						if(value == 1)
						{
							value = FindControl((JTextField)one, waveend,"waveend");
						}
					}
				}
			}	
				
			ArrayList<String> SmoothParamterList=new ArrayList<String>();
			
			for(i = 0;i < ControlNum; i++)	
			{	
				if (currentsmooth[i].getSelectedItem().toString().equals("空"))
				{
					break;
				}
				
				String MethodName = currentsmooth[i].getSelectedItem().toString();
					
				String MethodClass=smoothParameters.GetClassMethod(MethodName);
				String[] Parameter=new String[2];
				String MethodParameter;
				
				
				if(MethodClass!=null && !MethodClass.equals(""))
				{
					Parameter[0]=smoothParameters.GetMethodValue(MethodName);
					Parameter[1]=currentpara[i].getText().trim();
					MethodParameter=Compute.Parameter(MethodClass, Parameter);
					if (!MethodParameter.equals(""))
					{
						SmoothParamterList.add(MethodParameter + 
									wavebegin[i].getText().trim()+
									","+waveend[i].getText().trim());
							//out.println(MethodParameter);
					}
				}
				else
				{
					break;
				}
			}
			/*for(String one:SmoothParamterList)
			{
				System.out.println(one);
			}*/
			
			if (SmoothParamterList.size() == 0)
			{
				JOptionPane.showMessageDialog(process, "参数错误", "错误",JOptionPane.OK_OPTION);
				return; 
			}
			
			
			if (SmoothParamterList.size()>0)
			{
				String filename = fileField.getText().replace(".SPA","")+ "\\SmoothParameter.cop";
				
				FileWriter fw = null;	
				try
				{
					fw = new FileWriter(filename);
					fw.write(Integer.toString(SmoothParamterList.size())+"\n");
					for(String EachParameter:SmoothParamterList)
					{
						fw.write(EachParameter+"\n");
					}
				
					fw.close();
				
					String info=SmoothProcess.CheckParameter(fileField.getText(),filename);
					
					if (!info.equals(""))
					{
						info = info.replace("</br>", "\n");
						info = info.replace("<br>", "");
						JOptionPane.showMessageDialog(process, info, "错误",JOptionPane.CLOSED_OPTION);
						return;
					}
					
					SmoothCompute smoothcomputeProcess = new SmoothCompute();;
					String[] Result = null ;
					
					Result = smoothcomputeProcess.Compute(fileField.getText(),filename);
					System.out.println(Result[0]);	
					System.out.println(Result[1]);
					JOptionPane.showMessageDialog(process, Result[0], "结果",JOptionPane.CLOSED_OPTION);
				}
				catch(Exception e1)
				{
					e1.printStackTrace();
				}
			}
		}
		
	}
}
