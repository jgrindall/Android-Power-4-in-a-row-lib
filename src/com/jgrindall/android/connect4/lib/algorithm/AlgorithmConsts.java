package com.jgrindall.android.connect4.lib.algorithm;

public abstract class AlgorithmConsts{
		
	public static final int[] DEPTHS = {1,2,4};
	public static final double[] COLUMN_RANDOMS = {0.4, 0.1, 0};
	public static final double[] DEPTH_RANDOMS = {1, 0.5, 0.25};
	
	
	public static final int getDefaultDepth(){
		return getDepth(2);
	}
	public static final int getDepth(int d){
		return DEPTHS[d];
	}
	public static final double getRandCol(int d){
		return COLUMN_RANDOMS[d];
	}
	public static final double getRandDepth(int d){
		return DEPTH_RANDOMS[d];
	}
	
	
}