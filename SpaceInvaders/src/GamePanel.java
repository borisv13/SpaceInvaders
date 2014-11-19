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
	private GameEngine game;
	
	GamePanel(int screenWidth, int screenHeight) {
		this.setCursor(getTransparentCursor());
		this.setSize(screenWidth, screenHeight);
		game = new GameEngine(screenWidth, screenHeight);
		repaint();
	}
	
	Cursor getTransparentCursor() {
		Image image = Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(16, 16, new int[16 * 16], 0, 16));
		return Toolkit.getDefaultToolkit().createCustomCursor(image, new Point(0, 0), "transparentCursor");
	}
	
	private long frameCount = 0;
	private long totalDurationNS = 0;
	private int numFramesToAverage = 50;	
	public void paint(Graphics g) {
		long startTime = System.nanoTime();
		super.paint(g);
		requestFocusInWindow();

		Painter.drawBackground(g, game.getBackground(), this);
		Painter.drawDualCoordinateImages(g, game.getAliens(), this);
		Painter.drawDualCoordinateImage(g, game.getShip(), this);
		Painter.drawDualCoordinateImages(g, game.getShipMissiles(), this);
		Painter.drawDualCoordinateImages(g, game.getAlienMissiles(), this);
		Painter.drawInt(g, TunableParameters.ScoreDrawLabelText, game.getScore(), 
				TunableParameters.ScoreDrawXCoordinate, TunableParameters.ScoreDrawYCoordinate);
		Painter.drawInt(g, TunableParameters.ExhaustDrawLabelText, game.getExhaust(), 
				TunableParameters.ExhaustDrawXCoordinate, TunableParameters.ExhaustDrawYCoordinate);

		totalDurationNS += System.nanoTime() - startTime;
		frameCount++;
		if (frameCount % numFramesToAverage == 0) {
			long averageDurationNS = totalDurationNS / frameCount;
			/*System.out.printf(
					"Num Aliens,%d,Num Alien Missiles,%d,Num Ship Missiles,%d,AVG Update Frame Duration in MS,%f,Num Frames for AVG Calc,%d", 
					game.getAliens().size(), 
					game.getAlienMissiles().size(), 
					game.getShipMissiles().size(), 
					averageDurationNS/1000000.0,
					numFramesToAverage);
			System.out.println();*/
			frameCount = 0;
			totalDurationNS = 0;
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
			game.keyLeftDown();
		} else if (key == Event.RIGHT) {
			game.keyRightDown();
		} else if (key == Event.UP) { 
			game.keyUpDown();
		} else if (key == 32) { // space
			game.keySpaceDown();
		}
			
		return true;
	}
	
	public boolean keyUp(Event evt, int key) {
		if (key == Event.LEFT) {
			game.keyLeftUp();
		} else if (key == Event.RIGHT) {
			game.keyRightUp();
		}
			
		return true;
	}
	
	public void run() {
		game.run();
		repaint();
	}
}
