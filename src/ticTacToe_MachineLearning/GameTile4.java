// Andrew Stekar
// Mon., Mar. 7, 2022.

package ticTacToe_MachineLearning;

public class GameTile4 {
	/**
	 * Stores a String representing the owner "X" or "O" of a GameTile.
	 */
	protected String owner;

	/**
	 * Constructs a GameTile, sets owner to null by default.
	 */
	public GameTile4() {
		owner = null;
	}

	/**
	 * This will return who owns this particular GameTile.
	 * 
	 * @return String "X" if player 1 owns tile, "O" if player 2 owns tile, empty
	 *         string if unowned.
	 */
	public String getOwner() {
		if (this.owner == null) {
			return " ";
		} else {
			return owner;
		}
	}

	/**
	 * This will assign a new owner to the game tile.
	 * 
	 * @param player A String indicating which player will own the tile ("X" or "O").
	 */
	public void setOwner(String player) {

		owner = player;

	}

	/**
	 * This will determine whether any player owns a particular tile.
	 * 
	 * @return boolean true if a player owns the tile, false otherwise.
	 */
	public boolean owned() {
		if (this.owner == null) {
			return false;
		} else {
			return true;
		}
	}
}// end class
