package fr.polytech.queenspuzzle;

import fr.polytech.queenspuzzle.solutions.AdvancedSolution;

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
	 * @return An advanced solution which contains for a given state its associated fitness value with the number of iterations to get it.
	 */
	public AdvancedSolution solve();
}