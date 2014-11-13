import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameEngine {
	
	private Background background;
	private Ship ship;
	private AlienHorde aliens;
	private List<Missile> shipMissiles = new ArrayList<Missile>();
	private List<Missile> alienMissiles = new ArrayList<Missile>();
	Random randomGenerator = new Random();
	private int screenWidth;
	private int screenHeight;
	private boolean pause;
	
	GameEngine(int screenWidth, int screenHeight) {
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		background = Factory.createBackground(0, 0);
		ship = Factory.createShip(screenWidth/2, 0, screenWidth);
		ship.setY(screenHeight - ship.getImage().getHeight()*2);
		aliens = LevelCreator.getAliens(screenWidth, screenHeight);		
		pause = true;
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
		moveableObjects.add(ship);
		moveableObjects.add(background);
		moveableObjects.add(aliens);
		moveableObjects.addAll(alienMissiles);
		moveableObjects.addAll(shipMissiles);
		return moveableObjects;
	}

	void keyLeftDown() {
		if(processingOn()) {
			ship.startMovingLeft();
		}
	}
	
	void keyLeftUp() {
		if(processingOn()) {
			ship.stopMovingLeft();
		}
	}
	
	void keyRightDown() {
		if(processingOn()) {
			ship.startMovingRight();
		}
	}
	
	void keyRightUp() {
		if(processingOn()) {
			ship.stopMovingRight();
		}
	}
	
	void keyUpDown() {
		if(processingOn()) {
			this.shipMissiles.add(ship.fireMissile());
		}
	}
	
	void keySpaceDown() {
		togglePause();
	}
	
	synchronized void run() {
		if(processingOn()) {
			for(GameMoveableObject moveableObject : getMoveableObjects()) {
				moveableObject.move();
			}
			alienMissiles = randomlyGenerateMissiles(aliens.getAliens(), alienMissiles);
			
			CollisionDetector.checkShipMissilesAndAliens(aliens.getAliens(), shipMissiles);
			CollisionDetector.checkIfInScreen(shipMissiles, screenWidth, screenHeight);
			CollisionDetector.checkIfInScreen(alienMissiles, screenWidth, screenHeight);
		}
	}
	
	private boolean processingOn() {
		return !pause;
	}
	
	private List<Missile> randomlyGenerateMissiles(List<Alien> aliens, List<Missile> alienMissiles) {
		List<Missile> newAlienMissiles = alienMissiles;
		
		if (aliens.size() == 0)
			return newAlienMissiles;
			
		int randomInt = randomGenerator.nextInt(100);
		if (randomInt > 98)
		{
			int alienToShoot;
			if (aliens.size() > 1)			{
				alienToShoot = randomGenerator.nextInt(aliens.size()-1);
			}
			else {
				alienToShoot = 0;
			}
			newAlienMissiles.add(aliens.get(alienToShoot).fireMissile()); 
		}
		return newAlienMissiles;
	}
	
	private void togglePause() {
		pause = !pause;
	}
}
