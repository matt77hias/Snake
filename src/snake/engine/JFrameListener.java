package snake.engine;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class JFrameListener implements WindowListener {
	
	public JFrameListener(GameController gameController) {
		this.gameController = gameController;
	}
	
	public GameController getGameController() {
		return this.gameController;
	}
	
	private GameController gameController;

	public void windowActivated(WindowEvent e) {
		getGameController().resumeGame();
	}

	public void windowDeactivated(WindowEvent e) {
		getGameController().pauseGame();
	}


	public void windowDeiconified(WindowEvent e) {
		getGameController().resumeGame();
	}

	public void windowIconified(WindowEvent e) {
		getGameController().pauseGame();
	}

	public void windowClosing(WindowEvent e) {
		getGameController().stopGame();
	}

	public void windowClosed(WindowEvent e) {
		
	}
	
	public void windowOpened(WindowEvent e) {
		
	}
}
