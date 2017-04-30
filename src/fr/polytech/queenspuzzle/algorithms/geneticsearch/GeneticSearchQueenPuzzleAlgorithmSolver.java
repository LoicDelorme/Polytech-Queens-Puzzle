package fr.polytech.queenspuzzle.algorithms.geneticsearch;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import fr.polytech.queenspuzzle.algorithms.QueenPuzzleAlgorithmSolver;
import fr.polytech.queenspuzzle.solutions.AdvancedSolution;
import fr.polytech.queenspuzzle.solutions.Solution;

/**
 * This class represents a genetic search queen puzzle algorithm solver.
 *
 * @author DELORME Loïc
 * @since 1.0.0
 */
public class GeneticSearchQueenPuzzleAlgorithmSolver extends QueenPuzzleAlgorithmSolver {

	/**
	 * The randomizer.
	 */
	public final static SecureRandom RANDOMIZER = new SecureRandom();

	/**
	 * The number of generations.
	 */
	private final int nbGenerations;

	/**
	 * The crossover acceptance probability.
	 */
	private final double crossoverAcceptanceProbability;

	/**
	 * The population size.
	 */
	private final int populationSize;

	/**
	 * The number of best states to get during the reproduction process.
	 */
	private final int nbBestStates;

	/**
	 * Create a genetic search queen puzzle algorithm solver.
	 * 
	 * @param nbGenerations
	 *            The number of generations.
	 * @param crossoverAcceptanceProbability
	 *            The crossover acceptance probability.
	 * @param populationSize
	 *            The population size.
	 * @param nbBestStates
	 *            The number of best states to get during the reproduction process.
	 */
	public GeneticSearchQueenPuzzleAlgorithmSolver(int nbGenerations, double crossoverAcceptanceProbability, int populationSize, int nbBestStates) {
		super();
		this.nbGenerations = nbGenerations;
		this.crossoverAcceptanceProbability = crossoverAcceptanceProbability;
		this.populationSize = populationSize;
		this.nbBestStates = nbBestStates;
	}

	@Override
	public int fitness(int[] queensState) {
		final int nbQueens = queensState.length;
		int fitness = 0;

		int x, y, queen_x, queen_y;
		for (int currentQueen = 0; currentQueen < nbQueens; currentQueen++) {
			x = currentQueen;
			y = queensState[currentQueen];

			// Horizontal right.
			for (queen_x = x + 1, queen_y = y; queen_x < nbQueens; queen_x++) {
				if (queensState[queen_x] == queen_y) {
					fitness++;
				}
			}

			// Horizontal left.
			for (queen_x = x - 1, queen_y = y; queen_x >= 0; queen_x--) {
				if (queensState[queen_x] == queen_y) {
					fitness++;
				}
			}

			// Right up.
			for (queen_x = x + 1, queen_y = y + 1; (queen_y < nbQueens) && (queen_x < nbQueens); queen_y++, queen_x++) {
				if (queensState[queen_x] == queen_y) {
					fitness++;
				}
			}

			// Right down.
			for (queen_x = x + 1, queen_y = y - 1; (queen_y >= 0) && (queen_x < nbQueens); queen_y--, queen_x++) {
				if (queensState[queen_x] == queen_y) {
					fitness++;
				}
			}

			// Left up.
			for (queen_x = x - 1, queen_y = y + 1; (queen_y < nbQueens) && (queen_x >= 0); queen_y++, queen_x--) {
				if (queensState[queen_x] == queen_y) {
					fitness++;
				}
			}

			// Left down.
			for (queen_x = x - 1, queen_y = y - 1; (queen_y >= 0) && (queen_x >= 0); queen_y--, queen_x--) {
				if (queensState[queen_x] == queen_y) {
					fitness++;
				}
			}
		}

		return fitness;
	}

	@Override
	public AdvancedSolution solve(int[] initialState) {
		final Solution bestSolution = new Solution(null, Integer.MAX_VALUE);

		List<int[]> population = generateInitialPopulation(this.populationSize, initialState.length);
		getBestSolution(population, bestSolution);

		List<int[]> rouletteWheelPopulation = null;
		int currentGeneration;
		for (currentGeneration = 0; currentGeneration < this.nbGenerations; currentGeneration++) {
			rouletteWheelPopulation = rouletteWheelReproduction(population);
			population = bestSolutionsReproduction(this.nbBestStates, population);

			for (int index = this.nbBestStates; index < this.populationSize; index++) {
				if (RANDOMIZER.nextDouble() < this.crossoverAcceptanceProbability) {
					population.add(crossover(rouletteWheelPopulation));
				} else {
					population.add(mutation(rouletteWheelPopulation));
				}
			}

			getBestSolution(population, bestSolution);

			if (bestSolution.getFitness() == 0) {
				currentGeneration++;
				break;
			}
		}

		return new AdvancedSolution(bestSolution.getState(), bestSolution.getFitness(), currentGeneration);
	}

	/**
	 * Get the best solution from a given population.
	 * 
	 * @param population
	 *            The population.
	 * @param bestSolution
	 *            The current best solution which will be overriden or not.
	 */
	private void getBestSolution(List<int[]> population, Solution bestSolution) {
		int[] xMin = bestSolution.getState();
		int fMin = bestSolution.getFitness();

		int fitness;
		for (int[] state : population) {
			fitness = fitness(state);
			if (fitness < fMin) {
				xMin = state.clone();
				fMin = fitness;
			}
		}

		bestSolution.setState(xMin);
		bestSolution.setFitness(fMin);
	}

	/**
	 * Generate an initial population.
	 * 
	 * @param populationSize
	 *            The population size.
	 * @param nbQueens
	 *            The number of queens.
	 * @return The generated population.
	 */
	private List<int[]> generateInitialPopulation(int populationSize, int nbQueens) {
		final List<int[]> population = new ArrayList<int[]>(populationSize);

		int[] state = null;
		for (int currentState = 0; currentState < populationSize; currentState++) {
			state = new int[nbQueens];
			for (int index = 0; index < nbQueens; index++) {
				state[index] = RANDOMIZER.nextInt(nbQueens);
			}

			population.add(state);
		}

		return population;
	}

	/**
	 * Apply a roulette wheel reproduction on states from a given population.
	 * 
	 * @param population
	 *            The population.
	 * @return The list of selected states.
	 */
	private List<int[]> rouletteWheelReproduction(List<int[]> population) {
		int fitnessSum = 0;
		final int populationSize = population.size();
		final int[] populationFitness = new int[populationSize];
		for (int index = 0; index < populationSize; index++) {
			populationFitness[index] = fitness(population.get(index));
			fitnessSum += populationFitness[index];
		}

		final List<int[]> selectedStates = new ArrayList<int[]>();

		int sum = 0;
		int randomWheelValue;
		for (int index = 0; index < populationSize; index++) {
			randomWheelValue = RANDOMIZER.nextInt(fitnessSum);

			for (int currentState = 0; currentState < populationSize; currentState++) {
				sum += fitnessSum - populationFitness[currentState];
				if (sum >= randomWheelValue) {
					selectedStates.add(population.get(currentState).clone());
					break;
				}
			}
		}

		return selectedStates;
	}

	/**
	 * Get the X best solutions into a given population.
	 * 
	 * @param nbBestStates
	 *            The number of best solutions to get.
	 * @param population
	 *            The population.
	 * @return The X best solutions.
	 */
	private List<int[]> bestSolutionsReproduction(int nbBestStates, List<int[]> population) {
		return population.stream() //
		        .map(state -> new Solution(state, fitness(state))) //
		        .sorted((solutionOne, solutionTwo) -> solutionOne.getFitness() - solutionTwo.getFitness()) //
		        .limit(nbBestStates) //
		        .map(solution -> solution.getState()) //
		        .collect(Collectors.toList());
	}

	/**
	 * Apply a crossover operation on states from a given population.
	 * 
	 * @param population
	 *            The population.
	 * @return The best state.
	 */
	private int[] crossover(List<int[]> population) {
		final int size = population.get(0).length;

		int[] xMin = null;
		int fMin = Integer.MAX_VALUE;

		final int[] x = new int[size];
		int fX;

		// Crossovers
		final int firstStateIndex = RANDOMIZER.nextInt(population.size());
		final int secondStateIndex = RANDOMIZER.nextInt(population.size());
		final int crossoverIndex = RANDOMIZER.nextInt(size);

		System.arraycopy(population.get(firstStateIndex), 0, x, 0, crossoverIndex);
		System.arraycopy(population.get(secondStateIndex), crossoverIndex, x, crossoverIndex, size - crossoverIndex);

		fX = fitness(x);
		if (fX < fMin) {
			xMin = x.clone();
			fMin = fX;
		}

		System.arraycopy(population.get(secondStateIndex), 0, x, 0, crossoverIndex);
		System.arraycopy(population.get(firstStateIndex), crossoverIndex, x, crossoverIndex, size - crossoverIndex);

		fX = fitness(x);
		if (fX < fMin) {
			xMin = x.clone();
			fMin = fX;
		}

		return xMin;
	}

	/**
	 * Apply a mutation operation on a random state from a given population.
	 * 
	 * @param population
	 *            The population.
	 * @return The new generated state.
	 */
	private int[] mutation(List<int[]> population) {
		final int[] randomState = population.get(RANDOMIZER.nextInt(population.size())).clone();

		final int x = RANDOMIZER.nextInt(randomState.length);
		final int y = RANDOMIZER.nextInt(randomState.length);
		randomState[x] = y;

		return randomState;
	}
}