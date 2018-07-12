package primary;

public class King extends Piece {

	public King(String color, int xPos, int yPos){
		super(color, xPos, yPos);
	}

	public King(String color, Position position){
		super(color, position);
	}

	public String symbol(){
		return this.color.equalsIgnoreCase("white")? "K" : "k";
	}

}
