import java.util.ArrayList;
import java.util.List;


public class AlienHorde implements GameMoveableObject {

	private List<Alien> aliens;
	
	public AlienHorde(List<Alien> aliens) {
		this.aliens = aliens;
	}

	@Override
	public void move() {
		// keeping old movement for this first iteration of adding in this wrapper class
		// Next will change to get desired motion
		for(Alien alien: this.aliens){
			alien.move();
		}
		
	}
	
	public List<Alien> getAliens(){
		return this.aliens;
	}

}
