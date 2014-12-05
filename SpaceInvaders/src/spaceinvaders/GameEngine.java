package spaceinvaders;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameEngine {
	
	private GameState currentGameState;
	private GameState changingGameState;
	private int screenWidth;
	private int screenHeight;
	private boolean paused;
	private int gameScore = 0;
	private Instrumenter instrument;
	private Instrumenter paintInstrument;	
	
	GameEngine(int screenWidth, int screenHeight) {
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		this.currentGameState = new GameState(screenWidth, screenHeight);
		this.paused = true;
		this.instrument = new Instrumenter(this, "EngineRun");
		this.paintInstrument = new Instrumenter(this, "Paint");
	}
	
	GameState getCurrentGameState() {
		return currentGameState;
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

	public int getExhaust() {
		return currentGameState.getShip().getExhaust();
	}

	void signalStartPaintFrame() {
		paintInstrument.startFrame();
	}
	
	void signalEndPaintFrame() {
		paintInstrument.endFrame();
	}
	
	boolean processingOn() {
		return !paused;
	}
	
	synchronized void keyLeftDown() {
		if(processingOn()) {
			currentGameState.startMovingLeft();
		}
	}
	
	synchronized void keyLeftUp() {
		if(processingOn()) {
			currentGameState.stopMovingLeft();
		}
	}
	
	synchronized void keyRightDown() {
		if(processingOn()) {
			currentGameState.startMovingRight();
		}
	}
	
	synchronized void keyRightUp() {
		if(processingOn()) {
			currentGameState.stopMovingRight();
		}
	}
	
	synchronized void keyUpDown() {
		if(processingOn()) {
			currentGameState.fireShipMissile();
		}
	}
	
	void keySpaceDown() {
		togglePause();
	}	
		
	@SuppressWarnings("unchecked")
	boolean run() {		
		instrument.startFrame();
		cloneGameState();
		boolean isGameOver = false;
		if(processingOn()) {
			moveObjects();
			changingGameState.regenerateShipWeapon();
			checkForKilledAliens();
			changingGameState.randomlyGenerateAlienMissile();
			changingGameState.setShipMissiles((List<Missile>) CollisionDetector.checkIfInScreen(changingGameState.getShipMissiles(), screenWidth, screenHeight));
			changingGameState.setAlienMissiles((List<Missile>) CollisionDetector.checkIfInScreen(changingGameState.getAlienMissiles(), screenWidth, screenHeight));
			
			isGameOver = gameOver();
		}
		this.currentGameState = changingGameState;
		instrument.endFrame();
		return isGameOver;
	}
	
	// This is in its own method so we can use the method level
	// synchronized keyword and only block on this section, 
	// not all of run method.
	private synchronized void cloneGameState() {
		this.changingGameState = new GameState(this.currentGameState);
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
	
	private void moveObjects() {
		for(GameMoveableObject moveableObject : getMoveableObjects()) {
			moveableObject.move();
		}
	}
		
	@SuppressWarnings("unchecked")
	private void checkForKilledAliens() {
		int numberOfAliens = changingGameState.getAliens().size();
		List<List<? extends DualCoordinateImage>> listOfListOfImages = 
			CollisionDetector.detectCollisionsAndRemoveTwoListsOfImages(changingGameState.getAliens(), changingGameState.getShipMissiles());
		changingGameState.setAliens((ArrayList<Alien>) listOfListOfImages.get(0));
		changingGameState.setShipMissiles((List<Missile>) listOfListOfImages.get(1));
		int numberOfAliensKilled = numberOfAliens - changingGameState.getAliens().size();
		this.incrementScore(TunableParameters.AlienScore * numberOfAliensKilled);
	}
	
	private void incrementScore(int increment) {
		this.gameScore += increment;
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
		
	private void togglePause() {
		paused = !paused;
	}
}
