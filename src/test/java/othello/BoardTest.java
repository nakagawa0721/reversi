package othello;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class BoardTest{
//	public static Cell getCell(int x, int y) {
//		return null;
//		
//	}
	@Test
	void testGetCell() {
		assertEquals(board.cells[1][1],Board.getCell(0, 1));
	}

}
