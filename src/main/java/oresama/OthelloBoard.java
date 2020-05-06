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

@SuppressWarnings("serial")
public class OthelloBoard extends JFrame implements MouseListener {

	static int NONE = 0, WHITE = 1, BLACK = 2;

	private int board[][] = new int[8][8];

	public static void main(String[] args) {
		new OthelloBoard();
	}
	public OthelloBoard() {

		// 盤面初期化
		board[3][3] = board[4][4] = WHITE;
		board[4][3] = board[3][4] = BLACK;

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setIconImage(new ImageIcon("./icon.png").getImage());
		setTitle("othe...REVERSI");
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
				if (board[x][y] == NONE)
					continue;
				Color piece = new Color(200, 200, 200);
				if (board[x][y] != WHITE)
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
				if(board[x][y] == 1) s = "○";
				if(board[x][y] == 2) s = "●";
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
				
				if(!ArtificialIntelligence.isPossible(false, new Point(x,y), board)) {
					System.out.println("can't put.");
					return;
				}
				
				ArtificialIntelligence.onMove(false, new Point(x,y), board);
				this.repaint();
				drawBoard();
				
				val bk = ArtificialIntelligence.move(true, board);
				this.repaint();
				drawBoard();
				
				if(bk != null) {
					ArtificialIntelligence.onMove(true, bk, board);
					this.repaint();
					drawBoard();
				}
			}
			else {
				val pts = ArtificialIntelligence.possiblePoints(false, board);
				if(pts.size() == 0) {
					System.out.println("your pass, ok.");
					val bk = ArtificialIntelligence.move(true, board);
					this.repaint();
					drawBoard();
					
					if(bk != null) {
						ArtificialIntelligence.onMove(true, bk, board);
						this.repaint();
						drawBoard();
					}
				}
			}
		}
		
		{
			int w = 0, b = 0;
			for(int y = 0; y < 8; ++y) {
				for(int x = 0; x < 8; ++x) {
					if(board[x][y] == WHITE) {
						w++;
					}
					if(board[x][y] == BLACK) {
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
