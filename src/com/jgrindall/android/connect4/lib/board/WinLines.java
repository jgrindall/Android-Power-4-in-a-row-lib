package com.jgrindall.android.connect4.lib.board;

public class WinLines{
	
	public final static int NUM_WIN = 69;
	public final static int NUM_3 = 98;
	public final static int NUM_2 = 131;
	
	public static APoint[][] twoLines = new APoint[NUM_2][2];
	public static APoint[][] threeLines = new APoint[NUM_3][3];
	public static APoint[][] winLines = new APoint[NUM_WIN][4];
	public static int[][] strength = new int[Board.NUMX][Board.NUMY];
	
	public static boolean built = false;
	
	
	public static void build(){
		if(!built){
			WinLines.buildLines(4, winLines);
			WinLines.buildLines(3, threeLines);
			WinLines.buildLines(2, twoLines);
			WinLines.buildStrength();
		}
		built = true;
	}
	public static int getStrengthAt(APoint p){
		return strength[p.x][p.y];
	}
	public static int getStrengthAt(int i, int j){
		return strength[i][j];
	}
	public static void buildStrength(){
		int[] r0 =  {3,4,5,5,4,3};
		int[] r1 =  {4,6,8,8,6,4};
		int[] r2 =  {5,8,11,11,8,5};
		int[] r3 =  {7,10,13,13,10,7};
		strength[0] = r0;
		strength[1] = r1;
		strength[2] = r2;
		strength[3] = r3;
		strength[4] = r2;
		strength[5] = r1;
		strength[6] = r0;
	}
	private static void buildLines(int n, APoint[][] vec){
		int i,j,k;
		APoint[] line;
		int num = 0;
		// horiz
		for(i=0;i<=Board.NUMX-n;i++){
			for(j=0;j<=Board.NUMY-1;j++){
				// horizontal
				line = new APoint[n];
				for(k=0;k<=n-1;k++){
					line[k] = new APoint(i+k,j);
				}
				vec[num] = line;
				num++;
			}
		}
		// vertic
		for(i=0;i<=Board.NUMX-1;i++){
			for(j=0;j<=Board.NUMY-n;j++){
				line = new APoint[n];
				for(k=0;k<=n-1;k++){
					line[k] = new APoint(i,j+k);
				}
				vec[num] = line;
				num++;
			}
		}
		// diag \
		for(i=0;i<=Board.NUMX-n;i++){
			for(j=0;j<=Board.NUMY-n;j++){
				line = new APoint[n];
				for(k=0;k<=n-1;k++){
					line[k] = new APoint(i+k,j+k);
				}
				vec[num] = line;
				num++;
			}
		}
		// diag /
		for(i=0;i<=Board.NUMX-n;i++){
			for(j=n-1;j<=Board.NUMY-1;j++){
				line = new APoint[n];
				for(k=0;k<=n-1;k++){
					line[k] = new APoint(i+k,j-k);
				}
				vec[num] = line;
				num++;
			}
		}
	}

	
}