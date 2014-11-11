import java.awt.Cursor;
import java.awt.Event;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Panel;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.MemoryImageSource;


public class GamePanel extends Panel {

	private Image dbImage;
	private GameEngine engine;
	
	GamePanel(int width, int height, GameEngine engine) {
		this.setCursor(getTransparentCursor());
		this.setSize(width, height);
		this.engine = engine;
		repaint();
	}
	
	Cursor getTransparentCursor() {
		Image image = Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(16, 16, new int[16 * 16], 0, 16));
		return Toolkit.getDefaultToolkit().createCustomCursor(image, new Point(0, 0), "transparentCursor");
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		requestFocusInWindow();
		//TODO Don't really want this here.  Want it in SpaceInvaders main loop, 
		// but the call to repaint is async so end up changing images while painting
		// Maybe need double buffering of an images list?
		this.engine.runFrame();
		for(DualCoordinateImage image : this.engine.getImages()) {
			g.drawImage(image.getImage(), image.getX(), image.getY(), this);
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
		return this.engine.processPlayerInput(evt, key);
	}
}
