
public class SpaceInvaders implements Runnable {

	GameFrame gameFrame;
	private GameEngine engine;
	
	public static void main(String[] args) {
		SpaceInvaders spaceInvaders = new SpaceInvaders();
		spaceInvaders.init();
		spaceInvaders.run();
	}

	void init() {
		engine = new GameEngine();
		gameFrame = new GameFrame(engine);		
		engine.init(gameFrame.getSize().width, gameFrame.getSize().height);
		Thread th = new Thread(this);
		th.start();
	}
	
	@Override
	public void run() {
		Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
		while(true) {
			try {				
				Thread.sleep(10);				
				this.gameFrame.repaint();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
