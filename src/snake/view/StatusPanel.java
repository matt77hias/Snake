package snake.view;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;

import snake.engine.StatisticsListener;

public class StatusPanel extends JPanel implements StatisticsListener {
	
	private static final long serialVersionUID = 946695656362223077L;

	public StatusPanel() {
		initialize();
	}
	
	private void initialize() {
	    setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
	    this.jtfScore = new JTextField("Score: 0");
	    this.jtfScore.setEditable(false);
		add(this.jtfScore);
		this.jtfTime = new JTextField("Time Spent: 0 secs");
	    this.jtfTime.setEditable(false);
	    add(this.jtfTime);
	}

	public void setScore(int no) {
		this.jtfScore.setText("Score: " + no);
	}
  
	private JTextField jtfScore;

	public void setTimeSpent(long t) {
		this.jtfTime.setText("Time Spent: " + t + " secs");
	}
  
	private JTextField jtfTime;

	@Override
	public void updatePerformanceInfo(String msg) {
	}

	@Override
	public void updateTimeSpentIngame(int time) {
		setTimeSpent(time);
	}
}
