package com.company;

import java.util.Scanner;

public class HumanPlayer implements Player{
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