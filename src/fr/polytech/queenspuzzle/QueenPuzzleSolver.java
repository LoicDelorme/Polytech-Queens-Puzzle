package fr.polytech.queenspuzzle;

import fr.polytech.queenspuzzle.algorithms.Pair;

/**
 * This interface represents a queen puzzle solver.
 *
 * @author DELORME Loïc
 * @since 1.0.0
 */
public interface QueenPuzzleSolver {

	/**
	 * Solve a queen puzzle problem.
	 * 
	 * @return A pair which contains a solution with its corresponding fitness value.
	 */
	public Pair<int[], Integer> solve();
}