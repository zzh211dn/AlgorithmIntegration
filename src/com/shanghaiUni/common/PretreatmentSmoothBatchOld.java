/**
 * 
 */
package com.shanghaiUni.common;
import com.shanghaiUni.datastruct.SpectrumInfoGroup;
/**
 * @author Administrator
 * 批处理光谱基类
 */
public class PretreatmentSmoothBatchOld extends Pretreatment {
    /* (non-Javadoc)
     * @see com.shanghaiUni.common.Pretreatment#ComputeSmooth(com.shanghaiUni.datastruct.SpectrumInfoGroup)
     */
    @ Override
	public int ComputeSmooth(SpectrumInfoGroup ComputeSpect) 
	{
		double[] OriginSet; 
		double[] ResultSet;
		double[] WaveNum;
        int SpectNum = ComputeSpect.size();
        int SpectLength = ComputeSpect.get(0).Length();

        OriginSet =new double[SpectNum * SpectLength];
        ResultSet =new double[SpectNum * SpectLength];
        WaveNum =new double[SpectLength];

        int i, j;

        System.arraycopy(ComputeSpect.get(0).getWaveNum(), 0, WaveNum, 0, SpectLength);
        

        for (i = 0 ; i < SpectNum; i++)
        {
            for (j = 0 ;j< SpectLength; j++)
            {
                OriginSet[j * SpectNum + i] = ComputeSpect.get(i).SpectrumValue(j);
            }
        }

        if (ComputeBatch(WaveNum, OriginSet, ResultSet, SpectNum, SpectLength))
        {
            for (i = 0; i < SpectNum; i++)
            {
                for (j = 0; j < SpectLength; j++)
                {
                    ComputeSpect.get(i).SetSpectrumValue(j, ResultSet[j * SpectNum + i]) ;
                }
            }
        }
		return 0;
	}
	/**
	 * 批处理
	 * @param waveNum 波数
	 * @param originSet 原始数值
	 * @param resultSet 计算后数值
	 * @param SpectNum 光谱个数
	 * @param SpectLength 光谱长度
	 * @return
	 */
	public boolean ComputeBatch(double[] waveNum, double[] originSet,double[] resultSet, int SpectNum, int SpectLength)
	{
		return false;
	}
}
