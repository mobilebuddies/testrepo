package be.mobilebuddies.tictactoe.model.test;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import be.mobilebuddies.tictactoe.model.CellValue;
import be.mobilebuddies.tictactoe.model.Grid;

public class _Grid {

	private static Grid grid;// = new Grid().initialize();
	private static final int rows = 3;
	private static final int cols = 3;
	
	@BeforeClass
	public static void initialiseGrid() throws Exception {
		grid = new Grid().initialize();
		System.out.println (grid);
	}
	@Test
	public void testGridInitialisation() {
		for (int row = 0; row < rows; row ++) {
			for (int col = 0; col < cols; col ++) {
				assertTrue(grid.isEmpty(row, col));
			}
		}
	}
	
	@Test
	public void testGridSize() {
		assertEquals(rows, grid.getRows());
		assertEquals(cols, grid.getCols());
	}

	@Test(expected=IllegalArgumentException.class)
	public void testIllegalRowEmpty() {
		grid.isEmpty(rows, 0);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testIllegalColumnEmpty() {
		grid.isEmpty(0, cols);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testIllegalRowValue() {
		grid.getValue(rows, 0);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testIllegalColumnValue() {
		grid.getValue(0, cols);
	}
	
	@Test
	public void testSetValue() {
		grid.setValue(0, 0, CellValue.COMPUTER);
		assertEquals(CellValue.COMPUTER, grid.getValue(0, 0));
		assertNotSame(CellValue.EMPTY, grid.getValue(0, 0));
	}
	
	@AfterClass
	public static void cleanupGrid() throws Exception {
		System.out.println(grid);
		grid.initialize();
	}
	
}
