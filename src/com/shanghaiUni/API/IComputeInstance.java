/**
 * 
 */
package com.shanghaiUni.API;

import com.shanghaiUni.datastruct.Instances;

/**
 * @author Administrator
 *
 */
public interface IComputeInstance {
	public int Compute(Instances instances, String filename) throws Exception;
	public String[] ComputeToFile(Instances instances, String filename, String Path) throws Exception;
}
