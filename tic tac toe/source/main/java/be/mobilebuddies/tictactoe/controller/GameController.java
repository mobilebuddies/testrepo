package be.mobilebuddies.tictactoe.controller;

import java.util.Date;
import java.util.Observable;
import java.util.Random;

import be.mobilebuddies.tictactoe.model.CellValue;
import be.mobilebuddies.tictactoe.model.Grid;

/**
 * The controller for the Tic-Tac-Toe game. This controller takes care of all the logic to determine
 * how the game will behave, how difficult it is to beat the computer, the scores to be maintained, etc.
 * 
 * @author koen
 */
public class GameController extends Observable {

	private Grid grid;
	private boolean gameEnded;
	private boolean computersTurn; 
	private int playerWins;
	private int computerWins;
	private Random randomValue;
	private Level level = Level.ADVANCED;
	
	/**
	 * creates a new controller, initialising some values, except the grid for the play field.
	 */
	public GameController() {
//		grid = new Grid().initialize();
		randomValue = new Random(new Date().getTime());
		gameEnded = false;
		computersTurn = false;
		playerWins = 0;
		computerWins = 0;
	}
	
	/** 
	 * assign a grid to the game controller
	 * @param grid the grid of the play field
	 * @return the came controller's instance with the grid assigned
	 */
	public GameController withGrid(Grid grid) {
		this.grid = grid.initialize();
		return this;
	}
	
	/**
	 * Applies the difficulty level of the game. This is an enum value:
	 * <ul>
	 * <li>BEGINNER
	 * <li>INTERMEDIATE
	 * <li>ADVANCED
	 * </ul> 
	 * 
	 * @param level the difficulty level.
	 * @return
	 */
	public GameController withLevel(Level level) {
		this.level = level;
		return this;
	}
	
	
	/**
	 * Determines who will start the game: the computer or you? This is based on a random value; 
	 * @return
	 */
	public CellValue whoStartsGame() {
		grid = grid.initialize();
		gameEnded = false;
		// decide who can start: computer or player
		computersTurn = computerStarts();
		return (computersTurn) ? CellValue.COMPUTER : CellValue.PLAYER;
	}
	
	/**
	 * Determines whether or not the computer can start the game. This is determined by a random value.
	 * 
	 * @return true if the computer starts the game, otherwise false
	 */
	private boolean computerStarts() {
		return randomValue.nextBoolean();
	}

	/**
	 * Makes the computer decide which cell of the grid to fill.
	 * If the game has already ended, the computer will not make a move.
	 * If the grid is fill, the game also ends.
	 * This is the sequence of checks the computer will do to decide which cell to fill:
	 * <ul>
	 * <li>can I win? (any level)
	 * <li>can I prevent winning? (advanced & intermediate level)
	 * <li>can I make 2 in a row, with another free space in that row to score 3? (advanced level only)
	 * <li>can I prevent the player from making 2 in a row? (advanced level only)
	 * <li>if not, fill a random cell (any level)
	 * </ul>
	 */
	public void doComputersMove() {
		TicTacToeEvent event = new TicTacToeEvent();
		GridCellEvent cellEvent = null;
		RowOf3Event rowEvent = null;
		if (!gameEnded) {
			if (grid.isFull()) {
				gameEnded = true;
			} else {
				cellEvent = canComputerWin();
				if (cellEvent != null) {
					event.withGridCellEvent(cellEvent);
					rowEvent = detect3InARow();
					event.withRowOf3Event(rowEvent);
					gameEnded = true;
					computerWins ++;
				} else {
					if (level == Level.ADVANCED || level == Level.INTERMEDIATE) {
						cellEvent = canComputerPreventWinning();	
					}
					if (cellEvent == null && level == Level.ADVANCED) {
						cellEvent = canComputerMake2();
					}
					if (cellEvent == null & level == Level.ADVANCED) {
						cellEvent = canComputerPrevent2();
					}
					if (cellEvent == null) {
						cellEvent = makeRandomComputerMove();
						rowEvent = detect3InARow();
						if (rowEvent != null) {
							gameEnded = true;
							computerWins ++;
							event.withRowOf3Event(rowEvent);
						}
					}
					event.withGridCellEvent(cellEvent);
					computersTurn = false;
				}
				if (grid.isFull()) {
					gameEnded = true;
				}
				// dispatch the event
				this.setChanged();
				this.notifyObservers(event);
			}
		}
	}
	
	/**
	 * check whether the computer can win the game (already have 2 in a row and
	 * can score a 3rd one). If so, the move will be made.
	 * 
	 * @return true if the winning move is made, otherwise false
	 */
	public GridCellEvent canComputerWin() {
		// row per row: can we score 3 in a row?
		GridCellEvent event = null;
		event = canMake3Horizontal(CellValue.COMPUTER);
		if (event == null) {
			// column per column: can we score 3 in a column?
			event = canMake3Vertical(CellValue.COMPUTER);
		}
		if (event == null) {
			event = canMake3DiagonalUp(CellValue.COMPUTER);
		}
		if (event == null) {
			event = canMake3DiagonalDown(CellValue.COMPUTER);
		}
		
		return event;
	}
	
	/**
	 * Checks whether the specified player can score 3 in a row, horizontally. 
	 * If so, the computer will fill that space, either scoring
	 * 3 himself or preventing the player from scoring 3 in a row.
	 * 
	 * @return an instance of TicTacToeEvent with the cell information filled in if a move is made
	 * otherwise null
	 */
	private GridCellEvent canMake3Horizontal(CellValue who) {
		GridCellEvent event = null;
		for (int row = 0; row < grid.getRows(); row ++) {
			for (int col = 0; col + 2 < grid.getCols(); col ++) {
				// from the current cell: look 2 cells ahead (if possible)
				event = canMake3(who, row, col, row, col + 1, row, col + 2);

				if (event != null) {
					break;
				}
			}
			if (event != null) {
				break;
			}
		}
		return event;
	}

	/**
	 * Checks whether the specified player can score 3 in a row, vertically. 
	 * If so, the computer will fill that space, either scoring
	 * 3 himself or preventing the player from scoring 3 in a row.
	 *  
	 * @return an instance of TicTacToeEvent with the cell information filled in if a move is made
	 * otherwise null
	 */
	private GridCellEvent canMake3Vertical(CellValue who) {
		GridCellEvent event = null;
		for (int col = 0; col < grid.getCols(); col ++) {
			for (int row = 0; row + 2 < grid.getRows(); row ++) {
				// from the current cell: look 2 cells ahead (if possible)
					event = canMake3(who, row, col, row + 1, col, row + 2, col);

				if (event != null) {
					break;
				}
			}
			if (event != null) {
				break;
			}
		}
		return event;
	}
	
	/**
	 * Checks whether the specified player can score 3 in a row, in whatever direction (horizontally, 
	 * vertically, diagonal up or diagonal down). If so, the computer will fill that space, either scoring
	 * 3 himself or preventing the player from scoring 3 in a row.
	 * 
	 * @param who for whom the check of 3 in a row has to be made
	 * @param curRow row of the current cell
	 * @param curCol column of the current cell
	 * @param nextRow row the next cell
	 * @param nextCol column of the next cell
	 * @param next2Row row of the 3rd cell
	 * @param next2Col column of the 3rd cell
	 * 
	 * @return an instance of TicTacToeEvent with the cell information filled in if a move is made
	 * otherwise null
	 */
	private GridCellEvent canMake3(CellValue who, int curRow, int curCol, int nextRow, int nextCol, int next2Row, int next2Col) {
		GridCellEvent event = null;
		if (grid.getValue(curRow, curCol) == who) {
			// X _ X
			if (grid.getValue(nextRow, nextCol) == CellValue.EMPTY && grid.getValue(next2Row, next2Col) == who) {
				// Fill computer here! to win or to prevent winning!
				grid.setValue(nextRow, nextCol, CellValue.COMPUTER);
				event = new GridCellEvent()
					.withValue(CellValue.COMPUTER)
					.withRow(nextRow)
					.withCol(nextCol);
			} else {
				// X X _
				if (grid.getValue(nextRow, nextCol) == who && grid.getValue(next2Row, next2Col) == CellValue.EMPTY) {
					// Fill computer here! to win or to prevent winning!
					grid.setValue(next2Row, next2Col, CellValue.COMPUTER);
					event = new GridCellEvent()
						.withValue(CellValue.COMPUTER)
						.withRow(next2Row)
						.withCol(next2Col);
				}
			}
		} else {
			if (grid.getValue(curRow, curCol) == CellValue.EMPTY) {
				// _ X X
				if (grid.getValue(nextRow, nextCol) == who && grid.getValue(next2Row, next2Col) == who) {
					// Fill computer here! to win or to prevent winning!
					grid.setValue(curRow, curCol, CellValue.COMPUTER);
					event = new GridCellEvent()
						.withValue(CellValue.COMPUTER)
						.withRow(curRow)
						.withCol(curCol);
				}
			} else {
				// go to the next cell;
			}
		}
		return event;
	}
	
	/**
	 * Checks whether the specified player can score 3 in a row, diagonally down 
	 * (from top left to bottom right). 
	 * If so, the computer will fill that space, either scoring 
	 * 3 himself or preventing the player from scoring 3 in a row.
	 * 
	 * @return an instance of TicTacToeEvent with the cell information filled in if a move is made
	 * otherwise null
	 */
	private GridCellEvent canMake3DiagonalDown(CellValue who) {
		GridCellEvent event = null;
		for (int row = 0; row + 2 < grid.getRows(); row ++) {
			for (int col = 0; col + 2 < grid.getCols(); col ++) {
				event = canMake3(who, row, col, row + 1, col + 1, row + 2, col + 2);
				if (event != null) {
					break;
				}
			}
			if (event != null) {
				break;
			}
		}
		return event;
	}

	/**
	 * Checks whether the specified player can score 3 in a row, diagonally up 
	 * (from bottom left to top right). 
	 * If so, the computer will fill that space, either scoring
	 * 3 himself or preventing the player from scoring 3 in a row.
	 * 
	 * @return an instance of TicTacToeEvent with the cell information filled in if a move is made
	 * otherwise null
	 */
	private GridCellEvent canMake3DiagonalUp(CellValue who) {
		GridCellEvent event = null;
		for (int row = grid.getRows() - 1; row - 2 >= 0; row --) {
			for (int col = 0; col + 2 < grid.getCols(); col ++) {
				event = canMake3(who, row, col, row - 1, col + 1, row - 2, col + 2);
				if (event != null) {
					break;
				}
			}
			if (event != null) {
				break;
			}
		}
		return event;
	}
	
		
	/**
	 * check whether the computer can avoid the player from winning the game
	 * (player has 2 in a row and can score a 3rd one). If so, the move will be 
	 * made.
	 * 
	 * @return an instance of TicTacToeEvent with the cell information filled in if a move is made
	 * otherwise null
	 */
	public GridCellEvent canComputerPreventWinning() {
		// row per row: can we score 3 in a row?
		GridCellEvent event = null;
		event = canMake3Horizontal(CellValue.PLAYER);
		if (event == null) {
			// column per column: can we score 3 in a column?
			event = canMake3Vertical(CellValue.PLAYER);
		}
		if (event == null) {
			event = canMake3DiagonalUp(CellValue.PLAYER);
		}
		if (event == null) {
			event = canMake3DiagonalDown(CellValue.PLAYER);
		}
		
		return event;
	}
	
	/**
	 * check whether the computer can make at least 2 in a row, as a preparation
	 * for 3 in a row. If so, the move will be made. 
	 * <p>
	 * A cell will not be picked if there is no 3rd cell free to complete 3 in a row.
	 * E.g.:
	 * <ul>
	 * <li><pre>O _ _</pre>
	 * <li><pre>_ O _</pre>
	 * <li><pre>_ _ O</pre>
	 * </ul>
	 * <p>But not: 
	 * <pre>X O _</pre>
	 * 
	 * @return an instance of TicTacToeEvent with the cell information filled in if a move is made
	 * otherwise null
	 */
	public GridCellEvent canComputerMake2() {
		
		GridCellEvent event = null;
		event = canMake2Horizontal(CellValue.COMPUTER);
		if (event == null) {
			event = canMake2Vertical(CellValue.COMPUTER);
		}
		if (event == null) {
			event = canMake2DiagonalDown(CellValue.COMPUTER);
		}
		if (event == null) {
			event = canMake2DiagonalUp(CellValue.COMPUTER);
		}
		
		return event;
	}

	/**
	 * Checks whether the computer can prevent the player from making 2 in a row, as a preparation for
	 * 3 in a row. If so, the move will be made.
	 * <p>
	 * A cell will not be picked if there is no 3rd cell free to complete 3 in a row.
	 * E.g.:
	 * <ul>
	 * <li><code>O _ _</code>
	 * <li><code>_ O _</code>
	 * <li><code>_ _ O</code>
	 * </ul>
	 * <p>But not: 
	 * <code>O X _</code>
	 * 
	 * @return an instance of TicTacToeEvent with the cell information filled in if a move is made
	 * otherwise null
	 */
	public GridCellEvent canComputerPrevent2() {
		GridCellEvent event = null;

		event = canMake2Horizontal(CellValue.PLAYER);
		if (event == null) {
			event = canMake2Vertical(CellValue.PLAYER);
		}
		if (event == null) {
			event = canMake2DiagonalDown(CellValue.PLAYER);
		}
		if (event == null) {
			event = canMake2DiagonalUp(CellValue.PLAYER);
		}
		
		
		return event;
	}
	
	/**
	 * checks whether the specified player can make 2 cells in a row horizontally, 
	 * with as a preparation for 3 in a row.
	 * <p>
	 * If so, the move will be made, either to prepare 3 in a row for the computer 
	 * or to prevent the player from preparing 3 in a row.
	 *  
	 * @param who the player to check 2 in a row horizontally
	 * @return an instance of TicTacToeEvent with the cell information filled in if a move is made
	 * otherwise null
	 */
	private GridCellEvent canMake2Horizontal(CellValue who) {
		GridCellEvent event = null;
		for (int row = 0; row < grid.getRows(); row ++) {
			for (int col = 0; col + 2 < grid.getCols(); col ++) {
				// from the current cell: look 2 cells ahead (if possible)
				event = canMake2(who, row, col, row, col + 1, row, col + 2);

				if (event != null) {
					break;
				}
			}
			if (event != null) {
				break;
			}
		}
		return event;
	}
	/**
	 * checks whether the specified player can make 2 cells in a row vertically, 
	 * with as a preparation for 3 in a row.
	 * <p>
	 * If so, the move will be made, either to prepare 3 in a row for the computer 
	 * or to prevent the player from preparing 3 in a row.
	 *  
	 * @param who the player to check 2 in a row vertically
	 * @return an instance of TicTacToeEvent with the cell information filled in if a move is made
	 * otherwise null
	 */
	
	private GridCellEvent canMake2Vertical(CellValue who) {
		GridCellEvent event = null;
		for (int col = 0; col < grid.getCols(); col ++) {
			for (int row = 0; row + 2 < grid.getRows(); row ++) {
				// from the current cell: look 2 cells ahead (if possible)
				event = canMake2(who, row, col, row + 1, col, row + 2, col);

				if (event != null) {
					break;
				}
			}
			if (event != null) {
				break;
			}
		}
		return event;

	}
	
	/**
	 * checks whether the specified player can make 2 cells in a row diagonally up 
	 * (from bottom left to top right), with as a preparation for 3 in a row.
	 * <p>
	 * If so, the move will be made, either to prepare 3 in a row for the computer 
	 * or to prevent the player from preparing 3 in a row.
	 *  
	 * @param who the player to check 2 in a row diagonally up
	 * @return an instance of TicTacToeEvent with the cell information filled in if a move is made
	 * otherwise null
	 */
	private GridCellEvent canMake2DiagonalUp(CellValue who) {
		GridCellEvent event = null;
		for (int row = grid.getRows() - 1; row - 2 >= 0; row --) {
			for (int col = 0; col + 2 < grid.getCols(); col ++) {
				event = canMake2(who, row, col, row - 1, col + 1, row - 2, col + 2);
				if (event != null) {
					break;
				}
			}
			if (event != null) {
				break;
			}
		}
		return event;
	}
	
	/**
	 * checks whether the specified player can make 2 cells in a row diagonally down 
	 * (from top left to bottom right), with as a preparation for 3 in a row.
	 * <p>
	 * If so, the move will be made, either to prepare 3 in a row for the computer 
	 * or to prevent the player from preparing 3 in a row.
	 *  
	 * @param who the player to check 2 in a row
	 * @return an instance of TicTacToeEvent with the cell information filled in if a move is made
	 * otherwise null
	 */
	private GridCellEvent canMake2DiagonalDown(CellValue who) {
		GridCellEvent event = null;
		for (int row = 0; row + 2 < grid.getRows(); row ++) {
			for (int col = 0; col + 2 < grid.getCols(); col ++) {
				event = canMake2(who, row, col, row + 1, col + 1, row + 2, col + 2);
				if (event != null) {
					break;
				}
			}
			if (event != null) {
				break;
			}
		}
		return event;
	}

	/**
	 * checks whether the specified player can make 2 cells in a row in whatever direction, 
	 * with as a preparation for 3 in a row.
	 * <p>
	 * If so, the move will be made, either to prepare 3 in a row for the computer 
	 * or to prevent the player from preparing 3 in a row.
	 *  
	 * @param who the player to check 2 in a row
	 * @param curRow row of the current cell
	 * @param curCol column of the current cell
	 * @param nextRow row the next cell
	 * @param nextCol column of the next cell
	 * @param next2Row row of the 3rd cell
	 * @param next2Col column of the 3rd cell
	 * @return an instance of TicTacToeEvent with the cell information filled in if a move is made
	 * otherwise null
	 */
	private GridCellEvent canMake2(CellValue who, int curRow, int curCol, int nextRow, int nextCol, int next2Row, int next2Col) {
		GridCellEvent event = null;
		if (grid.getValue(curRow, curCol) == CellValue.EMPTY) {
			// _ _ X
			if (grid.getValue(nextRow, nextCol) == CellValue.EMPTY && grid.getValue(next2Row, next2Col) == who) {
				grid.setValue(curRow, curCol, CellValue.COMPUTER);
				event = new GridCellEvent()
					.withValue(CellValue.COMPUTER)
					.withRow(curRow)
					.withCol(curCol);
			} else {
				// _ X _
				if (grid.getValue(nextRow, nextCol) == who && grid.getValue(next2Row, next2Col) == CellValue.EMPTY) {
					grid.setValue(curRow, curCol, CellValue.COMPUTER);
					event = new GridCellEvent()
						.withValue(CellValue.COMPUTER)
						.withRow(curRow)
						.withCol(curCol);
				}
			}
		} else {
			if (grid.getValue(curRow, curCol) == who) {
				// X _ _ 
				if (grid.getValue(nextRow, nextCol) == CellValue.EMPTY && grid.getValue(next2Row, next2Col) == CellValue.EMPTY) {
					grid.setValue(nextRow, nextCol, CellValue.COMPUTER);
					event = new GridCellEvent()
						.withValue(CellValue.COMPUTER)
						.withRow(nextRow)
						.withCol(nextCol);
				}
			} else {
				// go to the next cell;
			}
		}
		return event;
	}
	
	/**
	 * The computer will determine where a free place can be found (at random) 
	 * and will mark that place on the grid.
	 * The only check happening here is whether the computer by accident scored 3 in a row... 
	 * @return true if the computer by accident won the game, otherwise false
	 */
	private GridCellEvent makeRandomComputerMove() {
		GridCellEvent event = null;
		boolean done = false;
		while (!done) {
			int row = randomValue.nextInt(grid.getRows());
			int col = randomValue.nextInt(grid.getCols());
			if (grid.isEmpty(row, col)) {
				grid.setValue(row, col, CellValue.COMPUTER);
				done = true;
				event = new GridCellEvent()
					.withValue(CellValue.COMPUTER)
					.withRow(row)
					.withCol(col);
			}
		}
		return event;
	}
	
	/**
	 * Checks whether the specified cell is empty. If so, the cell will be marked, 
	 * otherwise the player has to specify another cell.
	 * 
	 * @param row the row of the cell to be filled
	 * @param col the column of the cell to be filled
	 * @return true if the specified cell was valid, otherwise false
	 */
	public boolean fillCell (int row, int col) {
		TicTacToeEvent event = new TicTacToeEvent();
		boolean isValid = false;
		if (grid.isEmpty(row, col)) {
			grid.setValue(row, col, CellValue.PLAYER);
			isValid = true;
			if (grid.isFull()) {
				gameEnded = true;
			} else {
				this.computersTurn = true;				
			}
			RowOf3Event rowEvent = detect3InARow();
			
			if (rowEvent != null) {
				event.withRowOf3Event(rowEvent);
				gameEnded = true;
				playerWins ++;
			}
			GridCellEvent cellEvent = new GridCellEvent()
				.withRow(row)
				.withCol(col)
				.withValue(CellValue.PLAYER);
			event.withGridCellEvent(cellEvent);
			setChanged();
			notifyObservers(event);
			
				
		}
		return isValid;
	}
	
	/**
	 * detects whether there are 3 in a row, no matter from whom.
	 * @return true if there is a winner, otherwise false
	 */
	private RowOf3Event detect3InARow() {
		RowOf3Event event = null;
		event = has3Horizontal();
		if (event == null) {
			event = has3Vertical();
		}
		if (event == null) {
			event = has3DiagonalUp();
		}
		if (event == null) {
			event = has3DiagonalDown();
		}
		return event;
	}
	
	/**
	 * detects whether there are 3 in a row, horizontally, no matter from whom.
	 * @return true if there is a winner, otherwise false
	 */
	private RowOf3Event has3Horizontal() {
		RowOf3Event event = null;
		for (int row = 0; row < grid.getRows(); row ++) {
			for (int col = 0; col < grid.getCols() -2; col ++) {
				if (grid.getValue(row, col) != CellValue.EMPTY) {
					if (grid.getValue(row, col) == grid.getValue(row, col + 1) && grid.getValue(row, col) == grid.getValue(row, col + 2)) {
						event = new RowOf3Event(grid.getValue(row, col))
							.withCell1(row, col)
							.withCell2(row, col + 1)
							.withCell3(row, col + 2);
						break;
					}
				}
			}
			if (event != null) {
				break;
			}
		}
		return event;
	}
	
	/**
	 * detects whether there are 3 in a row, vertically, no matter from whom
	 * @return true if there is a winner, otherwise false
	 */
	private RowOf3Event has3Vertical() {
		RowOf3Event event = null;
		for (int col = 0; col < grid.getCols(); col ++) {
			for (int row = 0; row < grid.getRows() -2; row ++) {
				if (grid.getValue(row, col) != CellValue.EMPTY) {
					if (grid.getValue(row, col) == grid.getValue(row + 1, col) && grid.getValue(row, col) == grid.getValue(row + 2, col)) {
						event = new RowOf3Event(grid.getValue(row, col))
							.withCell1(row, col)
							.withCell2(row + 1, col)
							.withCell3(row + 2, col);
						break;
					}
				}
			}
			if (event != null) {
				break;
			}
		}
		return event;
		
	}
	
	/**
	 * detects whether there are 3 in a row, diagonally up (bottom left to top right), no matter from whom.
	 * @return true if there is a winner, otherwise false
	 */
	private RowOf3Event has3DiagonalUp() {
		RowOf3Event event = null;
		for (int row = grid.getRows() - 1; row - 2 >= 0; row --) {
			for (int col = 0; col + 2 < grid.getCols(); col ++) {
				if (grid.getValue(row, col) != CellValue.EMPTY) {
					if (grid.getValue(row, col) == grid.getValue(row - 1, col + 1) && grid.getValue(row, col) == grid.getValue(row -2, col + 2)) {
						event = new RowOf3Event(grid.getValue(row, col))
							.withCell1(row, col)
							.withCell2(row - 1, col + 1)
							.withCell3(row - 2, col + 2);
						break;
					}
				}
			}
			if (event != null) {
				break;
			}
		}
		
		return event;
	}
	
	/**
	 * detects whether there are 3 in a row, diagonally down (top left to bottom right), no matter from whom.
	 * @return true if there is a winner, otherwise false
	 */
	private RowOf3Event has3DiagonalDown() {
		RowOf3Event event = null;
		for (int row = 0; row < grid.getRows() - 2; row ++) {
			for (int col = 0; col < grid.getCols() - 2; col ++) {
				if (grid.getValue(row, col) != CellValue.EMPTY) {
					if (grid.getValue(row, col) == grid.getValue(row + 1, col + 1) && grid.getValue(row, col) == grid.getValue(row + 2, col + 2)) {
						event = new RowOf3Event(grid.getValue(row, col))
							.withCell1(row, col)
							.withCell2(row + 1, col + 1)
							.withCell3(row + 2, col + 2);
						break;
					}
				}
			}
			if (event != null) {
				break;
			}
		}
		return event;	
	}
	
	/**
	 * returns the status of the game: true if ended, otherwise false.
	 * @return true if the game has ended, otherwise false
	 */
	public boolean isGameEnded() {
		return gameEnded;
	}
	
	/**
	 * returns a string representation of the controller. This is the status of the grid (like the string
	 * representation of the Grid class). If the game has ended, the score is shown.
	 */
	public String toString() {
		StringBuffer strBuf = new StringBuffer(grid.toString());
		if (gameEnded) {
			strBuf.append("\nGame ended. Score: You ");
			strBuf.append(playerWins);
			strBuf.append(" - Computer ");
			strBuf.append(computerWins);
		}
		return strBuf.toString();
	}

	/**
	 * returns the score of the player - the number of games won.
	 * @return the number of games won by the player.
	 */
	public int getPlayerWins() {
		return playerWins;
	}

	/**
	 * returns the score of the computer - the number of games won.
	 * @return the number of games won by the computer.
	 */
	public int getComputerWins() {
		return computerWins;
	}


	public Level getLevel() {
		return level;
	}
}
