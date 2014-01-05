package be.mobilebuddies.tictactoe.controller.test;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.Random;


import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import be.mobilebuddies.tictactoe.controller.GameController;
import be.mobilebuddies.tictactoe.controller.Level;
import be.mobilebuddies.tictactoe.model.CellValue;
import be.mobilebuddies.tictactoe.model.Grid;

public class _Controller {

	private static GameController ctrl;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ctrl = new GameController();
	}
	
	@Test
	public void testComputer3InARow() {
		Grid grid = new Grid().initialize();
		ctrl.withGrid(grid);
		grid.setValue(0, 0, CellValue.COMPUTER);
		grid.setValue(0, 1, CellValue.COMPUTER);
		assertTrue(ctrl.canComputerWin());
		System.out.println(grid);
		grid.initialize();
		grid.setValue(1, 0, CellValue.COMPUTER);
		grid.setValue(1, 2, CellValue.COMPUTER);
		assertTrue(ctrl.canComputerWin());
		System.out.println(grid);
		grid.initialize();
		grid.setValue(2, 1, CellValue.COMPUTER);
		grid.setValue(2, 2, CellValue.COMPUTER);
		assertTrue(ctrl.canComputerWin());
		System.out.println(grid);
		grid.initialize();
		grid.setValue(0, 0, CellValue.COMPUTER);
		grid.setValue(1, 2, CellValue.COMPUTER);
		assertFalse(ctrl.canComputerWin());
		System.out.println(grid);
	}
	
	@Test
	public void testComputer3InAColumn() {
		Grid grid = new Grid().initialize();
		ctrl.withGrid(grid);
		grid.setValue(0, 0, CellValue.COMPUTER);
		grid.setValue(1, 0, CellValue.COMPUTER);
		assertTrue(ctrl.canComputerWin());
		System.out.println(grid);
		grid.initialize();
		grid.setValue(0, 1, CellValue.COMPUTER);
		grid.setValue(2, 1, CellValue.COMPUTER);
		assertTrue(ctrl.canComputerWin());
		System.out.println(grid);
		grid.initialize();
		grid.setValue(1, 2, CellValue.COMPUTER);
		grid.setValue(2, 2, CellValue.COMPUTER);
		assertTrue(ctrl.canComputerWin());
		System.out.println(grid);
		grid.initialize();
		grid.setValue(0, 0, CellValue.COMPUTER);
		grid.setValue(2, 1, CellValue.COMPUTER);
		assertFalse(ctrl.canComputerWin());
		System.out.println(grid);
	}
	
	@Test
	public void testComputer3DiagonalDown() {
		Grid grid = new Grid().initialize();
		ctrl.withGrid(grid);
		grid.setValue(0, 0, CellValue.COMPUTER);
		grid.setValue(1, 1, CellValue.COMPUTER);
		assertTrue(ctrl.canComputerWin());
		System.out.println(grid);
		grid.initialize();
		grid.setValue(1, 1, CellValue.COMPUTER);
		grid.setValue(2, 2, CellValue.COMPUTER);
		assertTrue(ctrl.canComputerWin());
		System.out.println(grid);
		grid.initialize();
		grid.setValue(0, 0, CellValue.COMPUTER);
		grid.setValue(2, 2, CellValue.COMPUTER);
		assertTrue(ctrl.canComputerWin());
		System.out.println(grid);
		
	}

	@Test
	public void testComputer3DiagonalUp() {
		Grid grid = new Grid().initialize();
		ctrl.withGrid(grid);
		grid.setValue(2, 0, CellValue.COMPUTER);
		grid.setValue(1, 1, CellValue.COMPUTER);
		assertTrue(ctrl.canComputerWin());
		System.out.println(grid);
		grid.initialize();
		grid.setValue(1, 1, CellValue.COMPUTER);
		grid.setValue(0, 2, CellValue.COMPUTER);
		assertTrue(ctrl.canComputerWin());
		System.out.println(grid);
		grid.initialize();
		grid.setValue(2, 0, CellValue.COMPUTER);
		grid.setValue(0, 2, CellValue.COMPUTER);
		assertTrue(ctrl.canComputerWin());
		System.out.println(grid);
		
	}

	@Test
	public void testPrevent3InARow() {
		Grid grid = new Grid().initialize();
		ctrl.withGrid(grid);
		grid.setValue(0, 0, CellValue.PLAYER);
		grid.setValue(0, 1, CellValue.PLAYER);
		assertFalse(ctrl.canComputerWin());
		assertTrue(ctrl.canComputerPreventWinning());
		System.out.println(grid);
		grid.initialize();
		grid.setValue(1, 0, CellValue.PLAYER);
		grid.setValue(1, 2, CellValue.PLAYER);
		assertFalse(ctrl.canComputerWin());
		assertTrue(ctrl.canComputerPreventWinning());
		System.out.println(grid);
		grid.initialize();
		grid.setValue(2, 1, CellValue.PLAYER);
		grid.setValue(2, 2, CellValue.PLAYER);
		assertFalse(ctrl.canComputerWin());
		assertTrue(ctrl.canComputerPreventWinning());
		System.out.println(grid);
		grid.initialize();
		grid.setValue(0, 0, CellValue.PLAYER);
		grid.setValue(1, 2, CellValue.PLAYER);
		assertFalse(ctrl.canComputerPreventWinning());
		System.out.println(grid);
	}
	
	@Test
	public void testPrevent3InAColumn() {
		Grid grid = new Grid().initialize();
		ctrl.withGrid(grid);
		grid.setValue(0, 0, CellValue.PLAYER);
		grid.setValue(1, 0, CellValue.PLAYER);
		assertFalse(ctrl.canComputerWin());
		assertTrue(ctrl.canComputerPreventWinning());
		System.out.println(grid);
		grid.initialize();
		grid.setValue(0, 1, CellValue.PLAYER);
		grid.setValue(2, 1, CellValue.PLAYER);
		assertFalse(ctrl.canComputerWin());
		assertTrue(ctrl.canComputerPreventWinning());
		System.out.println(grid);
		grid.initialize();
		grid.setValue(1, 2, CellValue.PLAYER);
		grid.setValue(2, 2, CellValue.PLAYER);
		assertFalse(ctrl.canComputerWin());
		assertTrue(ctrl.canComputerPreventWinning());
		System.out.println(grid);
		grid.initialize();
		grid.setValue(0, 0, CellValue.PLAYER);
		grid.setValue(2, 1, CellValue.PLAYER);
		assertFalse(ctrl.canComputerWin());
		assertFalse(ctrl.canComputerPreventWinning());
		System.out.println(grid);
	}
	
	@Test
	public void testPrevent3DiagonalDown() {
		Grid grid = new Grid().initialize();
		ctrl.withGrid(grid);
		grid.setValue(0, 0, CellValue.PLAYER);
		grid.setValue(1, 1, CellValue.PLAYER);
		assertFalse(ctrl.canComputerWin());
		assertTrue(ctrl.canComputerPreventWinning());
		System.out.println(grid);
		grid.initialize();
		grid.setValue(1, 1, CellValue.PLAYER);
		grid.setValue(2, 2, CellValue.PLAYER);
		assertFalse(ctrl.canComputerWin());
		assertTrue(ctrl.canComputerPreventWinning());
		System.out.println(grid);
		grid.initialize();
		grid.setValue(0, 0, CellValue.PLAYER);
		grid.setValue(2, 2, CellValue.PLAYER);
		assertFalse(ctrl.canComputerWin());
		assertTrue(ctrl.canComputerPreventWinning());
		System.out.println(grid);
		
	}

	@Test
	public void testPrevent3DiagonalUp() {
		Grid grid = new Grid().initialize();
		ctrl.withGrid(grid);
		grid.setValue(2, 0, CellValue.PLAYER);
		grid.setValue(1, 1, CellValue.PLAYER);
		assertFalse(ctrl.canComputerWin());
		assertTrue(ctrl.canComputerPreventWinning());
		System.out.println(grid);
		grid.initialize();
		grid.setValue(1, 1, CellValue.PLAYER);
		grid.setValue(0, 2, CellValue.PLAYER);
		assertFalse(ctrl.canComputerWin());
		assertTrue(ctrl.canComputerPreventWinning());
		System.out.println(grid);
		grid.initialize();
		grid.setValue(2, 0, CellValue.PLAYER);
		grid.setValue(0, 2, CellValue.PLAYER);
		assertFalse(ctrl.canComputerWin());
		assertTrue(ctrl.canComputerPreventWinning());
		System.out.println(grid);
		
	}
	

	@Test
	public void testComputer2InARow() {
		Grid grid = new Grid().initialize();
		ctrl.withGrid(grid);
		grid.setValue(0, 0, CellValue.COMPUTER);
		assertTrue(ctrl.canComputerMake2());
		System.out.println(grid);
		grid.initialize();
		grid.setValue(1, 1, CellValue.COMPUTER);
		assertTrue(ctrl.canComputerMake2());
		System.out.println(grid);
		grid.initialize();
		grid.setValue(2, 2, CellValue.COMPUTER);
		assertTrue(ctrl.canComputerMake2());
		System.out.println(grid);
		grid.initialize();
		grid.setValue(0, 0, CellValue.COMPUTER);
		grid.setValue(0, 2, CellValue.PLAYER);
		grid.setValue(2, 2, CellValue.PLAYER);
		grid.setValue(2, 0, CellValue.PLAYER);
		assertFalse(ctrl.canComputerMake2());
		System.out.println(grid);
	}
	
	@Test
	public void testComputer2InAColumn() {
		Grid grid = new Grid().initialize();
		ctrl.withGrid(grid);
		grid.setValue(1, 0, CellValue.COMPUTER);
		assertTrue(ctrl.canComputerMake2());
		System.out.println(grid);
		grid.initialize();
		grid.setValue(2, 1, CellValue.COMPUTER);
		assertTrue(ctrl.canComputerMake2());
		System.out.println(grid);
	}
	
	@Test
	public void testComputer2DiagonalDown() {
		Grid grid = new Grid().initialize();
		ctrl.withGrid(grid);
		grid.setValue(0, 0, CellValue.COMPUTER);
		grid.setValue(0, 1, CellValue.PLAYER);
		grid.setValue(1, 0, CellValue.PLAYER);
		assertTrue(ctrl.canComputerMake2());
		System.out.println(grid);
		grid.initialize();
		grid.setValue(1, 1, CellValue.COMPUTER);
		grid.setValue(0, 1, CellValue.PLAYER);
		grid.setValue(1, 0, CellValue.PLAYER);
		assertTrue(ctrl.canComputerMake2());
		System.out.println(grid);
		grid.initialize();
		grid.setValue(0, 2, CellValue.PLAYER);
		grid.setValue(2, 0, CellValue.PLAYER);
		grid.setValue(2, 2, CellValue.COMPUTER);
		assertTrue(ctrl.canComputerMake2());
		System.out.println(grid);
		
	}

	@Test
	public void testComputer2DiagonalUp() {
		Grid grid = new Grid().initialize();
		ctrl.withGrid(grid);
		grid.setValue(2, 0, CellValue.COMPUTER);
		grid.setValue(1, 0, CellValue.PLAYER);
		grid.setValue(0, 1, CellValue.PLAYER);
		assertTrue(ctrl.canComputerMake2());
		System.out.println(grid);
		grid.initialize();
		grid.setValue(1, 1, CellValue.COMPUTER);
		grid.setValue(0, 1, CellValue.PLAYER);
		grid.setValue(1, 0, CellValue.PLAYER);
		assertTrue(ctrl.canComputerMake2());
		System.out.println(grid);
		grid.initialize();
		grid.setValue(0, 1, CellValue.PLAYER);
		grid.setValue(1, 0, CellValue.PLAYER);
		grid.setValue(0, 2, CellValue.COMPUTER);
		assertTrue(ctrl.canComputerMake2());
		System.out.println(grid);
	}

	@Test
	public void testPrevent2InARow() {
		Grid grid = new Grid().initialize();
		ctrl.withGrid(grid);
		grid.setValue(0, 0, CellValue.PLAYER);
		assertTrue(ctrl.canComputerPrevent2());
		System.out.println(grid);
		grid.initialize();
		grid.setValue(1, 1, CellValue.PLAYER);
		assertTrue(ctrl.canComputerPrevent2());
		System.out.println(grid);
		grid.initialize();
		grid.setValue(2, 2, CellValue.PLAYER);
		assertTrue(ctrl.canComputerPrevent2());
		System.out.println(grid);
		grid.initialize();
		grid.setValue(0, 0, CellValue.PLAYER);
		grid.setValue(0, 2, CellValue.COMPUTER);
		grid.setValue(2, 2, CellValue.COMPUTER);
		grid.setValue(2, 0, CellValue. COMPUTER);
		assertFalse(ctrl.canComputerPrevent2());
		System.out.println(grid);
	}
	
	@Test
	public void testPrevent2InAColumn() {
		Grid grid = new Grid().initialize();
		ctrl.withGrid(grid);
		grid.setValue(1, 0, CellValue.PLAYER);
		assertTrue(ctrl.canComputerPrevent2());
		System.out.println(grid);
		grid.initialize();
		grid.setValue(2, 1, CellValue.PLAYER);
		assertTrue(ctrl.canComputerPrevent2());
		System.out.println(grid);
	}
	
	@Test
	public void testPrevent2DiagonalDown() {
		Grid grid = new Grid().initialize();
		ctrl.withGrid(grid);
		grid.setValue(0, 0, CellValue.PLAYER);
		grid.setValue(2, 0, CellValue.PLAYER);
		grid.setValue(0, 1, CellValue.COMPUTER);
		grid.setValue(1, 0, CellValue.COMPUTER);
		assertTrue(ctrl.canComputerPrevent2());
		System.out.println(grid);
		grid.initialize();
		grid.setValue(1, 1, CellValue.PLAYER);
		grid.setValue(2, 1, CellValue.PLAYER);
		grid.setValue(0, 1, CellValue.COMPUTER);
		grid.setValue(1, 0, CellValue.COMPUTER);
		assertTrue(ctrl.canComputerPrevent2());
		System.out.println(grid);
		grid.initialize();
		grid.setValue(0, 2, CellValue.COMPUTER);
		grid.setValue(2, 0, CellValue.COMPUTER);
		grid.setValue(2, 2, CellValue.PLAYER);
		grid.setValue(1, 2, CellValue.PLAYER);
		assertTrue(ctrl.canComputerPrevent2());
		System.out.println(grid);
		
	}

	@Test
	public void testPrevent2DiagonalUp() {
		Grid grid = new Grid().initialize();
		ctrl.withGrid(grid);
		grid.setValue(2, 0, CellValue.PLAYER);
		grid.setValue(1, 2, CellValue.PLAYER);
		grid.setValue(1, 0, CellValue.COMPUTER);
		grid.setValue(0, 1, CellValue.COMPUTER);
		assertTrue(ctrl.canComputerPrevent2());
		System.out.println(grid);
		grid.initialize();
		grid.setValue(1, 1, CellValue.PLAYER);
		grid.setValue(1, 2, CellValue.PLAYER);
		grid.setValue(0, 1, CellValue.COMPUTER);
		grid.setValue(1, 0, CellValue.COMPUTER);
		assertTrue(ctrl.canComputerPrevent2());
		System.out.println(grid);
		grid.initialize();
		grid.setValue(0, 2, CellValue.PLAYER);
		grid.setValue(0, 0, CellValue.PLAYER);
		grid.setValue(0, 1, CellValue.COMPUTER);
		grid.setValue(1, 2, CellValue.COMPUTER);
		assertTrue(ctrl.canComputerPrevent2());
		System.out.println(grid);
	}

	@Test
	public void playSelf() {
		Random rnd = new Random(new Date().getTime());
		Grid grid = new Grid().initialize();
		ctrl.withGrid(grid);
		ctrl.withLevel(Level.BEGINNER);
		CellValue who = ctrl.whoStartsGame();
		if (who == CellValue.COMPUTER) {
			System.out.println("Computer starts");
			ctrl.doComputersMove();
			System.out.println (ctrl);
		} else {
			System.out.println("You start");
		}
		while (!ctrl.isGameEnded()) {
			boolean validMove = false;
			while (!validMove) {
				int row = rnd.nextInt(3);
				int col = rnd.nextInt(3);
				validMove = ctrl.fillCell(row, col);
			}
			System.out.println(ctrl);
			if (!ctrl.isGameEnded()) {
				ctrl.doComputersMove();
				System.out.println (ctrl);
			}
		}
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		ctrl = null;
	}

}
