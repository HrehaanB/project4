import java.sql.SQLOutput;

public class _2048
{
	private final int rows = 4;
	private final int cols = 4;
	private int[][] board;
	private int[][] previousBoard;
	private int score;
	private int previousScore;
	
	/**
	 * Initializes board and previousBoard using rows and cols.
	 * Uses the generateTile method to add two random tiles to board.
	 */
	public _2048()
	{
		board = new int[rows][cols];
		generateTile();
		generateTile();
	}
	
	/**
	 * Initializes the board of this object using the specified board.
	 * Initializes previousBoard using rows and cols.
	 * 
	 * Precondition: the specified board is a 4x4 2D Array.
	 * 
	 * @param board
	 */
	public _2048(int[][] board)
	{
		this.board = board;
		previousBoard = new int[rows][cols];
	}
	
	/**
	 * Generates a tile and add it to an empty spot on the board.
	 * 80% chance to generate a 2
	 * 20% chance to generate a 4
	 * 
	 * Does nothing if the board is full.
	 */
	private void generateTile()
	{
		if (!full()) {
			if (Math.random() >= 0.2) {
				while(true) {
					int a = (int) (Math.random() * 4);
					int b = (int) (Math.random() * 4);
					if (board[a][b] == 0) {
						board[a][b] = 2;
						break;
					}
				}
			}
			else {
				while(true) {
					int a = (int) (Math.random() * 4);
					int b = (int) (Math.random() * 4);
					if (board[a][b] == 0) {
						board[a][b] = 4;
						break;
					}
				}
			}
		}
	}
	
	/**
	 * Returns false if the board contains a 0, true otherwise.
	 * @return
	 */
	private boolean full()
	{
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				if (board[i][j] == 0) {
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * Returns the board.
	 * @return
	 */
	public int[][] getBoard()
	{
		return board;
	}
	
	/**
	 * Returns the score.
	 * @return
	 */
	public int getScore()
	{
		return score;
	}
	
	/**
	 * Saves board into previousBoard and score into previousScore
	 * then performs a move based on the specified direction:
	 * 
	 * Valid directions (not case sensitive):
	 *  up
	 *  down
	 *  left
	 *  right
	 *  
	 * Adds a new tile to the board using the generateTile method.
	 * 
	 * @param direction
	 */
	public void move(String direction)
	{
		previousBoard = board;
		previousScore = score;
		direction.toLowerCase();

		if (direction.equals("up")) {
			moveUp();
			moveUp();
			moveUp();
			moveUp();
		}
		else if (direction.equals("down")) {
			moveDown();
			moveDown();
			moveDown();
			moveDown();

		}
		else if (direction.equals("left")) {
			moveLeft();
			moveLeft();
			moveLeft();
			moveLeft();
		}
		else if (direction.equals("right")) {
			moveRight();
			moveRight();
			moveRight();
			moveRight();
		}
		generateTile();
	}
	
	/**
	 * Shifts all the tiles up, combines like tiles that collide.
	 */
	private void moveUp()
	{
		for (int i = 1; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				if (board[i][j] == board[i - 1][j]) {
					board[i - 1][j] = board[i][j] + board[i - 1][j];
					board[i][j] = 0;
				}
				else if (board[i - 1][j] == 0) {
					board[i - 1][j] = board[i][j];
					board[i][j] = 0;
				}
			}
		}
	}
	
	/**
	 * Shifts all the tiles down, combines like tiles that collide.
	 */
	private void moveDown()
	{
		for (int i = rows - 2; i >= 0; i--) {
			for (int j = 0; j < cols; j++) {
				if (board[i][j] == board[i + 1][j]) {
					board[i + 1][j] = board[i][j] + board[i + 1][j];
					board[i][j] = 0;
				}
				else if (board[i + 1][j] == 0) {
					board[i + 1][j] = board[i][j];
					board[i][j] = 0;
				}
			}
		}
	}
	
	/**
	 * Shifts all the tiles left, combines like tiles that collide.
	 */
	private void moveLeft()
	{
		for (int i = 0; i < rows; i++) {
			for (int j = 1; j < cols; j++) {
				if (board[i][j] == board[i][j - 1]) {
					board[i][j - 1] = board[i][j] + board[i][j - 1];
					board[i][j] = 0;
				}
				else if (board[i][j - 1] == 0) {
					board[i][j - 1] = board[i][j];
					board[i][j] = 0;
				}
			}
		}
	}
	
	/**
	 * Shifts all the tiles right, combines like tiles that collide.
	 */
	private void moveRight()
	{
		for (int i = 0; i < rows; i++) {
			for (int j = cols - 2; j >= 0; j--) {
				if (board[i][j] == board[i][j + 1]) {
					board[i][j + 1] = board[i][j] + board[i][j + 1];
					board[i][j] = 0;
				}
				else if (board[i][j + 1] == 0) {
					board[i][j + 1] = board[i][j];
					board[i][j] = 0;
				}
			}
		}
	}
	
	/**
	 * Sets board to previousBoard and score to previousScore
	 */
	public void undo()
	{
		board = previousBoard;
		score = previousScore;
	}
	
	/**
	 * Returns true if the game is over, false otherwise.
	 * @return
	 */
	public boolean gameOver()
	{
		if (full() || score == 2048) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Returns a String representation of this object.
	 */
	public String toString()
	{
		String rtn = "";
		
		for(int[] row : board)
		{
			rtn += "|";
			for(int num : row)
				if(num != 0)
				{
					String str = num + "";
					while(str.length() < 4)
						str = " " + str;
					rtn += str;
					
				}
				else
					rtn += "    ";
			rtn += "|\n";
		}
		
		rtn += "Score: " + score + "\n";
		
		return rtn;
	}
}
