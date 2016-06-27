package snake.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;

import snake.controller.MainController;

public class SnakePanel extends JPanel {
	
	private static final long serialVersionUID = 966214228179713129L;
	
	public SnakePanel(MainController mainController, int rows, int columns, int size) {
		this(mainController, columns * size, rows * size);
	}
	
	public SnakePanel(MainController mainController, int PWidth, int PHeight) {
		this.mainController = mainController;
		setPWidth(PWidth);
		setPHeight(PHeight);
		
		setBackground(Color.white);
	    setPreferredSize(new Dimension(getPWidth(), getPHeight()));
	    
	    setFocusable(true);
	    requestFocus();
		readyForTermination();
		
		this.font = new Font("SansSerif", Font.BOLD, 24);
		this.metrics = this.getFontMetrics(font);
	}
	
	public int getPWidth() {
		return PWidth;
	}
	
	public void setPWidth(int request) {
		PWidth = request;
	}
	
	private int PWidth;
	
	public int getPHeight() {
		return PHeight;
	}
	
	public static void setPHeight(int request) {
		PHeight = request;
	}
	
	private static int PHeight;
	
	public MainController getMainController() {
		return this.mainController;
	}
	
	private MainController mainController;
	
	private Font font;
	private FontMetrics metrics;

	private Graphics dbg; 
	private Image dbImage = null;
	
	public void gameRender() {
		if (this.dbImage == null){
		  this.dbImage = createImage(getPWidth(), getPHeight());
		  if (this.dbImage == null) {
		    System.out.println("dbImage is null");
		    return;
		  }
		  else
		    this.dbg = this.dbImage.getGraphics();
		}
		
		this.dbg.setColor(Color.white);
		this.dbg.fillRect(0, 0, getPWidth(), getPHeight());
		
		this.dbg.setColor(Color.black);
		
		for (Drawable d : this.drawables) {
			d.draw(this.dbg);
		}
		
		if (getMainController().getGameController().isGameOver()) {
		  gameOverMessage(this.dbg, getMainController().getSnakeAnimationLoop().getScore());
		}
	}
	
	private void gameOverMessage(Graphics g, int score) {
	    String msg = "Game Over. Your Score: " + score;
		int x = (getPWidth() - metrics.stringWidth(msg))/2; 
		int y = (getPHeight() - metrics.getHeight())/2;
		g.setColor(Color.red);
	    g.setFont(font);
		g.drawString(msg, x, y);
	 }


	 public void paintScreen() { 
		Graphics g;
		try {
		  g = getGraphics();
		  if ((g != null) && (this.dbImage != null)) {
		    g.drawImage(this.dbImage, 0, 0, null);
		  }
		  g.dispose();
		} catch (Exception e) {
			System.out.println("Graphics context error: " + e);
		}
	 }
	 
	 private void readyForTermination() {
		 addKeyListener( new KeyAdapter() {
			 
			 public void keyPressed(KeyEvent e) {
				 getMainController().getKeyController().keyPressed(e);
	         }
	     });
	  }
	 
	 public int getNumberOfDrawables() {
		return this.drawables.size();
	 }
	
	 public void addDrawable(Drawable drawable) {
		this.drawables.add(drawable);
	 }
	
	 public void addDrawableAt(int index, Drawable drawable) 
			throws IndexOutOfBoundsException {
		this.drawables.add(index, drawable);
	 }
	
	 public void removeDrawable(Drawable drawable) {
		this.drawables.remove(drawable);
	 }
	
	 public List<Drawable> getAllDrawables() {
		return Collections.unmodifiableList(this.drawables);
	 }
	
	 private List<Drawable> drawables = new LinkedList<Drawable>();
}
