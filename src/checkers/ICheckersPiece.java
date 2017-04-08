package checkers;

public interface ICheckersPiece {

	
	Player player();
	/**
	 * returns the player that owns this piece
	 * black or white
	 */
	
	String type();
	/**
	 * the type of checker piece
	 * in this case single or double (maybe change to piece or king or something)
	 */
	
	boolean isValidMove(Move move, ICheckersPiece[][] board);
	
	/**
	 * 
	 * 
	 */
	
}
