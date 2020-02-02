package com.company;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ComputerPlayerTest {
    @Test
    public void testComputerPlayer() {
        Board board = new Board(1, 0, 16);
        Player computer = new ComputerPlayer(CellValue.O, board);
        assertEquals(0, computer.getScore());
        assertEquals("Computer", computer.getName());
        assertEquals(0, computer.getIndex(board.getBoard(), 16));
        assertEquals(CellValue.O, computer.getPlayerType());
        assertEquals(CellValue.X, new ComputerPlayer(CellValue.X, board).getPlayerType());

    }
}