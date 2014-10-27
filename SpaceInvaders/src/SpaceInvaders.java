
public class SpaceInvaders implements Runnable {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SpaceInvaders spaceInvaders = new SpaceInvaders();
		spaceInvaders.init();
	}

	void init() {
		new GameFrame();
	}
	
	@Override
	public void run() {
		System.out.println("hello");
	}

}
