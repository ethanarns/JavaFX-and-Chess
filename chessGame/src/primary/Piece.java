package primary;

/**
 * The master class for chess pieces to be added to the board class. It cannot
 * be constructed, but is merely a reference for all the subclass methods and
 * properties
 *
 * @author Ethan Arns
 */
public abstract class Piece {

	protected String color;
	protected Position position;

	/**
	 * Constructor by coordinates
	 *
	 * @param color   color of piece
	 * @param xPos    x coordinate
	 * @param yPos    y coordinate
	 */
	public Piece(String color, int xPos, int yPos){
		this.color = color;
		this.position = new Position(xPos, yPos);
	}

	/**
	 * Constructor by position class
	 *
	 * @param color      color of piece
	 * @param position   position object to set initial position
	 */
	public Piece(String color, Position position){
		this.color = color;
		this.position = position;
	}

	/**
	 * Returns color as a String
	 *
	 * @return   Typically "White", "Black", or "Blank"
	 */
	public String getColor(){
		return color;
	}

	/**
	 * Returns the position as a Position object
	 *
	 * @return   position represented by Position object
	 */
	public Position getPosition(){
		return this.position;
	}
	/**
	 * Gets X position as Cartesian coordinate
	 * @return   x-coordinate
	 */
	public int getXpos(){
		return this.position.getXpos();
	}

	/**
	 * Gets Y position as Cartesian coordinate
	 * @return   Piece's Y location
	 */
	public int getYpos(){
		return this.position.getYpos();
	}

	/**
	 * Sets the position using another position object
	 * @param pos   new position object
	 */
	public void setPosition(Position pos){
		if(pos != null)
			this.position = pos;
	}
	/**
	 * Sets the position using Cartesian coordinates
	 *
	 * @param x   x-coordinate
	 * @param y   y-coordinate
	 */
	public void setPosition(int x, int y){
		this.position.setXpos(x);
		this.position.setYpos(y);
	}

	public String toString(){
		return color + " " + this.getClass().getSimpleName() + " at (" + position.getXpos() + ", " + position.getYpos() + ")";
	}

	/**
	 * Used to print out a symbol representing the piece on the ASCII board.
	 * Should never appear.
	 *
	 * @return string symbol to represent piece
	 */
	public String symbol(){
		return "?";
	}
}
