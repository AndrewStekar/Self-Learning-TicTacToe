// Andrew Stekar
// Mon., Mar. 7, 2022.

package ticTacToe_MachineLearning;

public class GameBoard4 {
	/**
	 * An array of 9 GameTiles representing the TicTacToe gameboard.
	 */
	GameTile4[] board;

	/**
	 * Constructs an empty gameboard of 9 GameTiles and fills it with unowned tiles.
	 */
	public GameBoard4() {
		board = new GameTile4[9];
		for (int i = 0; i < 9; i++)
			board[i] = new GameTile4();
	}

	/**
	 * This will allow a player to claim a GameTile on the TicTacToe board.
	 * 
	 * @return A boolean true if the player successfully played on a tile, false if
	 *         that tile is already owned or the index is out of bounds.
	 * @param player A String indicating which player is to own the tile ("X" or
	 *               "O").
	 * @param tile   An integer representing the tile the player wishes to claim.
	 */
	public boolean play(String player, int tile) {
		if (0 <= tile && tile <= 8 && board[tile].owned() == false) {
			board[tile].setOwner(player);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * This will check to see if there are three tiles in a row belonging to a
	 * player.
	 * 
	 * @return A boolean true if the player has three tiles in a row, false
	 *         otherwise.
	 * @param player A String indicating which player to check for a win ("X" or
	 *               "O").
	 */
	public boolean checkWin(String player) {
		if ((board[0].getOwner() == player && board[1].getOwner() == player && board[2].getOwner() == player)
				|| (board[3].getOwner() == player && board[4].getOwner() == player && board[5].getOwner() == player)
				|| (board[6].getOwner() == player && board[7].getOwner() == player && board[8].getOwner() == player)
				|| (board[0].getOwner() == player && board[3].getOwner() == player && board[6].getOwner() == player)
				|| (board[1].getOwner() == player && board[4].getOwner() == player && board[7].getOwner() == player)
				|| (board[2].getOwner() == player && board[5].getOwner() == player && board[8].getOwner() == player)
				|| (board[0].getOwner() == player && board[4].getOwner() == player && board[8].getOwner() == player)
				|| (board[2].getOwner() == player && board[4].getOwner() == player && board[6].getOwner() == player)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * This will draw the current gameboard on the screen.
	 */
	public void drawBoard() {
		System.out.println("	" + board[0].getOwner() + " | " + board[1].getOwner() + " | " + board[2].getOwner());
		System.out.println("       --- --- ---");
		System.out.println("	" + board[3].getOwner() + " | " + board[4].getOwner() + " | " + board[5].getOwner());
		System.out.println("       --- --- ---");
		System.out.println("	" + board[6].getOwner() + " | " + board[7].getOwner() + " | " + board[8].getOwner());
	}
}// end class
