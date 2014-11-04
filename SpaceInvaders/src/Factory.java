
public class Factory {

	static private ImageHandler imageHandler = new ImageHandler();
	
	static public Background createBackground(int x, int y) {
		return new Background(imageHandler.getBackground(), x, y);
	}
	
	static public Ship createShip(int x, int y) {
		return new Ship(imageHandler.getShip(), x, y);
	}

	static public Alien createAlien(int x, int y) {
		return new Alien(imageHandler.getAlien(), x, y);
	}

	static public Missile createMissile(int x, int y) {
		return new Missile(imageHandler.getMissile(), x, y);
	}
}
