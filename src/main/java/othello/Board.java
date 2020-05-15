package othello;

public class Board {
	
	private static Board board = new Board();
	
	private static final int BOARD_SIZE = 8;

	private Cell[][] cells = new Cell[BOARD_SIZE][BOARD_SIZE];
	
	static {
		for(int y =0; y < BOARD_SIZE; ++y) {
			for(int x = 0; x < BOARD_SIZE;++x) {
				board.cells[x][y] = new Cell(x,y);
 			}
		}
	}
	
	private Board() {
	}
	
	public static Cell getCell(int x, int y) {
		if(x < 0 || x > 7 || y < 0 || y > 7) {
			return null;
		}
		return board.cells[x][y];
	}
}
