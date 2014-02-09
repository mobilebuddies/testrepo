package be.mobilebuddies.tictactoe.controller;

/**
 * The difficulty level of the Tic-Tac-Toe game.
 *  
 * @author koen
 *
 */
public enum Level {
	BEGINNER(1, "Beginner"), INTERMEDIATE(2, "Intermediate"), ADVANCED(3, "Advanced");
	private final int value;
	private final String label;
	Level(int value, String label) {
		this.value = value;
		this.label = label;
	}
	
	public String toString() {
		return label;
	}
	
	public static Level getByValue(int value) {
		Level found = null;
		for (Level l : Level.values()) {
			if (value == l.value) {
				found = l;
			}
		}
		return found;
	}
}
