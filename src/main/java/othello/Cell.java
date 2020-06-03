package othello;

public class Cell {

	private int status = 0;

	private int x, y;



	public Cell(int x, int y) {
		if(x >= 0 && x <=7 && y >= 0 && y <= 7 ) {
			this.x = x;
			this.y = y;

		}
	}

	// xの値を取得
	public int getX(int x) {
		return this.x;
	}

	// yの値を取得
	public int getY(int y) {
		return this.y;
	}

	// 現在の状態を取得
	public int getStatus() {
		return this.status;
	}


	public boolean possibleMoveBlack() {
		if(this.status == 1 || this.status == 2) {
			return false;
		}else {
			for(int i = -1; i <= 1; i++) {
				for(int j = -1; j <= 1; j++) {

					if(i == 0 && j == 0) {
						continue;
					}

					if(x + i < 0 || x + i > 7 || y + j < 0 || y + j > 7) {
						continue;
					}

					if(Board.getCell(x+i,y+j).canFiip(this) == true){
						return true;
					}

				}
			}

		}
		return false;

	}

	public boolean possibleMoveWhite() {
		if(this.status == 1 || this.status == 2) {
			return false;
		}else {
			for(int i = -1; i <= 1; i++) {
				for(int j = -1; j <= 1; j++) {

					if(i == 0 && j == 0) {
						continue;
					}

					if(x + i < 0 || x + i > 7 || y + j < 0 || y + j > 7) {
						continue;
					}

					if(Board.getCell(x+i,y+j).canFiip(this) == true){
						return true;
					}

				}
			}

			return false;
		}

	}

	boolean canFiip(Cell client) {
		int x1 = client.x;
		int y1 = client.y;


		if(this.x == client.x) {
			x1 = this.x;
		}else if(this.x < client.x) {
			x1 = this.x + 1;
		}else{
			x1 = this.x -1;
		}

		if(this.y == client.y) {
			y1 = this.y;
		}else if(this.y < client.y) {
			y1 = this.y + 1;
		}else if(this.y > client.y) {
			y1 = this.y -1;
		}

		if(client.status == 0) {
			return Board.getCell(x1, y1).canFiip(this);
		}else if(this.status != client.status){
			return true;
		}else {
			if(x1 != client.x || y1 != client.y) {
				return Board.getCell(x1,  y1).canFiip(this);
			}else {
				return false;
			}
		}
	}

	public void onMove(boolean white) {
		if(white) {
			for(int i = -1; i <= 1; i++) {
				for(int j = -1; j <= 1; j++) {

					if(i == 0 && j == 0) {
						continue;
					}

					if(x + i < 0 || x + i > 7 || y + j < 0 || y + j > 7) {
						continue;
					}

					if(Board.getCell(x+i, y+j).canFiip(this) == true) {
						flip(this, false);
					}
				}
			}
		}else {
			for(int i = -1; i <= 1; i++) {
				for(int j = -1; j <= 1; j++) {

					if(i == 0 && j == 0) {
						continue;
					}

					if(x + i < 0 || x + i > 7 || y + j < 0 || y + j > 7) {
						continue;
					}

					if(Board.getCell(x+i, y+j).canFiip(this) == true) {
						flip(this, false);
					}
				}
			}
		}
	}

	public void flip(Cell client, boolean white) {
		if(white && this.status == 2) {

		}else if(white && this.status == 1) {
			if(canFiip(client)) {
				flip(client, true);
			}else {
				//例外発生
			}

		}
		if(!white && this.status ==1) {

		}else if(!white && this.status == 2) {
			this.status = 1;
		}
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
