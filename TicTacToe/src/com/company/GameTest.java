package com.company;

import org.junit.Test;

import static org.junit.Assert.*;

public class GameTest {
    @Test
    public void gameTest(){
        Board board = new Board(1, 0, 1);
        Game game = new Game(board, 1, 1,
                new ComputerPlayer( CellValue.O, board), new ComputerPlayer( CellValue.X, board),  0, 0);
        assertEquals(CellValue.EMPTY, game.updateScore(new ComputerPlayer( CellValue.O, board)));
    }

}