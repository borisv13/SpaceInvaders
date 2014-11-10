import java.awt.image.BufferedImage;


public class Ship extends DualCoordinateImage{

	Ship(BufferedImage image, int x, int y) {
		super(image, x, y);
	}
	
	public void moveLeft() {
		x -= TunableParameters.ShipSpeed;
	}
	
	public void moveRight() {
		x += TunableParameters.ShipSpeed;
	}
	
	public Missile fireMissile() {
		Missile newMissile = Factory.createMissile(0, 0);
		newMissile.setX(getNewMissileXCoordinate(newMissile));
		newMissile.setY(this.getY() - newMissile.getImage().getHeight());
		return newMissile;
	}
	
	private int getNewMissileXCoordinate(Missile newMissile) {
		int missileX = getX();
		missileX += getImage().getWidth()/2;
		missileX -= newMissile.getImage().getWidth()/2;
		return missileX;
	}
}
