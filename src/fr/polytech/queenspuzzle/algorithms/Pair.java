package fr.polytech.queenspuzzle.algorithms;

/**
 * This class represents a pair which contains a key and a value.
 *
 * @author DELORME Loïc
 * @param <K>
 *            The key type.
 * @param <V>
 *            The value type.
 * @since 1.0.0
 */
public class Pair<K, V> {

	/**
	 * The key.
	 */
	private K key;

	/**
	 * The value.
	 */
	private V value;

	/**
	 * Create a pair.
	 * 
	 * @param key
	 *            The key.
	 * @param value
	 *            The value.
	 */
	public Pair(K key, V value) {
		this.key = key;
		this.value = value;
	}

	/**
	 * Get the key.
	 * 
	 * @return The key.
	 */
	public K getKey() {
		return this.key;
	}

	/**
	 * Set the key.
	 * 
	 * @param key
	 *            The key.
	 */
	public void setKey(K key) {
		this.key = key;
	}

	/**
	 * Get the value.
	 * 
	 * @return The value.
	 */
	public V getValue() {
		return this.value;
	}

	/**
	 * Set the value.
	 * 
	 * @param value
	 *            The value.
	 */
	public void setValue(V value) {
		this.value = value;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "<" + this.key + ", " + this.value + ">";
	}
}