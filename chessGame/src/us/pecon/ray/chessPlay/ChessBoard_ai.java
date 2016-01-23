package us.pecon.ray.chessPlay;

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
	 * TODO: Returns a list of pieces that can capture the selected Piece
	 * @param target   the selected Piece
	 * @return         an ArrayList of Pieces that can capture the Piece
	 */
	public ArrayList<Piece> piecesThatCanCapture(Piece target){
		if(!isOnBoard(target.getXpos(),target.getYpos()) || target == null){
			return null;
		}
		ArrayList<Piece> pieceList = new ArrayList<Piece>();
		// unfinished
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
							System.out.println("" + p + " can move relatively: " + i + ", " + j);
							return true;
						}
					}
				}
			}
		}
		return false;
	}
}
