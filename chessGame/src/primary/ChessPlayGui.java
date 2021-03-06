package primary;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * A simple chess game using JavaFX.
 *
 * @author ethanarns
 */
public class ChessPlayGui extends Application {

	private BorderPane borderPane;
	private ToolBar toolbar;
	private GridPane board;
	private VBox moves;
	private ArrayList<String> moveList;
	private int turnNumber;

	private Button exitButton;
	private Button muteButton;
	private Button resetButton;
	private Button saveButton;
	private Button loadButton;

	private Position selectedSquare;
	private boolean lookingForMove;

	private ChessBoard_ai chess;

	private SoundManager playSound;
	private boolean muted;

	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * This class is everything called after the main method calls launch().
	 * You cannot do any actual work with the stage itself here, but for
	 * sake of clarity, do all non-stage setup in here.
	 */
	@Override
	public void init(){
		//initialize the chess API
		chess = new ChessBoard_ai();
		chess.setDebug(false);
		chess.resetBoard();

		playSound = new SoundManager();
		playSound.setVolume(1.0);//Maximum volume
		muted = false;

		if(chess.isDebug())
			System.out.println("Pre-initialization complete.");
	}

	/**
	 * The method that actually starts the gui. Do all Stage construction here,
	 * and do other initializations in the init() method
	 */
	@Override
	public void start(final Stage primaryStage) throws Exception {
		primaryStage.setTitle("Chess");
		borderPane = new BorderPane();
		initBoard();
		initToolbar();
		moveList = new ArrayList<String>();
		initMoves();
	    borderPane.setTop(toolbar);
	    borderPane.setCenter(board);
	    borderPane.setRight(moves);
	    primaryStage.setScene(new Scene(borderPane, 800,600));
	    updateBoard();
	    //File utilities
	    loadButton.setOnAction(
			new EventHandler<ActionEvent>(){
				@Override
				public void handle(ActionEvent event) {
					FileChooser fileChooser = new FileChooser();
					fileChooser.setTitle("Load game");
					File file = fileChooser.showOpenDialog(primaryStage);
					if(file != null){
						try {
							loadFile(file);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					else{
						System.out.println("Load failed.");
					}
				}
			}
	    );
	    saveButton.setOnAction(
	    	new EventHandler<ActionEvent>(){
				@Override
				public void handle(ActionEvent event) {
					FileChooser fileChooser = new FileChooser();
					fileChooser.setTitle("Save game");
					File file = fileChooser.showSaveDialog(primaryStage);
					if(file != null){
						try {
							saveFile(file);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
	    	}
	    );

	    primaryStage.show();

	    updateBoard();
	}

	/**
	 * Graphical initialization of the toolbar. Assumes toolbar is declared but
	 * not initialized, as well as the buttons
	 */
	public void initToolbar(){
		exitButton = new Button("Exit");
		exitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	playSound.click();
                Platform.exit();//The safer way to exit!
            }
        });
		exitButton.setAlignment(Pos.BOTTOM_RIGHT);

		muteButton = new Button("Mute");
		muteButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(muted){
                	muted = false;
                	playSound.setVolume(1.0);
                	muteButton.setText("Mute");
                }
                else{
                	muted = true;
                	playSound.setVolume(0.0);
                	muteButton.setText("Unmute");
                }
            }
        });

		resetButton = new Button("Reset");
		resetButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	playSound.click();
            	if(chess.getTurn().equalsIgnoreCase("black"))
            		chess.changeTurn();//set to white if black
            	resetBoard();
            	turnNumber = 1;
            	moves.getChildren().clear();
            	moveList.clear();
            	updateBoard();
            	if(chess.isDebug())
            		System.out.println("Board reset.");
            }
        });

		saveButton = new Button("Save");
		//saveButton moved to primary loader for stage variable

		loadButton = new Button("Load");
		//loadButton moved to primary loader for stage variable

		toolbar = new ToolBar(
			exitButton,
			muteButton,
			resetButton,
			saveButton,
			loadButton
		);
		int size = 50;
		toolbar.setPrefHeight(size);
		toolbar.setMaxHeight(size);
		toolbar.setMinHeight(size);
	}

	/**
	 * Graphical initialization of moves pane. Assumes moves is not initialized
	 * but that it has been declared
	 */
	public void initMoves(){
		moves = new VBox();
		moves.setPadding(new Insets(10));
	    moves.setSpacing(8);
	    moves.setBackground(new Background(new BackgroundFill(Color.web("#FFFFFF"), CornerRadii.EMPTY, Insets.EMPTY)));
	    moves.setMinWidth(240);
	    turnNumber = 1;
	}

	/**
	 * Updates the move list on the right side with the input string. If it
	 * would overflow, it removes the first and reprints in order to give it a
	 * scrolling effect.
	 *
	 * @param output   string that should be added to move list pane
	 */
	public void printMove(String output){
		moveList.add(output);
		if(moveList.size() < 23)
			moves.getChildren().add(new Text(moveList.get(moveList.size()-1)));
		else{
			moveList.remove(0);
			moves.getChildren().clear();
			for(int i = 0; i < moveList.size(); i++)
				moves.getChildren().add(new Text(moveList.get(i)));
		}
		/*
		 * TEST HERE
		 */
		//System.out.println(chess.isInCheck("white"));
		/*
		 * END TEST
		 */
	}

	/**
	 * Translates a click somewhere on the board GridePane into a computer-
	 * friendly chess location as a Position object. Checks if click is
	 * inside the boundaries, returns null if not on a square.
	 *
	 * @param eventX   the event.getX() input
	 * @param eventY   the event.getY() input
	 * @return         adjusted position if valid, null if invalid
	 */
	public Position getClickPos(double eventX, double eventY){
		int padding = 38;//Usually 35, but added a bit in order to fix borders
		if(eventX < padding || eventY < padding)
			return null;
		else if(eventX > 518 || eventY > 518)
			return null;
		int x = ((int) (eventX - padding))/60;
		int y = ((int) (eventY - padding))/60;
		return new Position(x, 7-y);
	}

	/**
	 * This should be called upon a square being clicked on the board pane. It
	 * first does a bunch of input tests, then checks if the click itself is
	 * legal based on current turn and whether or not the user is looking to
	 * place a piece on a blank space or an enemy piece. It initiates the move
	 * logic if all requirements are met.
	 * <br>
	 * It also works with the move history list, and prints the move. It also
	 * checks for piece capture, and will also print that to the history list.
	 *
	 * @param pos   the position of the square clicked
	 */
	public void squareClicked(Position pos){
		if(chess.isDebug())
			System.out.println("Looking for move is " + lookingForMove);

		if(pos == null)
			return;
		if(pos.getXpos() > 7 || pos.getXpos() < 0 || pos.getYpos() > 7 || pos.getYpos() < 0)
			return;
		if(chess.getPiece(pos).getClass().getSimpleName().equalsIgnoreCase("Blank") && !lookingForMove){
			if(chess.isDebug())
				System.out.println("Blank space selected without movement");
			return;
		}
		if(selectedSquare.equals(pos)){
			if(chess.isDebug())
				System.out.println("Deselecting...");
			updateBoard();
			selectedSquare.setXpos(-1);
			selectedSquare.setYpos(-1);
			lookingForMove = false;
			playSound.click();
			return;
		}
		if(chess.getTurn().equalsIgnoreCase("white") && chess.getPiece(pos).getColor().equalsIgnoreCase("black") && !lookingForMove){
			playSound.error();
			if(chess.isDebug())
				System.out.println("It is white's turn");
			return;
		}
		else if(chess.getTurn().equalsIgnoreCase("black") && chess.getPiece(pos).getColor().equalsIgnoreCase("white") && !lookingForMove){
			playSound.error();
			if(chess.isDebug())
				System.out.println("It is black's turn");
			return;
		}
		if(lookingForMove){
			//Do move!
			//first, get piece that is piece of prior selected square
			Piece currentPiece = chess.getPiece(selectedSquare);
			Position prePos = currentPiece.getPosition();

			//setup capture logic
			boolean isNewSquareEnemy = false;
			String newSquareColor = chess.getPiece(pos).getColor();
			String newSquareName = chess.getPiece(pos).getClass().getSimpleName();
			if(chess.isDebug())
				System.out.println("The new square's color: " + newSquareColor);
			if(chess.getTurn().equalsIgnoreCase("white")){
				if(newSquareColor.equalsIgnoreCase("black"))
					isNewSquareEnemy = true;
			}
			else if(chess.getTurn().equalsIgnoreCase("black")){
				if(newSquareColor.equalsIgnoreCase("white"))
					isNewSquareEnemy = true;
			}

			//Now the actual move check
			if(!chess.move(currentPiece, pos.getXpos() - prePos.getXpos(), pos.getYpos() - prePos.getYpos())){
				if(chess.isDebug())
					System.out.print("Move not successful.");
				playSound.error();
				return;
			}
			//Move done, do updates

			updateBoard();
			lookingForMove = false;
			selectedSquare.setXpos(-1);
			selectedSquare.setYpos(-1);
			chess.changeTurn();
			printMove("" + turnNumber + ". " + chess.getPiece(pos).getColor() +
					" " + chess.getPiece(pos).getClass().getSimpleName() +
					" moved to (" + (pos.getXpos()+1) + ", " + (pos.getYpos()+1) + ")");
			turnNumber++;
			if(isNewSquareEnemy){
				if(chess.isDebug())
					System.out.println("The new square was an enemy!");
				printMove("" + newSquareColor + " " + newSquareName + " at (" +
					(pos.getXpos()+1) + ", " + (pos.getYpos()+1) + ") defeated!");
				playSound.slash();
			}
			else
				playSound.place();
			return;
		}
		else if(!lookingForMove){//Nothing selected, needs move
			selectedSquare = pos;
			lookingForMove = true;
			setSquareSelectedColor(selectedSquare);
			playSound.click();
			return;
		}
	}

	/**
	 * A method that sets the square at the selected color to an appropriate
	 * selection color. It first updates the board, as to clear any other
	 * selection colors. The fill color is Color.LIGHTGRAY, the stroke is
	 * Color.GRAY.
	 *
	 * @param pos   position to set to select color
	 */
	public void setSquareSelectedColor(Position pos){
		updateBoard();
		Rectangle rect = new Rectangle();
		rect.setWidth(60);
		rect.setHeight(60);
		rect.setStroke(Color.GRAY);
		rect.setStrokeWidth(3);
		rect.setFill(Color.LIGHTGRAY);
		board.add(rect, pos.getXpos()+1, 8-pos.getYpos());
		String symbol = " " + getSymbol(chess.getPiece(pos.getXpos(), pos.getYpos()).symbol());
		Text tempText = new Text(symbol);
		Font tempFont = new Font(42);
		tempText.setFont(tempFont);
		if(!symbol.equalsIgnoreCase(" _"))
			board.add(tempText, pos.getXpos()+1, 8-pos.getYpos());
	}

	/**
	 * Initializes the board variable as a GridPane. It then adds in graphics
	 * representing blank spaces, sets the mouse event for square click
	 * recognition, and initiates the square selection variable and whether
	 * or not the user is looking to move, which should initially be false.
	 */
	public void initBoard(){
		int squareSize = 60;
		int padding = 35;
		board = new GridPane();
		board.getRowConstraints().add(new RowConstraints(padding));
		board.getColumnConstraints().add(new ColumnConstraints(padding));
		for(int i = 0; i < 8; i++){
			board.getColumnConstraints().add(new ColumnConstraints(squareSize));
			board.getRowConstraints().add(new RowConstraints(squareSize));
		}
		for(int i = 1; i < 9; i++){
			for(int j = 1; j < 9; j++){
				Rectangle rect = new Rectangle();
				rect.setWidth(squareSize);
				rect.setHeight(squareSize);
				rect.setStroke(Color.GREY);
				rect.setStrokeWidth(3);
				rect.setFill(Color.WHITE);
				board.add(rect, i, j);
			}
		}

		board.setOnMousePressed(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent event) {
				squareClicked(getClickPos(event.getX(), event.getY()));
			}//This makes it so when a square is clicked (ignoring objects
		});//inside, it returns a proper chess coordinate as a Position object

		selectedSquare = new Position(-1, -1);
		lookingForMove = false;
	}

	/**
	 * Takes the data from the existing chess matrix and places it on the
	 * graphical board object
	 */
	public void updateBoard(){
		board.getChildren().clear();
		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++){
				Rectangle rect = new Rectangle();
				rect.setWidth(60);
				rect.setHeight(60);
				rect.setStroke(Color.GREY);
				rect.setStrokeWidth(3);
				rect.setFill(Color.WHITE);
				board.add(rect, i+1, 8-j);
				String symbol = " " + getSymbol(chess.getPiece(i, j).symbol());
				Text tempText = new Text(symbol);
				Font tempFont = new Font(42);
				tempText.setFont(tempFont);
				if(!symbol.equalsIgnoreCase(" _"))
					board.add(tempText, i+1, 8-j);
			}
		}
	}

	/**
	 * Resets chess matrix, then updates graphical board to reflect the change
	 */
	public void resetBoard(){
		chess.resetBoard();
		board.getChildren().clear();
		updateBoard();
	}

	/**
	 * Places a Piece object onto the chess matrix, then updates the board to
	 * reflect that
	 *
	 * @param p   piece to place
	 */
	public void placePiece(Piece p){
		p.setPosition(p.getXpos(), p.getYpos());
		chess.placePiece(p);
		updateBoard();
	}

	/**
	 * Converts symbol of input String to actual chess character to be placed
	 * on the board
	 *
	 * @param input   input string
	 * @return        string that is an actual chess graphic, or empty space
	 */
	public String getSymbol(String input){
		if(input.equals("b"))
			return "♝";
		else if (input.equals("B"))
			return "♗";
		else if (input.equals("K"))
			return "♔";
		else if (input.equals("k"))
			return "♚";
		else if (input.equals("n"))
			return "♞";
		else if (input.equals("N"))
			return "♘";
		else if (input.equals("r"))
			return "♜";
		else if (input.equals("R"))
			return "♖";
		else if (input.equals("q"))
			return "♛";
		else if (input.equals("Q"))
			return "♕";
		else if (input.equals("p"))
			return "♟";
		else if (input.equals("P"))
			return "♙";
		return " ";
	}

	public Piece getSavedPiece(String symbol, int x, int y){
		if(symbol.length() != 1){
			if(chess.isDebug())
				System.out.println("Invalid Piece symbol.");
			return null;
		}
		if(symbol.equals("b"))
			return new Bishop("Black", x, y);
		else if (symbol.equals("B"))
			return new Bishop("White", x, y);
		else if (symbol.equals("K"))
			return new King("White",   x, y);
		else if (symbol.equals("k"))
			return new King("Black",   x, y);
		else if (symbol.equals("n"))
			return new Knight("Black", x, y);
		else if (symbol.equals("N"))
			return new Knight("White", x, y);
		else if (symbol.equals("r"))
			return new Rook("Black",   x, y);
		else if (symbol.equals("R"))
			return new Rook("White",   x, y);
		else if (symbol.equals("q"))
			return new Queen("Black",  x, y);
		else if (symbol.equals("Q"))
			return new Queen("White",  x, y);
		else if (symbol.equals("p"))
			return new Pawn("Black",   x, y);
		else if (symbol.equals("P"))
			return new Pawn("White",   x, y);
		return new Blank("Blank",      x, y);
	}

	/**
	 * A safe method of stopping. System.exit() works too, but with
	 * Platform.exit(), this little method gets called, and allows for things
	 * like saving on close etc. This is not to be manually called, only by the
	 * Application run itself
	 */
	@Override
	public void stop(){
		if(chess.isDebug())
			System.out.println("Safely stopping.");
	}

	/*
	 * File utilities
	 */

	public void loadFile(File file) throws IOException{
		if(file == null)
			return;
		if(chess.isDebug())
			System.out.println("File \'" + file.getName() + "\' has been loaded.");
		//First, some cleanup
		resetBoard();
		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++){
				placePiece(new Blank("Blank", i, j));
			}
		}
		moves.getChildren().clear();
		turnNumber = -1;
		moveList.clear();
		//initiate the reader
		BufferedReader reader = new BufferedReader(new FileReader(file.getAbsolutePath()));
		//Now for the actual reading. Remember to check input.
		//first should be a turn
		String turn = reader.readLine();
		if(!(turn.equalsIgnoreCase("white") || turn.equalsIgnoreCase("black"))){
			System.out.println("Bad save.");
			reader.close();
			return;
		}
		if(turn.equalsIgnoreCase("black")){
			chess.changeTurn();
		}//if its white, the reset should have made the turn white too
		//now, for the turn count
		String turnLine = reader.readLine();
		int turnInt = -1;
		try {
			turnInt = Integer.parseInt(turnLine);
		} catch (NumberFormatException e) {
			System.out.println("turnLine is not an integer");
			e.printStackTrace();
			reader.close();
			return;
		}
		if(turnInt > 0)
			turnNumber = turnInt;
		else{
			System.out.println("Invalid turn number.");
			reader.close();
			return;
		}
		//now for the actual piece positions
		for(int i = 0; i < 8; i++){
			String pieceLine = reader.readLine();
			//check for validity
			if(pieceLine.length() != 8){
				System.out.println("Bad save.");
				reader.close();
				return;
			}
			for(int j = 7; j >= 0; j--){
				placePiece(getSavedPiece(pieceLine.charAt(j) + "", i, j));
			}
		}
		//Now for the list of moves
		String moveLine = null;
		//setting a variable returns its data, so this'll go until it creates a null variable
		while((moveLine = reader.readLine()) != null){
			printMove(moveLine);
		}

		reader.close();
	}

	public void saveFile(File file) throws IOException{
		if(file == null)
			return;
		try (BufferedWriter writer = new BufferedWriter(new PrintWriter(file.getPath().toString() + ".chs"))) {
			//write turn, as a check
			writer.write(chess.getTurn());
			writer.newLine();
			writer.write("" + turnNumber);
			writer.newLine();
			//write positions of pieces
	        for(int i = 0; i < 8; i++){
	        	for(int j = 0; j < 8; j++){
	        		writer.write(chess.getPiece(i, j).symbol());
	        	}
	        	writer.newLine();
	        }
	        //now write the history list
	        if(moveList.size() < 1){
	        	if(chess.isDebug())
	        		System.out.println("Nothing to save.");
	        	return;
	        }
	        for(int i = 0; i < moveList.size(); i++){
	        	writer.write(moveList.get(i));
	        	writer.newLine();
	        }
	        writer.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
		if(chess.isDebug())
			System.out.println("File \'" + file.getName() + "\' has been saved.");
	}

}
