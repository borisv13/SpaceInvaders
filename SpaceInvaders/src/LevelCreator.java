import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;


public class LevelCreator {

	public static List<Alien> getAliens(int screenWidth, int screenHeight) {
		List<Alien> aliens = new ArrayList<Alien>();
		BufferedImage alienImage = Factory.getImageHandler().getAlien();
		for (int yCoord = alienImage.getHeight(null); yCoord < (screenHeight/5)+alienImage.getHeight(null); yCoord += alienImage.getHeight(null)*2)
		{
			for (int xCoord = alienImage.getWidth(null)*2; xCoord+alienImage.getWidth(null) < screenWidth; xCoord += alienImage.getWidth(null)*3)
			{
				aliens.add(Factory.createAlien(xCoord, yCoord, screenWidth));
			}
		}
		return aliens;
	}
}
