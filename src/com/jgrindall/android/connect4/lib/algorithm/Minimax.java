package com.jgrindall.android.connect4.lib.algorithm;

import com.jgrindall.android.connect4.lib.board.APoint;
import com.jgrindall.android.connect4.lib.board.Board;
import com.jgrindall.android.connect4.lib.board.IBoard;

public class Minimax extends AAlgorithm{
	
	public Minimax(IBoard b){
		super(b);
	}
	public APoint minimax(int depth){
		int ns0 = b.getNumSpaces();
		APoint val;
		int c;
		int bestMove = -1;
		int bestVal = Integer.MIN_VALUE;
		APoint[] youWin = b.checkWin();
		b.alternateTurn();
		APoint[] oppWin = b.checkWin();
		b.alternateTurn();
		if(youWin!=null){
			return new APoint(Integer.MAX_VALUE,bestMove);
		}
		else if(oppWin!=null){
			return new APoint(Integer.MIN_VALUE,bestMove);
		}
		if(depth==0){
			int eval = b.evaluateBoard();
			return new APoint(eval,b.getHighestStrengthCol());
		}
		c = b.getCanWinNow();
		if(c>=0){
			return new APoint(Integer.MAX_VALUE,c);
		}
		c = b.getCanStopWin();
		if(c>=0){
			return new APoint(Integer.MAX_VALUE,c);
		}
		if(ns0!=b.getNumSpaces()){
			return new APoint(Integer.MAX_VALUE,0);
		}
		for(c=0;c<=Board.NUMX-1;c++){
			if(!b.colFull(c)){
				b.pushCol(c,false);
				val = minimax(depth-1);
				int nVal = -val.x;
				b.popCol(c);
				if(nVal>bestVal){
					bestVal = nVal;
					bestMove = c;
				}
			}
		}
		return new APoint(bestVal,bestMove);
	}
	public APoint getBestPlay(int depth){
		return minimax(depth);
	}
	
	
}