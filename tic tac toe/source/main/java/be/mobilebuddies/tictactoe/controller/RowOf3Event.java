package be.mobilebuddies.tictactoe.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import be.mobilebuddies.tictactoe.model.CellValue;

public class RowOf3Event {

	private final List<GridCellEvent> cells;
	
	public RowOf3Event (CellValue who) {
		cells = new ArrayList<GridCellEvent>();
		cells.add(new GridCellEvent()
			.withValue(who));
		cells.add(new GridCellEvent()
			.withValue(who));
		cells.add(new GridCellEvent()
			.withValue(who));
	}
	
	public RowOf3Event withCell1(int row, int col) {
		cells.get(0)
			.withRow(row)
			.withCol(col);
		return this;
	}
	
	public RowOf3Event withCell2(int row, int col) {
		cells.get(1)
			.withRow(row)
			.withCol(col);
		return this;
	}
	
	public RowOf3Event withCell3(int row, int col) {
		cells.get(2)
			.withRow(row)
			.withCol(col);
		return this;
	}
	
	public Iterator<GridCellEvent> getCells()  {
		return cells.iterator();
	}

}
