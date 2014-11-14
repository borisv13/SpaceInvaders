
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
		while(true) {
			try {				
				Thread.sleep(17);				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.gameFrame.run();
		}
	}
}
