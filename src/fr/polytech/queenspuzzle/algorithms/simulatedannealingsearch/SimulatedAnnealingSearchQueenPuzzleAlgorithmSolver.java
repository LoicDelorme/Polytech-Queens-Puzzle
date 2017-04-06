package fr.polytech.queenspuzzle.algorithms.simulatedannealingsearch;

import java.security.SecureRandom;

import fr.polytech.queenspuzzle.algorithms.Pair;
import fr.polytech.queenspuzzle.algorithms.QueenPuzzleAlgorithmSolver;

/**
 * This class represents a simulated annealing search queen puzzle algorithm solver.
 *
 * @author DELORME Loïc
 * @since 1.0.0
 */
public class SimulatedAnnealingSearchQueenPuzzleAlgorithmSolver extends QueenPuzzleAlgorithmSolver {

	/**
	 * The randomizer.
	 */
	private final static SecureRandom RANDOMIZER = new SecureRandom();

	/**
	 * The number of states to generate for computing the initial temperature.
	 */
	private final static int NB_STATES_TO_GENERATE = 4;

	/**
	 * The number of generations by state.
	 */
	private final static int NB_GENERATIONS_BY_STATE = 2;

	/**
	 * The probability.
	 */
	private final double probability;

	/**
	 * The acceptance percentage.
	 */
	private final double acceptancePercentage;

	/**
	 * The number of max moves over the dimension.
	 */
	private final int nbMaxMoves;

	/**
	 * The temperature degradation.
	 */
	private final double u;

	/**
	 * Create simulated annealing search queen puzzle algorithm solver.
	 * 
	 * @param probability
	 *            The probability.
	 * @param acceptancePercentage
	 *            The acceptance percentage.
	 * @param nbMaxMoves
	 *            The number of max moves over the dimension.
	 * @param u
	 *            The temperature degradation.
	 */
	public SimulatedAnnealingSearchQueenPuzzleAlgorithmSolver(double probability, double acceptancePercentage, int nbMaxMoves, double u) {
		super();
		this.probability = probability;
		this.acceptancePercentage = acceptancePercentage;
		this.nbMaxMoves = nbMaxMoves;
		this.u = u;
	}

	@Override
	public Pair<int[], Integer> solve(int[] initialState) {
		int[] x = initialState;
		int fX = fitness(x);

		int[] xMin = x;
		int fMin = fX;

		double temperature = computeInitialTemperature(initialState);
		int nbMaxTemperatureChanges = computeNbMaxTemperatureChanges();
		int delta;

		Pair<int[], Integer> randomNeighbor = null;
		int[] neighbor;
		int fNeighbor;

		for (int currentTemperature = 0; currentTemperature < nbMaxTemperatureChanges; currentTemperature++) {
			for (int currentMove = 0; currentMove < this.nbMaxMoves; currentMove++) {
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
			}

			temperature *= this.u;
		}

		return new Pair<int[], Integer>(xMin, fMin);
	}

	/**
	 * Compute the initial temperature.
	 * 
	 * @param initialState
	 *            The initial state.
	 * 
	 * @return The computed initial temperature.
	 */
	private double computeInitialTemperature(int[] initialState) {
		int fWorseState = Integer.MIN_VALUE;
		int fBestState = Integer.MAX_VALUE;

		int[] state = null;
		int fState;
		Pair<int[], Integer> result = null;

		for (int currentGeneratedState = 0; currentGeneratedState < NB_STATES_TO_GENERATE; currentGeneratedState++) {
			state = initialState;
			for (int currentGeneration = 0; currentGeneration < NB_GENERATIONS_BY_STATE; currentGeneration++) {
				result = getNeighbor(state);
				state = result.getKey();
			}

			fState = result.getValue().intValue();

			if (fState > fWorseState) {
				fWorseState = fState;
			}

			if (fState < fBestState) {
				fBestState = fState;
			}
		}

		return (-(fWorseState - fBestState)) / Math.log(this.probability);
	}

	/**
	 * Compute the number of maximal temperatures changes.
	 * 
	 * @return The computed number of maximal temperatures changes.
	 */
	private int computeNbMaxTemperatureChanges() {
		return (int) Math.floor(Math.log(Math.log(this.probability) / Math.log(this.acceptancePercentage)) / Math.log(this.u));
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