package othello;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CellTest{

	Cell errtest = new Cell(0,7);
	Cell errtest2 = new Cell(-1,8);
	Cell cell = new Cell(5, 4);

	
	@Test
	void testCell() {
		assertEquals(null,Board.getCell(-1, 4));
	}

	@Test
	void testGetX() {
		assertEquals(5,cell.getX());
	}

	@Test
	void testGetY() {
		assertEquals(4,cell.getY());
	}

	@Test
	void testGetStatus() {
		assertEquals(0,cell.getStatus());
	}

	@Test
	void testPossibleMoveBlack() {
		Board.getCell(3, 4).setStatus(2);
		Board.getCell(4, 3).setStatus(2);
		Board.getCell(3, 3).setStatus(1);
		Board.getCell(4, 4).setStatus(1);
		assertEquals(true,Board.getCell(2, 4).possibleMoveBlack());
		assertEquals(false,Board.getCell(0, 7).possibleMoveBlack());
		assertEquals(false,Board.getCell(4, 4).possibleMoveBlack());
	}

	@Test
	void testPossibleMoveWhite() {
		Board.getCell(3, 4).setStatus(2);
		Board.getCell(4, 3).setStatus(2);
		Board.getCell(3, 3).setStatus(1);
		Board.getCell(4, 4).setStatus(1);
		assertEquals(true,Board.getCell(4, 5).possibleMoveWhite());
		assertEquals(false,Board.getCell(0, 7).possibleMoveWhite());
		assertEquals(false,Board.getCell(4, 4).possibleMoveWhite());
	}

	@Test
	void testCanFlip() {
		Board.getCell(3, 4).setStatus(2);
		Board.getCell(4, 3).setStatus(2);
		Board.getCell(3, 3).setStatus(1);
		Board.getCell(4, 4).setStatus(1);
		
		Board.getCell(0, 7).setStatus(1);
		errtest.setStatus(1);
		assertEquals(false,Board.getCell(0, 7).canFlip(errtest2));
		assertEquals(true,Board.getCell(3, 4).canFlip(Board.getCell(3, 3)));
		assertEquals(false,cell.canFlip(this.cell));
	}

	@Test
	void testOnMove() {
		cell.onMove(false);
		assertEquals(1,cell.getStatus());
		cell.onMove(true);
		assertEquals(2,cell.getStatus());
		
		errtest.onMove(false);
		errtest.onMove(true);
	}

	@Test
	void testFlipWhite() {
		Board.getCell(3, 4).setStatus(2);
		Board.getCell(4, 3).setStatus(2);
		Board.getCell(3, 3).setStatus(1);
		Board.getCell(4, 4).setStatus(1);

		Board.getCell(4, 4).flip(Board.getCell(4, 3), true);
		Board.getCell(3, 3).flip(Board.getCell(3, 4), true);
		Board.getCell(3, 3).flip(Board.getCell(4, 4), true);
		Board.getCell(3, 3).flip(Board.getCell(4, 3), true);
		Board.getCell(3, 3).flip(Board.getCell(2, 2), false);
	}
	@Test
	void testFlipBlack() {
		Board.getCell(3, 4).setStatus(2);
		Board.getCell(4, 3).setStatus(2);
		Board.getCell(3, 3).setStatus(1);
		Board.getCell(4, 4).setStatus(1);
		
		Board.getCell(3, 4).flip(Board.getCell(4, 4), false);
		Board.getCell(3, 4).flip(Board.getCell(3, 4), false);
		Board.getCell(4, 3).flip(Board.getCell(4, 4), false);
		Board.getCell(4, 3).flip(Board.getCell(3, 4), false);
		Board.getCell(4, 3).flip(Board.getCell(3, 3), false);
		Board.getCell(4, 3).flip(Board.getCell(2, 2), true);
	}
	@Test
	void testSetStatus() {
		cell.setStatus(1);
		assertEquals(1,cell.getStatus());
	}

}
