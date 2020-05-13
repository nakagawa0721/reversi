package oresama;

import java.awt.Point;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import lombok.val;

public class ArtificialIntelligence {

	private static void log(String s) {
		System.out.println(s);
	}

	private static int NONE = 0, WHITE = 2, BLACK = 1;

	private static boolean validPoint(Point pt) {
		return pt.x > -1 && pt.y > -1 && pt.x < 8 && pt.y < 8;
	}

	enum Vector {
		upleft {
			@Override
			Point next(Point pt) {
				pt.x--;
				pt.y--;
				return validPoint(pt) ? pt : null;
			}
		},
		upright {
			@Override
			Point next(Point pt) {
				pt.x++;
				pt.y--;
				return validPoint(pt) ? pt : null;
			}
		},
		downleft {
			@Override
			Point next(Point pt) {
				pt.x--;
				pt.y++;
				return validPoint(pt) ? pt : null;
			}
		},
		downright {
			@Override
			Point next(Point pt) {
				pt.x++;
				pt.y++;
				return validPoint(pt) ? pt : null;
			}
		},
		up {
			@Override
			Point next(Point pt) {
				pt.y--;
				return validPoint(pt) ? pt : null;
			}
		},
		down {
			@Override
			Point next(Point pt) {
				pt.y++;
				return validPoint(pt) ? pt : null;
			}
		},
		left {
			@Override
			Point next(Point pt) {
				pt.x--;
				return validPoint(pt) ? pt : null;
			}
		},
		right {
			@Override
			Point next(Point pt) {
				pt.x++;
				return validPoint(pt) ? pt : null;
			}
		};

		abstract Point next(Point pt);
	}

	private static List<Integer> nextVectorIs(Point pt, Vector vec, int[][] board) {
		val array = new ArrayList<Integer>();

		Point n = (Point) pt.clone();
		while (vec.next(n) != null) {
			array.add(board[n.x][n.y]);
		}

		return array;
	}

	private static boolean isEnemy(boolean white, int num) {
		return white ? num == BLACK : num == WHITE;
	}

	private static boolean isPossible(boolean white, List<Integer> array) {
		boolean enemy = false;

		for (val num : array) {
			if (num == NONE)
				return false;

			if (isEnemy(white, num)) {
				enemy = true;
			} else {
				return enemy;
			}
		}

		return false;
	}

	public static boolean isPossible(boolean white, Point pt, int[][] board) {
		if (board[pt.x][pt.y] != 0) {
			return false;
		}

		for (val vec : Vector.values()) {
			if (isPossible(white, nextVectorIs(pt, vec, board)))
				return true;
		}

		return false;
	}

	static List<Point> possiblePoints(boolean white, final int[][] board) {
		val pts = new ArrayList<Point>();

		for (int y = 0; y < 8; ++y) {
			for (int x = 0; x < 8; ++x) {
				val pt = new Point(x, y);
				if (isPossible(white, pt, board))
					pts.add(pt);
			}
		}

		return pts;
	}

	static SecureRandom number;
	static {
		try {
			number = SecureRandom.getInstance("SHA1PRNG");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	private static void randomMove(boolean white, int[][] board) {

		val pts1 = possiblePoints(white, board);
		if (pts1.size() == 0)
			return;

		val pt = pts1.get(number.nextInt(pts1.size()));

		onMove(white, pt, board);
	}

	private static int simulate(boolean enemy, final int[][] board) {

		final int[] won = new int[] { 0 };

		IntStream.range(0, 10000).parallel().forEach(i -> {

			int[][] brain = brain(board);

			boolean cur = enemy;

			while (true) {

				val pts1 = possiblePoints(cur, brain);
				val pts2 = possiblePoints(!cur, brain);

				if (pts1.isEmpty() && pts2.isEmpty()) {
					break;
				}

				randomMove(cur, brain);
				cur = !cur;
			}

			// カウント
			int w = 0, b = 0;
			for (int y = 0; y < 8; ++y) {
				for (int x = 0; x < 8; ++x) {
					if (brain[x][y] == WHITE) {
						w++;
					}
					if (brain[x][y] == BLACK) {
						b++;
					}
				}
			}

			if (enemy) {
				if (w < b)
					won[0]++;
			} else {
				if (b < w)
					won[0]++;
			}
		});

		return won[0];
	}

	static void onMove(boolean white, Point pt, int[][] board) {
		board[pt.x][pt.y] = white ? WHITE : BLACK;

		for (val vec : Vector.values()) {
			if (isPossible(white, nextVectorIs(pt, vec, board))) {
				val n = (Point) pt.clone();
				while (vec.next(n) != null) {
					if (isEnemy(white, board[n.x][n.y])) {
						board[n.x][n.y] = white ? WHITE : BLACK;
					} else
						break;
				}
			}
		}
	}

	private static int[][] brain(final int[][] original) {
		int[][] dst;
		dst = new int[original.length][];
		for (int i = 0; i < original.length; i++) {
			dst[i] = Arrays.copyOf(original[i], original[i].length);
		}
		return dst;
	}

	/**
	 * 
	 * @param white
	 * @param board
	 * @return
	 */
	public static Point move(boolean white, final int[][] board) {

		// Possible points.
		val pts = possiblePoints(white, board);

		if (!pts.isEmpty()) {
			
			// 相手の手が少なくなるように置いてみる
//			val nextPossible = new HashMap<Point, Integer>();
//			
//			pts.forEach(pt-> {
//				int[][] dummy = brain(board);
//				onMove(white, pt, dummy);
//				
//				val nx = possiblePoints(!white, dummy);
//				nextPossible.put(pt, nx.size());
//			});
//			
//			LinkedHashMap<Point, Integer> sorted = nextPossible.entrySet().stream().sorted(
//					Map.Entry.<Point, Integer>comparingByValue())
//					.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
//
//			int first = sorted.values().iterator().next();
//			
//			val npts = new ArrayList<Point>();
//			
//	        for(val s : sorted.entrySet()) {
//	            System.out.printf("Key is %s  value is %d \n", s.getKey().toString(), s.getValue());
//	            if(first == s.getValue()) {
//	            	npts.add(s.getKey());
//	            }
//	        }
	        
			val map = new HashMap<Point, Integer>();

			pts.parallelStream().forEach(pt -> {
				int[][] dummy = brain(board);
				onMove(white, pt, dummy);

				int won = simulate(!white, dummy);
				log("Point(" + pt.x + "," + pt.y + ")=" + won);

				map.put(pt, won);
			});

			val result = map.entrySet().stream().sorted(
						Map.Entry.<Point, Integer>comparingByValue().reversed()).findFirst().get();

			System.out.println("Max=" + result.getValue() + ", Point(" + result.getKey().x + ", " + result.getKey().y + ")");
			return result.getKey();
		}

		return null;
	}

	public static void main(String... args) {
		val board = new int[8][8];
		board[3][3] = board[4][4] = WHITE;
		board[4][3] = board[3][4] = BLACK;

		boolean current = true;

		while (true) {
			val pt = move(current, board);

			if (pt == null) {
				if (possiblePoints(!current, board).size() == 0) {
					log("============= There is no place to put.");
					break;
				}
			}

			log("move... " + (current ? "white" : "black") + "=" + pt.x + ", " + pt.y);

			onMove(current, pt, board);

			for (int y = 0; y < 8; ++y) {
				for (int x = 0; x < 8; ++x) {
					System.out.print("" + board[x][y]);
				}
				System.out.println();
			}

			current = !current;
		}
	}

}
