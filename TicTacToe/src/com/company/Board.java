package com.company;
import java.util.Random;

public class Board implements BoardMethods{

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
                if(irregularBoard == 0 && random.nextInt() < blockPercent){
                    board[i][j] = CellValue.BLOCKED;
                }
                else{
                    board[i][j] = CellValue.EMPTY;
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

            if(boardType == 0 && i%2 == 1)
                System.out.print("\t");

            for(int j = 0; j< boardDimension; j++){

                switch(board[i][j]){

                    case BLOCKED:
                        System.out.print(" \t\t");
                        break;

                    case O:
                        System.out.print("O\t\t");
                        break;

                    case X:
                        System.out.print("X\t\t");
                        break;

                    default:
                        System.out.print((i*boardDimension + j) + "\t\t");

                }
            }
            System.out.println();
        }
    }

    public CellValue[][] getBoard(){
        return  board.clone();
    }
}