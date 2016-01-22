package us.pecon.ray.chessPlay;

/**
 * One day, I'll make a smart game system. Then I'll come back to this, and
 * make it complete. And on that day... I'll feel smart. But not yet.
 * <br>
 * For now, I'll just convert everything into a more efficient means of play.
 * Things the computer will like and will go faster. Then I'll make the AI.
 * Keep in mind the AI will be doing the same things humans will, except it'll
 * need more free memory and computing power.
 * 
 * @author Ethan Arns
 */
public class ChessBoard_ai extends ChessBoard {
	
	/**
	 * The same as ChessBoard, but less wasted memory
	 */
	public ChessBoard_ai(){
		boardMatrix= new Piece[8][8];
		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++){
				boardMatrix[i][j] = new Blank("Blank", i, j);
			}
		}
		currentTurn = "White";
		isGameOver = false;
		verbose = false;
	}
}
