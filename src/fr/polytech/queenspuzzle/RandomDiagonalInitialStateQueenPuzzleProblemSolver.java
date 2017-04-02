package fr.polytech.queenspuzzle;

import java.security.SecureRandom;

import fr.polytech.queenspuzzle.algorithms.QueenPuzzleAlgorithmSolver;

/**
 * This class represents a random diagonal initial state queen puzzle problem solver.
 *
 * @author DELORME Loïc
 * @since 1.0.0
 */
public class RandomDiagonalInitialStateQueenPuzzleProblemSolver extends QueenPuzzleProblemSolver {

	/**
	 * The randomizer.
	 */
	private final static SecureRandom RANDOMIZER = new SecureRandom();

	/**
	 * Create a random diagonal initial state queen puzzle problem solver.
	 * 
	 * @param nbQueens
	 *            The number of queens.
	 * @param queenPuzzleAlgorithmSolver
	 *            The queen puzzle algorithm solver.
	 */
	public RandomDiagonalInitialStateQueenPuzzleProblemSolver(int nbQueens, QueenPuzzleAlgorithmSolver queenPuzzleAlgorithmSolver) {
		super(nbQueens, queenPuzzleAlgorithmSolver);
	}

	@Override
	protected void initializeQueens(int[] initialState) {
		for (int offset = 0; offset < this.nbQueens; offset++) {
			this.initialState[offset] = offset;
		}

		int x;
		int y;
		int temp;
		for (int currentPermutation = 0; currentPermutation < initialState.length; currentPermutation++) {
			x = RANDOMIZER.nextInt(initialState.length);
			do {
				y = RANDOMIZER.nextInt(initialState.length);
			} while (x == y);

			temp = initialState[x];
			initialState[x] = initialState[y];
			initialState[y] = temp;
		}
	}
}