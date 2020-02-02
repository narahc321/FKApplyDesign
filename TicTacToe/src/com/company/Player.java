package com.company;

public interface Player{
    public int getIndex(CellValue[][] board, int dimension);
    public CellValue getPlayerType();
    public String getName();
    public int getScore();
    public void addScore(int points);
}