package spaceinvaders;
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
	
	public void paint(Graphics g) {
		game.signalStartPaintFrame();
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
		Painter.drawInt(g, TunableParameters.FPSDrawLabelText, game.getFPS(), 
				TunableParameters.FPSDrawXCoordinate, TunableParameters.FPSDrawYCoordinate);
		Painter.drawInt(g, TunableParameters.PaintFPSDrawLabelText, game.getPaintFPS(), 
				TunableParameters.PaintFPSDrawXCoordinate, TunableParameters.PaintFPSDrawYCoordinate);
		
		game.signalEndPaintFrame();
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
	
	public boolean run() {
		boolean gameOver = game.run();
		repaint();
		return gameOver;
	}
}
