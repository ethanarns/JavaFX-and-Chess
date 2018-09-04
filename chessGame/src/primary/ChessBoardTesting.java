package primary;

import static org.junit.Assert.*;

import org.junit.*;

public class ChessBoardTesting {
	public ChessBoard board;
	
	@Before
	public void setUp() throws Exception {
		board = new ChessBoard();
		board.resetBoard();
	}

	@Test
	public void testIsOnBoardIntInt() {
		assertTrue(board.isOnBoard(0, 0));
		assertFalse(board.isOnBoard(-1, 9));
	}

	@Test
	public void testIsOnBoardPosition() {
		Position pos = new Position(0,0);
		assertTrue(board.isOnBoard(pos));
		pos.xPos = 20;
		assertFalse(board.isOnBoard(pos));
	}
	
	@Test
	public void testGetPieceIntInt() {
		assertNull(board.getPiece(-1,0));
		assertTrue(board.getPiece(3, 3) instanceof Blank);
		assertTrue(board.getPiece(0, 0) instanceof Rook);
	}
	
	@Test
	public void testMovesPieceintInt() {
		assertFalse(board.move(board.getPiece(0, 0), 1, 1));
		assertTrue(board.moveTo(board.getPiece(1, 1), 1, 2));
		assertFalse(board.move(board.getPiece(1, 1), 1, 1));
	}
	
	@Test
	public void testGetTurn() {
		assertEquals(board.getTurn(), "White");
		board.changeTurn();
		assertEquals(board.getTurn(), "Black");
	}
	
	@Test
	public void testGetPieces() {
		assertEquals(board.getAllPieces().size(), 32);
		assertEquals(board.getBlackPieces().size(), 16);
		assertEquals(board.getWhitePieces().size(), 16);
	}
}
