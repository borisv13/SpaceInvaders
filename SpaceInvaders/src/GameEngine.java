import java.awt.Event;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.Graphics;

public class GameEngine {

	// Using multiple lists pointing to the same objects to ease frame by frame processing
	// This could be a good place for semaphore type logic to update multiple lists together
	// Or if locking to update takes too long..use double buffering (two copies of each list..the editing and the using copy)
	private ArrayList<Alien> aliens = new ArrayList<Alien>(20);
	private ArrayList<Missile> missiles = new ArrayList<Missile>(10);
	private ArrayList<GameMoveableImage> moveableImages = new ArrayList<GameMoveableImage>(40);
	private Ship ship;
	private ArrayList<DualCoordinateImage> allImages = new ArrayList<DualCoordinateImage>(40);
	
	void init(int width, int height) {
		// TODO Move this code to a GameGenerator class?
		// TODO Maybe add individual addXX methods for background, alien, etc
		Background background = Factory.createBackground(0, 0);
		allImages.add(background);
		moveableImages.add(background);
		background = Factory.createBackground(0, background.getImage().getHeight() - 1);
		allImages.add(background);
		moveableImages.add(background);
		
		Alien alien = Factory.createAlien(0, 0);
		alien.setScreenWidth(width);		
		aliens.add(alien);		
		allImages.add(alien);
		moveableImages.add(alien);
		
		ship = Factory.createShip(width/2, 0);
		ship.setY(height - ship.getImage().getHeight()*2);
		allImages.add(ship);
	}
	
	void runFrame() {
		for(GameMoveableImage moveable : this.moveableImages) {
			moveable.move();
		}
	}
	
	public ArrayList<DualCoordinateImage> getImages() {
		//TODO Double buffering needed here...paint is too slow...paints old position while iterating
		return allImages;
		// TODO: Want to change this to an iterator based implementation so not exposing the ArrayList
	}
	
	boolean processPlayerInput(Event evt, int key) {
		if (key == Event.LEFT) {
			ship.moveLeft();
		} else if (key == Event.RIGHT) {
			ship.moveRight();
		} else if (key == Event.UP) {
			Missile missile = ship.fireMissile();
			missiles.add(missile);
			allImages.add(missile);
			moveableImages.add(missile);
		}
			
		return true;
	}
}
