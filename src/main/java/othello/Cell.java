package othello;

import oresama.OthelloBoard;

public class Cell {

	private int status = 0;

	private int x, y;

	private int BOARD_SIZE_MIN = 0, BOARD_SIZE_MAX = 7;

	private int X = 0, Y = 0;

	public Cell(int x, int y) {
		if(x < BOARD_SIZE_MIN || x > BOARD_SIZE_MAX || y < BOARD_SIZE_MIN || y > BOARD_SIZE_MAX ) {

			return;
		}else {
			this.x = x;
			this.y = y;
		}
	}

	// xの値を取得
	public int getX() {
		return this.x;
	}

	// yの値を取得
	public int getY() {
		return this.y;
	}

	// 現在の状態を取得
	public int getStatus() {
		return this.status;
	}

	// 状態なしから黒に変更できるか返答する
	public boolean possibleMoveBlack() {
		if(this.status == OthelloBoard.BLACK || this.status == OthelloBoard.WHITE) {
			return false;
		}else {
			for(int i = -1; i <= 1; i++) {
				for(int j = -1; j <= 1; j++) {

					if(i == X && j == Y) {
						continue;
					}

					if(x + i < BOARD_SIZE_MIN || x + i > BOARD_SIZE_MAX || y + j < BOARD_SIZE_MIN || y + j > BOARD_SIZE_MAX) {
						continue;
					}

					// 状態が白の時
					if (Board.getCell(x+i,y+j).status == OthelloBoard.WHITE ) {
						if(Board.getCell(x+i,y+j).canFiip(this) == true){
							return true;
						}
					}
				}
			}
			return false;
		}
	}

	// 状態なしから白に変更できるか返答する
	public boolean possibleMoveWhite() {
		if(this.status == OthelloBoard.BLACK || this.status ==  OthelloBoard.WHITE) {
			return false;
		}else {
			for(int i = -1; i <= 1; i++) {
				for(int j = -1; j <= 1; j++) {

					if(i == X && j == Y) {
						continue;
					}

					if(x + i < BOARD_SIZE_MIN || x + i > BOARD_SIZE_MAX || y + j < BOARD_SIZE_MIN || y + j > BOARD_SIZE_MAX) {
						continue;
					}

					// 状態が黒の時
					if(Board.getCell(x+i, y+j).status == OthelloBoard.BLACK ) {
						if(Board.getCell(x+i,y+j).canFiip(this) == true){
							return true;
						}
					}
				}
			}
			return false;
		}

	}

	boolean canFiip(Cell client) {

		if(this.status == OthelloBoard.NONE) {
			return false;
		}

		int x1 = client.x;
		int y1 = client.y;

		if(this.x == client.x) {
			x1 = this.x;
		}else if(this.x > client.x) {
			x1 = this.x + 1;
		}else{
			x1 = this.x - 1;
		}

		if(this.y == client.y) {
			y1 = this.y;
		}else if(this.y > client.y) {
			y1 = this.y + 1;
		}else {
			y1 = this.y -1;
		}

		if(client.status == OthelloBoard.NONE) {
			if(x1 < BOARD_SIZE_MIN || x1 > BOARD_SIZE_MAX || y1 < BOARD_SIZE_MIN || y1 > BOARD_SIZE_MAX)  {
				return false;
			}

			return Board.getCell(x1, y1).canFiip(this);
		}else if(client.status != this.status){

			return true;
		}else {
			if(x1 < BOARD_SIZE_MIN || x1 > BOARD_SIZE_MAX || y1 < BOARD_SIZE_MIN || y1 > BOARD_SIZE_MAX ) {
				return false;
			}

			if(x1 != client.x || y1 != client.y) {
				return Board.getCell(x1,  y1).canFiip(this);
			}else {
				return false;
			}
		}
	}

	public void onMove(boolean white) {
		// 状態を白に変更
		if(white) {

			for(int i = -1; i <= 1; i++) {
				for(int j = -1; j <= 1; j++) {

					if(i == X && j == Y) {
						continue;
					}


					if(x + i < BOARD_SIZE_MIN || x + i > BOARD_SIZE_MAX || y + j < BOARD_SIZE_MIN || y + j > BOARD_SIZE_MAX) {
						continue;
					}

					if(Board.getCell(x+i, y+j).status == OthelloBoard.BLACK) {
						if(Board.getCell(x+i, y+j).canFiip(this) == true) {
							Board.getCell(x+i, y+j).flip(this, true);
						}
					}
				}
			}
			this.status = OthelloBoard.WHITE;
		// 状態を黒に変更
		}else {
			for(int i = -1; i <= 1; i++) {
				for(int j = -1; j <= 1; j++) {

					if(i == X && j == Y) {
						continue;
					}

					if(x + i < BOARD_SIZE_MIN || x + i > BOARD_SIZE_MAX || y + j < BOARD_SIZE_MIN || y + j > BOARD_SIZE_MAX) {
						continue;
					}

					if(Board.getCell(x+i, y+j).status == OthelloBoard.WHITE) {
						if(Board.getCell(x+i, y+j).canFiip(this) == true) {
							Board.getCell(x+i, y+j).flip(this, false);
						}
					}
				}
			}
			this.status = OthelloBoard.BLACK;
		}

	}

	public void flip(Cell client, boolean white) {
		if(white && this.status == OthelloBoard.WHITE) {
			return;
		}else if(white && this.status == OthelloBoard.BLACK) {
			// 状態を白にする
			this.status = OthelloBoard.WHITE;
			int x1 = client.x;
			int y1 = client.y;

			if(this.x == client.x) {
				x1 = this.x;
			}else if(this.x > client.x) {
				x1 = this.x + 1;
			}else{
				x1 = this.x - 1;
			}

			if(this.y == client.y) {
				y1 = this.y;
			}else if(this.y > client.y) {
				y1 = this.y + 1;
			}else  {
				y1 = this.y -1;
			}

			if(x1 < BOARD_SIZE_MIN || x1 > BOARD_SIZE_MAX || y1 < BOARD_SIZE_MIN || y1 > BOARD_SIZE_MAX ) {
				//例外を発行
				throw new RuntimeException("Cellが存在しません。");
			}else {

				Board.getCell(x1, y1).flip(this,true);
			}

		}

		if(!white && this.status == OthelloBoard.BLACK) {
			return;

		}else if(!white && this.status == OthelloBoard.WHITE) {
			// 状態を黒にする
			this.status = OthelloBoard.BLACK;
			int x1 = client.x;
			int y1 = client.y;

			if(this.x == client.x) {
				x1 = this.x;
			}else if(this.x > client.x) {
				x1 = this.x + 1;
			}else{
				x1 = this.x - 1;
			}

			if(this.y == client.y) {
				y1 = this.y;
			}else if(this.y > client.y) {
				y1 = this.y + 1;
			}else {
				y1 = this.y -1;
			}

			if(x1 < BOARD_SIZE_MIN || x1 > BOARD_SIZE_MAX || y1 < BOARD_SIZE_MIN || y1 > BOARD_SIZE_MAX ) {
				//例外を発行
				throw new RuntimeException("Cellが存在しません。");
			}else {

				Board.getCell(x1, y1).flip(this,false);
			}

		}

	}

	// 現在の状態を設定する
	public void setStatus(int status) {
		this.status = status;
	}
}
