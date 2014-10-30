import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Panel;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.MemoryImageSource;


public class GamePanel extends Panel {

	private Background background;
	private Image dbImage;
	
	GamePanel(int width, int height) {
		this.setCursor(getTransparentCursor());
		this.setSize(width, height);
		background = Factory.createBackground(0, 0);
		repaint();
	}
	
	Cursor getTransparentCursor() {
		Image image = Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(16, 16, new int[16 * 16], 0, 16));
		return Toolkit.getDefaultToolkit().createCustomCursor(image, new Point(0, 0), "transparentCursor");
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		background.move();
		g.drawImage(background.getImage(), background.getX(), background.getY(), this);
		int secondBackgroundHeight = background.getY() + background.getImage().getHeight() -1;
		g.drawImage(background.getImage(), background.getX(), secondBackgroundHeight, this);
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
}
