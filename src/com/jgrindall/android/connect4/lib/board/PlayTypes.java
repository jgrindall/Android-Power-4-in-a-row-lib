package com.jgrindall.android.connect4.lib.board;

public class PlayTypes{

	public static final int USED_RANDOM_DEPTH=0;
	public static final int USED_LOOK=1;
	public static final int USED_MINIMAX=2;
	public static final int USED_RAND_COL=3;
	public static final int USED_FORCE=4;
	
	public static String getString(int i){
		if(i==USED_RANDOM_DEPTH){return "USED_RANDOM_DEPTH";}
		else if(i==USED_LOOK){return "USED_LOOK";}
		else if(i==USED_MINIMAX){return "USED_MINIMAX";}
		else if(i==USED_RAND_COL){return "USED_RAND_COL";}
		else if(i==USED_FORCE){return "USED_FORCE";}
		else{
			return "";
		}
	}
	
}