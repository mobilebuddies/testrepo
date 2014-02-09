package be.mobilebuddies.tictactoe.controller;

/**
 * container class for the events that can occur during the game:
 * <ul>
 * <li>cell filled
 * <li>row of 3
 * </ul>
 * @author koen
 *
 */
public class TicTacToeEvent {

	private GridCellEvent cellEvent;
	private RowOf3Event rowEvent;
	
	public TicTacToeEvent() {
		// do nothing
	}
	
	public TicTacToeEvent withGridCellEvent (GridCellEvent event) {
		cellEvent = event;
		return this;
	}
	
	public TicTacToeEvent withRowOf3Event (RowOf3Event event) {
		rowEvent = event;
		return this;
	}
	
	public boolean hasGridCellEvent() {
		return (cellEvent != null) ? true : false;
	}
	
	public boolean hasRowOf3Event() {
		return (rowEvent != null) ? true : false;
	}
	
	public GridCellEvent getGridCellEvent() {
		return cellEvent;
	}
	
	public RowOf3Event getRowOf3Event() {
		return rowEvent;
	}
}
 