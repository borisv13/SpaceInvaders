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
		long timeToSleepMS;
		
		while(true) {
			now = System.nanoTime();
			if (now-last >= framePeriodNS)
			{
				last = now;
				if(this.gameFrame.run()) {
					gameFrame.dispose();
					gameFrame = new GameFrame();
				}
				// Next we sleep a portion of the time until the next schedule frame run to give the CPU a break
				// However because of non-determinism in the sleep function we don't get closer than 8 ms to next run time.
				timeToSleepMS = (long) Math.floor((last + framePeriodNS - System.nanoTime()) / 1000000) - 8;
				if (timeToSleepMS > 0) {
					try {
						Thread.sleep(timeToSleepMS);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}
}
