package be.mobilebuddies.tictactoe.controller.test;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;


import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import be.mobilebuddies.tictactoe.controller.GameController;
import be.mobilebuddies.tictactoe.controller.Level;
import be.mobilebuddies.tictactoe.controller.GridCellEvent;
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
		System.out.println("--> start testComputer2InARow");
		Grid grid = new Grid().initialize();
		ctrl.withGrid(grid);
		grid.setValue(0, 0, CellValue.COMPUTER);
		grid.setValue(0, 1, CellValue.COMPUTER);
		assertNotNull(ctrl.canComputerWin());
		System.out.println(grid);
		grid.initialize();
		grid.setValue(1, 0, CellValue.COMPUTER);
		grid.setValue(1, 2, CellValue.COMPUTER);
		assertNotNull(ctrl.canComputerWin());
		System.out.println(grid);
		grid.initialize();
		grid.setValue(2, 1, CellValue.COMPUTER);
		grid.setValue(2, 2, CellValue.COMPUTER);
		assertNotNull(ctrl.canComputerWin());
		System.out.println(grid);
		grid.initialize();
		grid.setValue(0, 0, CellValue.COMPUTER);
		grid.setValue(1, 2, CellValue.COMPUTER);
		assertNull(ctrl.canComputerWin());
		System.out.println(grid);
		System.out.println("<-- end testComputer2InARow");
	}
	
	@Test
	public void testComputer3InAColumn() {
		System.out.println("--> start testComputer3InAColumn");
		Grid grid = new Grid().initialize();
		ctrl.withGrid(grid);
		grid.setValue(0, 0, CellValue.COMPUTER);
		grid.setValue(1, 0, CellValue.COMPUTER);
		assertNotNull(ctrl.canComputerWin());
		System.out.println(grid);
		grid.initialize();
		grid.setValue(0, 1, CellValue.COMPUTER);
		grid.setValue(2, 1, CellValue.COMPUTER);
		assertNotNull(ctrl.canComputerWin());
		System.out.println(grid);
		grid.initialize();
		grid.setValue(1, 2, CellValue.COMPUTER);
		grid.setValue(2, 2, CellValue.COMPUTER);
		assertNotNull(ctrl.canComputerWin());
		System.out.println(grid);
		grid.initialize();
		grid.setValue(0, 0, CellValue.COMPUTER);
		grid.setValue(2, 1, CellValue.COMPUTER);
		assertNull(ctrl.canComputerWin());
		System.out.println(grid);
		System.out.println("<-- end testComputer3InAColumn");		
	}
	
	@Test
	public void testComputer3DiagonalDown() {
		System.out.println("--> start testComputer3DiagonalDown");
		Grid grid = new Grid().initialize();
		ctrl.withGrid(grid);
		grid.setValue(0, 0, CellValue.COMPUTER);
		grid.setValue(1, 1, CellValue.COMPUTER);
		assertNotNull(ctrl.canComputerWin());
		System.out.println(grid);
		grid.initialize();
		grid.setValue(1, 1, CellValue.COMPUTER);
		grid.setValue(2, 2, CellValue.COMPUTER);
		assertNotNull(ctrl.canComputerWin());
		System.out.println(grid);
		grid.initialize();
		grid.setValue(0, 0, CellValue.COMPUTER);
		grid.setValue(2, 2, CellValue.COMPUTER);
		assertNotNull(ctrl.canComputerWin());
		System.out.println(grid);
		System.out.println("<-- end testComputer3DiagonalDown");
	}

	@Test
	public void testComputer3DiagonalUp() {
		System.out.println("--> start testComputer3DiagonalUp");
		Grid grid = new Grid().initialize();
		ctrl.withGrid(grid);
		grid.setValue(2, 0, CellValue.COMPUTER);
		grid.setValue(1, 1, CellValue.COMPUTER);
		assertNotNull(ctrl.canComputerWin());
		System.out.println(grid);
		grid.initialize();
		grid.setValue(1, 1, CellValue.COMPUTER);
		grid.setValue(0, 2, CellValue.COMPUTER);
		assertNotNull(ctrl.canComputerWin());
		System.out.println(grid);
		grid.initialize();
		grid.setValue(2, 0, CellValue.COMPUTER);
		grid.setValue(0, 2, CellValue.COMPUTER);
		assertNotNull(ctrl.canComputerWin());
		System.out.println(grid);
		System.out.println("<-- end testComputer3DiagonalUp");
	}

	@Test
	public void testPrevent3InARow() {
		System.out.println("--> start testPrevent3InARow");
		Grid grid = new Grid().initialize();
		ctrl.withGrid(grid);
		grid.setValue(0, 0, CellValue.PLAYER);
		grid.setValue(0, 1, CellValue.PLAYER);
		assertNull(ctrl.canComputerWin());
		assertNotNull(ctrl.canComputerPreventWinning());
		System.out.println(grid);
		grid.initialize();
		grid.setValue(1, 0, CellValue.PLAYER);
		grid.setValue(1, 2, CellValue.PLAYER);
		assertNull(ctrl.canComputerWin());
		assertNotNull(ctrl.canComputerPreventWinning());
		System.out.println(grid);
		grid.initialize();
		grid.setValue(2, 1, CellValue.PLAYER);
		grid.setValue(2, 2, CellValue.PLAYER);
		assertNull(ctrl.canComputerWin());
		assertNotNull(ctrl.canComputerPreventWinning());
		System.out.println(grid);
		grid.initialize();
		grid.setValue(0, 0, CellValue.PLAYER);
		grid.setValue(1, 2, CellValue.PLAYER);
		assertNull(ctrl.canComputerPreventWinning());
		System.out.println(grid);
		System.out.println("<-- end testPrevent3InARow");
	}
	
	@Test
	public void testPrevent3InAColumn() {
		System.out.println("--> start testPrevent3InAColumn");
		Grid grid = new Grid().initialize();
		ctrl.withGrid(grid);
		grid.setValue(0, 0, CellValue.PLAYER);
		grid.setValue(1, 0, CellValue.PLAYER);
		assertNull(ctrl.canComputerWin());
		assertNotNull(ctrl.canComputerPreventWinning());
		System.out.println(grid);
		grid.initialize();
		grid.setValue(0, 1, CellValue.PLAYER);
		grid.setValue(2, 1, CellValue.PLAYER);
		assertNull(ctrl.canComputerWin());
		assertNotNull(ctrl.canComputerPreventWinning());
		System.out.println(grid);
		grid.initialize();
		grid.setValue(1, 2, CellValue.PLAYER);
		grid.setValue(2, 2, CellValue.PLAYER);
		assertNull(ctrl.canComputerWin());
		assertNotNull(ctrl.canComputerPreventWinning());
		System.out.println(grid);
		grid.initialize();
		grid.setValue(0, 0, CellValue.PLAYER);
		grid.setValue(2, 1, CellValue.PLAYER);
		assertNull(ctrl.canComputerWin());
		assertNull(ctrl.canComputerPreventWinning());
		System.out.println(grid);
		System.out.println("<-- end testPrevent3InAColumn");
	}
	
	@Test
	public void testPrevent3DiagonalDown() {
		System.out.println("--> start testPrevent3DiagonalDown");
		Grid grid = new Grid().initialize();
		ctrl.withGrid(grid);
		grid.setValue(0, 0, CellValue.PLAYER);
		grid.setValue(1, 1, CellValue.PLAYER);
		assertNull(ctrl.canComputerWin());
		assertNotNull(ctrl.canComputerPreventWinning());
		System.out.println(grid);
		grid.initialize();
		grid.setValue(1, 1, CellValue.PLAYER);
		grid.setValue(2, 2, CellValue.PLAYER);
		assertNull(ctrl.canComputerWin());
		assertNotNull(ctrl.canComputerPreventWinning());
		System.out.println(grid);
		grid.initialize();
		grid.setValue(0, 0, CellValue.PLAYER);
		grid.setValue(2, 2, CellValue.PLAYER);
		assertNull(ctrl.canComputerWin());
		assertNotNull(ctrl.canComputerPreventWinning());
		System.out.println(grid);
		System.out.println("<-- end testPrevent3DiagonalDown");		
	}

	@Test
	public void testPrevent3DiagonalUp() {
		System.out.println("--> start testPrevent3DiagonalUp");
		Grid grid = new Grid().initialize();
		ctrl.withGrid(grid);
		grid.setValue(2, 0, CellValue.PLAYER);
		grid.setValue(1, 1, CellValue.PLAYER);
		assertNull(ctrl.canComputerWin());
		assertNotNull(ctrl.canComputerPreventWinning());
		System.out.println(grid);
		grid.initialize();
		grid.setValue(1, 1, CellValue.PLAYER);
		grid.setValue(0, 2, CellValue.PLAYER);
		assertNull(ctrl.canComputerWin());
		assertNotNull(ctrl.canComputerPreventWinning());
		System.out.println(grid);
		grid.initialize();
		grid.setValue(2, 0, CellValue.PLAYER);
		grid.setValue(0, 2, CellValue.PLAYER);
		assertNull(ctrl.canComputerWin());
		assertNotNull(ctrl.canComputerPreventWinning());
		System.out.println(grid);
		System.out.println("<-- end testPrevent3DiagonalUp");
	}
	

	@Test
	public void testComputer2InARow() {
		System.out.println("--> start testComputer2InARow");
		Grid grid = new Grid().initialize();
		ctrl.withGrid(grid);
		grid.setValue(0, 0, CellValue.COMPUTER);
		assertNotNull(ctrl.canComputerMake2());
		System.out.println(grid);
		grid.initialize();
		grid.setValue(1, 1, CellValue.COMPUTER);
		assertNotNull(ctrl.canComputerMake2());
		System.out.println(grid);
		grid.initialize();
		grid.setValue(2, 2, CellValue.COMPUTER);
		assertNotNull(ctrl.canComputerMake2());
		System.out.println(grid);
		grid.initialize();
		grid.setValue(0, 0, CellValue.COMPUTER);
		grid.setValue(0, 2, CellValue.PLAYER);
		grid.setValue(2, 2, CellValue.PLAYER);
		grid.setValue(2, 0, CellValue.PLAYER);
		assertNull(ctrl.canComputerMake2());
		System.out.println(grid);
		System.out.println("<-- end testComputer2InARow");
	}
	
	@Test
	public void testComputer2InAColumn() {
		System.out.println("--> start testComputer2InAColumn");
		Grid grid = new Grid().initialize();
		ctrl.withGrid(grid);
		grid.setValue(1, 0, CellValue.COMPUTER);
		assertNotNull(ctrl.canComputerMake2());
		System.out.println(grid);
		grid.initialize();
		grid.setValue(2, 1, CellValue.COMPUTER);
		assertNotNull(ctrl.canComputerMake2());
		System.out.println(grid);
		System.out.println("<-- end testComputer2InAColumn");
	}
	
	@Test
	public void testComputer2DiagonalDown() {
		System.out.println("--> start testComputer2DiagonalDown");
		Grid grid = new Grid().initialize();
		ctrl.withGrid(grid);
		grid.setValue(0, 0, CellValue.COMPUTER);
		grid.setValue(0, 1, CellValue.PLAYER);
		grid.setValue(1, 0, CellValue.PLAYER);
		assertNotNull(ctrl.canComputerMake2());
		System.out.println(grid);
		grid.initialize();
		grid.setValue(1, 1, CellValue.COMPUTER);
		grid.setValue(0, 1, CellValue.PLAYER);
		grid.setValue(1, 0, CellValue.PLAYER);
		assertNotNull(ctrl.canComputerMake2());
		System.out.println(grid);
		grid.initialize();
		grid.setValue(0, 2, CellValue.PLAYER);
		grid.setValue(2, 0, CellValue.PLAYER);
		grid.setValue(2, 2, CellValue.COMPUTER);
		assertNotNull(ctrl.canComputerMake2());
		System.out.println(grid);
		System.out.println("<-- end testComputer2DiagonalDown");
	}

	@Test
	public void testComputer2DiagonalUp() {
		System.out.println("--> start testComputer2DiagonalUp");
		Grid grid = new Grid().initialize();
		ctrl.withGrid(grid);
		grid.setValue(2, 0, CellValue.COMPUTER);
		grid.setValue(1, 0, CellValue.PLAYER);
		grid.setValue(0, 1, CellValue.PLAYER);
		assertNotNull(ctrl.canComputerMake2());
		System.out.println(grid);
		grid.initialize();
		grid.setValue(1, 1, CellValue.COMPUTER);
		grid.setValue(0, 1, CellValue.PLAYER);
		grid.setValue(1, 0, CellValue.PLAYER);
		assertNotNull(ctrl.canComputerMake2());
		System.out.println(grid);
		grid.initialize();
		grid.setValue(0, 1, CellValue.PLAYER);
		grid.setValue(1, 0, CellValue.PLAYER);
		grid.setValue(0, 2, CellValue.COMPUTER);
		assertNotNull(ctrl.canComputerMake2());
		System.out.println(grid);
		System.out.println("<-- end testComputer2DiagonalUp");
	}

	@Test
	public void testPrevent2InARow() {
		System.out.println("--> start testPrevent2InARow");
		Grid grid = new Grid().initialize();
		ctrl.withGrid(grid);
		grid.setValue(0, 0, CellValue.PLAYER);
		assertNotNull(ctrl.canComputerPrevent2());
		System.out.println(grid);
		grid.initialize();
		grid.setValue(1, 1, CellValue.PLAYER);
		assertNotNull(ctrl.canComputerPrevent2());
		System.out.println(grid);
		grid.initialize();
		grid.setValue(2, 2, CellValue.PLAYER);
		assertNotNull(ctrl.canComputerPrevent2());
		System.out.println(grid);
		grid.initialize();
		grid.setValue(0, 0, CellValue.PLAYER);
		grid.setValue(0, 2, CellValue.COMPUTER);
		grid.setValue(2, 2, CellValue.COMPUTER);
		grid.setValue(2, 0, CellValue. COMPUTER);
		assertNull(ctrl.canComputerPrevent2());
		System.out.println(grid);
		System.out.println("<-- end testPrevent2InARow");
	}
	
	@Test
	public void testPrevent2InAColumn() {
		System.out.println("--> start testPrevent2InAColumn");
		Grid grid = new Grid().initialize();
		ctrl.withGrid(grid);
		grid.setValue(1, 0, CellValue.PLAYER);
		assertNotNull(ctrl.canComputerPrevent2());
		System.out.println(grid);
		grid.initialize();
		grid.setValue(2, 1, CellValue.PLAYER);
		assertNotNull(ctrl.canComputerPrevent2());
		System.out.println(grid);
		System.out.println("<-- end testPrevent2InARow");
	}
	
	@Test
	public void testPrevent2DiagonalDown() {
		System.out.println("--> start testPrevent2DiagonalDown");
		Grid grid = new Grid().initialize();
		ctrl.withGrid(grid);
		grid.setValue(0, 0, CellValue.PLAYER);
		grid.setValue(2, 0, CellValue.PLAYER);
		grid.setValue(0, 1, CellValue.COMPUTER);
		grid.setValue(1, 0, CellValue.COMPUTER);
		assertNotNull(ctrl.canComputerPrevent2());
		System.out.println(grid);
		grid.initialize();
		grid.setValue(1, 1, CellValue.PLAYER);
		grid.setValue(2, 1, CellValue.PLAYER);
		grid.setValue(0, 1, CellValue.COMPUTER);
		grid.setValue(1, 0, CellValue.COMPUTER);
		assertNotNull(ctrl.canComputerPrevent2());
		System.out.println(grid);
		grid.initialize();
		grid.setValue(0, 2, CellValue.COMPUTER);
		grid.setValue(2, 0, CellValue.COMPUTER);
		grid.setValue(2, 2, CellValue.PLAYER);
		grid.setValue(1, 2, CellValue.PLAYER);
		assertNotNull(ctrl.canComputerPrevent2());
		System.out.println(grid);
		System.out.println("<-- end testPrevent2DiagonalDown");		
	}

	@Test
	public void testPrevent2DiagonalUp() {
		System.out.println("--> start testPrevent2DiagonalUp");
		Grid grid = new Grid().initialize();
		ctrl.withGrid(grid);
		grid.setValue(2, 0, CellValue.PLAYER);
		grid.setValue(1, 2, CellValue.PLAYER);
		grid.setValue(1, 0, CellValue.COMPUTER);
		grid.setValue(0, 1, CellValue.COMPUTER);
		assertNotNull(ctrl.canComputerPrevent2());
		System.out.println(grid);
		grid.initialize();
		grid.setValue(1, 1, CellValue.PLAYER);
		grid.setValue(1, 2, CellValue.PLAYER);
		grid.setValue(0, 1, CellValue.COMPUTER);
		grid.setValue(1, 0, CellValue.COMPUTER);
		assertNotNull(ctrl.canComputerPrevent2());
		System.out.println(grid);
		grid.initialize();
		grid.setValue(0, 2, CellValue.PLAYER);
		grid.setValue(0, 0, CellValue.PLAYER);
		grid.setValue(0, 1, CellValue.COMPUTER);
		grid.setValue(1, 2, CellValue.COMPUTER);
		assertNotNull(ctrl.canComputerPrevent2());
		System.out.println(grid);
		System.out.println("<-- end testPrevent2DiagonalUp");
	}

	@Test
	public void playSelf() {
		System.out.println("--> start testPlaySelf");
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
		System.out.println("<-- end testPlaySelf");
	}
	
	@Test
	public void testFullGrid() {
		System.out.println("--> start testFullGrid");
		Grid grid = new Grid();
		ctrl.withGrid(grid);
		ctrl.whoStartsGame(); // just to initialize the grid and the game 'not ended'
//		+-+-+-+
//		|O|X|O|
//		+-+-+-+
//		|O|X| |
//		+-+-+-+
//		|X|O|X|
//		+-+-+-+
		int compWon = ctrl.getComputerWins();
		int plyWon = ctrl.getPlayerWins();
		grid.setValue(0, 0, CellValue.COMPUTER);
		grid.setValue(0, 1, CellValue.PLAYER);
		grid.setValue(0, 2, CellValue.COMPUTER);
		grid.setValue(1, 0, CellValue.COMPUTER);
		grid.setValue(1, 1, CellValue.PLAYER);
		grid.setValue(1, 2, CellValue.EMPTY);
		grid.setValue(2, 0, CellValue.PLAYER);
		grid.setValue(2, 1, CellValue.COMPUTER);
		grid.setValue(2, 2, CellValue.PLAYER);
		System.out.println(grid);
		ctrl.doComputersMove();
		System.out.println (grid);
		assertTrue(ctrl.isGameEnded());
		assertEquals(compWon, ctrl.getComputerWins());
		assertEquals(plyWon, ctrl.getPlayerWins());

		ctrl.whoStartsGame();
		compWon = ctrl.getComputerWins();
		plyWon = ctrl.getPlayerWins();
		grid.setValue(0, 0, CellValue.COMPUTER);
		grid.setValue(0, 1, CellValue.PLAYER);
		grid.setValue(0, 2, CellValue.COMPUTER);
		grid.setValue(1, 0, CellValue.COMPUTER);
		grid.setValue(1, 1, CellValue.PLAYER);
		grid.setValue(1, 2, CellValue.EMPTY);
		grid.setValue(2, 0, CellValue.PLAYER);
		grid.setValue(2, 1, CellValue.COMPUTER);
		grid.setValue(2, 2, CellValue.PLAYER);
		ctrl.fillCell(1, 2);
		System.out.println(grid);
		assertTrue(ctrl.isGameEnded());
		
		System.out.println("<-- end testFullGrid");
	}
	
	@Test
	public void testNotify() {
		// copy can win test code. See if has changed.
		System.out.println("--> start testNotify");
		Grid grid = new Grid().initialize();
		ctrl.withGrid(grid);
		grid.setValue(0, 0, CellValue.COMPUTER);
		grid.setValue(0, 1, CellValue.COMPUTER);
		grid.setValue(2, 0, CellValue.PLAYER);
		grid.setValue(2, 1, CellValue.PLAYER);
		ctrl.addObserver(new Observer() {

			
			@Override
			public void update(Observable gameController, Object event) {
				assertNotNull(event);
				GridCellEvent ev = (GridCellEvent) event;
				assertEquals(CellValue.COMPUTER, ev.getvalue());
				assertEquals(0, ev.getRow());
				assertEquals(2, ev.getCol());
				assertTrue(ctrl.isGameEnded());
				gameController.deleteObserver(this);
				System.out.println(gameController);
				System.out.println("<-- end testNotify");
			}
		});
		ctrl.doComputersMove();
	}
		
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		ctrl = null;
	}

}
