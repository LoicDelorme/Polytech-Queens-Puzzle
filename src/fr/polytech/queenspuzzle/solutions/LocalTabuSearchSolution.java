package fr.polytech.queenspuzzle.solutions;

import java.util.Arrays;

/**
 * This class represents a local tabu search solution which contains for a given state its associated fitness value and transformation.
 *
 * @author DELORME Loïc
 * @since 1.0.0
 */
public class LocalTabuSearchSolution extends Solution {

	/**
	 * The transformation.
	 */
	private int[] transformation;

	/**
	 * Create a local tabu search solution.
	 * 
	 * @param state
	 *            The state.
	 * @param fitness
	 *            The fitness value.
	 * @param transformation
	 *            The transformation.
	 */
	public LocalTabuSearchSolution(int[] state, int fitness, int[] transformation) {
		super(state, fitness);
		this.transformation = transformation;
	}

	/**
	 * Get the transformation.
	 * 
	 * @return The transformation.
	 */
	public int[] getTransformation() {
		return this.transformation;
	}

	/**
	 * Set the transformation.
	 * 
	 * @param transformation
	 *            The transformation to set.
	 */
	public void setTransformation(int[] transformation) {
		this.transformation = transformation;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "LocalTabuSearchSolution [transformation=" + Arrays.toString(this.transformation) + ", state=" + Arrays.toString(this.state) + ", fitness=" + this.fitness + "]";
	}
}