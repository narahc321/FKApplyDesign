/*
javac TicTacToe.java -d ClassFiles
java -cp  ClassFiles/ TicTacToe.TicTacToe

*/
package TicTacToe;
import java.util.*;
import java.util.Scanner;
import java.util.Arrays;
import java.util.stream.Stream;
import java.util.stream.IntStream; 


class Board{
	public int dimension,boxes;
	public int[][] board;
	public int[][] boardIndex;
	int gameSize;
	Board(int gameSize, int dimension){
		this.dimension = dimension;
		this.gameSize = gameSize;
		boxes = gameSize; 
		board = new int[gameSize][gameSize];
		boardIndex = new int[gameSize][gameSize];
		for(int i = 0; i < gameSize; i++)
			for(int j = 0; j < gameSize; j++){
				board[i][j] = 0;
				boardIndex[i][j] = (i*gameSize + j);
			}
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
		if(xindex >= gameSize || xindex < 0 || yindex >= gameSize || yindex < 0 || board[xindex][yindex] != 0)
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
		for(int i = 0; i< gameSize; i++){
			System.out.println();
			for(int j = 0; j< gameSize; j++){
				System.out.print((i*gameSize + j) + "\t");
			}
			System.out.print("\t\t");
			for(int j = 0; j< gameSize; j++){
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
}

class HumanPlayer implements Player{
	private String name;
	private int playerType;

	HumanPlayer(String name, int playerType){
		this.name = name;
		this.playerType = playerType;
	}

	public int getIndex(int[][] orgBoard, int dimension){
		return -1;
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

	ComputerPlayer(int playerType){
		this.name = "Computer";
		this.playerType = playerType;
	}

	public int getIndex(int[][] orgBoard, int dimension){
		for(int i = 0; i < dimension; i++){
			for(int j=0; j< dimension; j++){
				if(orgBoard[i][j] == 0)
					return (i * dimension) + j;
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
	private int dimension ,boxes;
	private int[][] board;

	GameState(int dimension){
		this.dimension = dimension;
		boxes = dimension * dimension; 
		board = new int[dimension][dimension];
		this.dimension = 3;
	} 

	private void setBoard(int[][] boardString){
		int tempIndex = 0;
		for(int i = 0; i < dimension; i++){
			for(int j=0;j<dimension;j++)
			this.board[i][j] = boardString[i][j];
		}
	} 

	public boolean isFull(int[][] orgBoard){
		this.setBoard(orgBoard);
		for(int i = 0; i < dimension; i++){
			for(int j=0;j<dimension;j++)
				if(this.board[i][j] == 0)
					return false;
		}
		return true;
	}

	public boolean checkWin(int playerType, int[][] orgBoard){
		this.setBoard(orgBoard);
		Set<Integer> set;
		for(int i=0; i<dimension;i++){
			set = new HashSet<Integer>();
			set.add(playerType);
			for(int j=0;j<dimension;j++){
				set.add(orgBoard[i][j]);
			}
			if(set.size()==1)
				return true;
		} 	

		for(int i=0; i<dimension;i++){
			set = new HashSet<Integer>();
			set.add(playerType);
			for(int j=0;j<dimension;j++){
				set.add(orgBoard[j][i]);
			}
			if(set.size()==1)
				return true;
		} 	

		for(int i=0; i<dimension;i++){
			set = new HashSet<Integer>();
			set.add(playerType);
				set.add(orgBoard[i][i]);
			if(set.size()==1)
				return true;
		} 

		for(int i=0; i<dimension;i++){
			set = new HashSet<Integer>();
			set.add(playerType);
				set.add(orgBoard[dimension - 1 - i][i]);
			if(set.size()==1)
				return true;
		} 
		return false;
	}
}

interface Game{
	public void printBoard();
	public int update();
}

class SingleGame implements Game{
	// private Board board;
	private Scanner sc;
	private int nPlayers;
	private boolean gameOver;
	private String tempName;
	private Player player1, player2, currentPlayer;
	
	SingleGame(Player player1, Player player2){
		// this.board = new Board();
		gameOver = false;
		this.player1 = player1;
		this.player2 = player2;
		this.currentPlayer = player1;

	}

	public int update(){
		return 0;
	}
	public void printBoard(){
		// board.printBoard();
	}

}

class MegaGame implements Game{
	private Board board; 
	private int nPlayers, gameSize, level, xboardsStart, yboardsStart,
		  dimension,player1score=0,player2score=0;
	Scanner sc;
	private GameState gameState;
	private String tempName;
	boolean singleGame, gameOver;
	private Player player1, player2, currentPlayer;
	private Game aGame;
	private Game[][] multiGame ; 
	private int[][]  gameResult ; 
	private ArrayList <Integer> steps;
	MegaGame(int gameSize, int dimension, int player1score, int player2score){
				// System.out.println("di "+dimension+" bo "+gameSize+" x "+ xboardsStart+" y "+yboardsStart);
		steps = new ArrayList<Integer>();
		this.player1score=player1score;
		this.player2score=player2score;
		this.gameState = new GameState(dimension);
		this.dimension = dimension;
		multiGame = new Game[dimension][dimension]; 
		gameResult = new int[dimension][dimension];
		// System.out.println("hello " +gameSize);
		board = new Board(gameSize, dimension);
		this.xboardsStart = 0;
		this.yboardsStart = 0;
		// this.boardsEnd = gameSize - 1;
		this.gameSize = gameSize;
		System.out.println("gamesixe "+gameSize);
		if(gameSize > dimension)
			this.singleGame = false;
		else
			this.singleGame = true;
		this.level = 1;
		gameOver = false;
		sc = new Scanner(System.in);
		initialize();

		this.setPlayers();
		currentPlayer = player1;
	}

	MegaGame(int gameSize, int dimension, Player player1, Player player2,
						 int level, int xboardsStart, int yboardsStart){

		// System.out.println("di "+dimension+" bo "+gameSize+" x "+ xboardsStart+" y "+yboardsStart);
		multiGame = new Game[dimension][dimension]; 
		gameResult = new int[dimension][dimension];
		this.dimension = dimension;
		this.gameState = new GameState(dimension);
		// System.out.println("hello " +gameSize);
		this.xboardsStart = xboardsStart;
		this.yboardsStart = yboardsStart;
		if(gameSize > dimension)
			singleGame = false;
		else
			singleGame = true;
		this.level = level+1;
		gameOver = false;
		// sc = new Scanner(System.in);
		// this.setPlayers();
		currentPlayer = player1;
		initialize();
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
			player1 = new HumanPlayer(tempName, 1);
			player2 = new ComputerPlayer(2); 
		}
		else{
			System.out.println("Enter player 1 Name");
			tempName = sc.next();
			player1 = new HumanPlayer(tempName, 1);
			System.out.println("Enter player 2 Name");
			tempName = sc.next();
			player2 = new HumanPlayer(tempName, 2);
		}
	}

	private void initialize(){
		// System.out.println("init");
		// System.out.println("di "+dimension+" bo "+gameSize+" x "+ xboardsStart+" y "+yboardsStart);
		// System.out.println(singleGame);
		if(singleGame){
			aGame = new SingleGame(player1, player2);
		}
		else{
			for(int i = 0; i < dimension; i++){
				for(int j = 0; j < dimension; j++){

					// System.out.println("222initialize");
					// System.out.println((gameSize/this.dimension)*i +" "+(gameSize/this.dimension)*j);
					multiGame[i][j] = new MegaGame(gameSize/this.dimension,this.dimension,
					 player1, player2, this.level, (gameSize/this.dimension)*i, (gameSize/this.dimension)*j);
					gameResult[i][j] = 0;
				}
			}
			// for(int i = 0; i < 9; i++){
			// 	multiGame[i] = new MegaGame(gameSize/3, player1, player2, this.level, i*(gameSize/3));
			// 	gameResult[i] = 0;
			// }
		}
	}

	public void setBox(Player currentPlayer){
		int index;
		do{
			// index = currentPlayer.getIndex(new int[gameSize]);
			index = currentPlayer.getIndex(board.getBoard(), dimension);
			if(index == -1){
				Scanner sc = new Scanner(System.in);
				index =  sc.nextInt();
			}
		}while(!board.setBox((index/gameSize), (index%gameSize), currentPlayer));
		steps.add(index);
	}

	public int update(){
		if(singleGame){
			int[][] tempBoard = new int[dimension][dimension];
			for(int i = 0;i < dimension;i++){
				for(int j=0 ;j<dimension ;j++){
					// System.out.println(" hhii "+(xboardsStart+i) + "  "+(yboardsStart+j));
					// tempBoard[i][j] = board.board[xboardsStart+i][yboardsStart+j];
				}

			}
			if(gameState.checkWin(1, tempBoard))
				return 1;
			else if(gameState.checkWin(2, tempBoard))
				return 2;
			return 0;
		}
		for(int i = 0;i < dimension; i++){
			for(int j = 0; j< dimension; j++){
				if(gameResult[i][j]==0){
					gameResult[i][j] = multiGame[i][j].update();
				}
			}
		}
		if(gameState.checkWin(1, gameResult))
			return 1;
		else if(gameState.checkWin(2, gameResult))
			return 2;
		return 0;
	} 


	public void runGame(){
		// System.out.println("hello");

		this.printBoard();
		while(!gameOver){
			this.setBox(currentPlayer);
			if(steps.size()!=0){
				System.out.println("press 1 to undo 0 to skip");
				int undo = sc.nextInt();
				if(undo == 1){
					board.board[steps.get(steps.size()-1)/gameSize][steps.get(steps.size()-1)%gameSize]=0;
					continue;
				}
			}
			this.update();
			if(gameState.checkWin(currentPlayer.getPlayerType(), gameResult)){
				gameOver = true;
				board.printBoard();
				System.out.println("Player " + currentPlayer.getName() + " Won the Game");
				if(currentPlayer.getPlayerType()==1){
					player1score = player1score +2;
				}
				else{
					player2score = player2score +2;
				}
			}
			else if(gameState.isFull(board.getBoard())){
				gameOver = true;
				board.printBoard();
				System.out.println(" No more moves possible game is Tie");
				player1score = player1score + 1;
				player2score = player2score + 1;
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
		MegaGame game = new MegaGame(3, 3, player1score, player2score);
		game.runGame();

	}

	public void runnGame(){

	}

	public void printBoard(){
		board.printBoard();
	}

}

public class TicTacToe{
	public static void main(String[] args){

		MegaGame game = new MegaGame(3, 3, 0, 0);
		game.runGame();
		// SingleGame game = new SingleGame();
		// game.runGame(); 

	}
}






