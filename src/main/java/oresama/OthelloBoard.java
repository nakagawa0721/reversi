package oresama;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RadialGradientPaint;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import lombok.val;
import othello.Board;

@SuppressWarnings("serial")
public class OthelloBoard extends JFrame implements MouseListener {

	static int NONE = 0, BLACK = 1, WHITE = 2;

	public int[][] getBoard() {
		int board[][] = new int[8][8];
		for(int x = 0; x < 8; ++x) {
			for(int y = 0; y < 8; ++y) {
				board[x][y] = Board.getCell(x, y).getStatus();
			}
		}
		return board;
	}
	
	private int turn = BLACK;

	public OthelloBoard() {

		// 盤面初期化
		Board.getCell(3, 3).setStatus(BLACK);
		Board.getCell(4, 4).setStatus(BLACK);
		Board.getCell(4, 3).setStatus(WHITE);
		Board.getCell(3, 4).setStatus(WHITE);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setIconImage(new ImageIcon("./icon.png").getImage());
		setTitle("REVERSI");
		val insets = getInsets();
		val width = 600 + insets.left + insets.right;
		val height = 400 + insets.top + insets.bottom;
		setSize(width, height);
		
		addMouseListener(this);

		setVisible(true);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		val g2 = (Graphics2D) g;

		val p = new RadialGradientPaint(200, 200, 280, new float[] { 0.5f, 0.75f, 1.0f },
				new Color[] { new Color(0, 120, 0), new Color(0, 85, 0), new Color(0, 50, 0) });

		val insets = getInsets();

		g2.setPaint(p);
		g2.fillRect(0, 0, insets.left + 320, insets.top + 320);

		g2.setColor(Color.BLACK);
		for (int i = 0; i < 9; i++) {
			int x = 400 / 10 * i;
			g2.drawLine(insets.left + x, insets.top + 0, insets.left + x, insets.top + 320);
			g2.drawLine(insets.left + 0, insets.top + x, insets.left + 320, insets.top + x);
		}

		for (int y = 0; y < 8; ++y) {
			for (int x = 0; x < 8; ++x) {
				if (getBoard()[x][y] == NONE)
					continue;
				Color piece = new Color(200, 200, 200);
				if (getBoard()[x][y] != WHITE)
					piece = Color.black;

				int cx = 13 + x * 40;
				int cy = 37 + y * 40;

				val r = new RadialGradientPaint(cx, cy, 50, new float[] { 0.0f, 0.5f, 1.0f },
						new Color[] { Color.WHITE, piece, piece });
				g2.setPaint(r);
				g2.fillOval(cx, cy, 30, 30);
				g2.setColor(Color.black);
				g2.drawOval(cx, cy, 30, 30);
			}
		}

		g.dispose();
	}
	
	private void drawBoard() {
		for(int y = 0; y < 8; ++y) {
			for(int x = 0; x < 8; ++x) {
				String s = " ";
				if(getBoard()[x][y] == 1) s = "○";
				if(getBoard()[x][y] == 2) s = "●";
				System.out.print(s);
			}
			System.out.println();
		}
		System.out.println("=======================");
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		val insets = getInsets();

		val pt = e.getPoint();
		
		{
			int x = pt.x / 40;
			int y = (pt.y - insets.top) / 40;
			
			System.out.println("x=" + x + ", y=" + y);
			
			if(x < 8 && y < 8) {
				
				val cell = Board.getCell(x, y);
				
				if(turn == BLACK) {
					if(!cell.possibleMoveBlack()) {
						System.out.println("black can't be placed there.");
						return;
					}
					
					if(!ArtificialIntelligence.isPossible(false, new Point(x,y), getBoard())) {
						throw new RuntimeException("possibleMoveBlackの判定が間違っている");
					}

					cell.onMove(false);
					ArtificialIntelligence.onMove(false, new Point(x,y), getBoard());
				}
				else if(turn == WHITE) {
					if(!cell.possibleMoveWhite()) {
						System.out.println("white can't be placed there.");
						return;
					}

					if(!ArtificialIntelligence.isPossible(true, new Point(x,y), getBoard())) {
						throw new RuntimeException("possibleMoveWhiteの判定が間違っている");
					}

					cell.onMove(true);
				}
				
				ArtificialIntelligence.onMove(turn == WHITE, new Point(x,y), getBoard());
				this.repaint();
				drawBoard();
				
				turn = (turn == BLACK) ? WHITE : BLACK;
			}
			else {

				// パスができるかどうかを確認する
				boolean pass = true;
				for(y = 0; y < 8; y++) {
					for(x = 0; x < 8; x++) {
						val cell = Board.getCell(x, y);
						
		 				if(turn == BLACK) {
		 					if(cell.possibleMoveBlack()) {
		 						pass = false;
		 						break;
		 					}
						}
		 				else {
		 					if(cell.possibleMoveWhite()) {
		 						pass = false;
		 						break;
		 					}
		 				}
					}
				}
				
				if(!pass) {
					System.out.println("パスはできないそうです。置ける場所があると判定されています。");
					
					val pts = ArtificialIntelligence.possiblePoints(turn == WHITE, getBoard());
					if(pts.size() == 0) {
						System.out.println("（本当は置けない）");
					}
					return;
				}
				
				val pts = ArtificialIntelligence.possiblePoints(turn == WHITE, getBoard());
				if(pts.size() != 0) {
					System.out.println("置ける場所があるのにパスが認められました。");
				}
				
				if(turn == WHITE) {
					System.out.println("white pass, ok.");
					turn = BLACK;
				}
				else {
					System.out.println("black pass, ok.");
					turn = WHITE;
				}
			}
		}
		
		// 状況報告
		{
			int w = 0, b = 0;
			for(int y = 0; y < 8; ++y) {
				for(int x = 0; x < 8; ++x) {
					if(getBoard()[x][y] == WHITE) {
						w++;
					}
					if(getBoard()[x][y] == BLACK) {
						b++;
					}
				}
			}
			System.out.println("Black=" + b + ", White=" + w);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
