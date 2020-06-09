package othello;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import oresama.OthelloBoard;

class CellTest {

	Cell cell = new Cell(-1,7);

	// 初期値
	public void init() {
		for (int x = 0; x < 8; x++) {
			for(int y = 0; y < 8; y++) {
				Board.getCell(x, y).setStatus(0);
			}
		}
		Board.getCell(3, 3).setStatus(1);
		Board.getCell(4, 4).setStatus(1);
		Board.getCell(4, 3).setStatus(2);
		Board.getCell(3, 4).setStatus(2);

	}

	@Test
	void testCell() {

		init();

		// 範囲外の時、nullを返却する
		assertNull(Board.getCell(0, 8));
		assertNull(Board.getCell(7, -2));
	}

	@Test
	void testGetX() {

		init();

		// xの値を取得する
		assertEquals(3, Board.getCell(3, 3).getX());
		assertEquals(4, Board.getCell(4, 4).getX());
	}

	@Test
	void testGetY() {

		init();

		// yの値を取得する
		assertEquals(4, Board.getCell(3, 4).getY());
		assertEquals(3, Board.getCell(4, 3).getY());
	}

	@Test
	void testGetStatus() {

		init();
		//現在のステータスが0の時
		assertEquals(0, Board.getCell(1, 1).getStatus());
		//現在のステータスが1の時
		assertEquals(1, Board.getCell(3, 3).getStatus());
		assertEquals(1, Board.getCell(4, 4).getStatus());
		//現在のステータスが2の時
		assertEquals(2, Board.getCell(3, 4).getStatus());
		assertEquals(2, Board.getCell(4, 3).getStatus());
	}

	@Test
	void TestPossibleMoveBlack() {

		init();

		// 黒がすでにある時、falseを返す
		assertEquals(false, Board.getCell(3, 3).possibleMoveBlack());
		assertEquals(false, Board.getCell(4, 4).possibleMoveBlack());
		// 白がすでにある時、falseを返す
		assertEquals(false, Board.getCell(3, 4).possibleMoveBlack());
		assertEquals(false, Board.getCell(4, 3).possibleMoveBlack());


		// 白を挟んで置くとき、trueを返す
		assertEquals(true, Board.getCell(2, 4).possibleMoveBlack());
		assertEquals(true, Board.getCell(3, 5).possibleMoveBlack());
		assertEquals(true, Board.getCell(4, 2).possibleMoveBlack());
		assertEquals(true, Board.getCell(5, 3).possibleMoveBlack());

		// 白を挟めないところに置くとき、falseを返す
		assertEquals(false, Board.getCell(2, 2).possibleMoveBlack());
		assertEquals(false, Board.getCell(2, 3).possibleMoveBlack());
		assertEquals(false, Board.getCell(2, 5).possibleMoveBlack());
		assertEquals(false, Board.getCell(3, 2).possibleMoveBlack());
		assertEquals(false, Board.getCell(4, 5).possibleMoveBlack());
		assertEquals(false, Board.getCell(5, 2).possibleMoveBlack());
		assertEquals(false, Board.getCell(5, 4).possibleMoveBlack());
		assertEquals(false, Board.getCell(5, 5).possibleMoveBlack());

		// cellが対象範囲外の時、continueする
		assertEquals(false, Board.getCell(7, 7).possibleMoveBlack());

	}

	@Test
	void TestPossibleMoveWhite() {

		init();

		// 黒がすでにある時、falseを返す
		assertEquals(false, Board.getCell(3, 3).possibleMoveWhite());
		assertEquals(false, Board.getCell(4, 4).possibleMoveWhite());
		// 白がすでにある時、falseを返す
		assertEquals(false, Board.getCell(3, 4).possibleMoveWhite());
		assertEquals(false, Board.getCell(4, 3).possibleMoveWhite());


		// 黒を挟んで置くとき、trueを返す
		assertEquals(true, Board.getCell(2, 3).possibleMoveWhite());
		assertEquals(true, Board.getCell(3, 2).possibleMoveWhite());
		assertEquals(true, Board.getCell(4, 5).possibleMoveWhite());
		assertEquals(true, Board.getCell(5, 4).possibleMoveWhite());

		// 黒を挟めないところに置くとき、falseを返す
		assertEquals(false, Board.getCell(2, 2).possibleMoveWhite());
		assertEquals(false, Board.getCell(2, 4).possibleMoveWhite());
		assertEquals(false, Board.getCell(2, 5).possibleMoveWhite());
		assertEquals(false, Board.getCell(3, 5).possibleMoveWhite());
		assertEquals(false, Board.getCell(4, 2).possibleMoveWhite());
		assertEquals(false, Board.getCell(5, 2).possibleMoveWhite());
		assertEquals(false, Board.getCell(5, 3).possibleMoveWhite());
		assertEquals(false, Board.getCell(5, 5).possibleMoveWhite());

		// 次のcellが対象範囲外の時、continueを実行する
		assertEquals(false, Board.getCell(7, 7).possibleMoveWhite());

	}

	@Test
	void testCanFiip() {

		init();

		//現在のステータスが0の時にfalseを返す
		assertEquals(false,Board.getCell(1, 1).canFiip(Board.getCell(2, 1)));

		// 次のセルが存在しないときにfalseを返す
		assertEquals(false,Board.getCell(3, 4).canFiip(Board.getCell(3, 4)));


		// 黒を置いた時に、白が黒になるならtrueを返す
		assertEquals(true, Board.getCell(3, 4).canFiip(Board.getCell(2, 4)));
		assertEquals(true, Board.getCell(3, 4).canFiip(Board.getCell(3, 5)));
		assertEquals(true, Board.getCell(4, 3).canFiip(Board.getCell(4, 2)));
		assertEquals(true, Board.getCell(4, 3).canFiip(Board.getCell(5, 3)));

		// 白を置いた時に、黒が白になるならtrueを返す
		assertEquals(true, Board.getCell(3, 3).canFiip(Board.getCell(3, 2)));
		assertEquals(true, Board.getCell(3, 3).canFiip(Board.getCell(2, 3)));
		assertEquals(true, Board.getCell(4, 4).canFiip(Board.getCell(4, 5)));
		assertEquals(true, Board.getCell(4, 4).canFiip(Board.getCell(5, 4)));


		// 次のセルが範囲外の時に,falseを返す
		Board.getCell(0, 0).setStatus(OthelloBoard.BLACK);
		assertEquals(false, Board.getCell(0, 0).canFiip(Board.getCell(1, 1)));

		// 次のセルが範囲外かつ、呼び出し元と現在のステータスが同じとき
		Board.getCell(1, 1).setStatus(OthelloBoard.BLACK);
		assertEquals(false, Board.getCell(0, 0).canFiip(Board.getCell(1, 1)));

		// (3,5)に白色セルをセット
		Board.getCell(3, 5).setStatus(OthelloBoard.WHITE);
		// (3,6)に黒セルをセットすると、trueを返却するか
		assertEquals(true, Board.getCell(3, 5).canFiip(Board.getCell(3, 6)));


	}

	@Test
	void testOnMove() {
		init();

		// 黒を白に変更できているか
		Board.getCell(3, 2).setStatus(OthelloBoard.WHITE);
		Board.getCell(3, 2).onMove(true);
		assertEquals(OthelloBoard.WHITE, Board.getCell(3, 3).getStatus());
		Board.getCell(4, 5).setStatus(OthelloBoard.WHITE);
		Board.getCell(4, 5).onMove(true);
		assertEquals(OthelloBoard.WHITE, Board.getCell(4, 4).getStatus());

		// 初期値
		init();

		// 	白を黒に変更できるか
		Board.getCell(2, 4).setStatus(OthelloBoard.BLACK);
		Board.getCell(2, 4).onMove(false);
		assertEquals(OthelloBoard.BLACK, Board.getCell(3, 4).getStatus());
		Board.getCell(4, 2).setStatus(OthelloBoard.BLACK);
		Board.getCell(4, 2).onMove(false);
		assertEquals(OthelloBoard.BLACK, Board.getCell(4, 3).getStatus());

		// 次のcellが範囲外の時、continueを実行する(falseの場合)
		Board.getCell(7, 7).setStatus(OthelloBoard.BLACK);
		Board.getCell(7, 6).setStatus(OthelloBoard.WHITE);
		Board.getCell(7, 5).setStatus(OthelloBoard.BLACK);
		Board.getCell(7, 7).onMove(false);
		assertEquals(OthelloBoard.BLACK, Board.getCell(7, 6).getStatus());

		// 元に戻す
		init();

		// 次のcellが範囲外の時、continueを実行する(trueの場合)
		Board.getCell(7, 7).setStatus(OthelloBoard.WHITE);
		Board.getCell(7, 6).setStatus(OthelloBoard.BLACK);
		Board.getCell(7, 5).setStatus(OthelloBoard.WHITE);
		Board.getCell(7, 7).onMove(true);
		assertEquals(OthelloBoard.WHITE, Board.getCell(7, 6).getStatus());

	}

	@Test
	void testFlip() {

		init();

		// 白を黒に変更できるか
		Board.getCell(3, 4).flip(Board.getCell(3, 5), false);
		assertEquals(OthelloBoard.BLACK , Board.getCell(3, 4).getStatus());
		Board.getCell(4, 3).flip(Board.getCell(5, 3), false);
		assertEquals(OthelloBoard.BLACK , Board.getCell(4, 3).getStatus());

		init();

		// 黒を白に変更できるか
		Board.getCell(3, 3).flip(Board.getCell(2, 3), true);
		assertEquals(OthelloBoard.WHITE , Board.getCell(3, 3).getStatus());
		Board.getCell(4, 4).flip(Board.getCell(5, 4), true);
		assertEquals(OthelloBoard.WHITE , Board.getCell(4, 4).getStatus());


		// 次のcellが範囲外の時、例外発生する(falseの場合)
		Board.getCell(0, 0).setStatus(OthelloBoard.WHITE);
		assertThrows(RuntimeException.class, () -> Board.getCell(0, 0).flip(Board.getCell(1, 1), false));

		// 次のcellが範囲外の時、例外発生する(trueの場合)
		Board.getCell(0, 0).setStatus(OthelloBoard.BLACK);
		assertThrows(RuntimeException.class, () -> Board.getCell(0, 0).flip(Board.getCell(1, 1), true));

	}

	@Test
	void testSetStatus() {

		init();

		// 黒をセットできるか
		 Board.getCell(1, 1).setStatus(OthelloBoard.BLACK);
		 assertEquals(OthelloBoard.BLACK, Board.getCell(1, 1).getStatus());

		 // 白をセットできるか
		 Board.getCell(6, 6).setStatus(OthelloBoard.WHITE);
		 assertEquals(OthelloBoard.WHITE, Board.getCell(6, 6).getStatus());


	}

}
