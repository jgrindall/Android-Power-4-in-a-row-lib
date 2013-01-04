
import com.jgrindall.android.connect4.lib.board.Board;
import com.jgrindall.android.connect4.lib.board.IBoard;

public class Main{
	
	public final static void main(String[] args){
		
		new Main();
		
	}
	
	public Main(){
		IBoard b = new Board();
		System.out.println("built "+b);
	}
	
}