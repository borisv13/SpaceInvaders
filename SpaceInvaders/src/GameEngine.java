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
	private int gameScore = 0;
	
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
	
	private void incrementScore(int increment) {  // may need to be public later, not sure yet
		this.gameScore += increment;
	}
	
	int getScore() {
		return this.gameScore;
	}

	private long frameCount = 0;
	private long totalDurationNS = 0;
	private int numFramesToAverage = 50;
	synchronized void run() {		
		if(processingOn()) {
			long startTime = System.nanoTime();
			for(GameMoveableObject moveableObject : getMoveableObjects()) {
				moveableObject.move();
			}
			alienMissiles = aliens.randomlyGenerateMissiles(alienMissiles);
						
			int numAliensKilled = CollisionDetector.checkShipMissilesAndAliens(aliens.getAliens(), shipMissiles);			
			this.incrementScore(TunableParameters.AlienScore * numAliensKilled);
			
			CollisionDetector.checkIfInScreen(shipMissiles, screenWidth, screenHeight);
			CollisionDetector.checkIfInScreen(alienMissiles, screenWidth, screenHeight);
			totalDurationNS += System.nanoTime() - startTime;
			frameCount++;
			if (frameCount % numFramesToAverage == 0) {
				long averageDurationNS = totalDurationNS / frameCount;
				/*System.out.printf(
						"Num Aliens,%d,Num Alien Missiles,%d,Num Ship Missiles,%d,AVG Update Frame Duration in MS,%f,Num Frames for AVG Calc,%d", 
						aliens.getAliens().size(), 
						alienMissiles.size(), 
						shipMissiles.size(), 
						averageDurationNS/1000000.0,
						numFramesToAverage);*/
				//System.out.println();
				frameCount = 0;
				totalDurationNS = 0;
			}
		}		
	}
	
	private boolean processingOn() {
		return !pause;
	}
	
	private void togglePause() {
		pause = !pause;
	}
}
