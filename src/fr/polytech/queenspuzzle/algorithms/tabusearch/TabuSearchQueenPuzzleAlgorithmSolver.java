package fr.polytech.queenspuzzle.algorithms.tabusearch;

import fr.polytech.queenspuzzle.algorithms.Pair;
import fr.polytech.queenspuzzle.algorithms.QueenPuzzleAlgorithmSolver;

/**
 * This class represents a tabu search queen puzzle algorithm solver.
 *
 * @author DELORME Loïc
 * @since 1.0.0
 */
public class TabuSearchQueenPuzzleAlgorithmSolver extends QueenPuzzleAlgorithmSolver {

	/**
	 * The tabu list size.
	 */
	private int tabuListSize;

	/**
	 * The number of maximal iterations.
	 */
	private int nbMaxIterations;

	/**
	 * Create a tabu search queen puzzle algorithm solver.
	 * 
	 * @param tabuListSize
	 *            The tabu list size.
	 * @param nbMaxIterations
	 *            The number of maximal iterations.
	 */
	public TabuSearchQueenPuzzleAlgorithmSolver(int tabuListSize, int nbMaxIterations) {
		super();
		this.tabuListSize = tabuListSize;
		this.nbMaxIterations = nbMaxIterations;
	}

	@Override
	public Pair<int[], Integer> solve(int[] initialState) {
		int[] x = initialState;
		int fX = fitness(x);

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
	 * @param tabuList
	 *            The tabu list.
	 * @return The best neighbor.
	 */
	private Pair<int[], Pair<Integer, int[]>> getBestNeighbor(int[] initialState, TabuList tabuList) {
		int[] bestNeighbor = null;
		int fBestNeighbor = Integer.MAX_VALUE;
		int[] bestNeighborTransformation = null;

		int[] neighbor;
		int fNeighbor;
		int temp;
		for (int x = 0; x < initialState.length; x++) {
			for (int y = x + 1; y < initialState.length; y++) {
				if (tabuList.isValidTransformation(x, y)) {
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
						bestNeighborTransformation = new int[] { x, y };
					}
				}
			}
		}

		if (bestNeighbor == null) {
			return null;
		}

		return new Pair<int[], Pair<Integer, int[]>>(bestNeighbor, new Pair<Integer, int[]>(fBestNeighbor, bestNeighborTransformation));
	}
}