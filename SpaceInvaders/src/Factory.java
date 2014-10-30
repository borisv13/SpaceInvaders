
public class Factory {

	static private ImageHandler imageHandler = new ImageHandler();
	
	static public Background createBackground(int x, int y) {
		return new Background(imageHandler.getBackground(), x, y);
	}
	
	static public Ship createShip(int x, int y) {
		return new Ship(imageHandler.getShip(), x, y);
	}
}
