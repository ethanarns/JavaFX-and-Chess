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
	}

}
