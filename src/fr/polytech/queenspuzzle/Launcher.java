package fr.polytech.queenspuzzle;

import java.util.Arrays;

import fr.polytech.queenspuzzle.algorithms.QueenPuzzleAlgorithmSolver;
import fr.polytech.queenspuzzle.algorithms.geneticsearch.GeneticSearchQueenPuzzleAlgorithmSolver;
import fr.polytech.queenspuzzle.algorithms.simulatedannealingsearch.SimulatedAnnealingSearchQueenPuzzleAlgorithmSolver;
import fr.polytech.queenspuzzle.algorithms.simulatedannealingsearch.SimulatedAnnealingSearchQueenPuzzleAlgorithmSolverBis;
import fr.polytech.queenspuzzle.algorithms.tabusearch.TabuSearchQueenPuzzleAlgorithmSolver;
import fr.polytech.queenspuzzle.solutions.AdvancedSolution;

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

		final AdvancedSolution result = solveUsingTabuSearch();
		// final AdvancedSolution result = solveUsingSimulatedAnnealingSearch();
		// final AdvancedSolution result = solveUsingSimulatedAnnealingSearchBis();
		// final AdvancedSolution result = solveUsingGeneticSearch();
		System.out.println("Solution: " + Arrays.toString(result.getState()));
		System.out.println("Fitness: " + result.getFitness());
		System.out.println("Nb iterations: " + result.getNbIterations());

		final long stopTime = System.currentTimeMillis();

		long elapsedTime = stopTime - startTime;

		final long hours = elapsedTime / 3600000;
		elapsedTime -= hours * 3600000;

		final long minutes = elapsedTime / 60000;
		elapsedTime -= minutes * 60000;

		final long seconds = elapsedTime / 1000;
		elapsedTime -= seconds * 1000;

		System.out.println(String.format("%dh:%dm:%ds:%dms", hours, minutes, seconds, elapsedTime));
	}

	/**
	 * Solve the queen problem using a tabu search algorithm.
	 * 
	 * @return The solution.
	 */
	private static final AdvancedSolution solveUsingTabuSearch() {
		final int nbQueens = 300;
		final int tabuListSize = 15;
		final int nbMaxIterations = 200;

		final QueenPuzzleAlgorithmSolver algorithm = new TabuSearchQueenPuzzleAlgorithmSolver(tabuListSize, nbMaxIterations);
		final QueenPuzzleSolver solver = new RandomDiagonalInitialStateQueenPuzzleProblemSolver(nbQueens, algorithm);

		return solver.solve();
	}

	/**
	 * Solve the queen problem using a simulated annealing search algorithm.
	 * 
	 * @return The solution.
	 */
	private static final AdvancedSolution solveUsingSimulatedAnnealingSearch() {
		final int nbQueens = 8;
		final int nbMaxMoves = 6;
		final double acceptanceProbability = 0.5;
		final double descentProbability = 0.001;
		final double u = 0.95;

		final QueenPuzzleAlgorithmSolver algorithm = new SimulatedAnnealingSearchQueenPuzzleAlgorithmSolver(acceptanceProbability, descentProbability, nbMaxMoves, u);
		final QueenPuzzleSolver solver = new RandomDiagonalInitialStateQueenPuzzleProblemSolver(nbQueens, algorithm);

		return solver.solve();
	}

	/**
	 * Solve the queen problem using a simulated annealing search algorithm bis.
	 * 
	 * @return The solution.
	 */
	private static final AdvancedSolution solveUsingSimulatedAnnealingSearchBis() {
		final int nbQueens = 8;
		final int temperature = 7;
		final double threshold = 0.000001;
		final double u = 0.9997;

		final QueenPuzzleAlgorithmSolver algorithm = new SimulatedAnnealingSearchQueenPuzzleAlgorithmSolverBis(temperature, threshold, u);
		final QueenPuzzleSolver solver = new RandomDiagonalInitialStateQueenPuzzleProblemSolver(nbQueens, algorithm);

		return solver.solve();
	}

	/**
	 * Solve the queen problem using a genetic search algorithm.
	 * 
	 * @return The solution.
	 */
	private static final AdvancedSolution solveUsingGeneticSearch() {
		final int nbQueens = 100;
		final int nbGenerations = 1000;
		final double crossoverAcceptanceProbability = 0.800;
		final int populationSize = 100;
		final int nbBest = 35;

		final QueenPuzzleAlgorithmSolver algorithm = new GeneticSearchQueenPuzzleAlgorithmSolver(nbGenerations, crossoverAcceptanceProbability, populationSize, nbBest);
		final QueenPuzzleSolver solver = new RandomDiagonalInitialStateQueenPuzzleProblemSolver(nbQueens, algorithm);

		return solver.solve();
	}
}