package snake.engine;
	
public class Animator implements Runnable {
	
	private static final int DEFAULT_FPS = 10;
	
	private static final int MAX_FPS = 100;
	
	private static final int NUMBER_OF_DELAYS_PER_YIELD = 16;
	
	private static final int MAX_FRAME_SKIPS = 2;
	
	public Animator(GameController gameController, AnimationLoop animationLoop, int FPS) {
		setFPS(FPS);
		setPeriod(calculatePeriodNS(getFPS()));
		
		this.gameController = gameController;
		this.statMngr = new StatisticsManager(getFPS(), getPeriod());
		this.animationLoop = animationLoop;
	}
	
	public GameController getGameController() {
		return this.gameController;
	}
	
	private final GameController gameController;
	
	public StatisticsManager getStatisticsManager() {
		return this.statMngr;
	}
	
	private final StatisticsManager statMngr;
	
	public AnimationLoop getAnimationLoop() {
		return this.animationLoop;
	}
	
	private final AnimationLoop animationLoop;
	
	public int getFPS() {
		return this.FPS;
	}
	
	private void setFPS(int request) {
		if (request <= 0 || request > MAX_FPS) {
			this.FPS = DEFAULT_FPS;
		} else {
			this.FPS = request;
		}
	}
	
	private int FPS;
	
	public long getPeriod() {
		return this.period;
	}
	
	public static long calculatePeriodNS(int FPS) {
		return (long) 1000000000.0/FPS;
	}
	
	private void setPeriod(long request) {
		this.period = request;
	}
	
	private long period;
		
	@Override
	public void run() {
		long beforeTime;
		long afterTime;
		long timeDiff;
		long sleepTime;
		long overSleepTime = 0L;
		int numberOfDelays = 0;
		long excess = 0L;
		
		getStatisticsManager().setGameStartTime(System.nanoTime());
		getStatisticsManager().setPrevStatsTime(getStatisticsManager().getGameStartTime());
		beforeTime = getStatisticsManager().getGameStartTime();
		
		getGameController().setRunning(true);
		
		while(getGameController().isRunning()) {
			gameUpdate();
			getAnimationLoop().gameRender();
			getAnimationLoop().paintScreen();
			
			afterTime = System.nanoTime();
			timeDiff = afterTime - beforeTime;
			sleepTime = (getPeriod() - timeDiff) - overSleepTime;
			
			if (sleepTime > 0) {
				try {
					Thread.sleep(sleepTime/1000000L);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				overSleepTime = (System.nanoTime() - afterTime) - sleepTime;
			} else {
				excess -= sleepTime;
				overSleepTime = 0L;
				
				if (++numberOfDelays >= NUMBER_OF_DELAYS_PER_YIELD) {
					Thread.yield();
					numberOfDelays = 0;
				}
			}
			
			beforeTime = System.nanoTime();
			
			int skips = 0;
			while((excess > getPeriod()) && (skips < MAX_FRAME_SKIPS)) {
				excess -= getPeriod();
				gameUpdate();
				skips++;
			}
			
			getStatisticsManager().addFramesSkipped(skips);
			getStatisticsManager().storeStats();
		}
		
		finishGame();
	}
	
	private void gameUpdate() {
		if (!getGameController().isPaused() && !getGameController().isGameOver()) {
			getAnimationLoop().gameUpdate();
		}
	}
	
	private void finishGame() {
		getStatisticsManager().printStats();
		System.exit(0);
	}
}
