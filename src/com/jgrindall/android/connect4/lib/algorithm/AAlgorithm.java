package com.jgrindall.android.connect4.lib.algorithm;

import com.jgrindall.android.connect4.lib.board.APoint;
import com.jgrindall.android.connect4.lib.board.IBoard;

public abstract class AAlgorithm{
	
	protected IBoard b;
	
	
	public AAlgorithm(IBoard b){
		this.b = b;
	}
	
	public abstract APoint getBestPlay(int depth);

	
}