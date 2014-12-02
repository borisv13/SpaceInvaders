package spaceinvaders;
import java.awt.Frame;
import java.awt.event.WindowAdapter;


public class GameFrame extends Frame {
	private static final long serialVersionUID = 1L;
	
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
	
	public boolean run()
    {
		return gamePanel.run();
	}
}
