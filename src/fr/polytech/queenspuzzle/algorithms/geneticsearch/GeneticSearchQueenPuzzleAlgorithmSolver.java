package fr.polytech.queenspuzzle.algorithms.geneticsearch;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import fr.polytech.queenspuzzle.algorithms.Pair;
import fr.polytech.queenspuzzle.algorithms.QueenPuzzleAlgorithmSolver;

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
	private final static SecureRandom RANDOMIZER = new SecureRandom();

	/**
	 * The number of best solutions to get during the "best solutions reproduction".
	 */
	private final static int NB_BEST = 80;

	/**
	 * The population size.
	 */
	private final static int POPULATION_SIZE = 300;

	/**
	 * The number of generations.
	 */
	private final int nbGenerations;

	/**
	 * The crossover acceptance probability.
	 */
	private final double crossoverAcceptanceProbability;

	/**
	 * Create a genetic search queen puzzle algorithm solver.
	 * 
	 * @param nbGenerations
	 *            The number of generations.
	 * @param crossoverAcceptanceProbability
	 *            The crossover acceptance probability.
	 */
	public GeneticSearchQueenPuzzleAlgorithmSolver(int nbGenerations, double crossoverAcceptanceProbability) {
		super();
		this.nbGenerations = nbGenerations;
		this.crossoverAcceptanceProbability = crossoverAcceptanceProbability;
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
	public Pair<int[], Integer> solve(int[] initialState) {
		List<int[]> population = generateInitialPopulation(POPULATION_SIZE, initialState.length);

		Pair<int[], Integer> bestSolution = new Pair<int[], Integer>(initialState, fitness(initialState));
		getBestSolution(population, bestSolution);

		List<int[]> rouletteWheelPopulation = null;
		for (int currentGeneration = 0; currentGeneration < this.nbGenerations; currentGeneration++) {
			rouletteWheelPopulation = rouletteWheelReproduction(population);
			population = bestSolutionsReproduction(NB_BEST, population);

			for (int offset = NB_BEST; offset < POPULATION_SIZE; offset++) {
				if (RANDOMIZER.nextDouble() < this.crossoverAcceptanceProbability) {
					population.add(crossover(rouletteWheelPopulation, initialState.length));
				} else {
					population.add(mutation(rouletteWheelPopulation, initialState.length));
				}
			}

			getBestSolution(population, bestSolution);
		}

		return bestSolution;
	}

	/**
	 * Get the best solution of a given population.
	 * 
	 * @param population
	 *            The population.
	 * @param bestSolution
	 *            The current best solution which will be overriden or not.
	 */
	private void getBestSolution(List<int[]> population, Pair<int[], Integer> bestSolution) {
		int[] xMin = bestSolution.getKey();
		int fMin = bestSolution.getValue().intValue();

		int fitness;
		for (int[] state : population) {
			fitness = fitness(state);
			if (fitness < fMin) {
				xMin = state;
				fMin = fitness;
			}
		}

		bestSolution.setKey(xMin);
		bestSolution.setValue(fMin);
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
			for (int offset = 0; offset < nbQueens; offset++) {
				state[offset] = RANDOMIZER.nextInt(nbQueens);
			}

			population.add(state);
		}

		return population;
	}

	/**
	 * Apply the roulette wheel reproduction on a given population.
	 * 
	 * @param population
	 *            The population.
	 * @return The list of selected state.
	 */
	private List<int[]> rouletteWheelReproduction(List<int[]> population) {
		final List<int[]> selectedPopulation = new ArrayList<int[]>();

		int populationSize = population.size();
		double fitnessSum = 0;
		double[] populationFitness = new double[populationSize];
		for (int offset = 0; offset < populationSize; offset++) {
			populationFitness[offset] = fitness(population.get(offset));
			fitnessSum += populationFitness[offset];
		}

		double sum = 0;
		double randomWheelValue;
		for (int offset = 0; offset < populationSize; offset++) {
			randomWheelValue = RANDOMIZER.nextDouble();

			for (int currentState = 0; currentState < populationSize; currentState++) {
				sum += (fitnessSum - populationFitness[currentState]) / fitnessSum;
				if (sum >= randomWheelValue) {
					selectedPopulation.add(population.get(currentState).clone());
					break;
				}
			}
		}

		return selectedPopulation;
	}

	/**
	 * Get the X best solutions into a given population.
	 * 
	 * @param nbBest
	 *            The number of best solutions to get.
	 * @param population
	 *            The population.
	 * @return The X best solutions.
	 */
	private List<int[]> bestSolutionsReproduction(int nbBest, List<int[]> population) {
		return population.stream() //
		        .map(state -> new Pair<int[], Integer>(state, fitness(state))) //
		        .sorted((pairOne, pairTwo) -> pairOne.getValue().intValue() - pairTwo.getValue().intValue()) //
		        .limit(nbBest) //
		        .map(pair -> pair.getKey()) //
		        .collect(Collectors.toList());
	}

	/**
	 * Apply the crossover operation on a given population.
	 * 
	 * @param population
	 *            The population.
	 * @param nbQueens
	 *            The number of queens.
	 * @return The best state.
	 */
	private int[] crossover(List<int[]> population, int nbQueens) {
		int[] xMin = null;
		int fMin = Integer.MAX_VALUE;

		int[] x;
		int fX;

		int crossoverOffset;
		for (int currentPair = 0; currentPair < population.size(); currentPair += 2) {
			crossoverOffset = RANDOMIZER.nextInt(nbQueens);

			// First crossover.
			x = new int[nbQueens];
			System.arraycopy(population.get(currentPair), 0, x, 0, crossoverOffset);
			System.arraycopy(population.get(currentPair + 1), crossoverOffset, x, crossoverOffset, nbQueens - crossoverOffset);
			fX = fitness(x);

			if (fX < fMin) {
				fMin = fX;
				xMin = x;
			}

			// Second crossover.
			x = new int[nbQueens];
			System.arraycopy(population.get(currentPair + 1), 0, x, 0, crossoverOffset);
			System.arraycopy(population.get(currentPair), crossoverOffset, x, crossoverOffset, nbQueens - crossoverOffset);
			fX = fitness(x);

			if (fX < fMin) {
				fMin = fX;
				xMin = x;
			}
		}

		return xMin;
	}

	/**
	 * Apply the mutation operation on a given population.
	 * 
	 * @param population
	 *            The population.
	 * @param nbQueens
	 *            The number of queens.
	 * @return The best state.
	 */
	private int[] mutation(List<int[]> population, int nbQueens) {
		int[] selectedState = population.get(RANDOMIZER.nextInt(population.size())).clone();
		selectedState[RANDOMIZER.nextInt(nbQueens)] = RANDOMIZER.nextInt(nbQueens);

		return selectedState;
	}
}