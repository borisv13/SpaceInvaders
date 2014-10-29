
public class SpaceInvaders implements Runnable {

	GameFrame gameFrame;
	
	public static void main(String[] args) {
		SpaceInvaders spaceInvaders = new SpaceInvaders();
		spaceInvaders.init();
		spaceInvaders.run();
	}

	void init() {
		gameFrame = new GameFrame();
		Thread th = new Thread(this);
		th.start();
	}
	
	@Override
	public void run() {
		Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
		while(true) {
			try {
				Thread.currentThread().sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			gameFrame.repaint();
		}
	}

}
