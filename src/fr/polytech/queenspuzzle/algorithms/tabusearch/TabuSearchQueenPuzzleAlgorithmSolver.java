package fr.polytech.queenspuzzle.algorithms.tabusearch;

import java.util.ArrayList;
import java.util.List;

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
	 * The size of the tabu list.
	 */
	private int size;

	/**
	 * The number of maximal iterations.
	 */
	private int nMax;

	/**
	 * Create a tabu search queen puzzle algorithm solver.
	 * 
	 * @param size
	 *            The size of the tabu list.
	 * @param nMax
	 *            The number of maximal iterations.
	 */
	public TabuSearchQueenPuzzleAlgorithmSolver(int size, int nMax) {
		super();
		this.size = size;
		this.nMax = nMax;
	}

	@Override
	public Pair<int[], Integer> solve(int[] initialState) {
		int[] x = initialState;
		int fX = fitness(x);

		int[] xMin = x;
		int fMin = fX;

		int[] bestNeighbor = null;
		int fBestNeighbor;
		int[] bestNeighborElementaryTransformation = null;

		int fitness = Integer.MAX_VALUE;
		int delta;

		int currentIteration = 0;
		final TabuList tabuList = new TabuList(initialState.length, this.size);
		final List<Pair<int[], int[]>> neighbors = new ArrayList<Pair<int[], int[]>>();

		do {
			// Generate all neighbors without forbidden ones.
			neighbors.clear();
			neighbors.addAll(generateNeighbors(x, tabuList));

			if (!neighbors.isEmpty()) {
				// Choose the best neighbor.
				fBestNeighbor = Integer.MAX_VALUE;
				for (Pair<int[], int[]> neighbor : neighbors) {
					fitness = fitness(neighbor.getValue());
					if (fitness < fBestNeighbor) {
						bestNeighbor = neighbor.getValue();
						fBestNeighbor = fitness;
						bestNeighborElementaryTransformation = neighbor.getKey();
					}
				}

				// Compute the delta.
				delta = fBestNeighbor - fX;
				if (delta >= 0) {
					tabuList.addElementaryTransformation(bestNeighborElementaryTransformation);
				}

				// Check if it is the best solution we have ever met.
				if (fBestNeighbor < fMin) {
					fMin = fBestNeighbor;
					xMin = bestNeighbor;
				}

				x = bestNeighbor;
				fX = fitness;
			}

			currentIteration++;
		} while (currentIteration != this.nMax && !neighbors.isEmpty());

		return new Pair<int[], Integer>(xMin, fMin);
	}

	/**
	 * Generate the neighbors according to an initial state.
	 * 
	 * @param initialState
	 *            The initial state.
	 * @param tabuList
	 *            The tabu list.
	 * @return The list of neighbors excluding invalid neighbors (forbidden elementary transformation).
	 */
	private List<Pair<int[], int[]>> generateNeighbors(int[] initialState, TabuList tabuList) {
		final List<Pair<int[], int[]>> neighbors = new ArrayList<Pair<int[], int[]>>();

		int[] neighbor;
		int temp;
		for (int x = 0; x < initialState.length; x++) {
			for (int y = x + 1; y < initialState.length; y++) {
				if (tabuList.isValidElementaryTransformation(x, y)) {
					neighbor = initialState.clone();

					temp = neighbor[x];
					neighbor[x] = neighbor[y];
					neighbor[y] = temp;

					neighbors.add(new Pair<int[], int[]>(new int[] { x, y }, neighbor));
				}
			}
		}

		return neighbors;
	}
}