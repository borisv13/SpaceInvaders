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
	private Instrumenter instrument;
	private Instrumenter paintInstrument;
	
	GameEngine(int screenWidth, int screenHeight) {
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		background = Factory.createBackground(0, 0);
		ship = Factory.createShip(screenWidth/2, 0, screenWidth);
		ship.setY(screenHeight - ship.getImage().getHeight()*2);
		aliens = LevelCreator.getAliens(screenWidth, screenHeight);		
		pause = true;
		this.instrument = new Instrumenter(this, "EngineRun");
		this.paintInstrument = new Instrumenter(this, "Paint");
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
			Missile newMissile = ship.fireMissile();
			if(newMissile != null) {
				this.shipMissiles.add(newMissile);
			}
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
	
	int getFPS() {
		return this.instrument.getFPS();
	}
	
	int getPaintFPS() {
		return this.paintInstrument.getFPS();
	}

	@SuppressWarnings("unchecked")
	synchronized boolean run() {		
		if(processingOn()) {
			instrument.startFrame();
			for(GameMoveableObject moveableObject : getMoveableObjects()) {
				moveableObject.move();
			}
			ship.regenerate();
			Missile newMissile = aliens.randomlyGenerateMissiles();
			if(newMissile != null) {
				alienMissiles.add(newMissile);
			}
			
			int numberOfAliens = aliens.getAliens().size();
			List<List<? extends DualCoordinateImage>> listOfListOfImages = 
				CollisionDetector.detectCollisionsAndRemoveTwoListsOfImages(aliens.getAliens(), shipMissiles);
			aliens.setAliens((List<Alien>) listOfListOfImages.get(0));
			shipMissiles = (List<Missile>) listOfListOfImages.get(1);
			int numberOfAliensKilled = numberOfAliens - aliens.getAliens().size();
			this.incrementScore(TunableParameters.AlienScore * numberOfAliensKilled);
			
			CollisionDetector.checkIfInScreen(shipMissiles, screenWidth, screenHeight);
			CollisionDetector.checkIfInScreen(alienMissiles, screenWidth, screenHeight);
			
			boolean gameOver = false;
			if(aliens.getAliens().size() == 0) {
				gameOver = true;
			}
			
			instrument.endFrame();
			return gameOver;
		}
		return false;
	}
	
	public void signalStartPaintFrame() {
		paintInstrument.startFrame();
	}
	
	public void signalEndPaintFrame() {
		paintInstrument.endFrame();
	}
	
	private boolean processingOn() {
		return !pause;
	}
	
	private void togglePause() {
		pause = !pause;
	}
	
	public int getExhaust() {
		return ship.getExhaust();
	}
}
