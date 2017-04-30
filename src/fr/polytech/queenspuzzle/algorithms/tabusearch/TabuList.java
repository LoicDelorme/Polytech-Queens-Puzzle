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
	public static final int DEFAULT_INSERTION_OFFSET = 0;

	/**
	 * The size of the tabu list.
	 */
	private final int size;

	/**
	 * The forbidden transformations.
	 */
	private final List<int[]> forbiddenTransformations;

	/**
	 * The locked elements.
	 */
	private final Map<Integer, List<Integer>> lockedElements;

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
		this.lockedElements = new TreeMap<Integer, List<Integer>>();

		initializeLockedElements(nbQueens);
	}

	/**
	 * Initialize the locked elements tree.
	 * 
	 * @param nbQueens
	 *            The number of queens.
	 */
	private void initializeLockedElements(int nbQueens) {
		for (int index = 0; index < nbQueens; index++) {
			this.lockedElements.put(index, new ArrayList<Integer>(nbQueens));
		}
	}

	/**
	 * Check if a transformation is valid according to the current locked elements.
	 * 
	 * @param x
	 *            The X position.
	 * @param y
	 *            The Y position.
	 * @return True if it's a valid transformation, else False.
	 */
	public boolean isValidTransformation(int x, int y) {
		return !(this.lockedElements.get(x).contains(y) && this.lockedElements.get(y).contains(x));
	}

	/**
	 * Add an invalid transformation.
	 * 
	 * @param invalidTransformation
	 *            The invalid transformation.
	 */
	public void addInvalidTransformation(int[] invalidTransformation) {
		final Integer x = Integer.valueOf(invalidTransformation[0]);
		final Integer y = Integer.valueOf(invalidTransformation[1]);

		this.lockedElements.get(x).add(y);
		this.lockedElements.get(y).add(x);

		this.forbiddenTransformations.add(DEFAULT_INSERTION_OFFSET, invalidTransformation);
		if (this.forbiddenTransformations.size() > this.size) {
			removeOldInvalidTransformation();
		}
	}

	/**
	 * Remove the old invalid transformation.
	 */
	private void removeOldInvalidTransformation() {
		final int[] oldInvalidTransformation = this.forbiddenTransformations.get(this.size);

		final Integer x = Integer.valueOf(oldInvalidTransformation[0]);
		final Integer y = Integer.valueOf(oldInvalidTransformation[1]);

		this.lockedElements.get(x).remove(y);
		this.lockedElements.get(y).remove(x);

		this.forbiddenTransformations.remove(this.size);
	}
}