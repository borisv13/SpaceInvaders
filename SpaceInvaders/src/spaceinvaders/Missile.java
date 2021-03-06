package spaceinvaders;
import java.awt.image.BufferedImage;


public abstract class Missile extends DualCoordinateImage implements GameMoveableObject{
	
	Missile(BufferedImage image, int x, int y) {
		super(image, x, y);
	}

	Missile(Missile source) {
		super(source);
	}

	abstract public void move();
	
	public int getCenterRelativeXCoordinate(int relativeObjectX, int relativeObjectWidth) {
		int missileX = relativeObjectX;
		missileX += relativeObjectWidth/2;
		missileX -= image.getWidth()/2;
		return missileX;
	}
}
