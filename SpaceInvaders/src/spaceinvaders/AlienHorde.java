package spaceinvaders;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class AlienHorde implements GameMoveableObject {

	private ArrayList<Alien> aliens;
	private int screenWidth;
	private int deltaX;
	private int rowDroppedCount;
	private Random randomGenerator;
	
	public AlienHorde(int screenWidth, ArrayList<Alien> aliens) {
		this.screenWidth = screenWidth;
		this.aliens = aliens;
		this.deltaX = TunableParameters.AlienSpeed;
		this.rowDroppedCount = 0;
		this.randomGenerator = new Random();
	}
	
	public AlienHorde(AlienHorde source) {
		this.screenWidth = source.screenWidth;		
		this.aliens = new ArrayList<Alien>();
		for(Alien alien: source.aliens){
			this.aliens.add(new Alien(alien));
		}
		this.deltaX = source.deltaX;
		this.rowDroppedCount = source.rowDroppedCount;
		this.randomGenerator = source.randomGenerator;
	}

	@Override
	public void move() {
		int deltaY = 0;
		int newSpeed = Math.abs(deltaX);
		
		// first scan list to see if at an edge and need to switch directions
		for(Alien alien: this.aliens){
			if (alien.x <=0 || alien.x + alien.imageWidth >= this.screenWidth) {
				rowDroppedCount++;
				deltaY = TunableParameters.AlienVerticalSpeed;
				
				if ((rowDroppedCount % TunableParameters.NumRowsDropToIncreaseAlienSpeed) == 0) {
					newSpeed = Math.min(TunableParameters.AlienSpeedMax, newSpeed + TunableParameters.AlienSpeedIncreaseIncrement);
				}

				if (deltaX < 0) {
					deltaX = newSpeed;
				} else {
					deltaX = -newSpeed;
				}				
				break;
			}
		}
		
		for(Alien alien: this.aliens){
			alien.move(deltaX, deltaY);
		}		
	}
	
	public List<Alien> getAliens(){
		return this.aliens;
	}
	
	public void setAliens(ArrayList<Alien> aliens) {
		this.aliens = aliens;
	}
	
	public Missile randomlyGenerateMissiles() {
		if (aliens.size() == 0)
			return null;
			
		int randomInt = randomGenerator.nextInt(100);
		if (randomInt < TunableParameters.PercentChanceAlienFiresMissile)
		{
			int alienToShoot;
			if (aliens.size() > 1)			{
				alienToShoot = randomGenerator.nextInt(aliens.size()-1);
			}
			else {
				alienToShoot = 0;
			}
			return aliens.get(alienToShoot).fireMissile(); 
		}
		return null;
	}
	

}
