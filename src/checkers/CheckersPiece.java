package checkers;

import checkers.ICheckersPiece;
import java.math.*;
import checkers.Move;
import checkers.Player;

// PARENT CLASS FOR CHECKERS PIECES
public abstract class CheckersPiece implements ICheckersPiece{
		private Player owner;
		
		protected CheckersPiece(Player player) {
			this.owner = player;
		}
	 
		public abstract String type();
		
		/** *************************************************************************************************************** */
		
		public Player player(){
			return owner;
		}
		
		/** *************************************************************************************************************** */
		
		public boolean isValidMove(Move move, ICheckersPiece[][] board){
			// basic/fundamental move conditions (is there an obstruction/ is the "to" square empty)
			// this will be complimented with child-specific isValidMove functions
			// basically checks that the from location isn't null, and that the location they are jumping to IS null. 
			
			/* REGARDLESS of piece type or color, these rules are ALWAYS true, that's why these conditions are in the super class */
			
			if(board[move.fromRow][move.fromColumn] != null){		// making sure moving a valid piece
				if(board[move.toRow][move.toColumn] == null){		// checking that the "to" square is null 
					return true;
				}
				return false;					// otherwise return false
			}
			return false;
		}
	}

