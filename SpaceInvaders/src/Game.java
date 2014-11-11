import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {
	
	private Background background;
	private Ship ship;
	private Alien alien;
	private List<Missile> shipMissiles = new ArrayList<Missile>();
	private List<Missile> alienMissiles = new ArrayList<Missile>();
	Random randomGenerator = new Random();
	
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
		alienMissiles = randomlyGenerateMissiles(alien, alienMissiles);
		
		for(Missile missile : shipMissiles) {
			missile.move();
		}
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
