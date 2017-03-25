package fr.polytech.queenspuzzle;

import java.util.Arrays;

import fr.polytech.queenspuzzle.algorithms.Pair;
import fr.polytech.queenspuzzle.algorithms.QueenPuzzleAlgorithmSolver;
import fr.polytech.queenspuzzle.algorithms.tabusearch.TabuSearchQueenPuzzleAlgorithmSolver;
import simulatedannealingsearch.SimulatedAnnealingSearchQueenPuzzleAlgorithmSolver;

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

		final Pair<int[], Integer> result = solveUsingSimulatedAnnealingSearch();
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

	/**
	 * Solve the queen problem using a tabu search algorithm.
	 * 
	 * @return The solution.
	 */
	private static final Pair<int[], Integer> solveUsingTabuSearch() {
		final int nbQueens = 100;
		final int tabuListSize = 10;
		final int nbMaxIterations = 175;

		final QueenPuzzleAlgorithmSolver algorithmSolver = new TabuSearchQueenPuzzleAlgorithmSolver(tabuListSize, nbMaxIterations);

		final QueenPuzzleSolver solver = new QueenPuzzleProblemSolver(nbQueens, algorithmSolver);
		final Pair<int[], Integer> result = solver.solve();

		return result;
	}

	/**
	 * Solve the queen problem using a simulated annealing algorithm.
	 * 
	 * @return The solution.
	 */
	private static final Pair<int[], Integer> solveUsingSimulatedAnnealingSearch() {
		final int nbQueens = 1000;
		final int nbMaxMoves = 300;
		final int nbMaxTemperatureChanges = 300;
		final int initialTemperature = 6;
		final double u = 0.95;

		final QueenPuzzleAlgorithmSolver algorithmSolver = new SimulatedAnnealingSearchQueenPuzzleAlgorithmSolver(nbMaxMoves, nbMaxTemperatureChanges, initialTemperature, u);

		final QueenPuzzleSolver solver = new QueenPuzzleProblemSolver(nbQueens, algorithmSolver);
		final Pair<int[], Integer> result = solver.solve();

		return result;
	}
}