package othello;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CellTest{

	Cell errtest2 = new Cell(-1,8);
	Cell cell = new Cell(5, 4);
	public void initialize() {
		for(int x = 0; x < 8; ++x) {
			for(int y = 0; y < 8; ++y) {
				Board.getCell(x, y).setStatus(0);
			}
		}
		Board.getCell(3, 4).setStatus(2);
		Board.getCell(4, 3).setStatus(2);
		Board.getCell(3, 3).setStatus(1);
		Board.getCell(4, 4).setStatus(1);
	}
	
	
	@Test
	void testCell() {
		initialize();
		assertEquals(null,Board.getCell(-1, 8));
		assertEquals(null,Board.getCell(8, -1));		
	}

	@Test
	void testGetX() {
		//セットしたXの値がとれているか
		assertEquals(5,cell.getX());
		assertEquals(0,errtest2.getX());
	}

	@Test
	void testGetY() {
		//セットしたYの値がとれているか
		assertEquals(4,cell.getY());
		assertEquals(0,errtest2.getX());
	}

	@Test
	void testGetStatus() {
		//ステータスをとれているか
		//ステータスが0の時
		assertEquals(0,cell.getStatus());
		//ステータスが1の時
		cell.setStatus(1);
		assertEquals(1,cell.getStatus());
		//ステータスが2の時
		cell.setStatus(2);
		assertEquals(2,cell.getStatus());
	}

	@Test
	void testPossibleMoveBlack() {
		initialize();
		//左から右に置けるか
		assertEquals(true,Board.getCell(2, 4).possibleMoveBlack());
		//左から右に置けないか
		assertEquals(false,Board.getCell(2, 3).possibleMoveBlack());
		
		//下から上に置けるか
		assertEquals(true,Board.getCell(3, 5).possibleMoveBlack());
		//下から上に置けないか
		assertEquals(false,Board.getCell(4, 5).possibleMoveBlack());
		
		//上から下に置けるか
		assertEquals(true,Board.getCell(4, 2).possibleMoveBlack());
		//上から下に置けないか
		assertEquals(false,Board.getCell(3, 2).possibleMoveBlack());
		
		//右から左に置けるか
		assertEquals(true,Board.getCell(5, 3).possibleMoveBlack());
		//右から左に置けないか
		assertEquals(false,Board.getCell(5, 4).possibleMoveBlack());
		
		//左下から右上に置けるか
		Board.getCell(5, 2).setStatus(1);
		assertEquals(true,Board.getCell(2, 5).possibleMoveBlack());
		//右上から左下に置けないか
		assertEquals(false,Board.getCell(3, 4).possibleMoveBlack());
		
		//右下から左上に置けるか
		Board.getCell(3, 2).setStatus(1);
		assertEquals(true,Board.getCell(5, 4).possibleMoveBlack());
		//左上から右下に置けないか
		assertEquals(false,Board.getCell(2, 1).possibleMoveBlack());
		
		//左上から右下に置けるか
		Board.getCell(4, 5).setStatus(1);
		assertEquals(true,Board.getCell(2, 3).possibleMoveBlack());
		//右下から左上に置けないか
		assertEquals(false,Board.getCell(5, 6).possibleMoveBlack());
		
		//右上から左下に置けるか
		Board.getCell(4, 1).setStatus(2);
		assertEquals(true,Board.getCell(5, 0).possibleMoveBlack());
		//左下から右上に置けないか
		assertEquals(false,Board.getCell(3, 6).possibleMoveBlack());
		
		//ステータスが0以外の時false
		assertEquals(false,Board.getCell(4, 1).possibleMoveBlack());
		
		//端の時存在しないCellに対して continue をするか
		Board.getCell(0, 7).setStatus(0);
		Board.getCell(7, 0).setStatus(0);
		assertEquals(false,Board.getCell(0, 7).possibleMoveBlack());
		assertEquals(false,Board.getCell(7, 0).possibleMoveBlack());
	}

	@Test
	void testPossibleMoveWhite() {
		initialize();
		//左から右に置けないか
		assertEquals(false,Board.getCell(2, 4).possibleMoveWhite());
		//左から右に置けるか
		assertEquals(true,Board.getCell(2, 3).possibleMoveWhite());
		
		//下から上に置けないか
		assertEquals(false,Board.getCell(3, 5).possibleMoveWhite());
		//下から上に置けるか
		assertEquals(true,Board.getCell(4, 5).possibleMoveWhite());
		
		//上から下に置けないか
		assertEquals(false,Board.getCell(4, 2).possibleMoveWhite());
		//上から下に置けるか
		assertEquals(true,Board.getCell(3, 2).possibleMoveWhite());
		
		//右から左に置けないか
		assertEquals(false,Board.getCell(5, 3).possibleMoveWhite());
		//右から左に置けるか
		assertEquals(true,Board.getCell(5, 4).possibleMoveWhite());
		
		//左上から右下に置けないか
		Board.getCell(2, 2).setStatus(2);
		assertEquals(false,Board.getCell(1, 1).possibleMoveWhite());
		//右下から左上に置けるか
		assertEquals(true,Board.getCell(5, 5).possibleMoveWhite());

		//右上から左下に置けないか
		Board.getCell(4, 2).setStatus(2);
		assertEquals(false,Board.getCell(5, 1).possibleMoveWhite());
		//左下から右上に置けるか
		assertEquals(true,Board.getCell(2, 4).possibleMoveWhite());
		
		//左下から右上に置けないか
		Board.getCell(3, 5).setStatus(2);
		assertEquals(false,Board.getCell(2, 5).possibleMoveWhite());
		//右上から左下に置けるか
		assertEquals(true,Board.getCell(5, 3).possibleMoveWhite());
		
		//右下から左上に置けないか
		Board.getCell(3, 1).setStatus(1);
		assertEquals(false,Board.getCell(2, 5).possibleMoveWhite());
		//左上から右下に置けるか
		assertEquals(true,Board.getCell(2, 0).possibleMoveWhite());
		
		//ステータスが0以外の時false
		assertEquals(false,Board.getCell(3, 1).possibleMoveWhite());
		
		//端の時存在しないCellに対して continue をするか
		Board.getCell(0, 7).setStatus(0);
		Board.getCell(7, 0).setStatus(0);
		assertEquals(false,Board.getCell(0, 7).possibleMoveWhite());
		assertEquals(false,Board.getCell(7, 0).possibleMoveWhite());

	}

	@Test
	void testCanFlip() {
		//黒を置いたとき白が黒になる箇所があるかの確認
		initialize();
		Board.getCell(2, 2).setStatus(2);
		Board.getCell(5, 5).setStatus(2);

		assertEquals(true,Board.getCell(3, 4).canFlip(Board.getCell(2, 4)));
		assertEquals(true,Board.getCell(3, 4).canFlip(Board.getCell(3, 5)));
		assertEquals(true,Board.getCell(4, 3).canFlip(Board.getCell(4, 2)));
		assertEquals(true,Board.getCell(4, 3).canFlip(Board.getCell(5, 3)));
		
		assertEquals(true,Board.getCell(2, 2).canFlip(Board.getCell(1, 1)));
		assertEquals(true,Board.getCell(5, 5).canFlip(Board.getCell(6, 6)));
		
		//白を置いたとき黒が白になる箇所があるかの確認
		Board.getCell(5, 2).setStatus(1);
		Board.getCell(2, 5).setStatus(1);
		
		assertEquals(true,Board.getCell(3, 3).canFlip(Board.getCell(2, 3)));
		assertEquals(true,Board.getCell(3, 3).canFlip(Board.getCell(3, 2)));
		assertEquals(true,Board.getCell(4, 4).canFlip(Board.getCell(4, 5)));
		assertEquals(true,Board.getCell(4, 4).canFlip(Board.getCell(5, 4)));
		
		assertEquals(true,Board.getCell(5, 2).canFlip(Board.getCell(6, 1)));
		assertEquals(true,Board.getCell(2, 5).canFlip(Board.getCell(1, 6)));
		
		//呼び出し元が「なし」の時自分自身が引数となりcanFlipを呼び出す
		Board.getCell(0, 7).setStatus(1);
		assertEquals(false,Board.getCell(0, 7).canFlip(Board.getCell(1, 6)));
		
		Board.getCell(1, 6).setStatus(1);
		assertEquals(false,Board.getCell(0, 7).canFlip(Board.getCell(1, 6)));
	}

	@Test
	void testOnMove() {
		
		initialize();
		Board.getCell(4, 4).onMove(false);
		assertEquals(1,Board.getCell(4, 4).getStatus());
		Board.getCell(4, 4).onMove(true);
		
		initialize();
		Board.getCell(3, 4).onMove(true);
		assertEquals(2,Board.getCell(3, 4).getStatus());
		Board.getCell(3, 4).onMove(false);
		
		Board.getCell(0, 7).onMove(true);
		assertEquals(2,Board.getCell(0, 7).getStatus());
		Board.getCell(0, 7).onMove(false);
		assertEquals(1,Board.getCell(0, 7).getStatus());	
	}

	@Test
	void testFlipWhite() {
		initialize();

		Board.getCell(4, 4).flip(Board.getCell(4, 3), true);
		Board.getCell(3, 3).flip(Board.getCell(3, 4), true);
		Board.getCell(3, 3).flip(Board.getCell(4, 4), true);
		Board.getCell(3, 3).flip(Board.getCell(4, 3), true);
		Board.getCell(3, 3).flip(Board.getCell(2, 2), false);
	}
	
	@Test
	void testFlipBlack() {
		initialize();
		
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
