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
	
	public ArrayList<Piece> capturableBy(){
		ArrayList<Piece> pieceList = new ArrayList<Piece>();
		// do me
		return pieceList;
	}
	
	public boolean canMove(Piece p){
		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++){
				if(moveCheckAssigner(p, i, j)){
					return true;// check me
				}
			}
		}
		return false;
	}
}
