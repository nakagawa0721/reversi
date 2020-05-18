package othello;

public class Cell {

	private int status = 0;

	private int x, y;

	public Cell(int x, int y) {
		//0~7の範囲でなければならない
		if(x < 0 || x > 7 || y < 0 || y > 7) {
			return;
		}
		this.x = x;
		this.y = y;
	}
	//Xの位置
	public int getX() {
		return this.x;
	}
	//Yの位置
	public int getY() {
		return this.y;
	}
	//状態
	public int getStatus() {
		return this.status;
	}

	public boolean possibleMoveBlack() {
		if(this.status != 0) {
			return false;
		}else{
			for(int i = -1; i < 2; i++) {
				for(int z = -1;z < 2; z++ ) {
					if(i == 0 && z == 0) {
						continue;
					}
					if(x+i < 0 || x+i > 7 || y+z < 0 || y+z > 7) {
						continue;
					}
					if(Board.getCell((x+i),(y+z)).status == 2) {
						if(Board.getCell((x+i),(y+z)).canFlip(this) == true) {
							return true;
						}
					}
				}
			}
			return false;
		}
	}

	public boolean possibleMoveWhite() {
		if(this.status != 0) {
			return false;
		}else{
			for(int i = -1; i < 2; i++) {
				for(int z = -1;z < 2; z++ ) {
					if(i == 0 && z == 0) {
						continue;
					}
					if(x+i < 0 || x+i > 7 || y+z < 0 || y+z > 7) {
						continue;
					}
					if(Board.getCell((x+i),(y+z)).status == 1) {
						if(Board.getCell((x+i),(y+z)).canFlip(this) == true) {
							return true;
						}
					}
				}
			}
			return false;
		}
	}

	boolean canFlip(Cell client) {
		int X = client.x;
		int Y = client.y;
		if(client.x == this.x) {
			X = this.x;
		}
		if(client.x < this.x) {
			X = this.x+1;
		}
		if(client.x > this.x) {
			X = this.x-1;
		}
		if(client.y == this.y) {
			Y = this.y;
		}
		if(client.y < this.y) {
			Y = this.y+1;
		}
		if(client.y > this.y) {
			Y = this.y-1;
		}
		//自分自身が「なし」の時false
		if(this.status == 0) {
			return false;
		}
		
		//呼び出し元が「なし」の時自分自身が引数となりcanFlipを呼び出す
		if(client.status == 0) {
			if(X < 0 || X > 7 || Y < 0 || Y > 7 ) {
				return false;
			}
			return Board.getCell(X,Y).canFlip(this);
		}
		
		
		if(client.status == this.status) {
			if(X < 0 || X > 7 || Y < 0 || Y > 7 ) {
				return false;
			}else
				return Board.getCell(X,Y).canFlip(this);
		}
		
		
		if(client.status != this.status) {
			return true;
		}
		return false;
	}

	public void onMove(boolean white) {
		if(white) {
			for(int i = -1; i < 2; i++) {
				for(int z = -1;z < 2; z++ ) {
					if(i == 0 && z == 0) {
						continue;
					}
					if(x+i < 0 || x+i > 7 || y+z < 0 || y+z > 7) {
						continue;
					}
					if(Board.getCell((x+i),(y+z)).status == 1) {
						if(Board.getCell((x+i),(y+z)).canFlip(this) == true) {
							Board.getCell((x+i),(y+z)).flip(this,true);
						}
					}
				}
			}
			this.status = 2;
		}
		else {
			for(int i = -1; i < 2; i++) {
				for(int z = -1;z < 2; z++ ) {
					if(i == 0 && z == 0) {
						continue;
					}
					if(x+i < 0 || x+i > 7 || y+z < 0 || y+z > 7) {
						continue;
					}
					if(Board.getCell((x+i),(y+z)).status == 2) {
						if(Board.getCell((x+i),(y+z)).canFlip(this) == true) {
							Board.getCell((x+i),(y+z)).flip(this,false);
						}
					}
				}
			}
			this.status = 1;
		}
	}

	public void flip(Cell client, boolean white) {
		//白
		if(white && this.status == 2) {
			return;
		}else if(white && this.status == 1) {
			this.status = 2;
			int X = client.x;
			int Y = client.y;
			if(client.x == this.x) {
				X = this.x;
			}
			if(client.x < this.x) {
				X = this.x+1;
			}
			if(client.x > this.x) {
				X = this.x-1;
			}
			if(client.y == this.y) {
				Y = this.y;
			}
			if(client.y < this.y) {
				Y = this.y+1;
			}
			if(client.y > this.y) {
				Y = this.y-1;
			}
			if(X < 0 || X > 7 || Y < 0 || Y > 7 ) {
				throw new RuntimeException();
			}else {
				Board.getCell(X,Y).flip(this,true);
			}
		}

		//黒
		if(!white && this.status == 1) {
			return;
		}else if(!white && this.status == 2) {
			this.status = 1;
			int X = client.x;
			int Y = client.y;
			if(client.x == this.x) {
				X = this.x;
			}
			if(client.x < this.x) {
				X = this.x+1;
			}
			if(client.x > this.x) {
				X = this.x-1;
			}
			if(client.y == this.y) {
				Y = this.y;
			}
			if(client.y < this.y) {
				Y = this.y+1;
			}
			if(client.y > this.y) {
				Y = this.y-1;
			}
			if(X < 0 || X > 7 || Y < 0 || Y > 7 ) {
				throw new RuntimeException();
			}else {
				Board.getCell(X,Y).flip(this,false);
			}
		}
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
