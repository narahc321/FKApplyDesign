package com.company;

import org.junit.Test;

import static org.junit.Assert.*;

public class GameStateTest {
    @Test
    public void gameTest(){
        Board board = new Board(1, 0, 16);
        assertEquals(false, GameState.checkWin(0, CellValue.O, board.getBoard(), 16));
        assertEquals(false, GameState.checkWin(1, CellValue.X, board.getBoard(), 16));
    }
}