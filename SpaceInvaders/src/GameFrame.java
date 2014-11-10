import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.WindowAdapter;


public class GameFrame extends Frame {

	GamePanel gamePanel;
	
	GameFrame() {
		setSize(TunableParameters.ScreenWidth, TunableParameters.ScreenHeight);
		TunableParameters.SetReferenceSpeed(this.getWidth()/800);
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
	
	public void paint(Graphics g) {
		super.paint(g);
		gamePanel.repaint();
	}
}
