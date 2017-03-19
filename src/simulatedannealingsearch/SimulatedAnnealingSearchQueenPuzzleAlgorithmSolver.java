package simulatedannealingsearch;

import fr.polytech.queenspuzzle.algorithms.Pair;
import fr.polytech.queenspuzzle.algorithms.QueenPuzzleAlgorithmSolver;
import fr.polytech.queenspuzzle.algorithms.tabusearch.TabuList;

/**
 * This class represents a simulated annealing search queen puzzle algorithm solver.
 *
 * @author DELORME Loïc
 * @since 1.0.0
 */
public class SimulatedAnnealingSearchQueenPuzzleAlgorithmSolver extends QueenPuzzleAlgorithmSolver {

	/**
	 * The number of max moves over the dimension.
	 */
	private int nbMaxMoves;

	/**
	 * The number of max temperature changes.
	 */
	private int nbMaxTemperatureChanges;

	/**
	 * Create simulated annealing search queen puzzle algorithm solver.
	 * 
	 * @param nbMaxMoves
	 *            The number of max moves over the dimension.
	 * @param nbMaxTemperatureChanges
	 *            The number of max temperature changes.
	 */
	public SimulatedAnnealingSearchQueenPuzzleAlgorithmSolver(int nbMaxMoves, int nbMaxTemperatureChanges) {
		super();
		this.nbMaxMoves = nbMaxMoves;
		this.nbMaxTemperatureChanges = nbMaxTemperatureChanges;
	}

	@Override
	public Pair<int[], Integer> solve(int[] initialState) {
		int[] x = initialState;
		int fX = fitness(x);

		int t = 0; // ??

		int[] xMin = x;
		int fMin = fX;

		int delta;
		int currentIteration = 0;
		final TabuList tabuList = new TabuList(initialState.length, this.tabuListSize);

		Pair<int[], Pair<Integer, int[]>> generatedBestNeighbor = null;
		int[] bestNeighbor;
		int fBestNeighbor;
		int[] bestNeighborTransformation;

		do {
			// Get the best neighbor.
			generatedBestNeighbor = getBestNeighbor(x, tabuList);

			if (generatedBestNeighbor != null) {
				// Recover neighbor data.
				bestNeighbor = generatedBestNeighbor.getKey();
				fBestNeighbor = generatedBestNeighbor.getValue().getKey().intValue();
				bestNeighborTransformation = generatedBestNeighbor.getValue().getValue();

				// Compute the delta value.
				delta = fBestNeighbor - fX;
				if (delta >= 0) {
					tabuList.addElementaryTransformation(bestNeighborTransformation);
				}

				// Check if it's the best solution we have ever met.
				if (fBestNeighbor < fMin) {
					fMin = fBestNeighbor;
					xMin = bestNeighbor;
				}

				x = bestNeighbor;
				fX = fBestNeighbor;
			}

			currentIteration++;
		} while (currentIteration != this.nbMaxIterations && generatedBestNeighbor != null);

		return new Pair<int[], Integer>(xMin, fMin);
	}

	/**
	 * Generate all neighbors and get the best one according to an initial state.
	 * 
	 * @param initialState
	 *            The initial state.
	 * @return The best neighbor.
	 */
	private Pair<int[], Integer> getBestNeighbor(int[] initialState) {
		int[] bestNeighbor = null;
		int fBestNeighbor = Integer.MAX_VALUE;

		int[] neighbor;
		int fNeighbor;
		int temp;
		for (int x = 0; x < initialState.length; x++) {
			for (int y = x + 1; y < initialState.length; y++) {
				// Duplicate the initial state.
				neighbor = initialState.clone();

				// Apply local transformation (switch two columns).
				temp = neighbor[x];
				neighbor[x] = neighbor[y];
				neighbor[y] = temp;

				// Check if it's the best neighbor.
				fNeighbor = fitness(neighbor);
				if (fNeighbor < fBestNeighbor) {
					bestNeighbor = neighbor;
					fBestNeighbor = fNeighbor;
				}
			}
		}

		return new Pair<int[], Integer>(bestNeighbor, fBestNeighbor);
	}
}