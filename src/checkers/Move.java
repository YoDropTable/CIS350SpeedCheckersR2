package checkers;

public class Move {

	public int fromRow, fromColumn, toRow, toColumn;
	
	// default move constructor, effectively useless
	// I don't know if like an error message or something should go in here 
	// to let us know it's being used
	public Move() {
	}
	
	// every 'move' object consists of a fromRow and a fromCol (first click, board[x][y])
	// and a toRow and a toCol (second click, board[z][a]
	// series of "is valid" checks before move is completed by move method in CheckersModel
	public Move(int fromRow, int fromColumn, int toRow, int toColumn) { // move constructor, parameters for "from" location and "to" location
		this.fromRow = fromRow;
		this.fromColumn = fromColumn;
		this.toRow = toRow;
		this.toColumn = toColumn;
	}

}
