package fr.polytech.queenspuzzle;

import fr.polytech.queenspuzzle.algorithms.Pair;
import fr.polytech.queenspuzzle.algorithms.QueenPuzzleAlgorithmSolver;

/**
 * This class represents a queen puzzle problem solver.
 *
 * @author DELORME Loïc
 * @since 1.0.0
 */
public abstract class QueenPuzzleProblemSolver implements QueenPuzzleSolver {

	/**
	 * The number of queens.
	 */
	protected final int nbQueens;

	/**
	 * The initial state (offset = column ; value = line).
	 */
	protected final int[] initialState;

	/**
	 * The queen puzzle algorithm solver.
	 */
	protected final QueenPuzzleAlgorithmSolver queenPuzzleAlgorithmSolver;

	/**
	 * Create a queen puzzle problem solver.
	 * 
	 * @param nbQueens
	 *            The number of queens.
	 * @param queenPuzzleAlgorithmSolver
	 *            The queen puzzle algorithm solver.
	 */
	public QueenPuzzleProblemSolver(int nbQueens, QueenPuzzleAlgorithmSolver queenPuzzleAlgorithmSolver) {
		this.nbQueens = nbQueens;
		this.initialState = new int[nbQueens];
		this.queenPuzzleAlgorithmSolver = queenPuzzleAlgorithmSolver;

		initializeQueens();
	}

	/**
	 * Initialize the queens.
	 */
	protected abstract void initializeQueens();

	@Override
	public Pair<int[], Integer> solve() {
		return this.queenPuzzleAlgorithmSolver.solve(this.initialState);
	}
}