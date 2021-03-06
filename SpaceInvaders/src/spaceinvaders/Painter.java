package spaceinvaders;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Panel;
import java.io.ObjectInputStream.GetField;
import java.util.List;


public class Painter{

	public static void drawDualCoordinateImages(Graphics g, List<? extends DualCoordinateImage> images, Panel panel) {
		for(DualCoordinateImage image : images) {
			g.drawImage(image.getImage(), image.getX(), image.getY(), panel);
		}
	}
	
	public static void drawBackground(Graphics g, Background background, Panel panel) {
		g.drawImage(background.getImage(), background.getX(), background.getY(), panel);
		int secondBackgroundHeight = background.getY() + background.getImage().getHeight() -1;
		g.drawImage(background.getImage(), background.getX(), secondBackgroundHeight, panel);
	}
	
	public static void drawDualCoordinateImage(Graphics g, DualCoordinateImage image, Panel panel) {
		g.drawImage(image.getImage(), image.getX(), image.getY(), panel);
	}
	
	public static void drawInt(Graphics g, String string, int score, int x, int y) {
		g.setColor(Color.CYAN);
		Font prevFont = g.getFont();
		g.setFont(prevFont.deriveFont(12f));
		g.drawString(string + ": " + score, x, y);
		g.setFont(prevFont);				
	}
	
	public static void drawMessage(Graphics g, String string, int x, int y) {
		g.setColor(Color.RED);
		Font prevFont = g.getFont();
		g.setFont(prevFont.deriveFont(24f));
		g.drawString(string, x, y);
		g.setFont(prevFont);		
	}
}
