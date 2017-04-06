package fr.polytech.queenspuzzle;

import java.util.Arrays;

import fr.polytech.queenspuzzle.algorithms.Pair;
import fr.polytech.queenspuzzle.algorithms.QueenPuzzleAlgorithmSolver;
import fr.polytech.queenspuzzle.algorithms.geneticsearch.GeneticSearchQueenPuzzleAlgorithmSolver;
import fr.polytech.queenspuzzle.algorithms.simulatedannealingsearch.SimulatedAnnealingSearchQueenPuzzleAlgorithmSolver;
import fr.polytech.queenspuzzle.algorithms.simulatedannealingsearch.SimulatedAnnealingSearchQueenPuzzleAlgorithmSolverBis;
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

		final Pair<int[], Integer> result = solveUsingGeneticSearch();
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

		final QueenPuzzleSolver solver = new RandomDiagonalInitialStateQueenPuzzleProblemSolver(nbQueens, algorithmSolver);
		final Pair<int[], Integer> result = solver.solve();

		return result;
	}

	/**
	 * Solve the queen problem using a simulated annealing search algorithm.
	 * 
	 * @return The solution.
	 */
	private static final Pair<int[], Integer> solveUsingSimulatedAnnealingSearch() {
		final int nbQueens = 30;
		final int nbMaxMoves = 2000;
		final double p1 = 0.5;
		final double p2 = 0.000001;
		final double u = 0.999;

		final QueenPuzzleAlgorithmSolver algorithmSolver = new SimulatedAnnealingSearchQueenPuzzleAlgorithmSolver(p1, p2, nbMaxMoves, u);

		final QueenPuzzleSolver solver = new RandomDiagonalInitialStateQueenPuzzleProblemSolver(nbQueens, algorithmSolver);
		final Pair<int[], Integer> result = solver.solve();

		return result;
	}

	/**
	 * Solve the queen problem using a simulated annealing search algorithm bis.
	 * 
	 * @return The solution.
	 */
	private static final Pair<int[], Integer> solveUsingSimulatedAnnealingSearchBis() {
		final int nbQueens = 500;
		final int temperature = 2000;
		final double threshold = 0.000000001;
		final double u = 0.9997;

		final QueenPuzzleAlgorithmSolver algorithmSolver = new SimulatedAnnealingSearchQueenPuzzleAlgorithmSolverBis(temperature, threshold, u);

		final QueenPuzzleSolver solver = new RandomDiagonalInitialStateQueenPuzzleProblemSolver(nbQueens, algorithmSolver);
		final Pair<int[], Integer> result = solver.solve();

		return result;
	}

	/**
	 * Solve the queen problem using a genetic search algorithm.
	 * 
	 * @return The solution.
	 */
	private static final Pair<int[], Integer> solveUsingGeneticSearch() {
		final int nbQueens = 50;
		final double crossoverAcceptanceProbability = 0.1;

		final QueenPuzzleAlgorithmSolver algorithmSolver = new GeneticSearchQueenPuzzleAlgorithmSolver(nbQueens, crossoverAcceptanceProbability);

		final QueenPuzzleSolver solver = new RandomDiagonalInitialStateQueenPuzzleProblemSolver(nbQueens, algorithmSolver);
		final Pair<int[], Integer> result = solver.solve();

		return result;
	}
}