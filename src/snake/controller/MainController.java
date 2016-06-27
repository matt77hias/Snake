package snake.controller;

import snake.engine.GameController;
import snake.engine.JFrameListener;
import snake.view.SnakeFrame;

public class MainController {
	
	public static final int DEFAULT_ROWS = 50;
	
	public static final int DEFAULT_COLUMNS = 50;
	
	public static final int DEFAULT_SIZE = 10;
	
	public static final int DEFAULT_FPS = 10;
	
	public static void main(String args[]) { 
	    int fps = DEFAULT_FPS;
	    if (args.length != 0) {
	      fps = Integer.parseInt(args[0]);
	    }
	
	    MainController mc = new MainController();
	    mc.getGameController().startGame(mc.getSnakeAnimationLoop(), fps);
	    mc.getGameController().getAnimator().getStatisticsManager().addStatisticsListener(mc.getSnakeFrame().getStatusPanel());
	}
	
	public MainController() {
		this.gameController = new GameController();
		this.keyController = new KeyController(this);
		
		this.snakeFrame = new SnakeFrame(this, DEFAULT_ROWS, DEFAULT_COLUMNS, DEFAULT_SIZE);
		getSnakeFrame().addWindowListener(new JFrameListener(getGameController()));
		
		this.sal = new SnakeAnimationLoop(this);
	}

	public GameController getGameController() {
		return this.gameController;
	}
	
	private final GameController gameController;
	
	public KeyController getKeyController() {
		return this.keyController;
	}
	
	private final KeyController keyController;
	
	public SnakeAnimationLoop getSnakeAnimationLoop() {
		return this.sal;
	}
	
	private final SnakeAnimationLoop sal;
	
	public SnakeFrame getSnakeFrame() {
		return this.snakeFrame;
	}
	
	private final SnakeFrame snakeFrame;
}
