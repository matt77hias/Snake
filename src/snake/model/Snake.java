package snake.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Random;

import snake.view.Drawable;

public class Snake implements Drawable {
	
	public Snake(int rows, int columns, int size) {
		this.rows = rows;
		this.columns = columns;
		this.size = size;
		
		this.parts = new Point[getRows()*getColumns()];
		this.headPosn = -1;
		this.tailPosn = -1;
		this.partsCount = 0;
	}
	
	public int getRows() {
		return this.rows;
	}
	
	private final int rows;
	
	public int getColumns() {
		return this.columns;
	}
	
	private final int columns;
	
	public int getSize() {
		return this.size;
	}
	
	private final int size;
	
	private int partsCount;
	private int headPosn;
	private int tailPosn;
	private Point[] parts;

	public void move(Point direction) {
		if (this.headPosn == -1) {
			this.parts[++this.headPosn] = new Point(getRows()/2, getColumns()/2);
			this.partsCount++;
		} else {
			int newX = this.parts[this.headPosn].x + direction.x;
			int newY = this.parts[this.headPosn].y + direction.y;
			this.headPosn = (this.headPosn + 1) % this.parts.length;
			this.parts[this.headPosn] = borderCorrection(newX, newY);
		} 
	}
	
	public void addPart(boolean request) {
		if (!request) {
			this.tailPosn = (this.tailPosn + 1) % this.parts.length;
		} else {
			this.partsCount++;
		}
	}
	
	public Point getHead() {
		if (this.headPosn >= 0) {
			return this.parts[this.headPosn];
		}
		return null;
	}
	
	public Point borderCorrection(int x, int y) {
		if (x < 0) {
		  x = x + getColumns();
		}
		else if (x > getColumns()) {
		  x = x - getColumns();  
		}
		
		if (y < 0) {
		  y = y + getRows();
		}
		else if (y > getRows()) {
		  y = y - getRows();
		}
		
		return new Point(x,y);
	}
	
	public boolean isOverlapping() {
		Point head = this.parts[this.headPosn];
		for (int i = this.tailPosn; i < this.tailPosn + this.partsCount - 1; i++) {
			if (head.equals(this.parts[i % this.parts.length])) {
				return true;
			}
		}
		return false;
	}
	
	public Point getPositionOffSnake() {
		Random rg = new Random();
		Point result = null;
		int x; int y;
		
		int k = 0;
		int maxIt = this.parts.length;
		
		while (result == null && k < maxIt) {
			x = rg.nextInt(getRows());
			y = rg.nextInt(getColumns());
			
			Point p = null;
			for (int i = this.tailPosn; i < this.tailPosn + this.partsCount; i++) {
				p = this.parts[i % this.parts.length];
				if (p.x == x && p.y == y) {
					break;
				}
				if (i == this.headPosn) {
					result = new Point(x,y);
				}
			}
			k++;
		}
		return result;
	}
	
	@Override
	public void draw(Graphics g) {
		Point p;
		g.setColor(Color.black);
		for (int i = this.tailPosn; i < this.tailPosn + this.partsCount - 1; i++) {
			p = this.parts[i % this.parts.length];
			g.fillRect(p.x * this.size, p.y * this.size, this.size, this.size);
		}
		g.setColor(Color.red);
		p = this.parts[this.headPosn % this.parts.length];
		g.fillRect(p.x * this.size, p.y * this.size, this.size, this.size);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		Point p;
		for (int i = this.tailPosn; i < this.tailPosn + this.partsCount; i++) {
			p = this.parts[i % this.parts.length];
			sb.append(" ("+p.x+"|"+p.y+") ");
		}
		return sb.toString();
	}
}
