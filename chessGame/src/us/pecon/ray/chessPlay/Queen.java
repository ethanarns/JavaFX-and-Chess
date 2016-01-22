package us.pecon.ray.chessPlay;

public class Queen extends Piece {
	
	public Queen(String color, int xPos, int yPos){
		super(color, xPos, yPos);
	}
	
	public Queen(String color, Position position){
		super(color, position);
	}
	
	public String symbol(){
		return this.color.equalsIgnoreCase("white")? "Q" : "q";
	}
}
