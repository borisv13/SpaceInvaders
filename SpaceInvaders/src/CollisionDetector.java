import java.awt.Rectangle;
import java.util.Iterator;
import java.util.List;


public class CollisionDetector {
	
	public static void detectCollisionsAndRemoveTwoListsOfImages(List<? extends DualCoordinateImage> imagesOne, List<? extends DualCoordinateImage> imagesTwo) {
		for(Iterator<? extends DualCoordinateImage> imagesOneIterator = imagesOne.iterator(); imagesOneIterator.hasNext();) {
			DualCoordinateImage imageOne = imagesOneIterator.next();
			Rectangle imageOneRect = createNewRect(imageOne);
			for(Iterator<? extends DualCoordinateImage> imagesTwoIterator = imagesTwo.iterator(); imagesTwoIterator.hasNext();) {
				DualCoordinateImage imageTwo = imagesTwoIterator.next();
				Rectangle imageTwoRect = createNewRect(imageTwo);
				if(imageOneRect.intersects(imageTwoRect)) {
					imagesOneIterator.remove();
					imagesTwoIterator.remove();
					break;
				}
			}
		}
	}
	
	public static void checkIfInScreen(List<? extends DualCoordinateImage> images, int screenWidth, int screenHeight) {
		Rectangle screen = new Rectangle(0, 0, screenWidth, screenHeight);
		for(Iterator<? extends DualCoordinateImage> imageIterator = images.iterator(); imageIterator.hasNext();) {
			DualCoordinateImage image = imageIterator.next();
			Rectangle imageRect = createNewRect(image);
			if(!screen.intersects(imageRect)) {
				imageIterator.remove();
			}
		}
	}
	
	private static Rectangle createNewRect(DualCoordinateImage image) { 
		return new Rectangle(image.getX(), image.getY(), image.getImage().getWidth(), image.getImage().getHeight());
	}

}
