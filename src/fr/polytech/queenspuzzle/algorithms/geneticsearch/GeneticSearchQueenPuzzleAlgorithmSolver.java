package fr.polytech.queenspuzzle.algorithms.geneticsearch;

import java.security.SecureRandom;

import fr.polytech.queenspuzzle.algorithms.Pair;
import fr.polytech.queenspuzzle.algorithms.QueenPuzzleAlgorithmSolver;

/**
 * This class represents a genetic search queen puzzle algorithm solver.
 *
 * @author DELORME Loïc
 * @since 1.0.0
 */
public class GeneticSearchQueenPuzzleAlgorithmSolver extends QueenPuzzleAlgorithmSolver {

	/**
	 * The randomizer.
	 */
	private final static SecureRandom RANDOMIZER = new SecureRandom();

	/**
	 * The number of generations.
	 */
	private final int nbGenerations;

	/**
	 * The crossover acceptance probability.
	 */
	private final double crossoverAcceptanceProbability;

	/**
	 * Create a genetic search queen puzzle algorithm solver.
	 * 
	 * @param nbGenerations
	 *            The number of generations.
	 * @param crossoverAcceptanceProbability
	 *            The crossover acceptance probability.
	 */
	public GeneticSearchQueenPuzzleAlgorithmSolver(int nbGenerations, double crossoverAcceptanceProbability) {
		super();
		this.nbGenerations = nbGenerations;
		this.crossoverAcceptanceProbability = crossoverAcceptanceProbability;
	}

	@Override
	public Pair<int[], Integer> solve(int[] initialState) {
		return null;
	}
}