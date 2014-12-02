package spaceinvaders;
import java.awt.image.BufferedImage;


public class Ship extends DualCoordinateImage implements GameMoveableObject{

	private int deltaX;
	private int shipSpeed;
	private int limitX;
	private int maximumExhaust;
	private int exhaust;
	
	
	Ship(BufferedImage image, int x, int y, int screenWidth) {
		super(image, x, y);
		this.limitX = screenWidth - image.getWidth() - 10;  // Need the 10 to keep the wings on screen..not sure why yet
		this.shipSpeed = TunableParameters.ShipSpeed;
		this.deltaX = 0;
		this.maximumExhaust = TunableParameters.ShipExhaust;
		this.exhaust = maximumExhaust;
	}
	
	Ship(Ship source) {
		super(source);
		this.limitX = source.limitX;
		this.shipSpeed = source.shipSpeed;
		this.deltaX = source.deltaX;
		this.maximumExhaust = source.maximumExhaust;
		this.exhaust = source.exhaust;
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
		if(exhaust > TunableParameters.ShipFiringExhaustCost) {
			exhaust -= TunableParameters.ShipFiringExhaustCost;
			Missile newMissile = Factory.createShipMissile(0, 0);
			newMissile.setX(newMissile.getCenterRelativeXCoordinate(getX(), getImage().getWidth()));
			newMissile.setY(this.getY() - newMissile.getImage().getHeight());
			return newMissile;
		}
		return null;
	}

	@Override
	public void move() {
		x += deltaX;
		if (x <= 0){
			x = 0;
		} else if (x >= this.limitX) {
			x = this.limitX;
		}		
	}

	public void regenerate() {
		if(exhaust < maximumExhaust)
			exhaust++;
	}
	
	public int getExhaust() {
		return exhaust;
	}
}
