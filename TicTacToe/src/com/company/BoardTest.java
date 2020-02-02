package com.company;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BoardTest {

    @Test
    public void testBoard() {
        Board board = new Board(1, 0, 16);
        assertEquals( false, board.isFull(), "Initially board is not Empty" );
        assertEquals( CellValue.EMPTY, board.getCell(0, 0), "Initially board is not Empty" );

    }
}