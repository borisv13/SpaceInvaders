import java.awt.Rectangle;
import java.util.Iterator;
import java.util.List;


public class CollisionDetector {
	
	public static void checkShipMissilesAndAliens(List<Alien> aliens, List<Missile> shipMissiles) {
		for(Iterator<Alien> alienIterator = aliens.iterator(); alienIterator.hasNext();) {
			DualCoordinateImage alien = alienIterator.next();
			Rectangle alienRect = createNewRect(alien);
			for(Iterator<Missile> shipMissileIterator = shipMissiles.iterator(); shipMissileIterator.hasNext();) {
				DualCoordinateImage shipMissile = shipMissileIterator.next();
				Rectangle shipMissileRect = createNewRect(shipMissile);
				if(alienRect.intersects(shipMissileRect)) {
					alienIterator.remove();
					shipMissileIterator.remove();
				}
			}
		}
	}
	
	private static Rectangle createNewRect(DualCoordinateImage image) {
		return new Rectangle(image.getX(), image.getY(), image.getImage().getWidth(), image.getImage().getHeight());
	}

}
