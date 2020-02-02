package com.company;
import java.util.Set;
import java.util.HashSet;

public class GameState{

    public static boolean checkWin(int boardType, CellValue playerType, CellValue[][] board, int dimension){
        if(boardType == 0)
            return checkWinHexagonalBoard(playerType, board, dimension);
        else
            return checkWinHexagonalBoard(playerType, board, dimension);
    }

    private static boolean checkWinHexagonalBoard(CellValue playerType, CellValue[][] board, int dimension){
        if(CheckRow(playerType, board, dimension) || checkHexRightDiagonal(playerType, board, dimension)
                || checkHexLeftDiagonal(playerType, board, dimension))
            return true;
        else
            return false;
    }

    private static boolean checkWinSquareBoard(CellValue playerType, CellValue[][] board, int dimension){
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
        if(set.size() ==1)
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
