package snake.engine;

public interface StatisticsListener {
	
	public void updatePerformanceInfo(String msg);

	public void updateTimeSpentIngame(int time);
}
