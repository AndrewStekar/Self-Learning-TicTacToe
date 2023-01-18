// Andrew Stekar
// Mon., Mar. 7, 2022.

package ticTacToe_MachineLearning;

import java.io.*;

public class PlayTicTacToe4 {
	public static void main(String args[]) throws Exception {
		BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in), 1);
		GameBoard4 game = new GameBoard4();// makes a blank game board
		FileReadingAndWriting file = new FileReadingAndWriting();// creates object for file reading and writing class
		String[] boardConfig = new String[9];// stores current board configuration as a String array
		int turn = 0;
		int index = 0;
		int start;
		boolean errorFlag;
		boolean gameOver = false;
		System.out.println("You are X's");
		System.out.println("Press ENTER to continue");
		keyboard.readLine();
		if ((int) Math.floor(Math.random() * 2) % 2 == 0) {// randomly starting game
			start = 0;
			System.out.println("You Start!");
		} else {
			start = 1;
		}
		System.out.println();
		System.out.println();
		game.drawBoard();
		System.out.println();
		System.out.println();
		if (start == 1) {
			System.out.println("Computer Starts");
		}
		while (gameOver == false) {
			errorFlag = false;
			if ((start == 0 && turn % 2 == 0) || (start == 1 && turn % 2 == 1)) {// if it is players turn
				do {
					System.out.print("Enter your choice (1-9): ");
					try {
						index = Integer.parseInt(keyboard.readLine()) - 1;
					} catch (Exception e) {
						System.out.println("That square is already taken or invalid.  Try again");
						continue;
					}
					errorFlag = game.play("X", index);// Checking for validity of move
					if (errorFlag == false)
						System.out.println("That square is already taken or invalid.  Try again");
				} while (errorFlag == false);// makes sure player enters a square not yet used
				System.out.println();
				System.out.println();
				game.drawBoard();
				System.out.println();
				System.out.println();
				file.record(index);// recording the move played
				if (game.checkWin("X")) {
					System.out.println("     YOU WIN!!");
					gameOver = true;
					file.checkTie(false);// recording if game was a tie, or win/loss
				}
			} else {// computer turn
				do {// determining best move (if the method does not find a move, it returns 9)
					for (int i = 0; i < 9; ++i) {// populating current board String array
						boardConfig[i] = game.board[i].getOwner();
					}
					index = file.winMove(turn, start, boardConfig);// searching for known winning opportunities
					if (index == 9) {
						index = file.blockLoss(turn, start);// searching for known blocking opportunities
					}
					if (index == 9) {
						index = file.drawPath(turn);// searching for a known path to a draw
					}
					if (index == 9) {
						index = file.winPath(turn, start);// searching for a known path to a win
					}
					if (index == 9) {
						index = file.avoidLoss(turn, start, boardConfig);// making a new move / avoiding known losses
					}
					if (index == 9) {
						index = (int) Math.floor(Math.random() * 9);// random if all next possible moves lead to loss
					}
					errorFlag = game.play("O", index);
				} while (errorFlag == false);// makes sure computer enters a valid number
				System.out.println();
				System.out.println();
				game.drawBoard();
				System.out.println();
				System.out.println();
				file.record(index);// recording the move played
				if (game.checkWin("O")) {
					System.out.println("      You Lose  :(");
					gameOver = true;
					file.checkTie(false);// recording the game as a win/loss
				}
			}
			turn++;
			if (turn == 9 && gameOver == false) {
				gameOver = true;
				System.out.println("      DRAW");
				file.checkTie(true);// recording the game as a tie
			}
		} // end while
		file.storeGame();// storing game to test file when completed
	}// end main
}// end class
