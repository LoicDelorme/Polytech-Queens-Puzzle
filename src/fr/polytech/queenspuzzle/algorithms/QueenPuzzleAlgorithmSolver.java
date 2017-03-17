package fr.polytech.queenspuzzle.algorithms;

import fr.polytech.queenspuzzle.QueenPuzzleSolver;

/**
 * This class represents a queen puzzle algorithm solver.
 *
 * @author DELORME Loïc
 * @since 1.0.0
 */
public abstract class QueenPuzzleAlgorithmSolver implements QueenPuzzleSolver {

	/**
	 * Create a queen puzzle algorithm solver.
	 */
	public QueenPuzzleAlgorithmSolver() {
	}

	/**
	 * Compute the fitness value according to a queens state.
	 * 
	 * @param queensState
	 *            The queens state.
	 * @return The computed fitness value.
	 */
	public int fitness(int[] queensState) {
		int fitness = 0;

		final int nbQueens = queensState.length;
		int x, y, queen_x, queen_y;
		for (int currentQueen = 0; currentQueen < nbQueens; currentQueen++) {
			x = currentQueen;
			y = queensState[currentQueen];

			// Horizontal right.
			for (queen_x = x + 1, queen_y = y; queen_x < nbQueens; queen_x++) {
				if (queensState[queen_x] == queen_y) {
					fitness++;
				}
			}

			// Horizontal left.
			for (queen_x = x - 1, queen_y = y; queen_x >= 0; queen_x--) {
				if (queensState[queen_x] == queen_y) {
					fitness++;
				}
			}

			// Right up.
			for (queen_x = x + 1, queen_y = y + 1; (queen_y < nbQueens) && (queen_x < nbQueens); queen_y++, queen_x++) {
				if (queensState[queen_x] == queen_y) {
					fitness++;
				}
			}

			// Right down.
			for (queen_x = x + 1, queen_y = y - 1; (queen_y >= 0) && (queen_x < nbQueens); queen_y--, queen_x++) {
				if (queensState[queen_x] == queen_y) {
					fitness++;
				}
			}

			// Left up.
			for (queen_x = x - 1, queen_y = y + 1; (queen_y < nbQueens) && (queen_x >= 0); queen_y++, queen_x--) {
				if (queensState[queen_x] == queen_y) {
					fitness++;
				}
			}

			// Left down.
			for (queen_x = x - 1, queen_y = y - 1; (queen_y >= 0) && (queen_x >= 0); queen_y--, queen_x--) {
				if (queensState[queen_x] == queen_y) {
					fitness++;
				}
			}
		}

		return fitness;
	}
}