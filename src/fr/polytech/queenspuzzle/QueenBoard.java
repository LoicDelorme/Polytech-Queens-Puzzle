package fr.polytech.queenspuzzle;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * This class represents a queen board.
 *
 * @author DELORME Loïc
 * @since 1.0.0
 */
public class QueenBoard {

	/**
	 * The X direction value to add.
	 */
	private static final int[] DX = { -1, 0, 1, -1, 1, -1, 0, 1 };

	/**
	 * The Y direction value to add.
	 */
	private static final int[] DY = { -1, -1, -1, 0, 0, 1, 1, 1 };

	/**
	 * The number of queen.
	 */
	private final int nbQueens;

	/**
	 * The queen board.
	 */
	private final Map<Position2D, List<Position2D>> queenBoard;

	/**
	 * The positions.
	 */
	private final List<Position2D> positions;

	/**
	 * Create a queen board.
	 * 
	 * @param nbQueens
	 *            The number of queens.
	 */
	public QueenBoard(int nbQueens) {
		this.nbQueens = nbQueens;
		this.queenBoard = new TreeMap<Position2D, List<Position2D>>();
		this.positions = new ArrayList<Position2D>();

		initializePositions();
		initializeQueenBoard();
	}

	/**
	 * Initialize the positions.
	 */
	private void initializePositions() {
		for (int x = 0; x < this.nbQueens; x++) {
			for (int y = 0; y < this.nbQueens; y++) {
				this.positions.add(new Position2D(x, y));
			}
		}
	}

	/**
	 * Initialize the queen board.
	 */
	private void initializeQueenBoard() {
		this.positions.stream().forEach(position -> this.queenBoard.put(position, getLockedPositions(position)));
	}

	/**
	 * Get all locked positions from an initial position.
	 * 
	 * @param position
	 *            The initial position.
	 * @return The locked positions.
	 */
	private List<Position2D> getLockedPositions(Position2D position) {
		final List<Position2D> positions = new ArrayList<Position2D>();

		int xTemp;
		int yTemp;
		for (int translation = 0; translation < DX.length; translation++) {
			xTemp = position.getX() + DX[translation];
			yTemp = position.getY() + DY[translation];

			while (xTemp >= 0 && yTemp >= 0 && xTemp < this.nbQueens && yTemp < this.nbQueens) {
				positions.add(this.positions.get(xTemp + this.nbQueens * yTemp));

				xTemp = xTemp + DX[translation];
				yTemp = yTemp + DY[translation];
			}
		}

		return positions;
	}
}