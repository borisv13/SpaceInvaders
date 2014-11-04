import java.awt.image.BufferedImage;


public class Missile extends DualCoordinateImage{

	int imageHeight;
	
	Missile(BufferedImage image, int x, int y) {
		super(image, x, y);
		this.imageHeight = image.getHeight();
	}
	
	public void move() {
		this.moveUp();
	}
	
	private void moveUp() {
		y -= this.getImage().getHeight() / 3; //TODO move 3 to a tunable parameter?
	}	
}
