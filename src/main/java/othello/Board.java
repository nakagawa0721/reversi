package othello;

public class Board {
	
	private static Board board = new Board();
	
	private static final int BOARD_SIZE = 8;

	private Cell[][] cells = new Cell[BOARD_SIZE][BOARD_SIZE];
	
	private Board() {
	}
	
	public static Cell getCell(int x, int y) {
		return null;
	}
}
