package spaceinvaders;

import java.util.ArrayList;
import java.util.List;

public class GameState {

	private Background background;
	private AlienHorde aliens;
	private List<Missile> shipMissiles = new ArrayList<Missile>();
	private List<Missile> alienMissiles = new ArrayList<Missile>();
	private Ship ship;

	public GameState(int screenWidth, int screenHeight) {
		background = Factory.createBackground(0, 0);
		aliens = LevelCreator.getAliens(screenWidth, screenHeight);
		ship = Factory.createShip(screenWidth/2, 0, screenWidth);
		ship.setY(screenHeight - ship.getImage().getHeight()*2);
	}
	
	public GameState(GameState source) {
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
	
	public Background getBackground() {
		return background;
	}
	
	public Ship getShip() {
		return ship;
	}
	
	public AlienHorde getAlienHorde() {
		return aliens;
	}
	
	public List<Alien> getAliens() {
		return aliens.getAliens();
	}
	
	public List<Missile> getShipMissiles() {
		return shipMissiles;
	}
	
	public void setShipMissiles(List<Missile> newList) {
		this.shipMissiles = newList;
	}

	public List<Missile> getAlienMissiles() {
		return alienMissiles;
	}
	
	public void setAlienMissiles(List<Missile> newList) {
		this.alienMissiles = newList;
	}
}
