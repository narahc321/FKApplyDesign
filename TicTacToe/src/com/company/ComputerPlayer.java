package com.company;

public class ComputerPlayer implements Player{
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
