package othello;

public class Board {
	
	private static Board board = new Board();
	
	private static final int BOARD_SIZE = 8;

	private Cell[][] cells = new Cell[BOARD_SIZE][BOARD_SIZE];
	
	private Board() {
		for(int x = 0; x < BOARD_SIZE; x++) {
			for(int y = 0; y < BOARD_SIZE; y++) {
				cells[x][y] = new Cell(x, y);
			}
		}
	}
	
	public static Cell getCell(int x, int y) {
		if(x < 0 || x >= BOARD_SIZE)
			return null;
		
		if(y < 0 || y >= BOARD_SIZE)
			return null;

		return board.cells[x][y];
	}
}
