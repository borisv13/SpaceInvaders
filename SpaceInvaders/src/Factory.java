
public class Factory {

	static private ImageHandler imageHandler = new ImageHandler();
	
	static public Background createBackground(int x, int y) {
		return new Background(imageHandler.getBackground(), x, y);
	}
	
	static public Ship createShip(int x, int y, int screenWidth) {
		return new Ship(imageHandler.getShip(), x, y, screenWidth);
	}

	static public Alien createAlien(int x, int y, int screenWidth) {
		return new Alien(imageHandler.getAlien(), x, y, screenWidth);
	}

	static public Missile createShipMissile(int x, int y) {
		return new ShipMissile(imageHandler.getMissile(), x, y);
	}
	
	static public Missile createAlienMissile(int x, int y) {
		return new AlienMissile(imageHandler.getMissile(), x, y);
	}
	
	static public ImageHandler getImageHandler() {
		return imageHandler;
	}
}
