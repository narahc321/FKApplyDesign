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
import java.util.Random;

enum CellValue{
	EMPTY, O, X, BLOCKED;
}

class Board{
	private int boardDimension, boardType, blockPercent = 30;
	private CellValue[][] board;
	Random random;
	Board(int irregularBoard, int boardType, int boardDimension){
		this.boardType = boardType;
		this.boardDimension = boardDimension;
		random = new Random(100);
		board = new CellValue[boardDimension][boardDimension];
		for(int i = 0; i < boardDimension ; i++){
			for(int j = 0; j < boardDimension ; j++){
				board[i][j] = CellValue.EMPTY;
				if(irregularBoard == 0){
					if(random.nextInt() < blockPercent)
						board[i][j] = CellValue.BLOCKED;
				}
			}
		}
	}

	public boolean isFull(){
		for(int i = 0; i < boardDimension; i++){
			for(int j = 0; j < boardDimension; j++)
				if(board[i][j] == CellValue.EMPTY)
					return false;
		}
		return true;
	}

	public void resetCell(int index){
		board[index/boardDimension][index%boardDimension] = CellValue.EMPTY;
	}

	public CellValue getCell(int xindex, int yindex){
		return board[xindex][yindex];
	}

	public boolean setBox(int xindex, int yindex, Player player){
		System.out.println("Player  "+ player.getName() + " selected " + xindex + " " + yindex);
		if(!this.validIndex(xindex, yindex) || !this.validPlayer(player.getPlayerType())){
			System.out.println("Please Select valid box" + player.getName());
			System.out.println("player "+ player.getName() + " turn");
			return false;
		}
		else{
			board[xindex][yindex] = player.getPlayerType();
			return true;
		}
	}

	private boolean validIndex(int xindex, int yindex){
		if(xindex >= boardDimension || xindex < 0 || yindex >= boardDimension
						 || yindex < 0 || board[xindex][yindex] != CellValue.EMPTY)
			return false;
		else 
			return true;
	}


	private boolean validPlayer(CellValue playerType){
		if(playerType != CellValue.O && playerType != CellValue.X)
			return false;
		else 
			return true;
	}

	public void printBoard(){
		for(int i = 0; i< boardDimension; i++){
			System.out.println();
			for(int j = 0; j< boardDimension; j++){
				if(boardType == 0 && i%2 == 1)
					System.out.print("    ");
				if(board[i][j] == CellValue.BLOCKED)
					System.out.print(" \t");
				else if(board[i][j] == CellValue.O)
					System.out.print("O\t");
				else if(board[i][j] == CellValue.X)			
					System.out.print("X\t");
				else 
					System.out.print((i*boardDimension + j) + "\t");
			}
			System.out.println();
		}
	}

	public CellValue[][] getBoard(){
		return  board.clone();

	}
}

interface Player{
	public int getIndex(CellValue[][] board, int dimension);
	public CellValue getPlayerType();
	public String getName();
	public int getScore();
	public void addScore(int points);
}

class HumanPlayer implements Player{
	private String name;
	private CellValue type;
	private Board board; 
	public int score;
	Scanner sc;

	HumanPlayer(String name, CellValue type, Board board){
		this.name = name;
		this.type = type;
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

	public int getIndex(CellValue[][] board, int gameSize){
		System.out.println("Type a Index to mark that Cell");
		return sc.nextInt();
	}

	public CellValue getPlayerType(){
		return type;
	}

	public String getName(){
		return name;
	}

}

class ComputerPlayer implements Player{
	private String name;
	private CellValue type;
	private Board board;
	private int score;

	ComputerPlayer(CellValue playerType, Board board){
		this.name = "Computer";
		this.type = playerType;
		this.board = board;
		this.score = 0;
	}

	public int getScore(){
		return this.score;
	}

	public void addScore(int points){
		this.score = this.score + points;
	}

	public int getIndex(CellValue[][] board, int gameSize){
		for(int i = 0; i < gameSize; i++){
			for(int j = 0; j< gameSize; j++){
				if(board[i][j] == CellValue.EMPTY)
					return (i * gameSize) + j;
			}
		}
		return 0;
	}

	public CellValue getPlayerType(){
		return type;
	}

	public String getName(){
		return name;
	}

}

class GameState{
	public static boolean checkWin(int boardType, CellValue playerType, CellValue[][] board, int dimension){
		if(boardType == 0)
			return checkWinHexagonalBoard(playerType, board, dimension);
		else
			return checkWinHexagonalBoard(playerType, board, dimension);
	}

	public static boolean checkWinHexagonalBoard(CellValue playerType, CellValue[][] board, int dimension){
		if(CheckRow(playerType, board, dimension) || checkHexRightDiagonal(playerType, board, dimension)
						|| checkHexLeftDiagonal(playerType, board, dimension))
			return true;
		else
			return false;
	}

	public static boolean checkWinSquareBoard(CellValue playerType, CellValue[][] board, int dimension){
		if(CheckRow(playerType, board, dimension) || CheckColumn(playerType, board, dimension)
				|| CheckLeftDiagonal(playerType, board, dimension) || CheckRightDiagonal(playerType, board, dimension))
			return true;
		else
			return false;
	}

	private static boolean CheckRow(CellValue playerType, CellValue[][] board, int dimension){
		Set <CellValue> set;
		for(int i = 0; i < dimension; i++){
			set = new HashSet<CellValue>();
			set.add(playerType);
			for(int j=0;j<dimension;j++){
				set.add(board[i][j]);
			}
			if(set.size()==1)
				return true;
		}
		return false;
	}

	private static boolean CheckColumn(CellValue playerType, CellValue[][] board, int dimension){
		Set <CellValue> set;
		for(int i = 0; i < dimension; i++){
			set = new HashSet<CellValue>();
			set.add(playerType);
			for(int j = 0; j < dimension; j++){
				set.add(board[j][i]);
			}
			if(set.size() == 1)
				return true;
		} 	
		return false;
	}

	private static boolean CheckRightDiagonal(CellValue playerType, CellValue[][] board, int dimension){
		Set <CellValue> set;
		set = new HashSet<CellValue>();
		set.add(playerType);
		for(int i = 0; i < dimension; i++){
			set.add(board[i][i]);
		}
		if(set.size()==1)
			return true;
		return false;
	}

	private static boolean CheckLeftDiagonal(CellValue playerType, CellValue[][] board, int dimension){
		Set <CellValue> set;
		set = new HashSet<CellValue>();
		set.add(playerType);
		for(int i = 0; i < dimension; i++){
			set.add(board[i][dimension - 1 - i]);
		}
		if(set.size()==1)
			return true;
		return false;
	}

	private static boolean checkHexRightDiagonal(CellValue playerType, CellValue[][] board, int dimension){
		Set <CellValue> set;
		Set <Integer> countCells;
		for(int i = 0; i < dimension; i++){
			set = new HashSet<CellValue>();
			countCells = new HashSet<Integer>();
			set.add(playerType);	
			int k = i-1;
			for(int j = 0; j < dimension; j++){
				if(j%2==0)
					k++;
				if(k >= dimension)
					break;
				set.add(board[j][k]);
				countCells.add((j*dimension) + k);
			}
			if(countCells.size()!=dimension)
				set.add(CellValue.EMPTY);
			if(set.size()==1)
				return true;
		}
		return false;
	}

	private static boolean checkHexLeftDiagonal(CellValue playerType, CellValue[][] board, int dimension){
		Set <CellValue> set;
		Set <Integer> countCells;
		for(int i = 0; i < dimension; i++){
			set = new HashSet<CellValue>();
			countCells = new HashSet<Integer>();
			set.add(playerType);	
			int k = i-1;
			for(int j = 0; j < dimension; j++){
				if(j % 2 == 0)
					k--;
				if(k < 0)
					break;
				set.add(board[j][k]);
				countCells.add((j*dimension) + k);
			}
			if(countCells.size()!=dimension)
				set.add(CellValue.EMPTY);
			if(set.size()==1)
				return true;
		}
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
	private CellValue[][]  gameResult ; 
	private int nPlayers, gameSize, xboardsStart, yboardsStart,
		  dimension, boardType, irregularBoard;
	

	Game(int irregularBoard,int boardType, int gameSize, int dimension){
		steps = new ArrayList<Integer>();
		this.dimension = dimension;
		this.irregularBoard = irregularBoard;
		this.boardType = boardType;
		this.board = new Board(irregularBoard, boardType, gameSize);
		this.xboardsStart = 0;
		this.yboardsStart = 0;
		this.gameSize = gameSize;
		gameOver = false;
		sc = new Scanner(System.in);
		this.setPlayers();
		currentPlayer = player1;
		gameResult = new CellValue[dimension][dimension];
		for(int i = 0; i < dimension; i++){
			for(int j = 0; j < dimension; j++){
				gameResult[i][j] = CellValue.EMPTY;
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
		gameResult = new CellValue[dimension][dimension];
		this.gameSize = gameSize;		
		this.dimension = dimension;
		this.xboardsStart = xboardsStart;
		this.yboardsStart = yboardsStart;
		gameOver = false;
		gameResult = new CellValue[dimension][dimension];
		for(int i = 0; i < dimension; i++){
			for(int j = 0; j < dimension; j++){
				gameResult[i][j] = CellValue.EMPTY;
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
			player1 = new HumanPlayer(tempName, CellValue.O, this.board);
			player2 = new ComputerPlayer(CellValue.X, this.board); 
		}
		else{
			System.out.println("Enter player 1 Name");
			tempName = sc.next();
			player1 = new HumanPlayer(tempName, CellValue.O, this.board);
			System.out.println("Enter player 2 Name");
			tempName = sc.next();
			player2 = new HumanPlayer(tempName, CellValue.X, this.board);
		}
	}

	private void initialize(){
		for(int i = 0; i < dimension; i++){
			for(int j = 0; j < dimension; j++){
				multiGame[i][j] = new Game(board, gameSize/this.dimension,this.dimension,
				player1, player2, (gameSize/this.dimension)*i, (gameSize/this.dimension)*j);
				gameResult[i][j] = CellValue.EMPTY;
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

	public CellValue updateScore(Player player){
		if(this.gameSize == this.dimension){
			for(int i = 0; i < dimension; i++){
				for(int j = 0; j < dimension; j++){
					gameResult[i][j] = board.getCell(xboardsStart+i, yboardsStart+j);
				}

			}
		}
		else{
			for(int i = 0;i < dimension; i++){
				for(int j = 0; j< dimension; j++){
					if(gameResult[i][j] == CellValue.EMPTY){
						gameResult[i][j] = multiGame[i][j].updateScore(player);
					}
				}
			}
		}
		if(GameState.checkWin(boardType, player.getPlayerType(), gameResult, dimension)){
			player.addScore(this.gameSize);
			return player.getPlayerType();
		}
		return CellValue.EMPTY;
	} 

	public void runGame(){

		board.printBoard();
		while(!gameOver){
			this.setBox(currentPlayer);
			if(steps.size() != 0){
				System.out.println("press 1 to undo 0 to skip");
				int undo = sc.nextInt();
				if(undo == 1){
					board.resetCell(steps.get(steps.size()-1));
					steps.remove(steps.size() - 1);
					continue;
				}
			}
			this.updateScore(currentPlayer);
			if(GameState.checkWin(boardType, currentPlayer.getPlayerType(), gameResult, dimension)){
				gameOver = true;
				board.printBoard();
				System.out.println("Player " + currentPlayer.getName() + " Won the Game");
				currentPlayer.addScore(this.gameSize);
			}
			else if(board.isFull()){
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
		int singleGameDim, boardSize=1, level, boardType, irregularBoard;
		System.out.println("Enter 0 for Irregular Board or 1-9 for Square Board");
		irregularBoard = sc.nextInt();
		if(irregularBoard != 0){
			irregularBoard = 1;
		}
		System.out.println("Enter 0 for Hexogonal Board or 1-9 for Square Board");
		boardType = sc.nextInt();
		if(boardType != 0){
			boardType = 1;
		}
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
		Game game = new Game(irregularBoard, boardType, boardSize, singleGameDim);
		game.runGame();
	}
}