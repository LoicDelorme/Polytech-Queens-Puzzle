package fr.polytech.queenspuzzle.algorithms.simulatedannealingsearch;

import java.security.SecureRandom;

import fr.polytech.queenspuzzle.algorithms.QueenPuzzleAlgorithmSolver;
import fr.polytech.queenspuzzle.solutions.AdvancedSolution;
import fr.polytech.queenspuzzle.solutions.Solution;

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
	public final static SecureRandom RANDOMIZER = new SecureRandom();

	/**
	 * The number of states to generate for computing the initial temperature.
	 */
	public static final int NB_STATES_TO_GENERATE = 10;

	/**
	 * The acceptance probability.
	 */
	private final double acceptanceProbability;

	/**
	 * The descent probability.
	 */
	private final double descentProbability;

	/**
	 * The number of max moves per dimension.
	 */
	private final int nbMaxMoves;

	/**
	 * The temperature degradation.
	 */
	private final double u;

	/**
	 * Create simulated annealing search queen puzzle algorithm solver.
	 * 
	 * @param acceptanceProbability
	 *            The acceptance probability.
	 * @param descentProbability
	 *            The descent probability.
	 * @param nbMaxMoves
	 *            The number of max moves per dimension.
	 * @param u
	 *            The temperature degradation.
	 */
	public SimulatedAnnealingSearchQueenPuzzleAlgorithmSolver(double acceptanceProbability, double descentProbability, int nbMaxMoves, double u) {
		super();
		this.acceptanceProbability = acceptanceProbability;
		this.descentProbability = descentProbability;
		this.nbMaxMoves = nbMaxMoves;
		this.u = u;
	}

	@Override
	public AdvancedSolution solve(int[] initialState) {
		final int worseDelta = computeWorseDelta(initialState);
		final int initialTemperature = computeInitialTemperature(worseDelta);
		final int nbMaxTemperature = computeNbMaxTemperature(worseDelta, initialTemperature);

		int[] x = initialState;
		int fX = fitness(x);

		int[] xMin = x;
		int fMin = fX;

		int delta;
		double temperature = initialTemperature;

		Solution randomNeighbor = null;
		int[] neighbor;
		int fNeighbor;

		int currentIteration = 0;
		for (int currentTemperature = 0; currentTemperature < nbMaxTemperature; currentTemperature++) {
			for (int currentMove = 0; currentMove < this.nbMaxMoves; currentMove++) {
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
			}

			temperature *= this.u;
			currentIteration++;
		}

		return new AdvancedSolution(xMin, fMin, currentIteration);
	}

	/**
	 * Compute the worse delta value.
	 * 
	 * @param initialState
	 *            The initial state.
	 * @return The worse delta value.
	 */
	private int computeWorseDelta(int[] initialState) {
		int fWorseState = Integer.MIN_VALUE;
		int fBestState = Integer.MAX_VALUE;

		Solution neighbor = null;
		int fState;
		for (int currentState = 0; currentState < NB_STATES_TO_GENERATE; currentState++) {
			neighbor = getRandomNeighbor(initialState);
			fState = neighbor.getFitness();

			if (fState > fWorseState) {
				fWorseState = fState;
			}

			if (fState < fBestState) {
				fBestState = fState;
			}
		}

		return fWorseState - fBestState;
	}

	/**
	 * Compute the initial temperature.
	 * 
	 * @param delta
	 *            The delta value.
	 * @return The computed initial temperature.
	 */
	private int computeInitialTemperature(int delta) {
		return (int) Math.floor(-delta / Math.log(this.acceptanceProbability));
	}

	/**
	 * Compute the number of maximal temperature.
	 * 
	 * @param delta
	 *            The delta value.
	 * @param initialTemperature
	 *            The initial temperature.
	 * @return The computed number of maximal temperatures changes.
	 */
	private int computeNbMaxTemperature(int delta, int initialTemperature) {
		return (int) Math.floor(Math.log(-delta / (initialTemperature * Math.log(this.descentProbability))) / Math.log(this.u));
	}

	/**
	 * Generate a random neighbor according to an initial state.
	 * 
	 * @param initialState
	 *            The initial state.
	 * @return A neighbor.
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