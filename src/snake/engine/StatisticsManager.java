package snake.engine;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class StatisticsManager {
	
	private static final long MAX_STATS_INTERVAL = 1000000000L;
	
	public StatisticsManager(int FPS) {
		this(FPS, Animator.calculatePeriodNS(FPS));
	}
	
	public StatisticsManager(int FPS, long period) {
		setFPS(FPS);
		setPeriod(period);
		
	    this.fpsStore = new double[getFPS()];
	    this.upsStore = new double[getFPS()];
	    for (int i=0; i < getFPS(); i++) {
	      this.fpsStore[i] = 0.0;
	      this.upsStore[i] = 0.0;
	    }
	}
	
	public int getFPS() {
		return this.FPS;
	}
	
	private void setFPS(int request) {
		this.FPS = request;
	}
	
	private int FPS;
	
	public long getPeriod() {
		return this.period;
	}
	
	private void setPeriod(long request) {
		this.period = request;
	}
	
	private long period;
	
    public long getPrevStatsTime() {
		return this.prevStatsTime;
	}
	
	public void setPrevStatsTime(long request) {
		this.prevStatsTime =  request;
	}
	
	private long prevStatsTime;
	
	public long getGameStartTime() {
		return this.gameStartTime;
	}
	
	public void setGameStartTime(long request) {
		this.gameStartTime =  request;
	}
	
	private long gameStartTime;
	
	private long statsInterval = 0L;
	private long totalElapsedTime = 0L;
	private int timeSpentInGame = 0;
	
	private long statsCount = 0;
	
	private long frameCount = 0;
	private double fpsStore[];
	private double averageFPS = 0.0;
	
	public long getFramesSkipped() {
		return this.framesSkipped;
	}
	
	public void addFramesSkipped(long request) {
		this.framesSkipped += request;
	}
	
	private long framesSkipped = 0L;
	private long totalFramesSkipped = 0L;
	private double upsStore[];
	private double averageUPS = 0.0;

	public void storeStats() { 
		this.frameCount++;
		this.statsInterval += getPeriod();
		
		if (this.statsInterval >= MAX_STATS_INTERVAL) {
		  long timeNow = System.nanoTime();
		  this.timeSpentInGame = (int) ((timeNow - this.gameStartTime) / 1000000000L);
		
		  long realElapsedTime = timeNow - this.prevStatsTime;
		  this.totalElapsedTime += realElapsedTime;
		
		  double timingError = 
		     ((double) (realElapsedTime - this.statsInterval) / this.statsInterval) * 100.0;
		
		  this.totalFramesSkipped += getFramesSkipped();
		
		  double actualFPS = 0;
		  double actualUPS = 0;
		  if (this.totalElapsedTime > 0) {
		    actualFPS = (((double) this.frameCount / this.totalElapsedTime) * 1000000000L);
		    actualUPS = (((double) (this.frameCount + this.totalFramesSkipped) / this.totalElapsedTime) 
		                                                         * 1000000000L);
		  }
		
		  this.fpsStore[(int) this.statsCount % getFPS()] = actualFPS;
		  this.upsStore[(int) this.statsCount % getFPS()] = actualUPS;
		  this.statsCount++;
		
		  double totalFPS = 0.0;
		  double totalUPS = 0.0;
		  for (int i=0; i < getFPS(); i++) {
		    totalFPS += this.fpsStore[i];
		    totalUPS += this.upsStore[i];
		  }
		
		  if (statsCount < getFPS()) {
		    this.averageFPS = totalFPS/this.statsCount;
		    this.averageUPS = totalUPS/this.statsCount;
		  }
		  else {
		    this.averageFPS = totalFPS/getFPS();
		    this.averageUPS = totalUPS/getFPS();
		  }
		  
		  String performanceInfo = (timedf.format( (double) this.statsInterval/1000000000L) + " " + 
	              timedf.format((double) realElapsedTime/1000000000L) + "s " + 
			        df.format(timingError) + "% " + 
	              this.frameCount + "c " +
	              this.framesSkipped + "/" + this.totalFramesSkipped + " skip; " +
	              df.format(actualFPS) + " " + df.format(this.averageFPS) + " afps; " + 
	              df.format(actualUPS) + " " + df.format(this.averageUPS) + " aups" );
		  
		  System.out.println(performanceInfo);
		  
		  for (StatisticsListener sl : this.statisticsListeners) {
			  sl.updateTimeSpentIngame(this.timeSpentInGame);
			  sl.updatePerformanceInfo(performanceInfo);
		  }
		
		  this.framesSkipped = 0;
		  this.prevStatsTime = timeNow;
		  this.statsInterval = 0L;
		}
	}
	
	private static final DecimalFormat df = new DecimalFormat("0.##");
	private static final DecimalFormat timedf = new DecimalFormat("0.####"); 
	
	public void printStats() {
	    System.out.println("Frame Count/Loss: " + this.frameCount + " / " + this.totalFramesSkipped);
	System.out.println("Average FPS: " + df.format(this.averageFPS));
	System.out.println("Average UPS: " + df.format(this.averageUPS));
	System.out.println("Time Spent: " + this.timeSpentInGame + " secs");
	  }
  
	public int getNumberOfStatisticsListeners() {
		return this.statisticsListeners.size();
	}
	
	public void addStatisticsListener(StatisticsListener statisticsListener) {
		this.statisticsListeners.add(statisticsListener);
	}
	
	public void addStatisticsListenerAt(int index, StatisticsListener statisticsListener) 
			throws IndexOutOfBoundsException {
		this.statisticsListeners.add(index, statisticsListener);
	}
	
	public void removeStatisticsListener(StatisticsListener statisticsListener) {
		this.statisticsListeners.remove(statisticsListener);
	}
	
	public List<StatisticsListener> getAllStatisticsListeners() {
		return Collections.unmodifiableList(this.statisticsListeners);
	}
	
	private List<StatisticsListener> statisticsListeners = new LinkedList<StatisticsListener>();
}
