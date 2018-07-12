package primary;

public class Rook extends Piece {

	public Rook(String color, int xPos, int yPos){
		super(color, xPos, yPos);
	}

	public Rook(String color, Position position){
		super(color, position);
	}

	public String symbol(){
		return this.color.equalsIgnoreCase("white")? "R" : "r";
	}

}
