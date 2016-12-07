/**
 * 
 */
package com.smooth.parameter;

/**
 * @author Administrator
 *
 */
public class SmoothParameter {
	private String methodname;
	private String methodvalue;
	private String methodclass;
	private String methodcomment;
	private String value;
	/**
	 * 
	 */
	public SmoothParameter() {
		// TODO Auto-generated constructor stub
	}
	
	public SmoothParameter(String MethodName,String MethodValue)
	{
		  this.methodname=MethodName;
		  this.methodvalue=MethodValue;
	}
	public SmoothParameter(String[] arr)
	{
		 this.methodname=arr[0];
		 this.methodvalue=arr[1];
		 this.methodclass=arr[2];
		 this.methodcomment=arr[3];
		 this.value=arr[4];
	}
	public String getMethodname() {
		return methodname;
	}
	public void setMethodname(String methodname) {
		this.methodname = methodname;
	}
	public String getMethodvalue() {
		return methodvalue;
	}
	public void setMethodvalue(String methodvalue) {
		this.methodvalue = methodvalue;
	}
	public String getMethodclass() {
		return methodclass;
	}
	public void setMethodclass(String methodclass) {
		this.methodclass = methodclass;
	}

	public String getMethodcomment() {
		return methodcomment;
	}

	public void setMethodcomment(String methodcomment) {
		this.methodcomment = methodcomment;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
