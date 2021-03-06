package spaceinvaders;
import java.awt.image.BufferedImage;


public class Background extends DualCoordinateImage implements GameMoveableObject {
	
	Background(BufferedImage image, int initialX, int initialY) {
		super(image, initialX, initialY);
	}
	
	Background(Background source) {
		super(source);
	}
	
	public void move() {
		y -= TunableParameters.BackgroundSpeed;
		if(y <= -image.getHeight())
			y = 0;
	}
}
