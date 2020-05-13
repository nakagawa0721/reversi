package othello;

import java.awt.Point;

import oresama.ArtificialIntelligence;

public class Cell {
	
	private int status = 0;
	
	private int x, y;

	public Cell(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}

	public int getStatus() {
		return status;
	}
	
	public boolean possibleMoveBlack() {
		return ArtificialIntelligence.isPossible(false, new Point(x,y), App.ob.getBoard());
	}

	public boolean possibleMoveWhite() {
		return ArtificialIntelligence.isPossible(true, new Point(x,y), App.ob.getBoard());
	}
	
	boolean canFiip(Cell client) {
		return false;
	}

	public void onMove(boolean white) {
		this.status = white ? 2:1;
	}

	public void flip(Cell client, boolean white) {
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
}
