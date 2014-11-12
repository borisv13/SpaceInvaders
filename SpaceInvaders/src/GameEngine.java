import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class GameEngine {
	
	private Background background;
	private Ship ship;
	private AlienHorde aliens;
	private List<Missile> shipMissiles = new ArrayList<Missile>();
	private List<Missile> alienMissiles = new ArrayList<Missile>();
	Random randomGenerator = new Random();
	private int screenHeight;
	
	GameEngine(int screenWidth, int screenHeight) {
		this.screenHeight = screenHeight;
		background = Factory.createBackground(0, 0);
		ship = Factory.createShip(screenWidth/2, 0);
		ship.setY(screenHeight - ship.getImage().getHeight()*2);
		aliens = LevelCreator.getAliens(screenWidth, screenHeight);		
	}
	
	public Background getBackground() {
		return background;
	}
	
	public Ship getShip() {
		return ship;
	}
	
	public List<Alien> getAliens() {
		return aliens.getAliens();
	}
	
	public List<Missile> getShipMissiles() {
		return shipMissiles;
	}
	
	public List<Missile> getAlienMissiles() {
		return alienMissiles;
	}
	
	private List<GameMoveableObject> getMoveableObjects() {
		List<GameMoveableObject> moveableObjects = new ArrayList<GameMoveableObject>();
		moveableObjects.add(background);
		moveableObjects.add(aliens);
		moveableObjects.addAll(alienMissiles);
		moveableObjects.addAll(shipMissiles);
		return moveableObjects;
	}

	void keyLeft() {
		ship.moveLeft();
	}
	
	void keyRight() {
		ship.moveRight();
	}
	
	void keySpace() {
		this.shipMissiles.add(ship.fireMissile());
	}
	
	void run() {
		for(GameMoveableObject moveableObject : getMoveableObjects()) {
			moveableObject.move();
		}
		alienMissiles = randomlyGenerateMissiles(aliens.getAliens(), alienMissiles);
		
		CollisionDetector.checkShipMissilesAndAliens(aliens.getAliens(), shipMissiles);
		removeOffScreenMissiles();
	}
	
	private void removeOffScreenMissiles() {
		// First pass at removing off screen missiles.
		// Code is repeated due to different logic to detect off screen.
		// TODO Extract into a method on Missile to determine if off screen then each class can check for itself
		// then make this method accept the list as a parameter and call it once for each missile list.
		Iterator<Missile> itShip = shipMissiles.iterator();
		while (itShip.hasNext()) {
			Missile missile = itShip.next();
			if (missile.getY() < -missile.getImage().getHeight()) {
				itShip.remove();
			}
		}
		
		Iterator<Missile> itAlien = alienMissiles.iterator();
		while (itAlien.hasNext()) {
			Missile missile = itAlien.next();
			if (missile.getY() > this.screenHeight) {
				itAlien.remove();
			}
		}	
	}
	
	private List<Missile> randomlyGenerateMissiles(List<Alien> aliens, List<Missile> alienMissiles) {
		List<Missile> newAlienMissiles = alienMissiles;
		int randomInt = randomGenerator.nextInt(100);
		if (randomInt > 98)
		{
			randomInt = randomGenerator.nextInt(aliens.size()-1);
			newAlienMissiles.add(aliens.get(randomInt).fireMissile()); 
		}
		return newAlienMissiles;
	}
}
