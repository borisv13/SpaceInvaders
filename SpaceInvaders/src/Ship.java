import java.awt.image.BufferedImage;


public class Ship extends DualCoordinateImage implements GameMoveableObject{

	private int deltaX;
	private int shipSpeed = TunableParameters.ShipSpeed;
	
	Ship(BufferedImage image, int x, int y) {
		super(image, x, y);
		deltaX = 0;
	}
	
	public void startMovingLeft() {
		deltaX = -shipSpeed;
	}
	
	public void stopMovingLeft() {
		if(deltaX == -shipSpeed) {
			deltaX = 0;
		}
	}
	
	public void startMovingRight() {
		deltaX = shipSpeed;
	}
	
	public void stopMovingRight() {
		if(deltaX == shipSpeed) {
			deltaX = 0;
		}
	}
	
	public Missile fireMissile() {
		Missile newMissile = Factory.createShipMissile(0, 0);
		newMissile.setX(newMissile.getCenterRelativeXCoordinate(getX(), getImage().getWidth()));
		newMissile.setY(this.getY() - newMissile.getImage().getHeight());
		return newMissile;
	}

	@Override
	public void move() {
		x += deltaX;
	}
}
