package com.jgrindall.android.connect4.lib.board;

import java.util.Arrays;
import com.jgrindall.android.connect4.lib.algorithm.AAlgorithm;
import com.jgrindall.android.connect4.lib.algorithm.AlgorithmConsts;
import com.jgrindall.android.connect4.lib.algorithm.LookUp;

public abstract class AConnect4Board implements IBoard{
	public static final int NUMX = 7;
	public static final int NUMY = 6;	
	protected int[][] full = new int[NUMX][NUMY];
	protected int numSpaces;
	protected int playersGo = Players.PLAYER1;
	protected int[] heights = new int[NUMX];
	protected int depth = 1;
	protected APoint[][] winLines;
	protected APoint[][] threeLines;
	protected APoint[][] twoLines;
	protected double rand;
	private int difficulty;
	protected AAlgorithm alg;
	
	
	public AConnect4Board(){
		WinLines.build();
		winLines = WinLines.winLines;
		threeLines = WinLines.threeLines;
		twoLines = WinLines.twoLines;
		reset();
	}
	public int getPlayersGo(){
		return playersGo;
	}
	public void setPlayersGo(int p){
		playersGo = p;
	}
	protected APoint playRandom(){
		return new APoint(0,0);
	}
	public void setDifficulty(int d){
		difficulty = d;
	}
	public double getDifficulty(){
		return difficulty;
	}
	public int getDepth(){
		return depth;
	}
	public void setDepth(int d){
		depth = d;
	}
	protected int countControlledPower(){
		int c=0;
		for(int i=0;i<=NUMX-1;i++){
			for(int j=0;j<=NUMY-1;j++){
				if(this.get(i, j)==Players.NONE && controls(i,j)){
					int s = WinLines.getStrengthAt(i, j);
					c+=s;
				}
			}
		}
		return c;
	}
	private boolean controls(int x, int y){
		int your = 0;
		int opp  = 0;
		int mini = Math.max(x-1, 0);
		int maxi = Math.min(x+1, NUMX-1);
		int minj = Math.max(y-1, 0);
		int maxj = Math.min(y+1, NUMY-1);
		for(int i=mini;i<=maxi;i++){
			for(int j=minj;j<=maxj;j++){
				int p = this.get(i, j);
				if(p==Players.POWER_PLAYER){
					your+=1;
					opp+=1;
				}
				else if(p==this.playersGo){
					your++;
				}
				else if(p!=Players.NONE){
					opp++;
				}
			}
		}
		boolean r = (your>=2 && your>opp && (your+opp)>=4);
		//System.out.println(r);
		return r;
	}
	private APoint makeFirstPlay(){
		// first move
		double firstMove = 0.25;
		double r = Math.random();
		if(r<firstMove){
			return new APoint(2,11); 
		}
		else if(r>1-firstMove){
			return new APoint(3,11);
		}
		else{
			return new APoint(4,11);
		}
	}
	public APoint getBestPlay(){
		if(this.numSpaces==NUMX*NUMY){
			return makeFirstPlay();
		}
		int look = this.lookUp();
		if(look>=0){
			return new APoint(look,PlayTypes.USED_LOOK);
		}
		int origDepth = depth;
		boolean randCol = (Math.random() < AlgorithmConsts.getRandCol(difficulty));
		if(randCol){
			return new APoint(getHighestStrengthCol(), PlayTypes.USED_RAND_COL);
		}
		boolean useRand = false;
		if(Math.random()<=AlgorithmConsts.getRandDepth(difficulty)){
			depth = AlgorithmConsts.getDepth(difficulty);
			useRand=true;
		}
		APoint m = alg.getBestPlay(depth);
		if(useRand){
			depth = origDepth;
			return new APoint(m.y,PlayTypes.USED_RANDOM_DEPTH);
		}
		else{
			return new APoint(m.y,PlayTypes.USED_MINIMAX);
		}
		
	}
	protected APoint[] orderColsByStrength(){
		APoint[] ret = new APoint[NUMX];
		int i;
		for(i=0;i<=NUMX-1;i++){
			ret[i] = new APoint(i,getStrengthOnCol(i));
		}
		Arrays.sort(ret, APoint.YComparator);
		return ret;
	}
	
	private int lookUp(){
		String s = this.encode();
		return LookUp.lookUp(s);
	}
	protected int countThreePower(){
		int r=0;
		int s = WinLines.NUM_3;
		for(int i=0;i<=s-1;i++){
			APoint[] line = threeLines[i];
			if(this.getOwns(line) ){
				r+=getPower(line);
			}
		}
		return r;
	}
	public int evaluateBoard(){
		return 0;
	}
	private int getPower(APoint[] line){
		int c=0;
		for(int i=0;i<=line.length-1;i++){
			c+=WinLines.getStrengthAt(line[i]);
		}
		return c;
	}
	protected int countTwoPower(){
		int r=0;
		int s = WinLines.NUM_2;
		for(int i=0;i<=s-1;i++){
			APoint[] line = twoLines[i];
			if(this.getOwns(line) ){
				r+=getPower(line);
			}
		}
		return r;
	}
	public int getHighestStrengthCol(){
		APoint[] s = this.orderColsByStrength();
		return (s[s.length-1].x);
	}
	public void outputE(int d, int v, int move){
		//System.out.println("DEPTH "+d+" best score for player "+playersGo+" for board\n");
		//this.output("");
		//System.out.println("is "+move+" value is "+v);
	}
	public void pushCol(int c, boolean power){
		int d = this.getStepsDown(c);
		if(!power){
			this.fill(c, d, playersGo);
		}
		else{
			this.fill(c, d, Players.POWER_PLAYER);
		}
		this.alternateTurn();
		heights[c]++;
	}
	public void popCol(int c){
		int d = this.getStepsDown(c);
		this.fill(c, d+1, Players.NONE);
		this.alternateTurn();
		heights[c]--;
	}
	public int getCanWinNow(){
		boolean w;
		for(int c=0;c<=NUMX-1;c++){
			w = playWinsNow(c);
			if(w){
				return c;
			}
		}
		return -1;
	}
	public boolean getOwns(APoint[] line ){
		int s = line.length;
		for(int i=0;i<=s-1;i++){
			int g = this.get(line[i]);
			if(g!=Players.POWER_PLAYER && g!=playersGo){
				return false;
			}
		}
		return true;
	}
	protected boolean playWinsNow(int c){
		if(!this.colFull(c)){
			this.pushCol(c, false);
			this.alternateTurn();
			APoint[] line = this.checkWin();
			this.popCol(c);
			this.alternateTurn();
			if(line!=null ){
				return true;
			}
		}
		return false;
	}
	public int getCanStopWin(){
		this.alternateTurn();
		for(int c=0;c<=NUMX-1;c++){
			if(playWinsNow(c)){
				this.alternateTurn();
				return c;
			}
		}
		this.alternateTurn();
		return -1;
	}
	public static int getAlternateTurn(int p){
		if(p==Players.PLAYER1){
			return Players.PLAYER2;
		}
		else if(p==Players.PLAYER2){
			return Players.PLAYER1;
		}
		return 0;
	}
	public void alternateTurn(){
		playersGo = AConnect4Board.getAlternateTurn(playersGo);
	}
	public APoint[] checkWin(){
		int s = WinLines.NUM_WIN;
		boolean owns;
		for(int i=0;i<=s-1;i++){
			APoint[] line = winLines[i];
			owns = this.getOwns(line);
			if(owns){
				return line;
			}
		}
		return null;
	}
	public int getStepsDown(int c){
		return NUMY - 1 - heights[c];
	}
	
	public boolean colFull(int c){
		return (heights[c]==NUMY);
	}
	public int getNumSpaces(){
		return numSpaces;
	}
	public int get(APoint p){
		return this.get(p.x, p.y);
	}
	private int getStrengthOnCol(int c){
		int d = getStepsDown(c);
		int s;
		if(d==-1){
			s= -1;
		}
		else{
			s = WinLines.getStrengthAt(c, d);
		}
		return s;
	}
	public int get(int i, int j){
		return full[i][j];
	}
	public void fill(int i,int j,int v){
		full[i][j] = v;
		if(v==Players.NONE){
			numSpaces++;
		}
		else{
			numSpaces--;
		}
	}
	public void fill(APoint p,int v){
		fill(p.x,p.y,v);
	}
	public void reset(){
		heights = new int[NUMX];
		for(int i=0;i<=AConnect4Board.NUMX-1;i++){
			for(int j=0;j<=AConnect4Board.NUMY-1;j++){
				full[i][j] = Players.NONE;
			}
			heights[i] = 0;
		}
		setPlayersGo(Players.PLAYER1);
		numSpaces=NUMX*NUMY;
		
	}
	
	public String encode(){
		String s="";
		int gap=0;
		int c;
		for(int j=0;j<=NUMY-1;j++){
			for(int i=0;i<=NUMX-1;i++){
				c = this.get(i, j);
				if(c==Players.NONE){
					gap++;
				}
				else{
					if(gap>=1){
						s+=""+gap;
						gap=0;
					}
					if(c==Players.PLAYER1){
						s+="R";
					}
					else if(c==Players.PLAYER2){
						s+="Y";
					}
				}
			}
		}
		return s;
	}
	
	public void output(String pad){
		String line;
		for(int j=0;j<=AConnect4Board.NUMY-1;j++){
			line=pad;
			for(int i=0;i<=AConnect4Board.NUMX-1;i++){
				int cont = get(i, j);
				if(cont==Players.NONE){
					line+="O ";
				}
				else if(cont==Players.PLAYER2){
					line+="Y ";
				}
				else if(cont==Players.PLAYER1){
					line+="R ";
				}
			}
			System.out.println(line);
		}
		
	}
	
	
	
}