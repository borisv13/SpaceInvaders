package spaceinvaders;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;


public class LevelCreator {
	
	private static final int ScreenHeightDivisor = 5;
	private static final int AlienHeightSeperator = 2;
	private static final int AlienWidthSeperator = 3;
	private static final int AlienWidthBufferFactor = 2;

	public static AlienHorde getAliens(int screenWidth, int screenHeight) {
		List<Alien> aliens = new ArrayList<Alien>();
		BufferedImage alienImage = Factory.getImageHandler().getAlien();
		int height = alienImage.getHeight();
		int width = alienImage.getWidth();
		for (int yCoord = height; yCoord < (screenHeight/ScreenHeightDivisor)+height; yCoord += height*AlienHeightSeperator)
		{
			for (int xCoord = width*AlienWidthBufferFactor; xCoord+width < screenWidth; xCoord += width*AlienWidthSeperator)
			{
				aliens.add(Factory.createAlien(xCoord, yCoord, screenWidth));
			}
		}
		return new AlienHorde(screenWidth, aliens);
	}
}
