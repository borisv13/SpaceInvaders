import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {
	
	private Background background;
	private Ship ship;
	private List<Alien> aliens = new ArrayList<Alien>();
	private List<Missile> shipMissiles = new ArrayList<Missile>();
	private List<Missile> alienMissiles = new ArrayList<Missile>();
	Random randomGenerator = new Random();
	
	Game(int screenWidth, int screenHeight) {
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
		return aliens;
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
		moveableObjects.addAll(aliens);
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
		alienMissiles = randomlyGenerateMissiles(aliens.get(0), alienMissiles);
	}
	
	private List<Missile> randomlyGenerateMissiles(Alien alien, List<Missile> alienMissiles) {
		List<Missile> newAlienMissiles = alienMissiles;
		int randomInt = randomGenerator.nextInt(100);
		if (randomInt > 98)
		{
			newAlienMissiles.add(alien.fireMissile());
		}
		return newAlienMissiles;
	}
}
