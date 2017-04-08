package checkers;

public enum Player {
	// this is an enum class. flip flops between our two colors, black and red
	BLACK, RED;

	/**
	 * Return the {@code Player} whose turn is next.
	 *
	 * @return the {@code Player} whose turn is next
	 */
	public Player next() { // changes to opposite color 
		return this == BLACK ? RED : BLACK;
	}
}
