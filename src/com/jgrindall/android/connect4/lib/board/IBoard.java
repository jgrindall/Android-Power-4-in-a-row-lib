package com.jgrindall.android.connect4.lib.board;

public interface IBoard{
	
	public int getPlayersGo();
	public void setPlayersGo(int p);
	public APoint getBestPlay();
	public void pushCol(int c, boolean power);
	public void popCol(int c);
	public int getCanWinNow();
	public void alternateTurn();
	public APoint[] checkWin();
	public int getStepsDown(int c);
	public boolean getOwns(APoint[] line );
	public boolean colFull(int c);
	public int getNumSpaces();
	public int get(APoint p);
	public int get(int i, int j);
	public void fill(APoint p,int v);
	public void reset();
	public String encode();
	public void output(String pad);
	public int evaluateBoard();
	public void setDifficulty(int d);
	public void setDepth(int d);
	public int getDepth();
	public int getHighestStrengthCol();
	public int getCanStopWin();
	
}