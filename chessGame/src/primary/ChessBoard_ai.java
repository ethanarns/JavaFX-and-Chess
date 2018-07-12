package primary;

import java.util.ArrayList;

/**
 * An AI version of ChessBoard.java
 *
 * @author Ethan Arns
 */
public class ChessBoard_ai extends ChessBoard {

	/**
	 * The same as ChessBoard, but with other AI abilities
	 */
	public ChessBoard_ai(){
		super();
	}

	/**
	 * Determines if inputted piece can capture what is at the position x and y
	 * @param attacker   attacking piece
	 * @param x          x coordinate of attacked position
	 * @param y          y coordinate of attacked position
	 * @return           true if attacker can capture a piece at location
	 */
	public boolean canCapture(Piece attacker, int x, int y){
		if(canMove(attacker) == false || !isOnBoard(x, y))
			return false;
		Piece p = getPiece(x, y);
		// Do move check
		if(!moveCheckAssigner(attacker, x - attacker.getXpos(), y - attacker.getYpos())){
			return false;
		}
		if(p.getColor().equalsIgnoreCase("white") && attacker.getColor().equalsIgnoreCase("black")) {
			return true;
		}
		else if(p.getColor().equalsIgnoreCase("black") && attacker.getColor().equalsIgnoreCase("white")) {
			return true;
		}
		else
			return false;
	}

	/**
	 * Returns a list of pieces that can capture the selected Piece
	 * @param target   the selected Piece
	 * @return         an ArrayList of Pieces that can capture the Piece
	 */
	public ArrayList<Piece> piecesThatCanCaptureThis(Piece target){
		if(!isOnBoard(target.getXpos(),target.getYpos()) || target == null)
			return null;
		ArrayList<Piece> pieceList = new ArrayList<Piece>();
		refreshPieceList();
		if(target.getColor().equalsIgnoreCase("black")){
			for(int i = 0; i < whitePieces.size(); i++){
				if(canCapture(whitePieces.get(i), target.getXpos(), target.getYpos())){
					pieceList.add(whitePieces.get(i));
				}
			}
		}
		else if(target.getColor().equalsIgnoreCase("white")){
			for(int i = 0; i < blackPieces.size(); i++){
				if(canCapture(blackPieces.get(i), target.getXpos(), target.getYpos())){
					pieceList.add(blackPieces.get(i));
				}
			}
		}
		else {
			return null;
		}
		return pieceList;
	}

	/**
	 * A checker for whether or not the selected piece can move
	 * @param p   the Piece to check
	 * @return    true if the piece can move, false if not
	 */
	public boolean canMove(Piece p){
		return canMove(p.getXpos(),p.getYpos());
	}

	/**
	 * A checker for whether or not the selected piece can move
	 * @param x   the Piece's x position
	 * @param y   the Piece's y position
	 * @return    true if the piece can move, false if not
	 */
	public boolean canMove(int x, int y){
		if(!isOnBoard(x,y))
			return false;
		Piece p = getPiece(x,y);
		for(int i = -7; i < 8; i++){
			for(int j = -7; j < 8; j++){
				if(!(i == 0 && j == 0) && isOnBoard(x+i,y+j)){
					if(moveCheckAssigner(p, i, j)){
						if(!getPiece(x+i,y+j).getColor().equalsIgnoreCase(p.getColor())){
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	/**
	 * A helper function for retrieving the player of set color's King. Returns
	 * the color's King object.
	 * @param color   player whose king is to be found
	 * @return        king object of player, null if error
	 */
	public King getKing(String color){
		if(color.equalsIgnoreCase("black") && getBlackPieces().size() > 0){
			for(Piece p : blackPieces){
				if(p instanceof King){
					return (King)p;
				}
			}
		}
		if(color.equalsIgnoreCase("white") && getBlackPieces().size() > 0){
			for(Piece p : whitePieces){
				if(p instanceof King){
					return (King)p;
				}
			}
		}
		return null;
	}

	/**
	 * TODO: possibleMoves
	 * Returns a list of positions that the piece at the inputted location
	 * can go to legally
	 * @param x   x position of piece to check
	 * @param y   y position of piece to check
	 * @return    ArrayList of possible position moves, null if error
	 */
	public ArrayList<Position> possibleMoves(int x, int y){
		// Finish me
		return null;
	}

	/**
	 * Checks if the player of set color is in check. Finds their King, and
	 * sees if it is capturable by any pieces of the opposite color. If it can
	 * be, return true.
	 * @param color   color of player in question
	 * @return        true if player is in check, false if not or error
	 */
	public boolean isInCheck(String color){
		refreshPieceList();
		ArrayList<Piece> pieceList;
		if(color.equalsIgnoreCase("black")){
			pieceList = getWhitePieces();
			for(int i = 0; i < pieceList.size(); i++){
				King k = getKing("black");
				if(canCapture(pieceList.get(i),k.getXpos(),k.getYpos()))
					return true;
			}
		}
		else if(color.equalsIgnoreCase("white")){
			pieceList = getBlackPieces();
			for(int i = 0; i < pieceList.size(); i++){
				King k = getKing("white");
				if(canCapture(pieceList.get(i),k.getXpos(),k.getYpos()))
					return true;
			}
		}
		return false;
	}

	/**
	 * TODO: Checks the player of set color for checkmate
	 * @param color   color of player being tested for checkmate
	 * @return        true if player is in checkmate, false if not or error
	 */
	public boolean isCheckmated(String color){
		if(isInCheck(color)){
			// Finish me
		}
		return false;
	}
}
