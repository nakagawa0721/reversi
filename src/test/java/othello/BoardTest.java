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
		Cell[][] cells = new Cell[8][8];
		for(int y =0; y < 8; ++y) {
			for(int x = 0; x < 8;++x) {
				cells[x][y] = new Cell(x,y);
			}
		}
		
		assertEquals(null ,Board.getCell(12, 1));
		assertEquals(null ,Board.getCell(-1, 1));
		assertEquals(null ,Board.getCell(12, 11));
		assertEquals(null ,Board.getCell(-1, -1));
		assertEquals(null ,Board.getCell(1, 11));
		assertEquals(null ,Board.getCell(2, -11));
	}
	
}