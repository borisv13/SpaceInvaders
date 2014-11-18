import java.awt.image.BufferedImage;


public class Ship extends DualCoordinateImage implements GameMoveableObject{

	private int deltaX;
	private int shipSpeed = TunableParameters.ShipSpeed;
	private int limitX;
	private int maximumExhaust = TunableParameters.ShipExhaust;
	private int exhaust;
	
	
	Ship(BufferedImage image, int x, int y, int screenWidth) {
		super(image, x, y);
		this.limitX = screenWidth - image.getWidth() - 10;  // Need the 10 to keep the wings on screen..not sure why yet
		deltaX = 0;
		exhaust = maximumExhaust;
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
