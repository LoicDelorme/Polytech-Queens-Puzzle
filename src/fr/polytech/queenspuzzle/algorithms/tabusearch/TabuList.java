package fr.polytech.queenspuzzle.algorithms.tabusearch;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * This class represents a tabu list.
 *
 * @author DELORME Loïc
 * @since 1.0.0
 */
public class TabuList {

	/**
	 * The default insertion offset.
	 */
	private static final int DEFAULT_INSERTION_OFFSET = 0;

	/**
	 * The size of the tabu list.
	 */
	private final int size;

	/**
	 * The forbidden transformations.
	 */
	private final List<int[]> forbiddenTransformations;

	/**
	 * The tabu list.
	 */
	private final Map<Integer, List<Integer>> tabuList;

	/**
	 * Create a tabu list.
	 * 
	 * @param nbQueens
	 *            The number of queens.
	 * @param size
	 *            The size of the tabu list.
	 */
	public TabuList(int nbQueens, int size) {
		this.size = size;
		this.forbiddenTransformations = new ArrayList<int[]>(size + 1);
		this.tabuList = new TreeMap<Integer, List<Integer>>();

		initializeTabuList(nbQueens);
	}

	/**
	 * Initialize the tabu list.
	 * 
	 * @param nbQueens
	 *            The number of queens.
	 */
	private void initializeTabuList(int nbQueens) {
		for (int offset = 0; offset < nbQueens; offset++) {
			this.tabuList.put(offset, new ArrayList<Integer>(nbQueens));
		}
	}

	/**
	 * Check if the transformation is valid.
	 * 
	 * @param x
	 *            The X position.
	 * @param y
	 *            The Y position.
	 * @return True if it is a valid elementary transformation, else False.
	 */
	public boolean isValidTransformation(int x, int y) {
		return !(this.tabuList.get(x).contains(y) && this.tabuList.get(y).contains(x));
	}

	/**
	 * Add a new elementary transformation.
	 * 
	 * @param transformation
	 *            The new elementary transformation.
	 */
	public void addElementaryTransformation(int[] transformation) {
		this.tabuList.get(transformation[0]).add(Integer.valueOf(transformation[1]));
		this.tabuList.get(transformation[1]).add(Integer.valueOf(transformation[0]));

		this.forbiddenTransformations.add(DEFAULT_INSERTION_OFFSET, transformation);
		if (this.forbiddenTransformations.size() > this.size) {
			removeOldElementaryTransformations();
		}
	}

	/**
	 * Remove the old elementary transformations.
	 */
	private void removeOldElementaryTransformations() {
		final List<int[]> oldForbiddenTransformations = this.forbiddenTransformations.subList(this.size, this.forbiddenTransformations.size());
		for (int[] oldForbiddenTransformation : oldForbiddenTransformations) {
			this.tabuList.get(oldForbiddenTransformation[0]).remove(Integer.valueOf(oldForbiddenTransformation[1]));
			this.tabuList.get(oldForbiddenTransformation[1]).remove(Integer.valueOf(oldForbiddenTransformation[0]));
		}

		oldForbiddenTransformations.clear();
	}
}