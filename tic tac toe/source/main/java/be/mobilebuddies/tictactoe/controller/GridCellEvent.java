package be.mobilebuddies.tictactoe.controller;
import be.mobilebuddies.tictactoe.model.CellValue;


public class GridCellEvent {


	private CellValue value;
	private int row;
	private int col;	
	
	/**
	 * default constructor for the TicTacToe event. Does not initialise any value; this should be done using
	 * the different with... methods, for cascaded initialisation (to keep the constructor as light as possible).
	 */
	public GridCellEvent() {
		super();
	}
	
	/**
	 * initialise the value of the cell.
	 * @param v the value to be set
	 * @return the event with the cell value set
	 */
	public GridCellEvent withValue(CellValue v) {
		value = v;
		return this;
	}
	
	/**
	 * initialise the value of the row of the cell in the grid that got filled.
	 * @param r the row value to be set
	 * @return the event with the row value set
	 */
	public GridCellEvent withRow(int r) {
		row = r;
		return this;
	}
	
	/**
	 * initialise the value of to column of the cell in the grid that got filled.
	 * @param c the column value to be set
	 * @return the event with the row value set
	 */
	public GridCellEvent withCol(int c) {
		col = c;
		return this;
	}
	

	/**
	 * returns the value of the cell that got filled in
	 * @return the cell value
	 */
	public CellValue getvalue() {
		return value;
	}

	/**
	 * returns the row of the cell that got filled in
	 * @return the row value
	 */
	public int getRow() {
		return row;
	}

	/**
	 * returns the column of the cell that got filled in
	 * @return the column value
	 */
	public int getCol() {
		return col;
	}	
	
}
