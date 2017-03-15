package fr.polytech.queenspuzzle;

/**
 * This class represents a position in 2 dimensions.
 *
 * @author DELORME Loïc
 * @since 1.0.0
 */
public class Position2D implements Comparable<Position2D> {

	/**
	 * The X position.
	 */
	private final int x;

	/**
	 * The Y position.
	 */
	private final int y;

	/**
	 * Create a position in 2 dimensions.
	 * 
	 * @param x
	 *            The X position.
	 * @param y
	 *            The Y position.
	 */
	public Position2D(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Get the X position.
	 * 
	 * @return The X position.
	 */
	public int getX() {
		return this.x;
	}

	/**
	 * Get the Y position.
	 * 
	 * @return The Y position.
	 */
	public int getY() {
		return this.y;
	}

	@Override
	public int compareTo(Position2D position2D) {
		int result = this.x - position2D.x;
		if (result == 0) {
			result += this.y - position2D.y;
		}

		return result;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "(" + this.x + ", " + this.y + ")";
	}
}