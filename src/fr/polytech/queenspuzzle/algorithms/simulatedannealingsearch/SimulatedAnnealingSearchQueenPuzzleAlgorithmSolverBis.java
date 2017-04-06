package fr.polytech.queenspuzzle.algorithms.simulatedannealingsearch;

import java.security.SecureRandom;

import fr.polytech.queenspuzzle.algorithms.Pair;
import fr.polytech.queenspuzzle.algorithms.QueenPuzzleAlgorithmSolver;

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
	private final static SecureRandom RANDOMIZER = new SecureRandom();

	/**
	 * The temperature.
	 */
	private final double temperature;

	/**
	 * The threshold.
	 */
	private final double threshold;

	/**
	 * The temperature degradation.
	 */
	private final double u;

	/**
	 * Create simulated annealing search queen puzzle algorithm solver.
	 * 
	 * @param temperature
	 *            The temperature.
	 * @param threshold
	 *            The threshold.
	 * @param u
	 *            The temperature degradation.
	 */
	public SimulatedAnnealingSearchQueenPuzzleAlgorithmSolverBis(double temperature, double threshold, double u) {
		super();
		this.temperature = temperature;
		this.threshold = threshold;
		this.u = u;
	}

	@Override
	public Pair<int[], Integer> solve(int[] initialState) {
		int[] x = initialState;
		int fX = fitness(x);

		int[] xMin = x;
		int fMin = fX;

		double temperature = this.temperature;
		int delta;

		Pair<int[], Integer> randomNeighbor = null;
		int[] neighbor;
		int fNeighbor;

		while (temperature > this.threshold) {
			randomNeighbor = getNeighbor(x);
			neighbor = randomNeighbor.getKey();
			fNeighbor = randomNeighbor.getValue().intValue();

			delta = fNeighbor - fX;
			if (delta <= 0) {
				x = neighbor;
				fX = fNeighbor;

				if (fNeighbor < fMin) {
					fMin = fNeighbor;
					xMin = neighbor;

					if (fMin == 0) {
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
		}

		return new Pair<int[], Integer>(xMin, fMin);
	}

	/**
	 * Generate a random neighbor according to an initial state.
	 * 
	 * @param initialState
	 *            The initial state.
	 * @return A neighbor.
	 */
	private Pair<int[], Integer> getNeighbor(int[] initialState) {
		final int x = RANDOMIZER.nextInt(initialState.length);
		int y;
		do {
			y = RANDOMIZER.nextInt(initialState.length);
		} while (x == y);

		// Duplicate the initial state.
		final int[] neighbor = initialState.clone();

		// Apply local transformation (switch two columns).
		int temp = neighbor[x];
		neighbor[x] = neighbor[y];
		neighbor[y] = temp;

		return new Pair<int[], Integer>(neighbor, fitness(neighbor));
	}
}