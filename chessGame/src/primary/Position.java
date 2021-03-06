package primary;

/**
 * Pretty self explanatory. A collection of getters and setters for a class
 * that simplifies location pointing on a chess board
 *
 * @author Ethan Arns
 */
public class Position {

	public int xPos;
	public int yPos;

	/**
	 * Constructor with coordinates (4,3)
	 */
	public Position(){
		this.xPos = 4;
		this.yPos = 3;
	}
	/**
	 * Constructor with set coordinates
	 *
	 * @param x   x position coordinate
	 * @param y   y position coordinate
	 */
	public Position(int x, int y){
		this.xPos = x;
		this.yPos = y;
	}

	/**
	 * Gets x position
	 * @return   x position
	 */
	public int getXpos() {
		return xPos;
	}

	/**
	 * Sets x position
	 * @param xPos   x position
	 */
	public void setXpos(int xPos) {
		this.xPos = xPos;
	}

	/**
	 * Gets y position
	 * @return   y position
	 */
	public int getYpos() {
		return yPos;
	}

	/**
	 * Sets y position
	 * @param yPos   y position
	 */
	public void setYpos(int yPos) {
		this.yPos = yPos;
	}

	public String toString(){
		return "x position: " + xPos + ", y position: " + yPos;
	}

	/**
	 * A way to safely and efficiently check if two positions are equal.
	 *
	 * @param pos    position to be checked again this Position object
	 * @return       true if positions match
	 */
	public boolean equals(Position pos){
		if(pos.getXpos() == this.getXpos() && pos.getYpos() == this.getYpos())
			return true;
		return false;
	}
}
