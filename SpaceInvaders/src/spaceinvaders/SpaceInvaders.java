package spaceinvaders;

public class SpaceInvaders implements Runnable {

	GameFrame gameFrame;
	
	public static void main(String[] args) {
		SpaceInvaders spaceInvaders = new SpaceInvaders();
		spaceInvaders.init();
		spaceInvaders.run();
	}

	void init() {
		gameFrame = new GameFrame();		
	}
	
	@Override
	public void run() {
		long now = 0;
		long last = 0;
		int fps = TunableParameters.TargetFPS;
		long framePeriodNS = 1000000000/fps; 
		
		while(true) {
			now = System.nanoTime();
			if (now-last >= framePeriodNS)
			{
				last = now;
				if(this.gameFrame.run()) {
					gameFrame.dispose();
					gameFrame = new GameFrame();
				}				
			}						
		}
	}
}
