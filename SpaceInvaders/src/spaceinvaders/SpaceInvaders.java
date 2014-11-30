package spaceinvaders;

public class SpaceInvaders implements Runnable {

	GameFrame gameFrame;
	
	public static void main(String[] args) {
		
		// Set Variable for the SplashScreen
        SplashScreen splash = new SplashScreen(15000);

        // Normally, we'd call splash.showSplash() and get on 
        // with the program. But, since this is only a test...
        //splash.showSplashAndExit();
        splash.showSplash();
		
		SpaceInvaders spaceInvaders = new SpaceInvaders();
		spaceInvaders.init();
		spaceInvaders.run();
	}

	void init() {
		gameFrame = new GameFrame();		
	}
	
	@Override
	public void run() 
	{
		
		long now = System.currentTimeMillis();
		long last = 0;
		int fps = 45;
		
		while(true)
		{
			if(now-last >= 1000/fps)
			{
				this.gameFrame.run();
				last = now;
			} // end of if
			
			now = System.currentTimeMillis();
		} // end of while
	} // end of run
}
