package spaceinvaders;

public class Instrumenter {

	private long frameCount = 0;
	private long totalDurationNS = 0;
	private long currentFrameStartTime = 0;
	private GameEngine engine;
	private String frameType;
	private static final long nanoSecondsPerSecond = 1000000000;
	private long fpsCalculationIntervalNanoSeconds = TunableParameters.FPSCalculationIntervalSeconds * nanoSecondsPerSecond;
	private int currentFPS = 0;
	private long fpsStartTime;
		
	public Instrumenter(GameEngine engine, String frameType) {
		this.engine = engine;
		this.frameType = frameType;
		this.fpsStartTime = System.nanoTime();		
	}
	
	public void startFrame() {
		currentFrameStartTime = System.nanoTime();
	}
	
	public void endFrame() {
		totalDurationNS += System.nanoTime() - currentFrameStartTime;
		frameCount++;		
		long durationSincefpsStartNS = System.nanoTime() - this.fpsStartTime;
		if (durationSincefpsStartNS >= fpsCalculationIntervalNanoSeconds) {
			currentFPS = (int) (frameCount / (durationSincefpsStartNS / nanoSecondsPerSecond));
			long averageDurationNS = totalDurationNS / frameCount;
			if (TunableParameters.Instrument)
			{
				System.out.printf(
						"Frame Type,%s,Num Aliens,%d,Num Alien Missiles,%d,Num Ship Missiles,%d,AVG Frame Duration in MS,%d,Num Frames,%d,FPS,%d", 
						this.frameType,
						engine.getCurrentGameState().getAliens().size(), 
						engine.getCurrentGameState().getAlienMissiles().size(), 
						engine.getCurrentGameState().getShipMissiles().size(), 
						averageDurationNS/nanoSecondsPerSecond,
						frameCount,
						currentFPS);
				System.out.println();
			}
			frameCount = 0;
			totalDurationNS = 0;
			this.fpsStartTime = System.nanoTime();
		}
	}
	
	public int getFPS() {
		return this.currentFPS;
	}
}
