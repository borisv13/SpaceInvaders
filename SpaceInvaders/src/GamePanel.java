import java.awt.Cursor;
import java.awt.Event;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Panel;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.MemoryImageSource;
import java.util.ArrayList;


public class GamePanel extends Panel {

	private Image dbImage;
	private ArrayList<Missile> missiles = new ArrayList<Missile>(10);
	private Game game;
	
	GamePanel(int width, int height) {
		this.setCursor(getTransparentCursor());
		this.setSize(width, height);
		game = new Game(width, height);
		repaint();
	}
	
	Cursor getTransparentCursor() {
		Image image = Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(16, 16, new int[16 * 16], 0, 16));
		return Toolkit.getDefaultToolkit().createCustomCursor(image, new Point(0, 0), "transparentCursor");
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		requestFocusInWindow();
		drawBackground(g);
		drawAlien(g);
		drawShip(g);
		
		for(Missile missile : this.missiles) {
			missile.move();
		}
		
		for(Missile missile : this.missiles) {
			g.drawImage(missile.getImage(), missile.getX(), missile.getY(), this);
		}		
	}
	
	public void drawBackground(Graphics g) {
		Background background = game.getBackground();
		background.move();
		g.drawImage(background.getImage(), background.getX(), background.getY(), this);
		int secondBackgroundHeight = background.getY() + background.getImage().getHeight() -1;
		g.drawImage(background.getImage(), background.getX(), secondBackgroundHeight, this);
	}
	
	public void drawShip(Graphics g) {
		Ship ship = game.getShip();
		g.drawImage(ship.getImage(), ship.getX(), ship.getY(), this);
	}
	
	public void drawAlien(Graphics g) {
		Alien alien = game.getAlien();
		alien.move();
		g.drawImage(alien.getImage(), alien.getX(), alien.getY(), this);
	}
	
	public void update (Graphics g)
	{
		if (dbImage == null)
		{
			dbImage = createImage(this.getSize().width, this.getSize().height);
		}
		Graphics dbg = dbImage.getGraphics();
		dbg.setColor(getBackground());
		dbg.fillRect(0, 0, this.getSize().width, this.getSize().height);
		dbg.setColor(getForeground());
		paint(dbg);
		g.drawImage(dbImage, 0, 0, this);
	}
	
	public boolean keyDown(Event evt, int key) {
		if (key == Event.LEFT) {
			game.keyLeft();
		} else if (key == Event.RIGHT) {
			game.keyRight();
		} else if (key == Event.UP) {
			//this.missiles.add(ship.fireMissile());
		}
			
		return true;
	}
}
