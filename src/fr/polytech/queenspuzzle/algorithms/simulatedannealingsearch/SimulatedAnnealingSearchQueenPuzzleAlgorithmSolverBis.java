package fr.polytech.queenspuzzle.algorithms.simulatedannealingsearch;

import java.security.SecureRandom;

import fr.polytech.queenspuzzle.algorithms.QueenPuzzleAlgorithmSolver;
import fr.polytech.queenspuzzle.solutions.AdvancedSolution;
import fr.polytech.queenspuzzle.solutions.Solution;

/**
 * This class represents a simulated annealing search queen puzzle algorithm solver bis.
 *
 * @author DELORME Loïc
 * @since 1.0.0
 */
public class SimulatedAnnealingSearchQueenPuzzleAlgorithmSolverBis extends QueenPuzzleAlgorithmSolver {

	/**
	 * The randomizer.
	 */
	public final static SecureRandom RANDOMIZER = new SecureRandom();

	/**
	 * The initial temperature.
	 */
	private final int initialTemperature;

	/**
	 * The threshold.
	 */
	private final double threshold;

	/**
	 * The temperature degradation.
	 */
	private final double u;

	/**
	 * Create simulated annealing search queen puzzle algorithm solver bis.
	 * 
	 * @param initialTemperature
	 *            The initial temperature.
	 * @param threshold
	 *            The threshold.
	 * @param u
	 *            The temperature degradation.
	 */
	public SimulatedAnnealingSearchQueenPuzzleAlgorithmSolverBis(int initialTemperature, double threshold, double u) {
		super();
		this.initialTemperature = initialTemperature;
		this.threshold = threshold;
		this.u = u;
	}

	@Override
	public AdvancedSolution solve(int[] initialState) {
		int[] x = initialState;
		int fX = fitness(x);

		int[] xMin = x;
		int fMin = fX;

		double temperature = this.initialTemperature;
		int delta;

		Solution randomNeighbor = null;
		int[] neighbor;
		int fNeighbor;

		int currentIteration = 0;
		while (temperature > this.threshold) {
			randomNeighbor = getRandomNeighbor(x);
			neighbor = randomNeighbor.getState();
			fNeighbor = randomNeighbor.getFitness();

			delta = fNeighbor - fX;
			if (delta <= 0) {
				x = neighbor;
				fX = fNeighbor;

				if (fNeighbor < fMin) {
					fMin = fNeighbor;
					xMin = neighbor;

					if (fMin == 0) {
						currentIteration++;
						break;
					}
				}
			} else {
				if (RANDOMIZER.nextDouble() <= Math.exp(-delta / temperature)) {
					x = neighbor;
					fX = fNeighbor;
				}
			}

			temperature *= this.u;
			currentIteration++;
		}

		return new AdvancedSolution(xMin, fMin, currentIteration);
	}

	/**
	 * Generate a random neighbor according to an initial state.
	 * 
	 * @param initialState
	 *            The initial state.
	 * @return A random neighbor.
	 */
	private Solution getRandomNeighbor(int[] initialState) {
		final int x = RANDOMIZER.nextInt(initialState.length);
		final int y = RANDOMIZER.nextInt(initialState.length);

		// Duplicate the initial state
		final int[] randomNeighbor = initialState.clone();

		// Apply local transformation (= switch two columns)
		final int temp = randomNeighbor[x];
		randomNeighbor[x] = randomNeighbor[y];
		randomNeighbor[y] = temp;

		return new Solution(randomNeighbor, fitness(randomNeighbor));
	}
}