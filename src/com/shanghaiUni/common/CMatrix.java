/**
 * 
 */
package com.shanghaiUni.common;

/**
 * @author Administrator
 *
 */
public class CMatrix {
	private int m_nNumColumns;
	private int m_nNumRows;
	private double[] m_pData;
	CMatrix()
	{
		this.m_nNumColumns =1;
		this.m_nNumRows =1;
		this.m_pData = null;
		Init(m_nNumRows,m_nNumColumns);
	}
	CMatrix(int nRows, int nCols)
	{
		this.m_nNumColumns =nRows;
		this.m_nNumRows =nCols;
		this.m_pData =null;
		Init(m_nNumRows,m_nNumColumns);
	}
	CMatrix(int nRows, int nCols,double[] value)
	{
		this.m_nNumColumns =nRows;
		this.m_nNumRows =nCols;
		this.m_pData =null;
		Init(m_nNumRows,m_nNumColumns);

		SetData(value);
	}
	void SetData(double[] value)
	{
		//empty the memory		
		//copy data		
		System.arraycopy(value, 0, m_pData, 0, m_nNumColumns*m_nNumRows);
	}
	

	// 初始化矩阵
	private boolean Init(int nRows, int nCols)
	{
		
		this.m_nNumColumns =nCols;
		this.m_nNumRows =nRows;
		int nSize=nCols*nRows;
		if (nSize<0) return false;

		//分配内存
		this.m_pData=new double[nSize];
		if (this.m_pData==null) return false;
		
		for (int i=0; i< nSize; i++)
		{
			m_pData[i]=0;
		}
		//memset(m_pData,0,sizeof(double)*nSize);
		return true;
	}

	// 获取矩阵的列数
	int GetNumColumns()
	{
		return m_nNumColumns;
	}

	// 获取矩阵的行数
	int GetNumRows()
	{
		return m_nNumRows;
	}

	// 约化一般实矩阵为赫申伯格矩阵的初等相似变化法
	void MakeHberg()
	{
		int i,j,k,u,v;
		i=0;
		double d,t;
		for(k=1;k<=this.m_nNumColumns-2;k++)
		{
			d=0.0;
			for(j=k;j<=this.m_nNumColumns-1;j++)
			{
				u=j*this.m_nNumColumns+k-1;
				t=m_pData[u];

				if (Math.abs(t)>Math.abs(d))
				{
					d=t;
					i=j;
				}
				if(d!=0.0)
				{
					if(i!=k)
					{
						for(j=k-1;j<this.m_nNumColumns-1;j++)
						{
							u=i*this.m_nNumColumns+j;
							v=k*this.m_nNumColumns+j;
							t=m_pData[u];
							m_pData[u]=m_pData[v];
							m_pData[v]=t;
						}

						for(j=0;j<this.m_nNumColumns-1;j++)
						{
							u=j*this.m_nNumColumns+i;
							v=j*this.m_nNumColumns+k;
							t=m_pData[u];
							m_pData[u]=m_pData[v];
							m_pData[v]=t;
						}
					}

					for(i=k+1;i<=this.m_nNumColumns-1;i++)
					{
						u=i*this.m_nNumColumns+k-1;
						t=m_pData[u]/d;
						m_pData[u]=0.0;
						for(j=k;j<=m_nNumColumns-1;j++)
						{
							v=i*m_nNumColumns+j;
							m_pData[v]=m_pData[v]-t*m_pData[k*m_nNumColumns+j];
						}

						for(j=k;j<=m_nNumColumns-1;j++)
						{
							v=j*m_nNumColumns+k;//有疑问
							m_pData[v]=m_pData[v]+t*m_pData[j*m_nNumColumns+i];
						}
					}
				}
			}
		}

	}

	boolean SymTriEigenv(double[] dblB, double[] dblC, CMatrix mtxQ,int nMaxIt, double eps)
	{
		int i,j,k,m,it,u,v;
	    double d,f,h,g,p,r,e,s;
	    
		// 初值
		int n = mtxQ.GetNumColumns();
		dblC[n-1]=0.0; 
		d=0.0; 
		f=0.0;
	    
		// 迭代计算

		for (j=0; j<=n-1; j++)
	    { 
			it=0;
	        h=eps*(Math.abs(dblB[j])+Math.abs(dblC[j]));
	        if (h>d) 
			{
				d=h;
			}
	        
			m=j;
	        while ((m<=n-1)&&(Math.abs(dblC[m])>d)) 
			{
				m=m+1;
			}
	        
			if (m!=j)
	        { 
				do
	            { 
					if (it==nMaxIt)
					{
						return false;
					}

	                it=it+1;
	                g=dblB[j];
	                p=(dblB[j+1]-g)/(2.0*dblC[j]);
	                r=Math.sqrt(p*p+1.0);
	                if (p>=0.0) 
					{
						dblB[j]=dblC[j]/(p+r);
					}
	                else 
					{
						dblB[j]=dblC[j]/(p-r);
					}
	                
					h=g-dblB[j];
	                for (i=j+1; i<=n-1; i++)
					{
						dblB[i]=dblB[i]-h;
					}
	                
					f=f+h; 
					p=dblB[m]; 
					e=1.0; 
					s=0.0;
	                for (i=m-1; i>=j; i--)
	                { 
						g=e*dblC[i]; 
						h=e*p;
	                    if (Math.abs(p)>=Math.abs(dblC[i]))
	                    { 
							e=dblC[i]/p; 
							r=Math.sqrt(e*e+1.0);
	                        dblC[i+1]=s*p*r; 
							s=e/r; 
							e=1.0/r;
	                    }
	                    else
						{ 
							e=p/dblC[i]; 
							r=Math.sqrt(e*e+1.0);
	                        dblC[i+1]=s*dblC[i]*r;
	                        s=1.0/r; 
							e=e/r;
	                    }
	                    
						p=e*dblB[i]-s*g;
	                    dblB[i+1]=h+s*(e*g+s*dblB[i]);
	                    for (k=0; k<=n-1; k++)
	                    { 
							u=k*n+i+1; 
							v=u-1;
	                        h=mtxQ.m_pData[u]; 
							mtxQ.m_pData[u]=s*mtxQ.m_pData[v]+e*h;
	                        mtxQ.m_pData[v]=e*mtxQ.m_pData[v]-s*h;
	                    }
	                }
	                
					dblC[j]=s*p; 
					dblB[j]=e*p;
	            
				} while (Math.abs(dblC[j])>d);
	        }
	        
			dblB[j]=dblB[j]+f;
	    }
	    
		for (i=0; i<=n-1; i++)
	    { 
			k=i; 
			p=dblB[i];
	        if (i+1<=n-1)
	        { 
				j=i+1;
	            while ((j<=n-1)&&(dblB[j]<=p))
	            { 
					k=j; 
					p=dblB[j]; 
					j=j+1;
				}
	        }

	        if (k!=i)
	        { 
				dblB[k]=dblB[i]; 
				dblB[i]=p;
	            for (j=0; j<=n-1; j++)
	            { 
					u=j*n+i; 
					v=j*n+k;
	                p=mtxQ.m_pData[u]; 
					mtxQ.m_pData[u]=mtxQ.m_pData[v]; 
					mtxQ.m_pData[v]=p;
	            }
	        }
	    }

		return true;
	}

	// 约化对称矩阵为对称三对角阵的豪斯荷尔德变换法
	boolean MakeSymTri(CMatrix mtxQ, CMatrix mtxT, double[] dblB, double[] dblC)
	{
		int i,j,k,u;
	    double h,f,g,h2;
	    
		// 初始化矩阵Q和T
		if (! mtxQ.Init(m_nNumColumns, m_nNumColumns) ||
			! mtxT.Init(m_nNumColumns, m_nNumColumns))
			return false;

		if (dblB == null || dblC == null)
			return false;

		for (i=0; i<=m_nNumColumns-1; i++)
		{
			for (j=0; j<=m_nNumColumns-1; j++)
			{ 
				u=i*m_nNumColumns+j; 
				mtxQ.m_pData[u]=m_pData[u];
			}
		}

	    for (i=m_nNumColumns-1; i>=1; i--)
	    { 
			h=0.0;
	        if (i>1)
			{
				for (k=0; k<=i-1; k++)
	            { 
					u=i*m_nNumColumns+k; 
					h=h+mtxQ.m_pData[u]*mtxQ.m_pData[u];
				}
			}

	        if (h == 0.0)
	        { 
				dblC[i]=0.0;
	            if (i==1) 
					dblC[i]=mtxQ.m_pData[i*m_nNumColumns+i-1];
	            dblB[i]=0.0;
	        }
	        else
	        { 
				dblC[i]=Math.sqrt(h);
	            u=i*m_nNumColumns+i-1;
	            if (mtxQ.m_pData[u]>0.0) 
					dblC[i]=-dblC[i];

	            h=h-mtxQ.m_pData[u]*dblC[i];
	            mtxQ.m_pData[u]=mtxQ.m_pData[u]-dblC[i];
	            f=0.0;
	            for (j=0; j<=i-1; j++)
	            { 
					mtxQ.m_pData[j*m_nNumColumns+i]=mtxQ.m_pData[i*m_nNumColumns+j]/h;
	                g=0.0;
	                for (k=0; k<=j; k++)
						g=g+mtxQ.m_pData[j*m_nNumColumns+k]*mtxQ.m_pData[i*m_nNumColumns+k];

					if (j+1<=i-1)
						for (k=j+1; k<=i-1; k++)
							g=g+mtxQ.m_pData[k*m_nNumColumns+j]*mtxQ.m_pData[i*m_nNumColumns+k];

	                dblC[j]=g/h;
	                f=f+g*mtxQ.m_pData[j*m_nNumColumns+i];
	            }
	            
				h2=f/(h+h);
	            for (j=0; j<=i-1; j++)
	            { 
					f=mtxQ.m_pData[i*m_nNumColumns+j];
	                g=dblC[j]-h2*f;
	                dblC[j]=g;
	                for (k=0; k<=j; k++)
	                { 
						u=j*m_nNumColumns+k;
	                    mtxQ.m_pData[u]=mtxQ.m_pData[u]-f*dblC[k]-g*mtxQ.m_pData[i*m_nNumColumns+k];
	                }
	            }
	            
				dblB[i]=h;
	        }
	    }
	    
		for (i=0; i<=m_nNumColumns-2; i++) 
			dblC[i]=dblC[i+1];
	    
		dblC[m_nNumColumns-1]=0.0;
	    dblB[0]=0.0;
	    for (i=0; i<=m_nNumColumns-1; i++)
	    { 
			if ((dblB[i]!=0.0)&&(i-1>=0))
			{
				for (j=0; j<=i-1; j++)
	            { 
					g=0.0;
					for (k=0; k<=i-1; k++)
						g=g+mtxQ.m_pData[i*m_nNumColumns+k]*mtxQ.m_pData[k*m_nNumColumns+j];

					for (k=0; k<=i-1; k++)
	                { 
						u=k*m_nNumColumns+j;
						mtxQ.m_pData[u]=mtxQ.m_pData[u]-g*mtxQ.m_pData[k*m_nNumColumns+i];
	                }
	            }
			}

	        u=i*m_nNumColumns+i;
	        dblB[i]=mtxQ.m_pData[u]; mtxQ.m_pData[u]=1.0;
	        if (i-1>=0)
			{
				for (j=0; j<=i-1; j++)
	            { 
					mtxQ.m_pData[i*m_nNumColumns+j]=0.0; 
					mtxQ.m_pData[j*m_nNumColumns+i]=0.0;
				}
			}
	    }

	    // 构造对称三对角矩阵
	    for (i=0; i<m_nNumColumns; ++i)
		{
		    for (j=0; j<m_nNumColumns; ++j)
			{
	            mtxT.SetElement(i, j, 0);
	            k = i - j;
	            if (k == 0) 
		            mtxT.SetElement(i, j, dblB[j]);
				else if (k == 1)
		            mtxT.SetElement(i, j, dblC[j]);
				else if (k == -1)
		            mtxT.SetElement(i, j, dblC[i]);
	        }
	    }
		return true;
	}

	// 设置指定元素的值
	boolean SetElement(int nRow, int nCol, double value)
	{
		return false;
	}

	// 把矩阵传给数组
	boolean GetAllElement(double[] value)
	{
		int i=0;

		for(i=0;i<m_nNumColumns*m_nNumRows;i++)
		{
			value[i]=m_pData[i];
		}
		return false;
	}
	//////////////////////////////////////////////////////////////////////
	// 实矩阵求逆的全选主元高斯－约当法
	//
	// 参数：无
	//
	// 返回值：BOOL型，求逆是否成功
	//////////////////////////////////////////////////////////////////////
	boolean InvertGaussJordan()
	{
		int pnRow[], pnCol[],i,j,k,l,u,v;
	    double d = 0, p = 0;

		// 分配内存
	    pnRow = new int[m_nNumColumns];
	    pnCol = new int[m_nNumColumns];
		if (pnRow == null || pnCol == null)
			return false;

		// 消元
	    for (k=0; k<=m_nNumColumns-1; k++)
	    { 
			d=0.0;
	        for (i=k; i<=m_nNumColumns-1; i++)
			{
				for (j=k; j<=m_nNumColumns-1; j++)
				{ 
					l=i*m_nNumColumns+j; p=Math.abs(m_pData[l]);
					if (p>d) 
					{ 
						d=p; 
						pnRow[k]=i; 
						pnCol[k]=j;
					}
				}
			}
	        
			// 失败
			if (d == 0.0)
			{				
				pnRow=null;
				pnCol=null;
				return false;
			}

	        if (pnRow[k] != k)
			{
				for (j=0; j<=m_nNumColumns-1; j++)
				{ 
					u=k*m_nNumColumns+j; 
					v=pnRow[k]*m_nNumColumns+j;
					p=m_pData[u]; 
					m_pData[u]=m_pData[v]; 
					m_pData[v]=p;
				}
			}
	        
			if (pnCol[k] != k)
			{
				for (i=0; i<=m_nNumColumns-1; i++)
	            { 
					u=i*m_nNumColumns+k; 
					v=i*m_nNumColumns+pnCol[k];
					p=m_pData[u]; 
					m_pData[u]=m_pData[v]; 
					m_pData[v]=p;
	            }
			}

	        l=k*m_nNumColumns+k;
	        m_pData[l]=1.0/m_pData[l];
	        for (j=0; j<=m_nNumColumns-1; j++)
			{
				if (j != k)
	            { 
					u=k*m_nNumColumns+j; 
					m_pData[u]=m_pData[u]*m_pData[l];
				}
			}

	        for (i=0; i<=m_nNumColumns-1; i++)
			{
				if (i!=k)
				{
					for (j=0; j<=m_nNumColumns-1; j++)
					{
						if (j!=k)
						{ 
							u=i*m_nNumColumns+j;
							m_pData[u]=m_pData[u]-m_pData[i*m_nNumColumns+k]*m_pData[k*m_nNumColumns+j];
						}
	                }
				}
			}

	        for (i=0; i<=m_nNumColumns-1; i++)
			{
				if (i!=k)
	            { 
					u=i*m_nNumColumns+k; 
					m_pData[u]=-m_pData[u]*m_pData[l];
				}
			}
	    }

	    // 调整恢复行列次序
	    for (k=m_nNumColumns-1; k>=0; k--)
	    { 
			if (pnCol[k]!=k)
			{
				for (j=0; j<=m_nNumColumns-1; j++)
	            { 
					u=k*m_nNumColumns+j; 
					v=pnCol[k]*m_nNumColumns+j;
					p=m_pData[u]; 
					m_pData[u]=m_pData[v]; 
					m_pData[v]=p;
	            }
			}

	        if (pnRow[k]!=k)
			{
				for (i=0; i<=m_nNumColumns-1; i++)
	            { 
					u=i*m_nNumColumns+k; 
					v=i*m_nNumColumns+pnRow[k];
					p=m_pData[u]; 
					m_pData[u]=m_pData[v]; 
					m_pData[v]=p;
	            }
			}
	    }		

		// 成功返回
		return true;
	}
}
