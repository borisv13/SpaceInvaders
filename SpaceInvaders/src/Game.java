import java.util.ArrayList;
import java.util.List;

public class Game {
	
	private Background background;
	private Ship ship;
	private Alien alien;
	private ArrayList<Missile> shipMissiles = new ArrayList<Missile>();
	private ArrayList<Missile> alienMissiles = new ArrayList<Missile>();
	
	Game(int width, int height) {
		background = Factory.createBackground(0, 0);
		ship = Factory.createShip(width/2, 0);
		ship.setY(height - ship.getImage().getHeight()*2);
		alien = Factory.createAlien(0, 0);
		alien.setScreenWidth(width);
	}
	
	public Background getBackground() {
		return background;
	}
	
	public Ship getShip() {
		return ship;
	}
	
	public Alien getAlien() {
		return alien;
	}
	
	public List<Missile> getShipMissiles() {
		return shipMissiles;
	}
	
	public List<Missile> getAlienMissiles() {
		return alienMissiles;
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
		background.move();
		alien.move();
		for(Missile missile : alienMissiles) {
			missile.move();
		}
		alienMissiles.add(alien.fireMissile());
		
		for(Missile missile : shipMissiles) {
			missile.move();
		}
	}
}
