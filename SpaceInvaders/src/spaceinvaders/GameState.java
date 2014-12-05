package spaceinvaders;

import java.util.ArrayList;
import java.util.List;

public class GameState {

	private Background background;
	private AlienHorde aliens;
	private List<Missile> shipMissiles = new ArrayList<Missile>();
	private List<Missile> alienMissiles = new ArrayList<Missile>();
	private Ship ship;

	GameState(int screenWidth, int screenHeight) {
		background = Factory.createBackground(0, 0);
		aliens = LevelCreator.getAliens(screenWidth, screenHeight);
		ship = Factory.createShip(screenWidth/2, 0, screenWidth);
		ship.setY(screenHeight - ship.getImage().getHeight()*2);
	}
	
	GameState(GameState source) {
		this.background = new Background(source.background);
		this.aliens = new AlienHorde(source.aliens);
		for(Missile shipMissile: source.shipMissiles){
			this.shipMissiles.add(new ShipMissile((ShipMissile) shipMissile));
		}
		for(Missile alienMissile: source.alienMissiles){
			this.alienMissiles.add(new AlienMissile((AlienMissile) alienMissile));
		}
		this.ship = source.ship;
	}
	
	Background getBackground() {
		return background;
	}
	
	Ship getShip() {
		return ship;
	}
	
	AlienHorde getAlienHorde() {
		return aliens;
	}
	
	List<Alien> getAliens() {
		return aliens.getAliens();
	}
	
	void setAliens(ArrayList<Alien> alienList) {
		this.aliens.setAliens(alienList);
	}
	
	List<Missile> getShipMissiles() {
		return shipMissiles;
	}
	
	void setShipMissiles(List<Missile> newList) {
		this.shipMissiles = newList;
	}

	List<Missile> getAlienMissiles() {
		return alienMissiles;
	}
	
	void setAlienMissiles(List<Missile> newList) {
		this.alienMissiles = newList;
	}
	
	void regenerateShipWeapon() {
		this.ship.regenerate();
	}
	
	void randomlyGenerateAlienMissile() {
		Missile newMissile = this.aliens.randomlyGenerateMissiles();
		if(newMissile != null) {
			this.alienMissiles.add(newMissile);
		}
	}	
	
	void startMovingLeft() {
		this.ship.startMovingLeft();
	}

	void stopMovingLeft() {
		this.ship.stopMovingLeft();
	}

	void startMovingRight() {
		this.ship.startMovingRight();
	}

	void stopMovingRight() {
		this.ship.stopMovingRight();
	}
	
	void fireShipMissile() {
		Missile newMissile = ship.fireMissile();
		if(newMissile != null) {
			shipMissiles.add(newMissile);
		}
	}
}
