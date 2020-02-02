package com.company;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HumanPlayerTest {
    @Test
    public void testHumanPlayer() {
        Board board = new Board(1, 0, 16);
        Player human = new HumanPlayer("Charan", CellValue.O, board);
        assertEquals(0, human.getScore());
        assertEquals("Charan", human.getName());
        assertEquals(CellValue.O, human.getPlayerType());
        assertEquals(CellValue.X, new HumanPlayer("Sai", CellValue.X, board).getPlayerType());

    }
}