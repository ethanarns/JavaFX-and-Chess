package primary.assets;

public class Blank extends Piece {

	public Blank(String color, int xPos, int yPos){
		super(color, xPos, yPos);
		color = "Blank";
	}

	public Blank(String color, Position position){
		super(color, position);
		color = "Blank";
	}

	public String symbol(){
		return "_";
	}

	public String toString(){
		return "Blank space at (" + position.getXpos() + ", " + position.getYpos() + ")";
	}
}
