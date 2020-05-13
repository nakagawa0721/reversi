package othello;

public class Cell {
	
	private int status = 0;
	
	private int x, y;

	public Cell(int x, int y) {
	}
	
	public int getX() {
		return 0;
	}
	
	public int getY() {
		return 0;
	}

	public int getStatus() {
		return 0;
	}
	
	public boolean possibleMoveBlack() {
		return false;
	}

	public boolean possibleMoveWhite() {
		return false;
	}
	
	boolean canFiip(Cell client) {
		return false;
	}

	public void onMove(boolean white) {
	}

	public void flip(Cell client, boolean white) {
	}
	
	public void setStatus(int status) {
	}
}
