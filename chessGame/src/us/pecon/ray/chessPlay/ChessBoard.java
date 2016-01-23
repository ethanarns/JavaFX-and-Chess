package us.pecon.ray.chessPlay;

import java.util.ArrayList;

/**
 * A class representing a chess board in which individual pieces will be placed
 * and manipulated upon. Its primary method of manipulation and containment is 
 * a matrix array containing individual Piece classes, which can be moved around
 * with either human readable or machine friendly commands.
 * 
 * @author Ethan Arns
 */
public class ChessBoard {
	
	protected Piece boardMatrix[][];
	protected String currentTurn;
	protected boolean verbose;

	protected ArrayList<Piece> blackPieces;
	protected ArrayList<Piece> whitePieces;
	
	/**
	 * Chess board class constructor. Sets current turn to white and initiates
	 * the two other variables blank
	 */
	public ChessBoard(){
		boardMatrix= new Piece[8][8];
		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++){
				boardMatrix[i][j] = new Blank("Blank", i, j);
			}
		}
		currentTurn = "White";
		blackPieces = new ArrayList<Piece>();
		whitePieces = new ArrayList<Piece>();

		verbose = false;
	}
	
	
	/**
	 * Sets debug mode. If on, System will print messages showing moves, errors,
	 * and board states.
	 * 
	 * @param which   true to set debug mode on, false to disable debug mode
	 */
	public void setDebug(boolean which){
		if(which)
			verbose = true;
		else
			verbose = false;
	}
	
	
	/**
	 * Getter for debug mode.
	 * 
	 * @return   true if debug mode is on
	 */
	public boolean isDebug(){
		return verbose;
	}
	
	
	/**
	 * Returns the current turn as a String
	 * @return a string representing the turn
	 */
	public String getTurn(){
		return currentTurn;
	}
	
	
	/**
	 * Changes the turn to the opposite of what it currently is. If turn is
	 * invalid, it set it to White.
	 */
	public void changeTurn(){
		if(currentTurn.equalsIgnoreCase("White")){
			currentTurn = "Black";
			if(verbose)
				System.out.println("It is now Black's turn.");
		}
		else{
			currentTurn = "White";
			if(verbose)
				System.out.println("It is now White's turn.");
		}
	}
	
	
	/**
	 * Retrieves piece at coordinate location
	 * @param x       the x coordinate
	 * @param y       the y coordinate
	 * @return piece  the Piece at the location specified, null if invalid
	 */
	public Piece getPiece(int x, int y){
		if(!isOnBoard(x,y)){
			if(verbose)
				System.out.println("Invalid position in getPiece(x,y)");
			return null;
		}
		return x > 7 || x < 0 || y > 7 || y < 0 ? null : boardMatrix[x][y];
	}
	
	
	/**
	 * Retrieves piece at Position class location
	 * @param pos     the position object to retrieve located piece
	 * @return        piece found, else returns null
	 */
	public Piece getPiece(Position pos){
		if(!isOnBoard(pos.getXpos(),pos.getYpos())){
			if(verbose)
				System.out.println("Invalid position in getPiece(pos");
			return null;
		}
		return pos.getXpos() > 7 || pos.getXpos() < 0 || pos.getYpos() > 7 || pos.getYpos() < 0 ? null : boardMatrix[pos.getXpos()][pos.getYpos()];
	}
	
	
	/**
	 * Prints the board to Console output in ASCII format
	 */
	public void printBoardState(){
		System.out.print("  ");
		for(int k = 0; k < 8; k++)
			System.out.print(k + " ");
		System.out.println("");
		for(int i = 7; i >= 0; i--){//go *backwards* through the top level
			System.out.print(i + " ");
			for(int j = 0; j < 8; j++){//but go forwards through the sublevels
				System.out.print(getPiece(j,i).symbol() + " ");//print in *reverse* coordinates
			}
			System.out.print(i);
			System.out.println();
		}
		System.out.print("  ");
		for(int k = 0; k < 8; k++)
			System.out.print(k + " ");
		System.out.println("\n");
	}
	
	
	/**
	 * Checks if a specified coordinates are legal on the board
	 * @param x   x coordinate to check
	 * @param y   y coordinate to check
	 * @return    true if legal position
	 */
	protected boolean isOnBoard(int x, int y){
		return !(x > 7 || x < 0 || y > 7 || y < 0);
	}
	
	
	/**
	 * Checks if a specific Position object's position is legal on the board
	 * @param pos  position to be checked
	 * @return     true if legal position
	 */
	protected boolean isOnBoard(Position pos){
		return !(pos.getXpos() > 7 || pos.getXpos() < 0 || pos.getYpos() > 7 || pos.getYpos() < 0);
	}
	
	
	/**
	 * Places a new piece on the ChessBoard
	 * @param piece  the piece object to be placed
	 */
	public void placePiece(Piece piece){
		if(!isOnBoard(piece.getPosition())){
			if(verbose)
				System.out.println("Invalid piece place!");
			return;
		}
		boardMatrix[piece.getXpos()][piece.getYpos()] = piece;
	}
	
	
	/**
	 * Place a piece on the ChessBoard, adjusted with Position object
	 * @param piece     piece to place
	 * @param pos       location to place piece
	 */
	public void placePiece(Piece piece, Position pos){
		if(!isOnBoard(piece.getPosition())){
			if(verbose)
				System.out.println("Invalid piece place!");
			return;
		}
		piece.setPosition(pos);
		boardMatrix[piece.getXpos()][piece.getYpos()] = piece;
	}
	
	
	/**
	 * Place a piece on the ChessBoard, adjusted with coordinates
	 * @param piece   the piece object to be placed
	 * @param x       the x coordinate
	 * @param y       the y coordinate
	 */
	public void placePiece(Piece piece, int x, int y){
		if(!isOnBoard(piece.getPosition())){
			if(verbose)
				System.out.println("Invalid piece place location!");
			return;
		}
		piece.setPosition(x, y);
		boardMatrix[piece.getXpos()][piece.getYpos()] = piece;
	}
	
	
	/**
	 * Places all pieces in proper starting position
	 */
	public void resetBoard(){
		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++){
				boardMatrix[i][j] = null;
				placePiece(new Blank("Blank", i, j));
			}
		}
		for(int i = 0; i < 8; i++)
			placePiece(new Pawn("White", i, 1));
		for(int j = 0; j < 8; j++)
			placePiece(new Pawn("Black", j, 6));
		//0 x is black pieces, 7 x is white pieces
		placePiece( new Rook("White",  0,0) );
		placePiece( new Rook("White",  7,0) );
		placePiece( new Rook("Black",  0,7) );
		placePiece( new Rook("Black",  7,7) );
		
		placePiece( new Knight("White",6,0) );
		placePiece( new Knight("White",1,0) );
		placePiece( new Knight("Black",6,7) );
		placePiece( new Knight("Black",1,7) );
		
		placePiece( new Bishop("White",5,0) );
		placePiece( new Bishop("White",2,0) );
		placePiece( new Bishop("Black",5,7) );
		placePiece( new Bishop("Black",2,7) );
		
		placePiece( new King("White",  3,0) );
		placePiece( new Queen("White", 4,0) );
		
		placePiece( new King("Black",  3,7) );
		placePiece( new Queen("Black", 4,7) );
		
		refreshPieceList();
	}
	
	
	/**
	 * Returns a piece relative to the piece in question by obtaining the location
	 * coordinates of the piece itself and then checking the offset set in the
	 * x and y coordinate parameters
	 * @param p   the initial piece to check from
	 * @param x   the up and down movement
	 * @param y   the left and right movement
	 * @return    piece found at location, null if invalid
	 */
	protected Piece getPieceRelative(Piece p, int x, int y){
		if(p.getXpos()+x > 7 || p.getXpos() < 0 || p.getYpos() > 7 || p.getYpos() < 0 || p == null){
			if(verbose)
				System.out.println("Invalid relative piece position.");
			return null;
		}
		if(getPiece(p.getXpos() + x, p.getYpos() + y) == null){
			if(verbose)
				System.out.println("Relative piece is off the board.");
			return null;
		}
		return getPiece(p.getXpos() + x, p.getYpos() + y);
	}
	
	
	/**
	 * Returns a piece relative to the location specified, located by checking the
	 * position offset by the x and y coordinate parameters
	 * @param pos  the initial piece's position to check from
	 * @param x    the up and down movement
	 * @param y    the left and right movement
	 * @return     piece found at location, null if invalid
	 */
	protected Piece getPieceRelative(Position pos, int x, int y){
		if(pos.getXpos()+x > 7 || pos.getXpos() < 0 || pos.getYpos() > 7 || pos.getYpos() < 0 || pos == null){
			if(verbose)
				System.out.println("Invalid realative piece position.");
			return null;
		}
		if(getPiece(pos.getXpos() + x, pos.getYpos() + y) == null){
			if(verbose)
				System.out.println("Relative piece is off the board.");
			return null;
		}
		return getPiece(pos.getXpos() + x, pos.getYpos() + y);
	}
	
	
	/**
	 * Moves a piece by advancing it according to the offset specified by x and
	 * y parameters. Also determines capture. Returns true if successful.
	 * 
	 * @param p  the piece selected to be moved
	 * @param x  how far left or right to move
	 * @param y  how far up or down to move
	 * @return   true if move was successful, false if not
	 */
	public boolean move(Piece p, int x, int y){
		if(p.getXpos() > 7 || p.getXpos() < 0 || p.getYpos() > 7 || p.getYpos() < 0 || p == null){
			if(verbose)
				System.out.println("Invalid piece position.");
			return false;
		}
		if(p instanceof Blank){
			if(verbose)
				System.out.println("Bad move! You cannot move a blank space.");
			return false;
		}
		//set old position (for blank placement purposes
		Position oldPos = new Position(p.getXpos(), p.getYpos());
		//new position, using x and y to be relative
		Piece newPiece = getPieceRelative( p.getPosition(), x, y);
		if(oldPos == null || newPiece == null){
			if(verbose)
				System.out.println("Invalid creation of pieces during move()");
			return false;
		}
		//Is it the same color?
		if(newPiece.getColor().equalsIgnoreCase(p.getColor())){
			if(verbose)
				System.out.println("Bad move! Can't land on same color.");
			return false;
		}

		//capture logic
		boolean placeIsEnemy = false;
		//if it ISNT blank and it ISNT the same color
		if(!newPiece.getColor().equalsIgnoreCase("Blank") && !newPiece.getColor().equalsIgnoreCase(p.getColor()))
			placeIsEnemy = true;//The spot is an enemy piece!
		if(!moveCheckAssigner(p,x,y)){
			if(verbose)
				System.out.println("Bad move! Illegal move for " + p.getClass().getSimpleName() + ".");
			return false;
		}
		
		if(newPiece instanceof King){
			if(verbose)
				System.out.println("Bad move! Kings cannot be captured.");
			return false;
		}
		
		//Everything checks out, so set the piece's position anew
		p.setPosition(newPiece.getXpos(), newPiece.getYpos());
		placePiece(p);//place it according to the new position
		//and set the old position to a Blank place
		placePiece(new Blank("Blank",oldPos.getXpos(), oldPos.getYpos()));
		if(verbose)
			printBoardState();
		else{
			if(verbose)
				System.out.println(p.getColor() + " " + p.getClass().getSimpleName() + " moved to (" + newPiece.getXpos() + ", " + newPiece.getYpos() + ")\n");
		}
		return true;
	}
	
	
	/**
	 * Attempts to move a piece to the set parameters. Also determines capture.
	 * Returns true if successful.
	 * 
	 * @param p  the piece selected to be moved
	 * @param x  x position to attempt to move to
	 * @param y  y position to attempt to move to
	 * @return   true if move was successful, false if not
	 */
	public boolean moveTo(Piece p, int x, int y){
		if(!isOnBoard(x,y) || p == null)
			return false;
		int relX = x - p.getXpos();
		int relY = y - p.getYpos();
		if(!isOnBoard(relX,relY))
			return false;
		return move(p, relX, relY);
	}
	
	
	/**
	 * A small method that finds what type of piece is being checked for
	 * movement, then assigns and hands off the correct method to them
	 * @param p      input piece that is being checked for relative movement  
	 * @param relX   relative x movement
	 * @param relY   relative y movement
	 * @return       true if valid move
	 */
	protected boolean moveCheckAssigner(Piece p, int relX, int relY){
		if(p instanceof Blank)
			System.out.println("Blank spaces cannot be moved.");
		else if(p instanceof Pawn)
			return moveCheck_pawn(p, relX, relY);
		else if(p instanceof Rook)
			return moveCheck_rook(p, relX, relY);
		else if(p instanceof Bishop)
			return moveCheck_bishop(p, relX, relY);
		else if(p instanceof King)
			return moveCheck_king(p, relX, relY);
		else if(p instanceof Queen)
			return moveCheck_queen(p, relX, relY);
		else if(p instanceof Knight)
			return moveCheck_knight(p, relX, relY);
		return false;//What happened?
	}

	
	/**
	 * Returns farthest acceptable placement away from the set position
	 * straight up, left, right, or downwards ('u','l','r','d')
	 * @param x           x position of the checked position
	 * @param y           y position of the checked position
	 * @param dir         character representing up, down, left, or right
	 * @param initPiece   initial piece being checked, doesn't change, just used
	 * @param newPos      the new Position
	 * @return            the position of either end of check or right place
	 */
	protected Position straightRecursive(int x, int y, char dir, Piece initPiece, Position newPos){
		if(dir == 'u'){
			//If the place just checked isn't a blank space...
			if( !isOnBoard(x, y)){
				return getPiece(x,y-1).getPosition();
			}
			else if(newPos.getXpos() == x && newPos.getYpos() == y){
				//right space found!
				return getPiece(x,y).getPosition();
			}
			else if(!(getPiece(x, y) instanceof Blank)){
				if(!getPiece(x,y).getColor().equalsIgnoreCase(initPiece.getColor())){
					//get the current piece, its an enemy!
					return getPiece(x,y).getPosition();
				}
				else{
					//Likely same color, return one before
					return getPiece(x,y-1).getPosition();
				}
			}
			else{//... it'll shoot up through the recursiveness stack
				return straightRecursive(x,y+1,'u',initPiece, newPos);//or just do it again 1 higher
			}
		}
		
		if(dir == 'd'){
			//If the place just checked isn't a blank space...
			if( !isOnBoard(x, y)){
				//return one before it
				return getPiece(x,y+1).getPosition();
			}
			else if(newPos.getXpos() == x && newPos.getYpos() == y){
				//found good position!
				return getPiece(x,y).getPosition();
			}
			else if(!(getPiece(x, y) instanceof Blank)){
				if(!getPiece(x,y).getColor().equalsIgnoreCase(initPiece.getColor())){
					//get the current piece, its an enemy!
					return getPiece(x,y).getPosition();
				}
				else{
					//Likely same color, return one before
					return getPiece(x,y+1).getPosition();
				}
			}
			else{//... it'll shoot up through the recursiveness stack
				return straightRecursive(x,y-1,'d',initPiece, newPos);//or just do it again 1 higher
			}
		}
		
		if(dir == 'l'){
			//If the place just checked isn't a blank space...
			if( !isOnBoard(x, y)){
				return getPiece(x+1,y).getPosition();
			}
			else if(newPos.getXpos() == x && newPos.getYpos() == y){
				return getPiece(x,y).getPosition();
			}
			else if(!(getPiece(x, y) instanceof Blank)){
				if(!getPiece(x,y).getColor().equalsIgnoreCase(initPiece.getColor())){
					//get the current piece, its an enemy!
					return getPiece(x,y).getPosition();
				}
				else{
					//Likely same color, return one before
					return getPiece(x+1,y).getPosition();
				}
			}
			else{//... it'll shoot up through the recursiveness stack
				return straightRecursive(x-1,y,'l',initPiece, newPos);//or just do it again 1 higher
			}
		}
		
		if(dir == 'r'){
			//If the place just checked isn't a blank space...
			if( !isOnBoard(x, y)){
				return getPiece(x-1,y).getPosition();
			}
			else if(newPos.getXpos() == x && newPos.getYpos() == y){
				return getPiece(x,y).getPosition();
			}
			else if(!(getPiece(x, y) instanceof Blank)){
				if(!getPiece(x,y).getColor().equalsIgnoreCase(initPiece.getColor())){
					//get the current piece, its an enemy!
					return getPiece(x,y).getPosition();
				}
				else{
					//Likely same color, return one before
					return getPiece(x-1,y).getPosition();
				}
			}
			else{//... it'll shoot up through the recursiveness stack
				return straightRecursive(x+1, y,'r',initPiece, newPos);//or just do it again 1 higher
			}
		}
		return null;//just in case
	}
	
	
	/**
	 * Returns farthest acceptable diagonal placement, using multiple recursive
	 * functions.
	 * @param x           x position of the checked position
	 * @param y           y position of the checked position
	 * @param dir         diagonal directions as strings
	 * @param initPiece   initial piece being checked, doesn't change, just used
	 * @param newPos      the new Position
	 * @return            the position of either end of check or right place
	 */
	protected Position diagonalRecursive(int x, int y, String dir, Piece initPiece, Position newPos){
		if(dir == "ul"){
			//If the place just checked isn't a blank space...
			if( !isOnBoard(x, y)){
				return getPiece(x+1,y-1).getPosition();
			}
			else if(newPos.getXpos() == x && newPos.getYpos() == y){
				//right space found!
				return getPiece(x,y).getPosition();
			}
			else if(!(getPiece(x, y) instanceof Blank)){
				if(!getPiece(x,y).getColor().equalsIgnoreCase(initPiece.getColor())){
					//get the current piece, its an enemy!
					return getPiece(x,y).getPosition();
				}
				else{
					//Likely same color, return one before
					return getPiece(x+1,y-1).getPosition();
				}
			}
			else{//... it'll shoot up through the recursiveness stack
				return diagonalRecursive(x-1,y+1,"ul",initPiece, newPos);//or just do it again 1 higher
			}
		}
		
		if(dir == "dl"){
			//If the place just checked isn't a blank space...
			if( !isOnBoard(x, y)){
				//return one before it
				return getPiece(x+1,y+1).getPosition();
			}
			else if(newPos.getXpos() == x && newPos.getYpos() == y){
				//found good position!
				return getPiece(x,y).getPosition();
			}
			else if(!(getPiece(x, y) instanceof Blank)){
				if(!getPiece(x,y).getColor().equalsIgnoreCase(initPiece.getColor())){
					//get the current piece, its an enemy!
					return getPiece(x,y).getPosition();
				}
				else{
					//Likely same color, return one before
					return getPiece(x+1,y+1).getPosition();
				}
			}
			else{//... it'll shoot up through the recursiveness stack
				return diagonalRecursive(x-1,y-1,"dl",initPiece, newPos);//or just do it again 1 higher
			}
		}
		
		if(dir.equalsIgnoreCase("ur")){
			//If the place just checked isn't a blank space...
			if( !isOnBoard(x, y)){
				return getPiece(x-1,y-1).getPosition();
			}
			else if(newPos.getXpos() == x && newPos.getYpos() == y){
				return getPiece(x,y).getPosition();
			}
			else if(!(getPiece(x, y) instanceof Blank)){
				if(!getPiece(x,y).getColor().equalsIgnoreCase(initPiece.getColor())){
					//get the current piece, its an enemy!
					return getPiece(x,y).getPosition();
				}
				else{
					//Likely same color, return one before
					return getPiece(x-1,y-1).getPosition();
				}
			}
			else{//... it'll shoot up through the recursiveness stack
				return diagonalRecursive(x+1,y+1,"ur",initPiece, newPos);//or just do it again 1 higher
			}
		}
		
		if(dir.equalsIgnoreCase("dr")){
			//If the place just checked isn't a blank space...
			if( !isOnBoard(x, y)){
				return getPiece(x-1,y+1).getPosition();
			}
			else if(newPos.getXpos() == x && newPos.getYpos() == y){
				return getPiece(x,y).getPosition();
			}
			else if(!(getPiece(x, y) instanceof Blank)){
				if(!getPiece(x,y).getColor().equalsIgnoreCase(initPiece.getColor())){
					//get the current piece, its an enemy!
					return getPiece(x,y).getPosition();
				}
				else{
					//Likely same color, return one before
					return getPiece(x-1,y+1).getPosition();
				}
			}
			else{//... it'll shoot up through the recursiveness stack
				return diagonalRecursive(x+1, y-1,"dr",initPiece, newPos);//or just do it again 1 higher
			}
		}
		return null;//just in case
	}
	
	
	/**
	 * Checking movement validity for pawns. Done in ChessBoard class due to
	 * difficulties with checking board states in subclasses
	 * @param p      the piece being checked
	 * @param relX   the relative x movement
	 * @param relY   the relative y movement
	 * @return       true if move is okay
	 */
	protected boolean moveCheck_pawn(Piece p, int relX, int relY){
		if(p.getColor().equalsIgnoreCase("black")){
			//basic advance
			if(relX == 0 && relY == -1 && !getPieceRelative(p,relX,relY).getColor().equalsIgnoreCase("white"))
				return true;
			//first turn jump
			else if(relX == 0 && relY == -2 && p.getYpos() == 6){
				return true;
			}
			//capture, making sure capture is true
			else if((relX == 1 || relX == -1) && relY == -1 && getPieceRelative(p,relX,relY).getColor().equalsIgnoreCase("white"))
				return true;
		}
		else if(p.getColor().equalsIgnoreCase("white")){
			//basic advance
			if(relX == 0 && relY == 1 && !getPieceRelative(p,relX,relY).getColor().equalsIgnoreCase("black"))
				return true;
			//first turn jump
			else if(relX == 0 && relY == 2 && p.getYpos() == 1){
				return true;
			}
			//capture, making sure capture is true
			else if((relX == 1 || relX == -1) && relY == 1 && getPieceRelative(p,relX,relY).getColor().equalsIgnoreCase("black"))
				return true;
		}
		return false;
	}
	
	
	/**
	 * A general method for checking if a rook's new position works. Sends off
	 * p in differing directions, checking either if the correct position is on
	 * a blank or on the first enemy piece
	 * @param p      piece selected to be moved
	 * @param relX   relative x position
	 * @param relY   relative y position
	 * @return       true if move is okay
	 */
	protected boolean moveCheck_rook(Piece p, int relX, int relY){
		Piece newPos = getPieceRelative(p,relX,relY);
		if(straightRecursive(p.getXpos(), p.getYpos()+1, 'u', p, newPos.getPosition()).equals(newPos.getPosition()))
			return true;
		if(straightRecursive(p.getXpos(), p.getYpos()-1, 'd', p, newPos.getPosition()).equals(newPos.getPosition()))
			return true;
		if(straightRecursive(p.getXpos()-1, p.getYpos(), 'l', p, newPos.getPosition()).equals(newPos.getPosition()))
			return true;
		if(straightRecursive(p.getXpos()+1, p.getYpos(), 'r', p, newPos.getPosition()).equals(newPos.getPosition()))
			return true;
		if(verbose)
			System.out.println("Correct position for Rook not found.");
		return false;
	}
	
	
	/**
	 * Checks in all 4 diagonal directions to see if move is legal
	 * @param p      piece selected to be moved
	 * @param relX   relative x position
	 * @param relY   relative y position
	 * @return       true if move is okay
	 */
	protected boolean moveCheck_bishop(Piece p, int relX, int relY){
		Piece newPos = getPieceRelative(p,relX,relY);
		if(diagonalRecursive(p.getXpos()-1, p.getYpos()+1, "ul", p, newPos.getPosition()).equals(newPos.getPosition()))
			return true;
		if(diagonalRecursive(p.getXpos()+1, p.getYpos()-1, "dr", p, newPos.getPosition()).equals(newPos.getPosition()))
			return true;
		if(diagonalRecursive(p.getXpos()-1, p.getYpos()-1, "dl", p, newPos.getPosition()).equals(newPos.getPosition()))
			return true;
		if(diagonalRecursive(p.getXpos()+1, p.getYpos()+1, "ur", p, newPos.getPosition()).equals(newPos.getPosition()))
			return true;
		return false;
	}
	
	
	/**
	 * A checker for King movement. Checks the surrounding board according to
	 * legal moves, and returns true if it finds it.
	 * @param p      piece selected to be moved
	 * @param relX   relative x position
	 * @param relY   relative y position
	 * @return       true if move is legal
	 */
	protected boolean moveCheck_king(Piece p, int relX, int relY){
		if(relX > 1 || relX < -1 || relY > 1 || relY < -1){
			if(verbose)
				System.out.println("Bad move! King cannot move that far.");
			return false;
		}
		Piece newPos = getPieceRelative(p, relX, relY);
		if(p.getXpos()+1==newPos.getXpos() && p.getYpos()+0==newPos.getYpos()){//(1,0)
			return true;
		}
		else if(p.getXpos()+1==newPos.getXpos() && p.getYpos()-1==newPos.getYpos()){//(1,-1)
			return true;
		}
		else if(p.getXpos()+0==newPos.getXpos() && p.getYpos()-1==newPos.getYpos()){//(0,-1)
			return true;
		}
		else if(p.getXpos()-1==newPos.getXpos() && p.getYpos()-1==newPos.getYpos()){//(-1,-1)
			return true;
		}
		else if(p.getXpos()-1==newPos.getXpos() && p.getYpos()+0==newPos.getYpos()){//(-1,0)
			return true;
		}
		else if(p.getXpos()-1==newPos.getXpos() && p.getYpos()+1==newPos.getYpos()){//(-1,1)
			return true;
		}
		else if(p.getXpos()+0==newPos.getXpos() && p.getYpos()+1==newPos.getYpos()){//(0,1)
			return true;
		}
		else if(p.getXpos()+1==newPos.getXpos() && p.getYpos()+1==newPos.getYpos()){//(1,1)
			return true;
		}
		return false;
	}
	
	
	/**
	 * A checker for Queen movement. Checks the surrounding board according to
	 * legal moves, and returns true if it finds it.
	 * @param p      piece selected to be moved
	 * @param relX   relative x position
	 * @param relY   relative y position
	 * @return       true if move legal
	 */
	protected boolean moveCheck_queen(Piece p, int relX, int relY){
		Piece newPos = getPieceRelative(p,relX,relY);
		if(diagonalRecursive(p.getXpos()-1, p.getYpos()+1, "ul", p, newPos.getPosition()).equals(newPos.getPosition()))
			return true;
		if(diagonalRecursive(p.getXpos()+1, p.getYpos()-1, "dr", p, newPos.getPosition()).equals(newPos.getPosition()))
			return true;
		if(diagonalRecursive(p.getXpos()-1, p.getYpos()-1, "dl", p, newPos.getPosition()).equals(newPos.getPosition()))
			return true;
		if(diagonalRecursive(p.getXpos()+1, p.getYpos()+1, "ur", p, newPos.getPosition()).equals(newPos.getPosition()))
			return true;
		if(straightRecursive(p.getXpos(), p.getYpos()+1, 'u', p, newPos.getPosition()).equals(newPos.getPosition()))
			return true;
		if(straightRecursive(p.getXpos(), p.getYpos()-1, 'd', p, newPos.getPosition()).equals(newPos.getPosition()))
			return true;
		if(straightRecursive(p.getXpos()-1, p.getYpos(), 'l', p, newPos.getPosition()).equals(newPos.getPosition()))
			return true;
		if(straightRecursive(p.getXpos()+1, p.getYpos(), 'r', p, newPos.getPosition()).equals(newPos.getPosition()))
			return true;
		return false;
	}
	
	
	/**
	 * A checker for Knight movement. Checks the surrounding board according to
	 * legal moves, and returns true if it finds it.
	 * @param p      selected Piece
	 * @param relX   relative x position to check
	 * @param relY   relative y position to check
	 * @return       true if can move, false if not
	 */
	protected boolean moveCheck_knight(Piece p, int relX, int relY){
		Piece newPos = getPieceRelative(p, relX, relY);
		if(p.getXpos()+2==newPos.getXpos() && p.getYpos()+1==newPos.getYpos()){ //    (2,1)
			return true;
		}
		else if(p.getXpos()+2==newPos.getXpos() && p.getYpos()-1==newPos.getYpos()){//(2,-1)
			return true;
		}
		else if(p.getXpos()-2==newPos.getXpos() && p.getYpos()+1==newPos.getYpos()){//(-2,1)
			return true;
		}
		else if(p.getXpos()-2==newPos.getXpos() && p.getYpos()-1==newPos.getYpos()){//(-2,-1)
			return true;
		}
		else if(p.getXpos()+1==newPos.getXpos() && p.getYpos()+2==newPos.getYpos()){//(1,2)
			return true;
		}
		else if(p.getXpos()+1==newPos.getXpos() && p.getYpos()-2==newPos.getYpos()){//(1,-2)
			return true;
		}
		else if(p.getXpos()-1==newPos.getXpos() && p.getYpos()+2==newPos.getYpos()){//(-1,2)
			return true;
		}
		else if(p.getXpos()-1==newPos.getXpos() && p.getYpos()-2==newPos.getYpos()){//(-1,-2)
			return true;
		}
		return false;
	}
	
	/*
	 * Piece list utilities
	 */
	
	/**
	 * Refreshes the lists of white and black pieces
	 */
	public void refreshPieceList(){
		whitePieces.clear();
		blackPieces.clear();
		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++){
				Piece p = getPiece(i, j); // Make sure not blank
				if(!p.getColor().equalsIgnoreCase("blank")){
					if(p.getColor().equalsIgnoreCase("white"))
						whitePieces.add(p);
					else if(p.getColor().equalsIgnoreCase("black"))
						blackPieces.add(p);
				}
			}
		}
	}
	
	
	/**
	 * Prints to console a list of pieces on the board
	 */
	public void printPieceList(){
		refreshPieceList();
		System.out.print("White pieces: ");
		for(int i = 0; i < whitePieces.size(); i++){
			System.out.print(whitePieces.get(i).toString() + " ");
		}
		System.out.println("\nWhite piece count: " + whitePieces.size());
		System.out.print("Black pieces: ");
		for(int i = 0; i < blackPieces.size(); i++){
			System.out.print(blackPieces.get(i).toString() + " ");
		}
		System.out.println("\nBlack piece count: " + blackPieces.size());
	}
	
	/**
	 * Returns a list of white pieces on the board
	 * @return   ArrayList of white pieces
	 */
	public ArrayList<Piece> getWhitePieces() {
		refreshPieceList();
		return whitePieces;
	}
	
	/**
	 * Returns a list of black pieces on the board
	 * @return   ArrayList of black pieces
	 */
	public ArrayList<Piece> getBlackPieces() {
		refreshPieceList();
		return blackPieces;
	}
	
	/**
	 * Returns a list of all pieces on the board
	 * @return   ArrayList of all pieces
	 */
	public ArrayList<Piece> getAllPieces() {
		refreshPieceList();
		ArrayList<Piece> p = whitePieces;
		p.addAll(blackPieces);
		return p;
	}
	
}