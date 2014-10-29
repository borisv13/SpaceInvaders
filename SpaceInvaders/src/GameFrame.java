import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.WindowAdapter;


public class GameFrame extends Frame {

	GamePanel gamePanel;
	
	GameFrame() {
		setMaximizedBounds(getMaximumWindowBounds());
		setSize(getMaximizedBounds().getSize());
		setResizable(false);
		addWindowClosingListener();
		gamePanel = new GamePanel(this.getWidth(), this.getHeight());
		this.add(gamePanel);
		setVisible(true);
	}
	
	void addWindowClosingListener() {
		addWindowListener(new WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				System.exit(0);
			}
		});
	}
	
	Rectangle getMaximumWindowBounds() {
		GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
		return graphicsEnvironment.getMaximumWindowBounds();
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		gamePanel.repaint();
	}
}
