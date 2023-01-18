// Andrew Stekar
// Mon., Mar. 7, 2022.

package ticTacToe_MachineLearning;

import java.io.*;
import java.util.Scanner;

public class FileReadingAndWriting {

	File File = new File("GameFiles.txt");// location of text file.
	String currentGame = "nnnnnnnnn"; // "blank" String to store current game.
	String line;// a single game from the stored games text file.
	boolean originalGame = true;// used when checking whether the same game has already been recorded.
	int[] nextPossibleMove = new int[9];// stores next possible move.
	boolean[] moveDecision = new boolean[9];// booleans used in avoidLoss method.

	/**
	 * Records each move into String "currentGame".
	 * 
	 * @param index the index of last move played.
	 */
	public void record(Integer index) {
		currentGame = currentGame.replaceFirst("n", index.toString());
	}

	/**
	 * Records a tie with "TT", win/loss with a "nn" onto "currentGame".
	 * 
	 * @param tie true if a tie occurs, false if a win/loss occurs.
	 */
	public void checkTie(boolean tie) {
		if (tie) {
			currentGame = currentGame.concat("TT");
		} else {
			currentGame = currentGame.concat("nn");
		}
	}

	/**
	 * Writes CurrentGame to text file when a game is done. The game is only written
	 * when it is finished to avoid "half games" being stored. Only original games
	 * are stored.
	 * 
	 * @throws IOException
	 */
	public void storeGame() throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(File, true));
		Scanner freader = new Scanner(File);
		while (freader.hasNextLine()) {// checking if game has already been stored in the text file
			line = freader.nextLine();
			if (line.equals(currentGame)) {
				originalGame = false;
			}
		}
		freader.close();
		if (originalGame) {// writing game to text file only if it is original
			writer.write(currentGame);
			writer.newLine();
			writer.close();
		}
	}

	/**
	 * Used in "winPath" and "drawPath" to return the most frequent next best move.
	 * 
	 * @param arr an array that stores the frequency of next moves in similar games
	 *            stored in the text file.
	 * @return the index of the next best move, 9 if no best move can be determined.
	 */
	public int nextMove(int[] arr) {
		int index = 0;
		int largest = arr[0];
		for (int i = 1; i < 9; ++i) {
			if (arr[i] > largest) {
				largest = arr[i];
				index = i;
			}
		}
		if (largest == 0) {
			return 9;
		} else {
			return index;
		}
	}

	/**
	 * Resets arrays each turn.
	 */
	public void resetTally() {
		for (int i = 0; i < 9; ++i) {
			nextPossibleMove[i] = 0;
		}
		for (int i = 0; i < 9; ++i) {
			moveDecision[i] = false;
		}
	}

	/**
	 * Returns a move to win a known game in "winMove".
	 * 
	 * @param turn the number of moves already played in the current game.
	 * @param arr  a String array that stores current board configuration.
	 * @return the index that will lead to known win, 9 if none are found.
	 */
	public int winMoveReturn(Integer turn, String[] arr) {
		if (line.charAt(turn + 1) == 0 && arr[0] == " ") {
			return 0;
		}
		for (int i = 1; i < 9; ++i) {
			if ((line.charAt(turn) - 48) == i && arr[i] == " ") {
				return i;
			}
		}
		return 9;
	}

	/**
	 * checks for known wins.
	 * 
	 * @param turn  the number of moves already played in the current game.
	 * @param start indicates who started the game (player or computer).
	 * @return the index of the next best move.
	 * @throws IOException
	 */
	public int winPath(Integer turn, Integer start) throws IOException {
		Scanner freader = new Scanner(File);
		while (freader.hasNextLine()) {// scanning through all stored games
			line = freader.nextLine();
			if (((line.indexOf("n") % 2 == 0 && start == 0) || (line.indexOf("n") % 2 == 1 && start == 1))
					&& line.substring(0, turn).equals(currentGame.substring(0, turn)) == true) {
				// recording most popular next move for a win
				++nextPossibleMove[line.charAt(turn) - 48];
			}
		}
		freader.close();
		return nextMove(nextPossibleMove);// return next best known move.
	}

	/**
	 * checks for possible draws.
	 * 
	 * @param turn the number of moves already played in the current game.
	 * @return the index of the next best move.
	 * @throws IOException
	 */
	public int drawPath(Integer turn) throws IOException {
		Scanner freader = new Scanner(File);
		while (freader.hasNextLine()) {// scanning through all stored games
			line = freader.nextLine();
			if (line.charAt(9) == 'T' && line.substring(0, turn).equals(currentGame.substring(0, turn)) == true) {
				// recording most popular next move that leads to a draw
				++nextPossibleMove[line.charAt(turn) - 48];
			}
		}
		freader.close();
		return nextMove(nextPossibleMove);// return next best known move
	}

	/**
	 * Searches for a winning move based on past games by only looking at the
	 * computer's past moves.
	 * 
	 * @param turn  the number of moves already played in the current game.
	 * @param start indicates who started the game (player or computer).
	 * @param arr   a String array that stores current board configuration.
	 * @return a move that leads to a win, or 9 if none found.
	 * @throws IOException
	 */
	public int winMove(Integer turn, Integer start, String[] arr) throws IOException {
		resetTally();
		Scanner freader = new Scanner(File);
		while (freader.hasNextLine()) {// scanning through all stored games
			line = freader.nextLine();
			// checking for win, if computer moves are the same, and if stored game ends
			// after next move
			if ((start == 1 && line.indexOf("n") % 2 == 1 && line.charAt(turn + 1) == 'n' && ((turn == 4
					&& line.charAt(0) == currentGame.charAt(0) && line.charAt(2) == currentGame.charAt(2))
					|| (turn == 6 && line.charAt(0) == currentGame.charAt(0) && line.charAt(2) == currentGame.charAt(2)
							&& line.charAt(4) == currentGame.charAt(4))))
					|| (start == 0 && line.indexOf("n") % 2 == 0 && line.charAt(turn + 1) == 'n'
							&& ((turn == 5 && line.charAt(1) == currentGame.charAt(1)
									&& line.charAt(3) == currentGame.charAt(3))
									|| (turn == 7 && line.charAt(1) == currentGame.charAt(1)
											&& line.charAt(3) == currentGame.charAt(3)
											&& line.charAt(5) == currentGame.charAt(5))))) {
				freader.close();
				return winMoveReturn(turn, arr);
			}
		}
		freader.close();
		return 9;
	}

	/**
	 * Searches for a block based on past games by only looking at the computer's
	 * past moves.
	 * 
	 * @param turn  the number of moves already played in the current game.
	 * @param start indicates who started the game (player or computer).
	 * @return a move that blocks a loss, 9 if none found.
	 * @throws IOException
	 */
	public int blockLoss(Integer turn, Integer start) throws IOException {
		Scanner freader = new Scanner(File);
		while (freader.hasNextLine()) {// scanning through all stored games
			line = freader.nextLine();
			// checking for loss, if player moves are the same, and if stored game ends
			// after next move
			if ((start == 0 && line.indexOf("n") % 2 == 1 && line.charAt(turn + 2) == 'n' && ((turn == 3
					&& line.charAt(0) == currentGame.charAt(0) && line.charAt(2) == currentGame.charAt(2))
					|| (turn == 5 && line.charAt(0) == currentGame.charAt(0) && line.charAt(2) == currentGame.charAt(2)
							&& line.charAt(4) == currentGame.charAt(4))
					|| (turn == 7 && line.charAt(0) == currentGame.charAt(0) && line.charAt(2) == currentGame.charAt(2)
							&& line.charAt(4) == currentGame.charAt(4) && line.charAt(6) == currentGame.charAt(6))))
					|| (start == 1 && line.indexOf("n") % 2 == 0 && line.charAt(turn + 2) == 'n'
							&& ((turn == 4 && line.charAt(1) == currentGame.charAt(1)
									&& line.charAt(3) == currentGame.charAt(3))
									|| (turn == 6 && line.charAt(1) == currentGame.charAt(1)
											&& line.charAt(3) == currentGame.charAt(3)
											&& line.charAt(5) == currentGame.charAt(5))))) {
				freader.close();
				return line.charAt(turn + 1) - 48;
			}
		}
		freader.close();
		return 9;
	}

	/**
	 * Finds a move that will not lead to a known loss.
	 * 
	 * @param turn  the number of moves already played in the current game.
	 * @param start indicates who started the game (player or computer).
	 * @param arr   a String array that stores current board configuration.
	 * @return a move that has not yet lost in previous games, 9 if all moves have
	 *         led to a loss.
	 * @throws IOException
	 */
	public int avoidLoss(Integer turn, Integer start, String[] arr) throws IOException {
		resetTally();
		// avoids known losses and creates original games
		Scanner freader = new Scanner(File);
		while (freader.hasNextLine()) {// scanning through all stored games
			line = freader.nextLine();
			// checking if loss and if current game is same as a stored game
			if (((line.indexOf("n") % 2 == 1 && start == 0) || (line.indexOf("n") % 2 == 0 && start == 1))
					&& line.substring(0, turn).equals(currentGame.substring(0, turn)) == true) {
				moveDecision[line.charAt(turn) - 48] = true;
			}
		}
		freader.close();
		// returning a move that has not been made before
		for (int i = 0; i < 9; ++i) {
			if (moveDecision[i] == false && arr[i] == " ") {
				return i;
			}
		}
		return 9;// if all moves lead to a known loss, next move will be entirely random
	}
}// end class
