package fr.polytech.queenspuzzle;

import java.util.Arrays;

import fr.polytech.queenspuzzle.algorithms.Pair;
import fr.polytech.queenspuzzle.algorithms.QueenPuzzleAlgorithmSolver;
import fr.polytech.queenspuzzle.algorithms.tabusearch.TabuSearchQueenPuzzleAlgorithmSolver;

/**
 * This class represents the launcher of the application.
 *
 * @author DELORME Loïc
 * @since 1.0.0
 */
public class Launcher {

	/**
	 * The entry of the application.
	 * 
	 * @param args
	 *            Some arguments.
	 */
	public static void main(String[] args) {
		final int nbQueens = 100;
		final long a = System.currentTimeMillis();

		QueenPuzzleAlgorithmSolver algorithmSolver = new TabuSearchQueenPuzzleAlgorithmSolver(10, 500);
		QueenPuzzleSolver solver = new QueenPuzzleProblemSolver(nbQueens, algorithmSolver);
		final Pair<int[], Integer> solve = solver.solve();
		final int[] key = solve.getKey();
		final Integer value = solve.getValue();

		System.out.println("sol: " + Arrays.toString(key));
		System.out.println("value: " + value);

		final long b = System.currentTimeMillis();

		System.out.println((b - a) + " ms");
	}
}