package checkers;

import javax.swing.JOptionPane;

import checkers.ICheckersPiece;
import checkers.Player;

public class CheckersModel implements ICheckersModel{
	// initialize board and player
	private ICheckersPiece board[][];
	private Player p;
	
	/*
	 * The following elements are concerned with discerning between clicks
	 * 1st click selects the piece to be moved
	 * 2nd click determines the "move to" location
	 * These variables help us tell which click we are doing
	 * See the buttonListener method in panel
	 */
	
	private static final int NULL_VAL = 9;
	private int tempRows = NULL_VAL; 	 // temp variables for moving pieces, 9 will serve as a "null" value
	private int tempCols = NULL_VAL;
	
	// dimensions of board
	private static final int BOARD_ROWS = 8;
	private static final int BOARD_COLS = 8;
	
	// outcome of checkers is determined by capturing all of enemy's pieces
	// as opposed to checkmating system
	// these serve as "counters" down to the winner
	// used in isComplete() method
	// NOT IMPLEMENTED YET
	private int numBlackPieces = 12;
	private int numRedPieces = 12;
	
	public CheckersModel(){
		// initializing board, setting player to black (one player at this point) 
		board = (CheckersPiece[][]) new CheckersPiece[BOARD_ROWS][BOARD_COLS]; 
		p = Player.BLACK;
		
		
		// initializing pieces, setting the cells equal to a piece rather than an  square
		
		// red top pieces 
		// top row
		board[0][0] = null;
		board[0][1] = new SinglePiece(Player.RED); 
		board[0][2] = null;
		board[0][3] = new SinglePiece(Player.RED);
		board[0][4] = null;
		board[0][5] = new SinglePiece(Player.RED);
		board[0][6] = null;
		board[0][7] = new SinglePiece(Player.RED);
		//second row
		board[1][0] = new SinglePiece(Player.RED);
		board[1][1] = null;
		board[1][2] = new SinglePiece(Player.RED);
		board[1][3] = null;
		board[1][4] = new SinglePiece(Player.RED);
		board[1][5] = null;
		board[1][6] = new SinglePiece(Player.RED);
		board[1][7] = null;
		//third row
		board[2][0] = null;
		board[2][1] = new SinglePiece(Player.RED);
		board[2][2] = null;
		board[2][3] = new SinglePiece(Player.RED);
		board[2][4] = null;
		board[2][5] = new SinglePiece(Player.RED);
		board[2][6] = null;
		board[2][7] = new SinglePiece(Player.RED);
		
		
		// black bottom pieces
		// top row
		board[5][0] = new SinglePiece(Player.BLACK);
		board[5][1] = null;
		board[5][2] = new SinglePiece(Player.BLACK);
		board[5][3] = null;
		board[5][4] = new SinglePiece(Player.BLACK);
		board[5][5] = null;
		board[5][6] = new SinglePiece(Player.BLACK);
		board[5][7] = null;
		//second row
		board[6][0] = null;
		board[6][1] = new SinglePiece(Player.BLACK);
		board[6][2] = null;
		board[6][3] = new SinglePiece(Player.BLACK);
		board[6][4] = null;
		board[6][5] = new SinglePiece(Player.BLACK);
		board[6][6] = null;
		board[6][7] = new SinglePiece(Player.BLACK);
		//third row
		board[7][0] = new SinglePiece(Player.BLACK);
		board[7][1] = null;
		board[7][2] = new SinglePiece(Player.BLACK);
		board[7][3] = null;
		board[7][4] = new SinglePiece(Player.BLACK);
		board[7][5] = null;
		board[7][6] = new SinglePiece(Player.BLACK);
		board[7][7] = null;
		
		
	}
	
	
	
	public boolean isComplete() {
		// we will call this at the beginning of each turn
		// basically checks that the current player HAS pieces
		// and that they are able to move any of those pieces
		// because otherwise they have lost
		
		if(numBlackPieces <= 0){
			return true;	
		}
		if(numRedPieces <= 0) {
			return true;
		}
		return false;
	}
	/*
	 * Used to assist above isComplete() method
	 * Method that reduces the piece count by one
	 */
	
	public void deiteratePieces(){
		if(p == Player.BLACK){
			numRedPieces = numRedPieces - 1;
		}
		else {
			numBlackPieces = numBlackPieces -1;
		}
	}
	
	// also can be used for forcing jumps
	public boolean isValidMove(Move move) {
		// this method is passed a move
		// it checks that the FROM piece's super/class conditions are met
		// returns true if all is well
		if(pieceAt(move.fromRow, move.fromColumn).isValidMove(move, board) == true){
			return true;
		}
		return false;
		
	}
	
	public void move(Move move) {
		boolean tookPiece = false;
		// the method that actually makes the move
		// sets the to-position to the from-position
		// and then sets the old position to null
		
		/* 
		 * This portion of code removes the middle piece that was jumped.
		 * No need to check for color or piece type since that is already done in
		 * the isValidMove methods.
		 */
		if((move.toRow - move.fromRow) == 2 && (move.toColumn - move.fromColumn) == 2){
			board[move.toRow-1][move.toColumn-1] = null;
			deiteratePieces();
			tookPiece = true;
		}
		if((move.toRow - move.fromRow) == 2 && (move.toColumn - move.fromColumn) == -2){
			board[move.toRow-1][move.toColumn+1] = null;
			deiteratePieces();
			tookPiece = true;
		}
		if((move.toRow - move.fromRow) == -2 && (move.toColumn - move.fromColumn) == 2){
			board[move.toRow+1][move.toColumn-1] = null;
			deiteratePieces();
			tookPiece = true;
		}
		if((move.toRow - move.fromRow) == -2 && (move.toColumn - move.fromColumn) == -2){
			board[move.toRow+1][move.toColumn+1] = null;
			deiteratePieces();
			tookPiece = true;
		}
		
		/*
		 * Moving the actual piece
		 */
		board[move.toRow][move.toColumn] = board[move.fromRow][move.fromColumn];
		board[move.fromRow][move.fromColumn] = null;
		
		/*
		 * Deiterate the piece count
		 * This helps us determine when the game is over
		 * isComplete() uses these variables in the button listener method
		 */
		
		if(tookPiece == false || hasAnotherJump(move.toRow, move.toColumn) == false){
			p = p.next();
		}
		setTempRows(NULL_VAL);
		setTempCols(NULL_VAL);
		// maybe set a variable?
	}
	
		/*
		 * This method recieves two integers, a "row" and a "column". The method iterates 
		 * through the entire board to see if the pieceAt(r,c) has another valid move it can make.
		 * This method is only triggered if the player has already made a move and
		 * that move took an enemy piece, thereby triggering a "double jump"
		 */
		public boolean hasAnotherJump(int r, int c){
			Move temp;
			for(int i=0; i< BOARD_ROWS; i++){
				for(int j=0; j< BOARD_COLS; j++){
					temp = new Move(r, c, i, j);
					if(Math.abs(i - r) == 2 && Math.abs(j - c) == 2){
						if(isValidMove(temp) == true){
							return true;
						}
					}
				}
			}
			return false;
		}

	
/** *************************************************************************************************************** */
	
	public Player currentPlayer() {	// returns current player
		return p;
		
	}
	
	public Player nextPlayer(){		// returns next player, used only once
		return p.next();
	}
	
	/** *************************************************************************************************************** */
	
	public int getTempRows() {		// getters for temp variables (used in moving pieces
		return tempRows;
	}
	
	public int getTempCols() {
		return tempCols;
	}
	
	/** *************************************************************************************************************** */
	
	public void setTempRows(int r){	// setters for temp variables
		tempRows = r;
	}
	
	public void setTempCols(int c){
		tempCols = c;
	}
	
	/** *************************************************************************************************************** */


	public ICheckersPiece pieceAt(int row, int col) {
		return board[row][col];
	}
	
	public void checkForCrowns(){
		for(int row=0; row < BOARD_ROWS; row++){
			for(int col=0; col < BOARD_COLS; col++){
				if(row == 0){
					if(board[row][col] != null && pieceAt(row, col).player() == Player.BLACK){
						board[row][col] = new KingPiece(Player.BLACK);
					}
				}
				if(row == 7){
					if(board[row][col] != null && pieceAt(row, col).player() == Player.RED){
						board[row][col] = new KingPiece(Player.RED);
					}
				}
			}
		}
	}
	
}

