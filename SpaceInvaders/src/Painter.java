import java.awt.Graphics;
import java.awt.Panel;
import java.util.List;


public class Painter{

	public static void drawMissiles(Graphics g, List<Missile> missiles, Panel panel) {
		for(Missile missile : missiles) {
			g.drawImage(missile.getImage(), missile.getX(), missile.getY(), panel);
		}
	}
	
	public static void drawBackground(Graphics g, Background background, Panel panel) {
		g.drawImage(background.getImage(), background.getX(), background.getY(), panel);
		int secondBackgroundHeight = background.getY() + background.getImage().getHeight() -1;
		g.drawImage(background.getImage(), background.getX(), secondBackgroundHeight, panel);
	}
	
	public static void drawShip(Graphics g, Ship ship, Panel panel) {
		g.drawImage(ship.getImage(), ship.getX(), ship.getY(), panel);
	}
	
	public static void drawAliens(Graphics g, List<Alien> aliens, Panel panel) {
		for(Alien alien : aliens) {
			g.drawImage(alien.getImage(), alien.getX(), alien.getY(), panel);
		}
	}
}
