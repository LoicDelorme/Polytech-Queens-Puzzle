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
		final long startTime = System.currentTimeMillis();

		final int nbQueens = 200;
		final QueenPuzzleAlgorithmSolver algorithmSolver = new TabuSearchQueenPuzzleAlgorithmSolver(10, 175);

		final QueenPuzzleSolver solver = new QueenPuzzleProblemSolver(nbQueens, algorithmSolver);
		final Pair<int[], Integer> result = solver.solve();

		final int[] solution = result.getKey();
		final int fitness = result.getValue().intValue();

		System.out.println("solution: " + Arrays.toString(solution));
		System.out.println("fitness: " + fitness);

		final long stopTime = System.currentTimeMillis();

		final long elapsedTime = stopTime - startTime;
		System.out.println(elapsedTime + " ms");
		System.out.println((elapsedTime / 1000) + " s");
		System.out.println(((elapsedTime / 1000) / 60) + " m");
		System.out.println((((elapsedTime / 1000) / 60) / 60) + " h");
	}
}