package snake.model;

import java.awt.Point;

public class NormalObstacle extends Obstacle {
	
	public static final int DEFAULT_SCORE = 1;
	
	public NormalObstacle(Point point, int size) {
		super(point, size, DEFAULT_SCORE);
	}
	
	@Override
	public boolean isDead() {
		return false;
	}
	
	@Override
	public void setRectangle(Point point) {
		getRectangle().x = point.x;
		getRectangle().y = point.y;
	}
}
