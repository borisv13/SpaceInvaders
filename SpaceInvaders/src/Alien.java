import java.awt.image.BufferedImage;


public class Alien extends DualCoordinateImage{

	int screenWidth;
	int imageWidth;
	int imageHeight;
	boolean movingRight = true;
	
	Alien(BufferedImage image, int x, int y, int screenWidth) {
		super(image, x, y);
		this.imageWidth = image.getWidth();
		this.imageHeight = image.getHeight();
		this.screenWidth = screenWidth;
	}
	
	public void move(int deltaX, int deltaY) {
		x += deltaX;
		y += deltaY;
	}
	
	public Missile fireMissile() {
		Missile newMissile = Factory.createAlienMissile(0, 0);
		newMissile.setX(newMissile.getCenterRelativeXCoordinate(getX(), getImage().getWidth()));
		newMissile.setY(this.getY() + newMissile.getImage().getHeight());
		return newMissile;
	}
}
