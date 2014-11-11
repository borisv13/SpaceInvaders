import java.awt.Cursor;
import java.awt.Event;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Panel;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.MemoryImageSource;


public class GamePanel extends Panel {

	private static final long serialVersionUID = 1L;
	
	private Image dbImage;
	private Game game;
	
	GamePanel(int screenWidth, int screenHeight) {
		this.setCursor(getTransparentCursor());
		this.setSize(screenWidth, screenHeight);
		game = new Game(screenWidth, screenHeight);
		repaint();
	}
	
	Cursor getTransparentCursor() {
		Image image = Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(16, 16, new int[16 * 16], 0, 16));
		return Toolkit.getDefaultToolkit().createCustomCursor(image, new Point(0, 0), "transparentCursor");
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		requestFocusInWindow();

		game.run();
		Painter.drawBackground(g, game.getBackground(), this);
		Painter.drawAliens(g, game.getAliens(), this);
		Painter.drawShip(g, game.getShip(), this);
		Painter.drawMissiles(g, game.getShipMissiles(), this);
		Painter.drawMissiles(g, game.getAlienMissiles(), this);
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
		} else if (key == 32) { //space
			game.keySpace();
		}
			
		return true;
	}
}
