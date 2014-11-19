
public class NullInstrumenter extends Instrumenter {

	public NullInstrumenter(GameEngine engine, String frameType,
			int numFramesToAverage) {
		super(engine, frameType, numFramesToAverage);
	}
	
	@Override
	public void startFrame() {
		// do nothing
	}
	
	@Override
	public void endFrame() {
		// do nothing
	}
	

}
