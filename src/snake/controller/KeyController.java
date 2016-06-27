package snake.controller;

import java.awt.Point;
import java.awt.event.KeyEvent;

public class KeyController {
	
	public KeyController(MainController mainController) {
		this.mainController = mainController;
		this.currentDirection = EAST;
	}
	
	public MainController getMainController() {
		return this.mainController;
	}
	
	private MainController mainController;
	
	public Point getCurrentDirection() {
		return this.currentDirection;
	}
	
	private void setCurrentDirection(Point request) {
		this.currentDirection = request;
	}
	
	private volatile Point currentDirection;

	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if (keyCode == KeyEvent.VK_UP) {
			setCurrentDirection(NORTH);
		} else if (keyCode == KeyEvent.VK_LEFT) {	
			setCurrentDirection(WEST);
		} else if (keyCode == KeyEvent.VK_RIGHT) {	
			setCurrentDirection(EAST);
		} else if (keyCode == KeyEvent.VK_DOWN) {
			setCurrentDirection(SOUTH);
		} else if ((keyCode == KeyEvent.VK_ESCAPE) || (keyCode == KeyEvent.VK_Q) ||
				 (keyCode == KeyEvent.VK_END) ||
				 ((keyCode == KeyEvent.VK_C) && e.isControlDown()) ) {
			getMainController().getGameController().setRunning(false);
		}
	}
	
	private static final Point NORTH = new Point(0,-1);
	private static final Point EAST = new Point(1,0);
	private static final Point SOUTH = new Point(0,1);
	private static final Point WEST = new Point(-1,0);
}
