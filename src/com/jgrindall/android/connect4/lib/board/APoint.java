package com.jgrindall.android.connect4.lib.board;

import java.util.Comparator;

public class APoint implements Comparable<APoint> {
	
	public int x, y;
	
	public APoint(int x, int y){
		this.x = x;
		this.y = y;
	}
	public static Comparator<APoint> XComparator = new Comparator<APoint>() {

		@Override
		public int compare(APoint arg0, APoint arg1) {
			APoint p0 = (APoint)arg0;
			APoint p1 = (APoint)arg1;
			return (p0.x-p1.x);
		}
	};
	public static Comparator<APoint> YComparator = new Comparator<APoint>() {
		@Override
		public int compare(APoint arg0, APoint arg1) {
			APoint p0 = (APoint)arg0;
			APoint p1 = (APoint)arg1;
			return (p0.y-p1.y);
		}
	};
	@Override
	public String toString(){
		return ""+x+","+y;
	}
	@Override
	public int compareTo(APoint arg) {
		APoint p = (APoint)(arg);
		return (this.x-p.x);
	}

	public static APoint[] concat(APoint[] a0, APoint[] a1) {
		int a0L = a0.length;
		int a1L = a1.length;
		APoint[] ret = new APoint[a0L+a1L];
		int i;
		for(i=0;i<=a0L-1;i++){
			ret[i] = a0[i];
		}
		for(i=0;i<=a1L-1;i++){
			ret[i+a0L] = a1[i];
		}
		return ret;
	}
}