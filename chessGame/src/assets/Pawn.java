package us.pecon.ray.chessPlay;

public class Pawn extends Piece {
	
	public Pawn(String color, int xPos, int yPos){
		super(color, xPos, yPos);
	}
	
	public Pawn(String color, Position position){
		super(color, position);
	}
	
	public String symbol(){
		return this.color.equalsIgnoreCase("white")? "P" : "p";
	}
	
}
