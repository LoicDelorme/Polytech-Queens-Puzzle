package fr.polytech.queenspuzzle.solutions;

import java.util.Arrays;

/**
 * This class represents a solution which contains for a given state its associated fitness value.
 *
 * @author DELORME Loïc
 * @since 1.0.0
 */
public class Solution {

	/**
	 * The state.
	 */
	protected int[] state;

	/**
	 * The fitness value.
	 */
	protected int fitness;

	/**
	 * Create a solution.
	 * 
	 * @param state
	 *            The state.
	 * @param fitness
	 *            The fitness value.
	 */
	public Solution(int[] state, int fitness) {
		this.state = state;
		this.fitness = fitness;
	}

	/**
	 * Get the state.
	 * 
	 * @return The state.
	 */
	public int[] getState() {
		return this.state;
	}

	/**
	 * Set the state.
	 * 
	 * @param state
	 *            The state to set.
	 */
	public void setState(int[] state) {
		this.state = state;
	}

	/**
	 * Get the fitness value.
	 * 
	 * @return The fitness value.
	 */
	public int getFitness() {
		return this.fitness;
	}

	/**
	 * Set the fitness value.
	 * 
	 * @param fitness
	 *            The fitness value to set.
	 */
	public void setFitness(int fitness) {
		this.fitness = fitness;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Solution [state=" + Arrays.toString(this.state) + ", fitness=" + this.fitness + "]";
	}
}