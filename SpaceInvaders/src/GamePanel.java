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

	private Background background;
	private Ship ship;
	private Alien alien;
	private Image dbImage;
	private ArrayList<Missile> missiles = new ArrayList<Missile>(10);
	
	GamePanel(int width, int height) {
		this.setCursor(getTransparentCursor());
		this.setSize(width, height);
		background = Factory.createBackground(0, 0);
		ship = Factory.createShip(width/2, 0);
		ship.setY(height - ship.getImage().getHeight()*2);
		alien = Factory.createAlien(0, 0);
		alien.setScreenWidth(width);
		
		repaint();
	}
	
	Cursor getTransparentCursor() {
		Image image = Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(16, 16, new int[16 * 16], 0, 16));
		return Toolkit.getDefaultToolkit().createCustomCursor(image, new Point(0, 0), "transparentCursor");
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		requestFocusInWindow();
		background.move();
		alien.move();
		for(Missile missile : this.missiles) {
			missile.move();
		}
		
		g.drawImage(background.getImage(), background.getX(), background.getY(), this);
		int secondBackgroundHeight = background.getY() + background.getImage().getHeight() -1;
		g.drawImage(background.getImage(), background.getX(), secondBackgroundHeight, this);
		g.drawImage(ship.getImage(), ship.getX(), ship.getY(), this);	
		g.drawImage(alien.getImage(), alien.getX(), alien.getY(), this);
		for(Missile missile : this.missiles) {
			g.drawImage(missile.getImage(), missile.getX(), missile.getY(), this);
		}		
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
			ship.moveLeft();
		} else if (key == Event.RIGHT) {
			ship.moveRight();
		} else if (key == Event.UP) {
			this.missiles.add(ship.fireMissile());
		}
			
		return true;
	}
}
