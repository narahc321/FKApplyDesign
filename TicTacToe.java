/*
javac TicTacToe.java -d ClassFiles
java -cp  ClassFiles/ TicTacToe.TicTacToe

*/
package TicTacToe;
import java.util.Scanner;
import java.util.Arrays;
import java.util.stream.Stream;


class Board{
	private int dimension = 3,boxes;
	private int[] board;
	Board(){
		boxes = dimension * dimension; 
		board = new int[boxes];
		for(int i = 0; i < boxes; i++)
			board[i] = 0;
	}

	public boolean setBox(int index, Player player){
		System.out.println("Player  "+ player.getName() + " selected " + index);
		if(!this.validIndex(index) || !this.validPlayer(player.getPlayerType())){
			System.out.println("Please Select valid box" + player);
			// this.printBoard();
			System.out.println("player "+ player + " turn");
			return false;
		}
		else{
			board[index] = player.getPlayerType();
			// this.printBoard();
			return true;
		}
	}

	private boolean validIndex(int index){
		// System.out.println(" Invalid index ");
		if(index >= boxes || index < 0 || board[index] != 0)
			return false;
		else 
			return true;
	}

	private boolean validPlayer(int player){
		if(player != 1 && player != 2)
			return false;
		else 
			return true;
	}

	public void printBoard(){
		for(int i = 0; i < boxes; i++){
			if(i % dimension == 0){
				System.out.println();
				System.out.print((i) + " " + (i+1) + " " + (i+2) + "		");
			}
			if(board[i] == 1)
				System.out.print("O ");
			else if(board[i] == 2)			
				System.out.print("X ");
			else 
				System.out.print("_ ");
		}
		System.out.println();
	}

	public int[] getBoard(){
		// return Arrays.toString(board);
		return  board;
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
		int index;
    	Scanner sc = new Scanner(System.in);
		do{
			index = sc.nextInt();
		}while(!board.setBox(index, this));
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

	private int getIndex(int[] orgBoard){
		for(int i = 0; i < 9; i++){
			if(orgBoard[i] == 0)
				return i;
		}
		return 0;
	}

	public void setBox(){
		int index;
		do{
			index = getIndex(board.getBoard());
			System.out.println(" index " + index);
		}while(!board.setBox(index, this));
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

	private void setBoard(int[] boardString){
		int tempIndex = 0;
		for(int i = 0; i < boxes; i++)
			this.board[i] = boardString[i];
	} 

	public boolean isFull(int[] orgBoard){
		this.setBoard(orgBoard);
		for(int i = 0; i < boxes; i++){
			if(this.board[i] == 0)
				return false;
		}
		return true;
	}

	public boolean checkWin(int playerType, int[] orgBoard){
		this.setBoard(orgBoard);
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
		board.printBoard();
		while(!gameOver){
			currentPlayer.setBox();
			if(gameState.checkWin(currentPlayer.getPlayerType(), board.getBoard())){
				gameOver = true;
				board.printBoard();
				System.out.println("Player " + currentPlayer.getName() + " Won the Game");
			}
			else if(gameState.isFull(board.getBoard())){
				gameOver = true;
				board.printBoard();
				System.out.println(" No more moves possible game is Tie");
			}
			else{
				if(currentPlayer.equals(player1)){
					currentPlayer = player2;
				}
				else{
					currentPlayer = player1;
				}
				board.printBoard();
			}
		}
	}
}

public class TicTacToe{
	public static void main(String[] args){
		Game game = new Game();
		game.runGame();
	}
}