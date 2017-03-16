package fr.polytech.queenspuzzle.algorithms;

import java.util.ArrayList;
import java.util.List;

import fr.polytech.queenspuzzle.Position2D;

/**
 * This class represents a queen algorithm.
 *
 * @author DELORME Loïc
 * @since 1.0.0
 */
public abstract class QueenAlgorithm {

	/**
	 * The X direction value to add.
	 */
	private static final int[] DX = { -1, 0, 1, -1, 1, -1, 0, 1 };

	/**
	 * The Y direction value to add.
	 */
	private static final int[] DY = { -1, -1, -1, 0, 0, 1, 1, 1 };

	/**
	 * Create a queen algorithm.
	 */
	public QueenAlgorithm() {
	}

	/**
	 * The fitness of the queen puzzle problem.
	 * 
	 * @param queens
	 *            The queens.
	 * @return The fitness value.
	 */
	public double fitness(int[] queens) {
		final List<Position2D> queensPositions = new ArrayList<Position2D>();
		for (int offset = 0; offset < queens.length; offset++) {
			queensPositions.add(new Position2D(offset, queens[offset]));
		}

		int nbConflicts = 0;
		int xTemp;
		int yTemp;
		for (int offset = 0; offset < queens.length; offset++) {
			for (int translation = 0; translation < DX.length; translation++) {
				xTemp = offset + DX[translation];
				yTemp = queens[offset] + DY[translation];

				while (xTemp >= 0 && yTemp >= 0 && xTemp < queens.length && yTemp < queens.length) {
					if (queensPositions.contains(new Position2D(xTemp, yTemp))) {
						nbConflicts++;
					}

					xTemp = xTemp + DX[translation];
					yTemp = yTemp + DY[translation];
				}
			}
		}

		return nbConflicts;
	}

	/**
	 * Perform an algorithm.
	 * 
	 * @param queens
	 *            The queens.
	 * @return A solution.
	 */
	public abstract int[] perform(int[] queens);
}