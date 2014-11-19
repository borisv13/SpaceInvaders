
public class Instrumenter {

	private long frameCount = 0;
	private long totalDurationNS = 0;
	private int numFramesToAverage = 50;
	private long currentFrameStartTime = 0;
	private GameEngine engine;
	private String frameType;

	public Instrumenter(GameEngine engine, String frameType, int numFramesToAverage) {
		this.engine = engine;
		this.frameType = frameType;
		this.numFramesToAverage = numFramesToAverage;
	}
	
	public void startFrame() {
		currentFrameStartTime = System.nanoTime();
	}
	
	public void endFrame() {
		totalDurationNS += System.nanoTime() - currentFrameStartTime;
		frameCount++;
		if (frameCount % numFramesToAverage == 0) {
			long averageDurationNS = totalDurationNS / frameCount;
			System.out.printf(
					"Frame Type,%s,Num Aliens,%d,Num Alien Missiles,%d,Num Ship Missiles,%d,AVG Frame Duration in MS,%f,Num Frames for AVG Calc,%d", 
					this.frameType,
					engine.getAliens().size(), 
					engine.getAlienMissiles().size(), 
					engine.getShipMissiles().size(), 
					averageDurationNS/1000000.0,
					numFramesToAverage);
			System.out.println();
			frameCount = 0;
			totalDurationNS = 0;
		}		
	}
}
