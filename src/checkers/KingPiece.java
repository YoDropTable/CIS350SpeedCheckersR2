package checkers;

import checkers.CheckersPiece;
import checkers.ICheckersPiece;
import checkers.Move;
import checkers.Player;
import java.math.*;

public class KingPiece extends CheckersPiece {
	/**
	 * Haven't started on this one yet
	 * Just build it like the 'singlePiece' but with the "double" piece traits/rules
	 * this is a child of the CheckersPiece class so remember inheritance fundamentals
	 */
	
	
	protected KingPiece(Player player){
		super(player);
	}
	
	public String type() {
		return "kingPiece";
	}
	
	public boolean isValidMove(Move move, ICheckersPiece[][] board){
		// check base conditions
		if(super.isValidMove(move, board)== true){
			
			// check for a regular move in any direction
			if(Math.abs(move.toRow - move.fromRow) == 1 && Math.abs(move.toColumn - move.fromColumn) == 1){
				return true;
			}
			
			// check for a jumping move
			if(Math.abs(move.toRow - move.fromRow) == 2 && Math.abs(move.toColumn - move.fromColumn) == 2){
				// down-right diagonal jump
				if((move.toRow - move.fromRow) == 2 && (move.toColumn - move.fromColumn) == 2){
					if(board[move.toRow-1][move.toColumn-1] != null && board[move.toRow-1][move.toColumn-1].player() != board[move.fromRow][move.fromColumn].player()){
						return true;
					}
				}
				// down-left diagonal jump
				if((move.toRow - move.fromRow) == 2 && (move.toColumn - move.fromColumn) == -2){
					if(board[move.toRow-1][move.toColumn+1] != null && board[move.toRow-1][move.toColumn+1].player() != board[move.fromRow][move.fromColumn].player()){
						return true;
					}
				}
				// up-right diagonal jump
				if((move.toRow - move.fromRow) == -2 && (move.toColumn - move.fromColumn) == 2){
					if(board[move.toRow+1][move.toColumn-1] != null && board[move.toRow+1][move.toColumn-1].player() != board[move.fromRow][move.fromColumn].player()){
						return true;
					}
				}
				//up-left diagonal jump
				if((move.toRow - move.fromRow) == -2 && (move.toColumn - move.fromColumn) == -2){
					if(board[move.toRow+1][move.toColumn+1] != null && board[move.toRow+1][move.toColumn+1].player() != board[move.fromRow][move.fromColumn].player()){
						return true;
					}
				}
				return false;
			}
		}
		return false;
	}
	
	
	
	
}
