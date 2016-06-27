package snake.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import snake.view.Drawable;

public abstract class Obstacle implements Drawable {
	
	protected Obstacle(Point point, int size, int score) {
		this(point, size, score, Color.blue);
	}

	protected Obstacle(Point point, int size, int score, Color color) {
		this.rectangle = new Rectangle(point.x, point.y, size, size);
		this.score = score;
		this.color = color;
	}
	
	public int getScore() {
		return this.score;
	}
	
	private final int score;
	
	public Rectangle getRectangle() {
		return this.rectangle;
	}
	
	public abstract void setRectangle(Point point);
	
	private Rectangle rectangle;
	
	private Color color;

	public boolean isHit(Point point, int size) {
		return getRectangle().x == point.x && getRectangle().y == point.y;
	}
	
	public abstract boolean isDead();
	
	public void draw(Graphics g) {
	    g.setColor(this.color);
	    g.fillRect(getRectangle().x * getRectangle().width, getRectangle().y * getRectangle().width, getRectangle().width, getRectangle().height);
	}
}