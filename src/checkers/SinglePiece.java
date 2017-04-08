package checkers;

import checkers.CheckersPiece;
import checkers.ICheckersPiece;
import checkers.Move;
import checkers.Player;

/* 
 * This class is the definition of a singlePiece
 * It contains the specific move conditions for the piece type
 * as well as a string that returns it's name
 * and a relation to it's super class "CheckersPiece"
 */
public class SinglePiece extends CheckersPiece {
	protected SinglePiece(Player player) {
		super(player);
		// TODO Auto-generated constructor stub
	}
	
	public String type(){
		return "singlePiece";
	}
	
	/* In this method we will separate RED PIECE move conditions with 
	 * BLACK PIECE move conditions because of the way they traverse the 2D array. 
	 * i.e. RED pieces move down the board (increasing rows) and BLACK pieces move up the board (decreasing rows)
	 */
	public boolean isValidMove(Move move, ICheckersPiece[][] board){
		// first check that the super conditions are met
		if(super.isValidMove(move, board) == true){
			
			/**********************************************/
			// first we are checking conditions for red pieces
			if(super.player() == Player.RED){
				
				/* 
				* if moving down the board 1 space and left/right one space
				* a normal diagonal move
				*/
				if((move.toRow - move.fromRow) == 1 && Math.abs(move.toColumn - move.fromColumn) == 1){ // if the row value changes by +1 and the column value changes by +/- 1
					// we already know from super.isvalidmove that the move to space is empty
					// therefore this is a valid move
					return true; 	
				}
				
				/* 
				 * if jumping another piece
				 */
				
				// checking that they are moving down-diagonal-left 2 spaces
				if((move.toRow - move.fromRow) == 2 && (move.toColumn - move.fromColumn) == -2){	// if the row value moves +2 and the column value moves -2
					
					// if there is a piece being jumped over, and that piece is the opposite color of this one
					if(board[move.toRow-1][move.toColumn + 1] != null && board[move.toRow-1][move.toColumn + 1].player() == Player.BLACK){ 
						return true;
					}
				}
				
				// checking that piece is moving down-diagonal-right 2 spaces
				if(move.toRow - move.fromRow == 2 && (move.toColumn - move.fromColumn) == 2){
					// if there is a piece being jumped over, and that piece is the opposite color of this one.
					if(board[move.toRow-1][move.toColumn-1] != null && board[move.toRow-1][move.toColumn-1].player() == Player.BLACK){
						return true;
					}
				}
				return false;	
			}
			
			
			
			
			
			
			
			
			
			/***********************************************************/
			/*
			 * Same deal as above, but in case of black checker pieces
			 */
			if(super.player() == Player.BLACK){
				
				/* 
				 * if moving up the board one space and left/right one space
				 * normal move
				 */
				if((move.toRow - move.fromRow) == -1 && Math.abs(move.toColumn - move.fromColumn) == 1){
					return true;
				}
				
				/*
				 * if jumping another piece
				 */
				
				// checking that they re moving up-diagonal-left 2 spaces
				if((move.toRow - move.fromRow) == -2 && (move.toColumn - move.fromColumn) == -2){
					// if there is a piece being jumped over, and that piece is opposite color of this one
					if(board[move.toRow+1][move.toColumn+1] != null && board[move.toRow+1][move.toColumn+1].player() == Player.RED){
						return true;
					}
				}
				
				// checking that they're moving up-diagonal-right
				if((move.toRow - move.fromRow) == -2 && (move.toColumn - move.fromColumn) == 2){
					// if there is a piece being jumped over, and that piece is opposite color of this one
					if(board[move.toRow+1][move.toColumn-1] != null && board[move.toRow+1][move.toColumn-1].player() == Player.RED){
						return true;
					}
				}
			}
			return false;
		}
		return false;
	}
}