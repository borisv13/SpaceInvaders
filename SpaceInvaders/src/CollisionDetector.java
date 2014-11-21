import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class CollisionDetector {
	
	public static List<List<? extends DualCoordinateImage>> detectCollisionsAndRemoveTwoListsOfImages(List<? extends DualCoordinateImage> imagesOne, List<? extends DualCoordinateImage> imagesTwo) {
		List<List<? extends DualCoordinateImage>> listOfListOfImages = new ArrayList<List<? extends DualCoordinateImage>>();
		List<? extends DualCoordinateImage> localImagesOne = imagesOne;
		List<? extends DualCoordinateImage> localImagesTwo = imagesTwo;
		for(Iterator<? extends DualCoordinateImage> imagesOneIterator = localImagesOne.iterator(); imagesOneIterator.hasNext();) {
			DualCoordinateImage imageOne = imagesOneIterator.next();
			Rectangle imageOneRect = createNewRect(imageOne);
			for(Iterator<? extends DualCoordinateImage> imagesTwoIterator = localImagesTwo.iterator(); imagesTwoIterator.hasNext();) {
				DualCoordinateImage imageTwo = imagesTwoIterator.next();
				Rectangle imageTwoRect = createNewRect(imageTwo);
				if(imageOneRect.intersects(imageTwoRect)) {
					imagesOneIterator.remove();
					imagesTwoIterator.remove();
					break;
				}
			}
		}
		listOfListOfImages.add(localImagesOne);
		listOfListOfImages.add(localImagesTwo);
		return listOfListOfImages;
	}
	
	public static List<? extends DualCoordinateImage> checkIfInScreen(List<? extends DualCoordinateImage> images, int screenWidth, int screenHeight) {
		List<? extends DualCoordinateImage> localImages = images;
		Rectangle screen = new Rectangle(0, 0, screenWidth, screenHeight);
		for(Iterator<? extends DualCoordinateImage> imageIterator = localImages.iterator(); imageIterator.hasNext();) {
			DualCoordinateImage image = imageIterator.next();
			Rectangle imageRect = createNewRect(image);
			if(!screen.intersects(imageRect)) {
				imageIterator.remove();
			}
		}
		return localImages;
	}
	
	public static boolean checkIfImageCollidesWithListOfImages(DualCoordinateImage imageToCheck, List<? extends DualCoordinateImage> images) {
		List<? extends DualCoordinateImage> localImages = images;
		Rectangle imageToCheckRect = createNewRect(imageToCheck);
		for(Iterator<? extends DualCoordinateImage> imageIterator = localImages.iterator(); imageIterator.hasNext();) {
			DualCoordinateImage image = imageIterator.next();
			Rectangle imageRect = createNewRect(image);
			if(imageToCheckRect.intersects(imageRect)) {
				return true;
			}
		}
		
		return false;
	}
	
	private static Rectangle createNewRect(DualCoordinateImage image) { 
		return new Rectangle(image.getX(), image.getY(), image.getImage().getWidth(), image.getImage().getHeight());
	}

}
