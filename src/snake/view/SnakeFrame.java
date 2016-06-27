package snake.view;

import java.awt.Container;
import javax.swing.JFrame;

import snake.controller.MainController;

public class SnakeFrame extends JFrame {
	
	private static final long serialVersionUID = -7637581625027946726L;

	public SnakeFrame(MainController mainController, int rows, int columns, int factor) {
		super("Snake");
		
		this.snakePanel = new SnakePanel(mainController, rows, columns, factor);
		this.statusPanel = new StatusPanel();
		
		makeGUI();
		pack();
	    setResizable(false);
	    setVisible(true);	
	}
	
	 private void makeGUI() {
	    Container c = getContentPane();
	    c.add(this.snakePanel, "Center");
	    c.add(this.statusPanel, "South");
	  }
	  
	  public SnakePanel getSnakePanel() {
		  return this.snakePanel;
	  }
	  
	  private SnakePanel snakePanel;
	  
	  public StatusPanel getStatusPanel() {
		  return this.statusPanel;
	  }
	  
	  private StatusPanel statusPanel;
}
