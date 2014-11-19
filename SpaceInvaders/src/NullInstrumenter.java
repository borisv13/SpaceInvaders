
public class NullInstrumenter extends Instrumenter {

	public NullInstrumenter(GameEngine engine) {
		super(engine, "");
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
