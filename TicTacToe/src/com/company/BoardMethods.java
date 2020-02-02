package com.company;

public interface BoardMethods {
    public boolean isFull();
    public void resetCell(int index);
    public CellValue getCell(int xindex, int yindex);
    public boolean setBox(int xindex, int yindex, Player player);
    public void printBoard();
    public CellValue[][] getBoard();
}
