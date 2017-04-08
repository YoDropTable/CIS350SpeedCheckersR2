package checkers;

public interface ICheckersModel {
	
	/**
	 * 
	 * Contains basic game parameters for running the game
	 *
	 */
	
	boolean isComplete();
	
	/**
	 * returns true if the game is complete/over, false otherwise
	 */
	
	//boolean isValidMove(Move move);
	
	/**
	 * 
	 * checks that the proposed move is valid for piece type
	 * return true if move is valid, false otherwise
	 * 
	 * throws IndexOutOfBounds if [move.fromRow][move.fromCol] or [move.toRow][move.toCol] represents a location outside of board
	 */
	
	void move(Move move);
	
	/**
	 * 
	 * moves a piece from location [move.fromRow][move.fromCol] to [move.toRow][move.toCol] if this
	 * move represents a valid move
	 * 
	 * throws IndexOutOfBounds if either location represents somewhere outside of the board
	 */
	
	Player currentPlayer();
	 /**
	  * returns current player either Black or White
	  */
}
