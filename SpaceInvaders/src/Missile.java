import java.awt.image.BufferedImage;


public abstract class Missile extends DualCoordinateImage{

	
	Missile(BufferedImage image, int x, int y) {
		super(image, x, y);
	}
	
	abstract public void move();
	
	public int getCenterRelativeXCoordinate(int relativeObjectX, int relativeObjectWidth) {
		int missileX = relativeObjectX;
		missileX += relativeObjectWidth/2;
		missileX -= image.getWidth()/2;
		return missileX;
	}
}
