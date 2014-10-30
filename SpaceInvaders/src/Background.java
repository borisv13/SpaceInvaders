import java.awt.Point;
import java.awt.image.BufferedImage;


public class Background {

	private int x, y;
	private BufferedImage image;
	
	Background(BufferedImage image, int x, int y) {
		this.image = image;
		this.x = x;
		this.y = y;
	}
	
	public void move() {
		y--;
		if(y < -this.image.getHeight())
			y = 0;
	}
	
	public BufferedImage getImage() {
		return image;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
}
