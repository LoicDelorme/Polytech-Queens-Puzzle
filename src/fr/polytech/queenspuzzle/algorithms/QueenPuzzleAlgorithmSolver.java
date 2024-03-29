package fr.polytech.queenspuzzle.algorithms;

import fr.polytech.queenspuzzle.solutions.AdvancedSolution;

/**
 * This class represents a queen puzzle algorithm solver.
 *
 * @author DELORME Loïc
 * @since 1.0.0
 */
public abstract class QueenPuzzleAlgorithmSolver {

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
		final int nbQueens = queensState.length;
		int fitness = 0;

		int x, y, queen_x, queen_y;
		for (int currentQueen = 0; currentQueen < nbQueens; currentQueen++) {
			x = currentQueen;
			y = queensState[currentQueen];

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

	/**
	 * Solve the queen puzzle problem with an efficient algorithm.
	 * 
	 * @param initialState
	 *            An initial state.
	 * @return An advanced solution which contains for a given state its associated fitness value with the number of iterations to get it.
	 */
	public abstract AdvancedSolution solve(int[] initialState);
}