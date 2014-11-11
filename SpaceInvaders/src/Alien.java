import java.awt.image.BufferedImage;


public class Alien extends DualCoordinateImage implements GameMoveableImage{

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
	
	public void move() {
		if (this.movingRight) {
			if (x + this.imageWidth + 1 > this.screenWidth) {
				this.moveDown();
				this.movingRight = false;
			}
			else {
				this.moveRight();
			}				
		} else {
			if (x - 1 < 0) {		
				this.moveDown();
				this.movingRight = true;
			} else {
				this.moveLeft();
			}
		}
	}
	
	private void moveDown() {
		y += this.getImage().getHeight() + 5; //TODO move 5 to a tunable parameter?
	}
	
	private void moveRight() {
		x += TunableParameters.AlienSpeed;
	}

	private void moveLeft() {
		x -= TunableParameters.AlienSpeed;
	}
	
	public Missile fireMissile() {
		Missile newMissile = Factory.createAlienMissile(0, 0);
		newMissile.setX(newMissile.getCenterRelativeXCoordinate(getX(), getImage().getWidth()));
		newMissile.setY(this.getY() + newMissile.getImage().getHeight());
		return newMissile;
	}
}
