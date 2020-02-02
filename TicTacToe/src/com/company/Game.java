package com.company;

import java.util.Scanner;
import java.util.ArrayList;

public class Game implements GameMethods{
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
        if(gameSize != 1){
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
        if(gameSize != 1){
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

        System.out.println("Enter player 1 Name");
        tempName = sc.next();
        player1 = new HumanPlayer(tempName, CellValue.O, this.board);

        if(nPlayers == 1){

            player2 = new ComputerPlayer(CellValue.X, this.board);

        }
        else{

            System.out.println("Enter player 2 Name");
            tempName = sc.next();
            player2 = new HumanPlayer(tempName, CellValue.X, this.board);

        }
    }

    private void initialize(){
        for(int i = 0; i < dimension; i++){
            for(int j = 0; j < dimension; j++){
                gameResult[i][j] = CellValue.EMPTY;
                multiGame[i][j] = new Game(board, gameSize/this.dimension, this.dimension,
                        player1, player2, (gameSize/this.dimension) * i, (gameSize/this.dimension) * j);
            }
        }
    }

    private void setBox(Player currentPlayer){
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

        if(this.gameSize == 1){
            return board.getCell(xboardsStart, yboardsStart);
        }

        for(int i = 0;i < dimension; i++){
            for(int j = 0; j< dimension; j++){
                if(gameResult[i][j] == CellValue.EMPTY){
                    gameResult[i][j] = multiGame[i][j].updateScore(player);
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
                currentPlayer = switchPlayer(currentPlayer);
                board.printBoard();

            }

        }
        System.out.println(player1.getName()+" Score is "+player1.getScore());
        System.out.println(player2.getName()+" Score is "+player2.getScore());
    }

    private Player switchPlayer(Player currentPlayer){
        if(currentPlayer.equals(player1)){
            return player2;
        }
        else{
            return player1;
        }
    }
}
