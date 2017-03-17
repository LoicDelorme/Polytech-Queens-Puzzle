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
	 * The list of forbidden elementary transformations.
	 */
	private final List<int[]> elementaryTransformations;

	/**
	 * The tabu list.
	 */
	private final Map<Integer, List<Integer>> tabuList;

	/**
	 * Create a tabu list.
	 * 
	 * @param domainSize
	 *            The domain size.
	 * @param size
	 *            The size of the tabu list.
	 */
	public TabuList(int domainSize, int size) {
		this.size = size;
		this.elementaryTransformations = new ArrayList<int[]>(size + 1);
		this.tabuList = new TreeMap<Integer, List<Integer>>();

		initializeTabuList(domainSize);
	}

	/**
	 * Initialize the tabu list.
	 * 
	 * @param domainSize
	 *            The domain size.
	 */
	private void initializeTabuList(int domainSize) {
		for (int offset = 0; offset < domainSize; offset++) {
			this.tabuList.put(offset, new ArrayList<Integer>(domainSize));
		}
	}

	/**
	 * Check if the elementary transformation is valid.
	 * 
	 * @param x
	 *            The X position.
	 * @param y
	 *            The Y position.
	 * @return True if it is a valid elementary transformation, else False.
	 */
	public boolean isValidElementaryTransformation(int x, int y) {
		return !(this.tabuList.get(x).contains(y) && this.tabuList.get(y).contains(x));
	}

	/**
	 * Add a new elementary transformation.
	 * 
	 * @param transformation
	 *            The new elementary transformation.
	 */
	public void addElementaryTransformation(int[] transformation) {
		this.elementaryTransformations.add(DEFAULT_INSERTION_OFFSET, transformation);
		this.tabuList.get(transformation[0]).add(Integer.valueOf(transformation[1]));
		this.tabuList.get(transformation[1]).add(Integer.valueOf(transformation[0]));

		if (this.elementaryTransformations.size() > this.size) {
			removeOldElementaryTransformations();
		}
	}

	/**
	 * Remove the old elementary transformations.
	 */
	private void removeOldElementaryTransformations() {
		final List<int[]> oldTransformations = this.elementaryTransformations.subList(this.size, this.elementaryTransformations.size());
		for (int[] oldTransformation : oldTransformations) {
			this.tabuList.get(oldTransformation[0]).remove(Integer.valueOf(oldTransformation[1]));
			this.tabuList.get(oldTransformation[1]).remove(Integer.valueOf(oldTransformation[0]));
		}

		oldTransformations.clear();
	}
}