package simulatedannealingsearch;

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
	 * The number of max moves over the dimension.
	 */
	private final int nbMaxMoves;

	/**
	 * The number of max temperature changes.
	 */
	private final int nbMaxTemperatureChanges;

	/**
	 * The temperature degradation.
	 */
	private final double u;

	/**
	 * Create simulated annealing search queen puzzle algorithm solver.
	 * 
	 * @param nbMaxMoves
	 *            The number of max moves over the dimension.
	 * @param nbMaxTemperatureChanges
	 *            The number of max temperature changes.
	 * @param u
	 *            The temperature degradation.
	 */
	public SimulatedAnnealingSearchQueenPuzzleAlgorithmSolver(int nbMaxMoves, int nbMaxTemperatureChanges, double u) {
		super();
		this.nbMaxMoves = nbMaxMoves;
		this.nbMaxTemperatureChanges = nbMaxTemperatureChanges;
		this.u = u;
	}

	private int initialiazeTemperature(int nbNeighbors, int[] initialState) {
		for (int i = 0; i < nbNeighbors; i++) {

		}
	}

	@Override
	public Pair<int[], Integer> solve(int[] initialState) {
		int[] x = initialState;
		int fX = fitness(x);

		int[] xMin = x;
		int fMin = fX;

		double temperature = initialiazeTemperature(nbNeighbors, initialState);
		int delta;

		Pair<int[], Integer> randomNeighbor = null;
		int[] neighbor;
		int fNeighbor;

		for (int currentTemperature = 0; currentTemperature < this.nbMaxTemperatureChanges; currentTemperature++) {
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
					}
				} else {
					if (RANDOMIZER.nextDouble() <= Math.exp(-delta / temperature)) {
						x = neighbor;
						fX = fNeighbor;
					}
				}
			}

			temperature = this.u * temperature;
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