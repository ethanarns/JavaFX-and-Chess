package primary;

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
import javafx.stage.Stage;
import us.pecon.ray.chessPlay.Blank;
import us.pecon.ray.chessPlay.ChessBoard;
import us.pecon.ray.chessPlay.Piece;
import us.pecon.ray.chessPlay.Position;

/**
 * I have officially begun using GitHub, and this lame, un-original little
 * project will be my first go at it. It is essentially playing with creation
 * of a well-documented API, then being used in a graphical interface. Pretty
 * pointless, but a good test of proper coding conventions.
 * 
 * @author ethanarns
 */
public class ChessPlayGui extends Application {

	private BorderPane borderPane;
	private ToolBar toolbar;
	private GridPane board;
	private VBox moves;
	private ArrayList<String> moveList;
	
	private Button exitButton;
	
	private Position selectedSquare;
	private boolean lookingForMove;
	
	private ChessBoard chess;
	
	/*
	 * fun fact: todo get recognized by Eclipse and can be accessed in a window
	 * TODO: Click
	 */
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
		chess = new ChessBoard();
		chess.setDebug(true);
		chess.resetBoard();
		
		if(chess.isDebug())
			System.out.println("Pre-initialization complete.");
	}
	
	/**
	 * The method that actually starts the gui. Do all Stage construction here,
	 * and do other initializations in the init() method
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
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
                Platform.exit();//The safer way to exit!
            }
        });
		exitButton.setAlignment(Pos.BOTTOM_RIGHT);
		
		toolbar = new ToolBar(
			exitButton
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
	}
	
	/**
	 * Translates a click somewhere on the board GridePane into a computer-
	 * friendly chess location as a Position object. Checks if click is
	 * inside the boundaries, returns null if not on a square.
	 * 
	 * @param eventX   the event.getX() input
	 * @param eventY   the event.getY() input
	 * @return         Adjusted position if valid, null if invalid
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
	
	public void squareClicked(Position pos){
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
			return;
		}
		if(chess.getTurn().equalsIgnoreCase("white") && chess.getPiece(pos).getColor().equalsIgnoreCase("black") && !lookingForMove){
			if(chess.isDebug())
				System.out.println("It is white's turn");
			return;
		}
		else if(chess.getTurn().equalsIgnoreCase("black") && chess.getPiece(pos).getColor().equalsIgnoreCase("white") && !lookingForMove){
			if(chess.isDebug())
				System.out.println("It is black's turn");
			return;
		}
		if(lookingForMove){
			//Do move!
			//first, get piece that is piece of prior selected square
			Piece currentPiece = chess.getPiece(selectedSquare);
			Position prePos = currentPiece.getPosition();
			chess.move(currentPiece, pos.getXpos() - prePos.getXpos(), pos.getYpos() - prePos.getYpos());
			//Move done, do updates
			updateBoard();
			lookingForMove = false;
			selectedSquare.setXpos(-1);
			selectedSquare.setYpos(-1);
			chess.changeTurn();
			return;
		}
		else if(!lookingForMove){//Nothing selected, needs move
			selectedSquare = pos;
			lookingForMove = true;
			setSquareSelectedColor(selectedSquare);
			return;
		}
		System.out.println("What happened?");
	}
	
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
	 * representing blank spaces
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
	
	/**
	 * A safe method of stopping. System.exit() works too, but with
	 * Platform.exit(), this little method gets called, and allows for things
	 * like saving on close etc.
	 */
	@Override
	public void stop(){
		if(chess.isDebug())
			System.out.println("Safely stopping.");
	}
	
}