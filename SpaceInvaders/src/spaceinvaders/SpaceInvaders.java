package spaceinvaders;

public class SpaceInvaders implements Runnable {

	GameFrame gameFrame;
	
	public static void main(String[] args) {	
        SplashScreen splash = new SplashScreen();
        splash.showSplashModal(TunableParameters.SplashScreenDurationMS);
		
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
		long nextFrameNS;
		long timeToSleepMS;
		boolean gameOver;
		
		while(true) {
			now = System.nanoTime();
			if (now-last >= framePeriodNS)
			{
				last = now;
				gameOver = this.gameFrame.run();
				if (gameOver) {
					gameOver = false;
					gameFrame.dispose();
					gameFrame = new GameFrame();					
				}
				
				// Next we sleep a portion of the time until 
				// the next scheduled frame run to give the CPU a break
				// However because of non-determinism in the sleep function
				// we don't get closer than 8 ms to next run time.
				nextFrameNS = last + framePeriodNS;
				timeToSleepMS = (long) Math.floor((nextFrameNS - System.nanoTime()) / 1000000) - 8;
				if (timeToSleepMS > 0) {
					try {
						Thread.sleep(timeToSleepMS);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}
