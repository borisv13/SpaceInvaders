import java.awt.image.BufferedImage;


public abstract class DualCoordinateImage {

	protected BufferedImage image;
	protected int x, y;
	
	public DualCoordinateImage(BufferedImage image, int x, int y) {
		this.image = image;
		this.x = x;
		this.y = y;
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
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
}
