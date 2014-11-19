import java.util.List;
import java.util.Random;


public class AlienHorde implements GameMoveableObject {

	private List<Alien> aliens;
	private int screenWidth;
	private int deltaX = TunableParameters.AlienSpeed;
	Random randomGenerator = new Random();
	
	public AlienHorde(int screenWidth, List<Alien> aliens) {
		this.screenWidth = screenWidth;
		this.aliens = aliens;
	}

	@Override
	public void move() {
		int deltaY = 0;
		
		// first scan list to see if at an edge and need to switch directions
		for(Alien alien: this.aliens){
			if (alien.x <=0 || alien.x + alien.imageWidth >= this.screenWidth) {
				deltaY = TunableParameters.AlienVerticalSpeed;
				deltaX = -deltaX;
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
