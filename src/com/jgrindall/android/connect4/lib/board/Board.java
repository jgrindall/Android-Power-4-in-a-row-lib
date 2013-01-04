package com.jgrindall.android.connect4.lib.board;

import com.jgrindall.android.connect4.lib.algorithm.Minimax;


public class Board extends AConnect4Board{
	
	public Board(){
		super();
		alg = new Minimax(this);
		//alg = new AlphaBeta(this);
	}
	
	@Override
	public int evaluateBoard(){
		int weight2 = 1;
		int weight3 = 4;
		int weightC = 3;
		int num2 = countTwoPower();
		int num3 = countThreePower();
		int numC = countControlledPower();
		alternateTurn();
		int opp2 = countTwoPower();
		int opp3 = countThreePower();
		int oppC = countControlledPower();
		alternateTurn();
		int v = weight3*(num3-opp3)+weightC*(numC-oppC)+weight2*(num2-opp2);
		return v;
	}
	
	
}