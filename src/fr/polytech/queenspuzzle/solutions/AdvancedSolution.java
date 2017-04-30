package fr.polytech.queenspuzzle.solutions;

import java.util.Arrays;

/**
 * This class represents an advanced solution which contains for a given state its associated fitness value with the number of iterations to get it.
 *
 * @author DELORME Loïc
 * @since 1.0.0
 */
public class AdvancedSolution extends Solution {

	/**
	 * The number of iterations.
	 */
	private int nbIterations;

	/**
	 * Create an advanced solution.
	 * 
	 * @param state
	 *            The state.
	 * @param fitness
	 *            The fitness value.
	 * @param nbIterations
	 *            The number of iterations.
	 */
	public AdvancedSolution(int[] state, int fitness, int nbIterations) {
		super(state, fitness);
		this.nbIterations = nbIterations;
	}

	/**
	 * Get the number of iterations.
	 * 
	 * @return The number of iterations.
	 */
	public int getNbIterations() {
		return this.nbIterations;
	}

	/**
	 * Set the number of iterations.
	 * 
	 * @param nbIterations
	 *            The number of iterations to set.
	 */
	public void setNbIterations(int nbIterations) {
		this.nbIterations = nbIterations;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AdvancedSolution [nbIterations=" + this.nbIterations + ", state=" + Arrays.toString(this.state) + ", fitness=" + this.fitness + "]";
	}
}