/*
javac TicTacToe.java -d ClassFiles
java -cp  ClassFiles/ TicTacToe.TicTacToe

*/
package TicTacToe;
import java.util.Scanner;
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

	public void printBoard(){
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

interface Player{
	public void setBox();
	public int getPlayerType();
	public String getName();
}

class HumanPlayer implements Player{
	private String name;
	private int playerType;
	private Board board; 

	HumanPlayer(String name, int playerType, Board board){
		this.name = name;
		this.playerType = playerType;
		this.board = board;
	}

	public void setBox(){
		int index = -1;
    	Scanner sc = new Scanner(System.in);
		while(!board.setBox(index, this.playerType)){
			index = sc.nextInt();
		}
	}

	public int getPlayerType(){
		return playerType;
	}

	public String getName(){
		return name;
	}

}

class ComputerPlayer implements Player{
	private String name;
	private int playerType;
	private Board board; 

	ComputerPlayer(int playerType, Board board){
		this.name = "Computer";
		this.playerType = playerType;
		this.board = board;
	}

	private int getIndex(String s){
		int tempIndex = 0;
		for(char c : s.toCharArray()){
			if(c == '1' || c=='2')
				tempIndex++;
			else if(c == '0')	
				return tempIndex;
		}
		return tempIndex;
	}

	public void setBox(){
		int index = -1;
		while(!board.setBox(index, playerType)){
			index = getIndex(board.getBoard());
		}
	}

	public int getPlayerType(){
		return playerType;
	}

	public String getName(){
		return name;
	}

}

class GameState{
	private int dimension = 3,boxes;
	private int[] board;

	GameState(){
		boxes = dimension * dimension; 
		board = new int[boxes];
		this.dimension = 3;
	} 

	private void setBoard(String boardString){
		int tempIndex = 0;
		for(char c : boardString.toCharArray()){
			if(c == '1'){
				this.board[tempIndex] = 1;
				tempIndex++;
			}
			else if(c == '2'){
				this.board[tempIndex] = 2;
				tempIndex++;			
			}
			else if(c == '0'){
				this.board[tempIndex] = 0;
				tempIndex++;			
			}
		}
	} 

	public boolean isFull(String boardString){
		this.setBoard(boardString);
		for(int i = 0; i < boxes; i++){
			if(this.board[i] == 0)
				return false;
		}
		return true;
	}

	public boolean checkWin(int playerType){
		if(playerType == board[0] && playerType == board[1] && playerType == board[2])
			return true;
		if(playerType == board[3] && playerType == board[4] && playerType == board[5])
			return true;
		if(playerType == board[6] && playerType == board[7] && playerType == board[8])
			return true;
		if(playerType == board[0] && playerType == board[3] && playerType == board[6])
			return true;
		if(playerType == board[1] && playerType == board[4] && playerType == board[7])
			return true;
		if(playerType == board[2] && playerType == board[5] && playerType == board[8])
			return true;
		if(playerType == board[0] && playerType == board[4] && playerType == board[8])
			return true;
		if(playerType == board[2] && playerType == board[4] && playerType == board[6])
			return true;
		return false;
	}
}

class Game{
	private GameState gameState;
	private Board board;
	private Player player1, player2, currentPlayer;
	private Scanner sc;
	private int nPlayers;
	private boolean gameOver;
	private String tempName;
	
	Game(){
		this.board = new Board();
		this.gameState = new GameState();
		sc = new Scanner(System.in);
		this.setPlayers();
		currentPlayer = player1;
		gameOver = false;
	}

	private void setPlayers(){
		do{
			System.out.println("Enter 1 for Single Player");
			System.out.println("Enter 2 for Multi Player");
			nPlayers = sc.nextInt();
		}while( nPlayers != 1 && nPlayers != 2);

		if(nPlayers == 1){
			System.out.println("Enter player Name");
			tempName = sc.next();
			player1 = new HumanPlayer(tempName, 1, this.board);
			player2 = new ComputerPlayer(2, this.board); 
		}
		else{
			System.out.println("Enter player 1 Name");
			tempName = sc.next();
			player1 = new HumanPlayer(tempName, 1, this.board);
			System.out.println("Enter player 2 Name");
			tempName = sc.next();
			player2 = new HumanPlayer(tempName, 2, this.board);
		}
	}

	public void runGame(){
		while(!gameOver){
			currentPlayer.setBox();
			if(gameState.checkWin(currentPlayer.getPlayerType())){
				gameOver = true;
				System.out.println(" Player " + currentPlayer.getName() + " Won the Game");
			}
			else if(gameState.isFull(board.getBoard())){
				gameOver = true;
				System.out.println(" No more moves possible game is Tie");
			}
			else{
				if(currentPlayer.equals(player1)){
					currentPlayer = player2;
				}
				else{
					currentPlayer = player1;
				}
			}
			board.printBoard();
		}
	}
}

public class TicTacToe{
	public static void main(String[] args){
		Game game = new Game();
		// game.runGame();
	}
}