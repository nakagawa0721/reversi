package othello;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;


class BoardTest {

	@Test
	// 範囲内の時
	void testgetCell_1() {

		assertNotNull(Board.getCell(0, 0));
		assertNotNull(Board.getCell(2, 2));
		assertNotNull(Board.getCell(7, 7));

	}
	@Test
	// 範囲外の時
	void testgetCell_2() {
		assertNull(Board.getCell(8, 8));
		assertNull(Board.getCell(10, 10));
		assertNull(Board.getCell(-1, -1));
		assertNull(Board.getCell(-10, -10));

	}

}
