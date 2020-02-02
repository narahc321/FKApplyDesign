package com.company;

import java.util.Scanner;

public class Main {

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
