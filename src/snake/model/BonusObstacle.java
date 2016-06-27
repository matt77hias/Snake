package snake.model;

import java.awt.Color;
import java.awt.Point;

public class BonusObstacle extends Obstacle {

	public static final int DEFAULT_SCORE = 10;
	
	public static final int DEFAULT_LIFETIME = 50;
	
	public static final Color COLOR = Color.green;
	
	public BonusObstacle(Point point, int size) {
		this(point, size, DEFAULT_LIFETIME);
	}
	
	public BonusObstacle(Point point, int size, int lifetime) {
		super(point, size, DEFAULT_SCORE, COLOR);
		this.lifetime = lifetime;
		this.originalLifetime = lifetime;
	}
	
	public int getLifetime() {
		return this.lifetime;
	}
	
	public void decrementLifetime() {
		this.lifetime--;
	}
	
	private void resetLifetime() {
		this.lifetime = this.originalLifetime;
	}
	
	public boolean isDead() {
		return getLifetime() == 0;
	}
	
	public void setRectangle(Point point) {
		getRectangle().x = point.x;
		getRectangle().y = point.y;
		resetLifetime();
	}
	
	private int lifetime;
	
	private int originalLifetime;
}
