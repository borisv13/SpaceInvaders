package spaceinvaders;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameEngine {
	
	private GameState currentGameState;
	private GameState changingGameState;
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
		this.currentGameState = new GameState(screenWidth, screenHeight);
		pause = true;
		this.instrument = new Instrumenter(this, "EngineRun");
		this.paintInstrument = new Instrumenter(this, "Paint");
	}
	
	public GameState getCurrentGameState() {
		return currentGameState;
	}
		
	private List<GameMoveableObject> getMoveableObjects() {
		List<GameMoveableObject> moveableObjects = new ArrayList<GameMoveableObject>();
		moveableObjects.add(changingGameState.getShip());
		moveableObjects.add(changingGameState.getBackground());
		moveableObjects.add(changingGameState.getAlienHorde());
		moveableObjects.addAll(changingGameState.getAlienMissiles());
		moveableObjects.addAll(changingGameState.getShipMissiles());
		return moveableObjects;
	}

	public synchronized void keyLeftDown() {
		if(processingOn()) {
			currentGameState.getShip().startMovingLeft();
		}
	}
	
	public synchronized void keyLeftUp() {
		if(processingOn()) {
			currentGameState.getShip().stopMovingLeft();
		}
	}
	
	public synchronized void keyRightDown() {
		if(processingOn()) {
			currentGameState.getShip().startMovingRight();
		}
	}
	
	public synchronized void keyRightUp() {
		if(processingOn()) {
			currentGameState.getShip().stopMovingRight();
		}
	}
	
	public synchronized void keyUpDown() {
		if(processingOn()) {
			Missile newMissile = currentGameState.getShip().fireMissile();
			if(newMissile != null) {
				currentGameState.getShipMissiles().add(newMissile);
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

	private void moveObjects() {
		for(GameMoveableObject moveableObject : getMoveableObjects()) {
			moveableObject.move();
		}
	}
	
	private void conditionallyAddMissile() {
		Missile newMissile = changingGameState.getAlienHorde().randomlyGenerateMissiles();
		if(newMissile != null) {
			changingGameState.getAlienMissiles().add(newMissile);
		}
	}
	
	@SuppressWarnings("unchecked")
	private void checkForKilledAliens() {
		int numberOfAliens = changingGameState.getAliens().size();
		List<List<? extends DualCoordinateImage>> listOfListOfImages = 
			CollisionDetector.detectCollisionsAndRemoveTwoListsOfImages(changingGameState.getAliens(), changingGameState.getShipMissiles());
		changingGameState.getAlienHorde().setAliens((ArrayList<Alien>) listOfListOfImages.get(0));
		changingGameState.setShipMissiles((List<Missile>) listOfListOfImages.get(1));
		int numberOfAliensKilled = numberOfAliens - changingGameState.getAliens().size();
		this.incrementScore(TunableParameters.AlienScore * numberOfAliensKilled);
	}
	
	@SuppressWarnings("unchecked")
	public synchronized boolean run() {		
		instrument.startFrame();
		this.changingGameState = new GameState(this.currentGameState);
		boolean isGameOver = false;
		if(processingOn()) {
			moveObjects();
			changingGameState.getShip().regenerate();
			checkForKilledAliens();
			conditionallyAddMissile();
			changingGameState.setShipMissiles((List<Missile>) CollisionDetector.checkIfInScreen(changingGameState.getShipMissiles(), screenWidth, screenHeight));
			changingGameState.setAlienMissiles((List<Missile>) CollisionDetector.checkIfInScreen(changingGameState.getAlienMissiles(), screenWidth, screenHeight));
			
			isGameOver = gameOver();
		}
		this.currentGameState = changingGameState;
		instrument.endFrame();
		return isGameOver;
	}
	
	private boolean gameOver() {
		boolean gameOver = false;
		if(changingGameState.getAliens().size() == 0) {
			gameOver = true;
		}
		if(!gameOver) {
			gameOver = CollisionDetector.checkIfImageCollidesWithListOfImages(changingGameState.getShip(), changingGameState.getAliens());
		}
		if(!gameOver) {
			gameOver = CollisionDetector.checkIfImageCollidesWithListOfImages(changingGameState.getShip(), changingGameState.getAlienMissiles());
		}
		
		return gameOver;
	}
	
	public void signalStartPaintFrame() {
		paintInstrument.startFrame();
	}
	
	public void signalEndPaintFrame() {
		paintInstrument.endFrame();
	}
	
	public boolean processingOn() {
		return !pause;
	}
	
	private void togglePause() {
		pause = !pause;
	}
	
	public int getExhaust() {
		return currentGameState.getShip().getExhaust();
	}
}
