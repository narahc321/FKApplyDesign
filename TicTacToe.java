/*
javac TicTacToe.java -d ClassFiles
java -cp  ClassFiles/ TicTacToe.TicTacToe

*/
package TicTacToe;
// import java.util.TextUtils;
import java.util.Arrays;

class Board{
	private int dimension = 3,boxes;
	private int[] board;
	Board(){
		boxes = dimension * dimension; 
		board = new int[boxes];
		for(int i = 0; i < boxes; i++)
			board[i] = 0;
	}

	public boolean setBox(int index, int player){
		if(!this.validIndex(index) || !this.validPlayer(player)){
			System.out.println("Please Select valid box");
			this.printBoard();
			System.out.println("player "+ player + " turn");
			return false;
		}
		else{
			board[index] = player;
			this.printBoard();
			return true;
		}
	}

	private boolean validIndex(int index){
		if(index >= boxes || board[index] != 0)
			return false;
		else 
			return true;
	}

	private boolean validPlayer(int player){
		if(player != 0 && player != 1)
			return false;
		else 
			return true;
	}

	private void printBoard(){
		for(int i = 0; i < boxes; i++){
			if(i % dimension == 0){
				System.out.println();
			}
			if(board[i] == 1)
				System.out.print("O ");
			else if(board[i] == 2)			
				System.out.print("X ");
			else 
				System.out.print("  ");
		}
	}

	public String getBoard(){
		return Arrays.toString(board);
		// return TextUtils.join("",board);

	}
}

public class TicTacToe{
	public static void main(String[] args){
		Board b = new Board();
		System.out.print(b.getBoard());
	}
}