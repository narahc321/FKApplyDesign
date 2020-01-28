/*
javac TicTacToe.java -d ClassFiles
java -cp  ClassFiles/ TicTacToe.TicTacToe

*/
package TicTacToe;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;
import java.util.stream.IntStream; 


class Board{
	private int boardDimension;
	private int[][] board;
	Board(int boardDimension){
		this.boardDimension = boardDimension;
		board = new int[boardDimension][boardDimension];
		for(int i = 0; i < boardDimension ; i++){
			for(int j = 0; j < boardDimension ; j++)
				board[i][j] = 0;
		}
	}

	public void resetCell(int index){
		board[index/boardDimension][index%boardDimension]=0;
	}

	public int getCell(int xindex, int yindex){
		// System.out.println(xindex + " hell  "+yindex);
		return board[xindex][yindex];
	}

	public boolean setBox(int xindex, int yindex, Player player){
		System.out.println("Player  "+ player.getName() + " selected " + xindex + " " + yindex);
		if(!this.validIndex(xindex, yindex) || !this.validPlayer(player.getPlayerType())){
			System.out.println("Please Select valid box" + player.getName());
			// this.printBoard();
			System.out.println("player "+ player.getName() + " turn");
			return false;
		}
		else{
			// System.out.println("test1");
			board[xindex][yindex] = player.getPlayerType();
			// System.out.println("test1");
			return true;
		}
	}

	private boolean validIndex(int xindex, int yindex){
		// System.out.println(" Invalid index ");
		if(xindex >= boardDimension || xindex < 0 || yindex >= boardDimension
						 || yindex < 0 || board[xindex][yindex] != 0)
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
		for(int i = 0; i< boardDimension; i++){
			System.out.println();
			for(int j = 0; j< boardDimension; j++){
				System.out.print((i*boardDimension + j) + "\t");
			}
			System.out.print("\t\t");
			for(int j = 0; j< boardDimension; j++){
				if(board[i][j] == 1)
					System.out.print("O\t");
				else if(board[i][j] == 2)			
					System.out.print("X\t");
				else 
					System.out.print("_\t");
			}
			System.out.println();
		}
	}

	public int[][] getBoard(){
		// return Arrays.toString(board);
		return  board.clone();
		// return TextUtils.join("",board);

	}
}

interface Player{
	public int getIndex(int[][] orgBoard, int dimension);
	public int getPlayerType();
	public String getName();
	public int getScore();
	public void addScore(int points);
}

class HumanPlayer implements Player{
	private String name;
	private int playerType;
	private Board board; 
	public int score;
	Scanner sc;

	HumanPlayer(String name, int playerType, Board board){
		this.name = name;
		this.playerType = playerType;
		this.board = board;
		this.score = 0;
		sc = new Scanner(System.in);
	}

	public int getScore(){
		return this.score;
	}

	public void addScore(int points){
		this.score = this.score + points;
	}

	public int getIndex(int[][] orgBoard, int dimension){
		return sc.nextInt();
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
	private int score;

	ComputerPlayer(int playerType, Board board){
		this.name = "Computer";
		this.playerType = playerType;
		this.board = board;
		this.score = 0;
	}

	public int getScore(){
		return this.score;
	}

	public void addScore(int points){
		this.score = this.score + points;
	}

	public int getIndex(int[][] board, int gameSize){
		for(int i = 0; i < gameSize; i++){
			for(int j = 0; j< gameSize; j++){
				if(board[i][j] == 0)
					return (i * gameSize) + j;
			}
		}
		return 0;
	}

	public int getPlayerType(){
		return playerType;
	}

	public String getName(){
		return name;
	}

}

class GameState{
	public static int dimension ;

	GameState(int dimension){
		this.dimension = dimension;
	}

	public static boolean isFull(int[][] board){
		for(int i = 0; i < dimension; i++){
			for(int j=0;j<dimension;j++)
				if(board[i][j] == 0)
					return false;
		}
		return true;
	}

	public static boolean checkWin(int playerType, int[][] board){

		Set<Integer> set;
		// System.out.println(1);
		for(int i = 0; i < dimension; i++){
			set = new HashSet<Integer>();
			set.add(playerType);
			for(int j=0;j<dimension;j++){
				set.add(board[i][j]);
			}
			if(set.size()==1)
				return true;
		} 	

		// System.out.println(2);
		for(int i = 0; i < dimension; i++){
			set = new HashSet<Integer>();
			set.add(playerType);
			for(int j = 0; j < dimension; j++){
				set.add(board[j][i]);
			}
			if(set.size() == 1)
				return true;
		} 	

		// System.out.println(3);
		set = new HashSet<Integer>();
		set.add(playerType);
		// System.out.println(playerType);
		for(int i = 0; i < dimension; i++){
			// System.out.println(board[i][i]);
			set.add(board[i][i]);
		}
		if(set.size()==1)
			return true;

		// System.out.println(4);
		set = new HashSet<Integer>();
		set.add(playerType);
		for(int i = 0; i < dimension; i++){
			set.add(board[i][dimension - 1 - i]);
		}
		if(set.size()==1)
			return true;
		// System.out.println(5);
		return false;
	}
}

class Game{
	private Board board;
	private Player player1, player2, currentPlayer;
	private Scanner sc;
	private boolean gameOver;
	private String tempName;
	private ArrayList <Integer> steps;
	private Game[][] multiGame ; 
	private int[][]  gameResult ; 
	private int nPlayers, gameSize, xboardsStart, yboardsStart,
		  dimension;
	

	Game(int gameSize, int dimension){
		steps = new ArrayList<Integer>();
		this.dimension = dimension;
		GameState.dimension = dimension;
		this.board = new Board(gameSize);
		this.xboardsStart = 0;
		this.yboardsStart = 0;
		this.gameSize = gameSize;
		gameOver = false;
		sc = new Scanner(System.in);
		this.setPlayers();
		currentPlayer = player1;
		gameResult = new int[dimension][dimension];
		for(int i = 0; i < dimension; i++){
			for(int j = 0; j < dimension; j++){
				gameResult[i][j] = 0;
			}
		}
		if(gameSize > dimension){
			multiGame = new Game[dimension][dimension]; 
			initialize();
		}
	}

	Game(Board board, int gameSize, int dimension, Player player1, Player player2,
						 int xboardsStart, int yboardsStart){

		this.board = board;
		multiGame = new Game[dimension][dimension]; 
		gameResult = new int[dimension][dimension];
		this.gameSize = gameSize;		
		this.dimension = dimension;
		this.xboardsStart = xboardsStart;
		this.yboardsStart = yboardsStart;
		gameOver = false;
		gameResult = new int[dimension][dimension];
		for(int i = 0; i < dimension; i++){
			for(int j = 0; j < dimension; j++){
				gameResult[i][j] = 0;
			}
		}
		if(gameSize > dimension){
			multiGame = new Game[dimension][dimension]; 
			initialize();
		}
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

	private void initialize(){
		for(int i = 0; i < dimension; i++){
			for(int j = 0; j < dimension; j++){
				multiGame[i][j] = new Game(board, gameSize/this.dimension,this.dimension,
				player1, player2, (gameSize/this.dimension)*i, (gameSize/this.dimension)*j);
				gameResult[i][j] = 0;
			}
		}
	}

	public void setBox(Player currentPlayer){
		int index;
		do{
			index = currentPlayer.getIndex(board.getBoard(), gameSize);
			if(index == -1){
				Scanner sc = new Scanner(System.in);
				index =  sc.nextInt();
			}
		}while(!board.setBox((index/gameSize), (index%gameSize), currentPlayer));
		steps.add(index);
	}

	public int updateScore(Player player){
		// System.out.println(this.gameSize + " " + this.dimension);
		if(this.gameSize == this.dimension){
			for(int i = 0; i < dimension; i++){
				for(int j = 0; j < dimension; j++){
					// System.out.println(" hhii "+(xboardsStart+i) + "  "+(yboardsStart+j));
					gameResult[i][j] = board.getCell(xboardsStart+i, yboardsStart+j);
				}

			}
		}
		else{
			for(int i = 0;i < dimension; i++){
				for(int j = 0; j< dimension; j++){
					if(gameResult[i][j]==0){
						gameResult[i][j] = multiGame[i][j].updateScore(player);
					}
				}
			}
		}
		if(GameState.checkWin(player.getPlayerType(), gameResult)){
			player.addScore(this.gameSize);
			return player.getPlayerType();
		}
		return 0;
	} 

	public void runGame(){
		// System.out.println("hello");

		board.printBoard();
		while(!gameOver){
			this.setBox(currentPlayer);
			if(steps.size()!=0){
				System.out.println("press 1 to undo 0 to skip");
				int undo = sc.nextInt();
				if(undo == 1){
					board.resetCell(steps.get(steps.size()-1));
					steps.remove(steps.size() - 1);
					continue;
				}
			}
			this.updateScore(currentPlayer);
			if(GameState.checkWin(currentPlayer.getPlayerType(), gameResult)){
				gameOver = true;
				board.printBoard();
				System.out.println("Player " + currentPlayer.getName() + " Won the Game");
				currentPlayer.addScore(this.gameSize);
			}
			else if(GameState.isFull(board.getBoard())){
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
		System.out.println(player1.getName()+" Score is "+player1.getScore());
		System.out.println(player2.getName()+" Score is "+player2.getScore());
	}
	
}

public class TicTacToe{
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		int singleGameDim, boardSize=1, level;
		System.out.println("Enter single Game Dimension ");
		singleGameDim = sc.nextInt();
		System.out.println("Enter Number of levels deep ");
		System.out.println("Ex: for dim = 3,\nlevel 1: 3x3\nlevel 2: 9x9\nlevel 3: 27x27");
		level = sc.nextInt();
		if(singleGameDim <= 0 || level <= 0 )
			return;
		for(int i=0;i<level;i++)
			boardSize = boardSize * singleGameDim;
		System.out.println(singleGameDim + " "+ boardSize);
		Game game = new Game(boardSize, singleGameDim);
		game.runGame();
	}
}