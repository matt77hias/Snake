package snake.controller;

import java.awt.Point;

import snake.engine.AnimationLoop;
import snake.model.BonusObstacle;
import snake.model.NormalObstacle;
import snake.model.Snake;
import snake.view.SnakePanel;

public class SnakeAnimationLoop implements AnimationLoop {
	
	public SnakeAnimationLoop(MainController mainController) {
		this.mainController = mainController;
		createModels();
	}
	
	public MainController getMainController() {
		return this.mainController;
	}
	
	private MainController mainController;
	
	public SnakePanel getSnakePanel() {
		return getMainController().getSnakeFrame().getSnakePanel();
	}
	
	private void createModels() {
		this.snake = new Snake(MainController.DEFAULT_ROWS, MainController.DEFAULT_COLUMNS, MainController.DEFAULT_SIZE);
		this.nObs = new NormalObstacle(new Point(getSnake().getRows()/2, getSnake().getColumns()/4), MainController.DEFAULT_SIZE);
		this.bObs = new BonusObstacle(new Point(getSnake().getRows()/4, getSnake().getColumns()/2), MainController.DEFAULT_SIZE);
		
		getMainController().getSnakeFrame().getSnakePanel().addDrawable(snake);
		getMainController().getSnakeFrame().getSnakePanel().addDrawable(this.nObs);
		getMainController().getSnakeFrame().getSnakePanel().addDrawable(this.bObs);
	}
	
	public Snake getSnake() {
		return this.snake;
	}
	
	private Snake snake;
	
	public int getScore() {
		return this.score;
	}
	
	private void addScore(int request) {
		this.score += request;
		
		getMainController().getSnakeFrame().getStatusPanel().setScore(getScore());
	}
	
	private int score;

	@Override
	public void gameUpdate() {
		getSnake().move(getMainController().getKeyController().getCurrentDirection());
		
		if (this.nObs.isHit(getSnake().getHead(),getSnake().getSize())) {
			getSnake().addPart(true);
			addScore(this.nObs.getScore());
			this.nObs.setRectangle(getSnake().getPositionOffSnake());
		} else {
			getSnake().addPart(false);
		}

		if (this.bObs.isHit(getSnake().getHead(),getSnake().getSize())) {
			addScore(this.bObs.getScore());
			this.bObs.setRectangle(getSnake().getPositionOffSnake());
		} else {
			this.bObs.decrementLifetime();
		}
		if (this.bObs.isDead()) {
			this.bObs.setRectangle(getSnake().getPositionOffSnake());
		}
		
		if (getSnake().isOverlapping()) {
			getMainController().getGameController().setGameOver(true);
		}
	}

	@Override
	public void gameRender() {
		getSnakePanel().gameRender();
	}

	@Override
	public void paintScreen() {
		getSnakePanel().paintScreen();
	}
	
	private NormalObstacle nObs;
	private BonusObstacle bObs;
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
