package us.pecon.ray.chessPlay;

public class Knight extends Piece {
	
	public Knight(String color, int xPos, int yPos){
		super(color, xPos, yPos);
	}
	
	public Knight(String color, Position position){
		super(color, position);
	}
	
	public String symbol(){
		return this.color.equalsIgnoreCase("white")? "N" : "n";
	}

}
