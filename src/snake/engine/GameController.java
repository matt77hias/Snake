package snake.engine;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class GameController {
	
	public GameController() {
		
	}
	
	public boolean isGameOver() {
		return this.gameOver;
	}
	
	public void setGameOver(boolean request) {
		this.gameOver = request;
	}
	
	private boolean gameOver;
	
	public boolean isRunning() {
		return this.running;
	}
	
	public void setRunning(boolean request) {
		this.running = request;
	}
	
	private boolean running;
	
	public boolean isPaused() {
		return this.isPaused;
	}
	
	private void setPaused(boolean request){
		this.isPaused = request;
	}
	
	private boolean isPaused;
	
	public void startGame(AnimationLoop animationLoop, int FPS) {
		if (this.animatorThread == null || !isRunning()) {
			this.animator = new Animator(this, animationLoop, FPS);
			this.animatorThread = new Thread(getAnimator());
			this.animatorThread.start();
			
			for (GameListener gl : this.gameListeners) {
				gl.startGame();
			}
		}
	}
	
	public Animator getAnimator() {
		return this.animator;
	}
	
	private Animator animator;
	
	private Thread animatorThread;
	
    public void resumeGame() {
		setPaused(false);
		
		for (GameListener gl : this.gameListeners) {
			gl.resumeGame();
		}
	}
	
	public void pauseGame() {
		setPaused(true);
		
		for (GameListener gl : this.gameListeners) {
			gl.pauseGame();
		}
	}
	
	public void stopGame() {
		setRunning(false);
		
		for (GameListener gl : this.gameListeners) {
			gl.stopGame();
		}
	}
	
	public int getNumberOfGameListeners() {
		return this.gameListeners.size();
	}
	
	public void addGameListener(GameListener gameListener) {
		this.gameListeners.add(gameListener);
	}
	
	public void addGameListenerAt(int index, GameListener gameListener) 
			throws IndexOutOfBoundsException {
		this.gameListeners.add(index, gameListener);
	}
	
	public void removeGameListener(GameListener gameListener) {
		this.gameListeners.remove(gameListener);
	}
	
	public List<GameListener> getAllGameListeners() {
		return Collections.unmodifiableList(this.gameListeners);
	}
	
	private List<GameListener> gameListeners = new LinkedList<GameListener>();
}
