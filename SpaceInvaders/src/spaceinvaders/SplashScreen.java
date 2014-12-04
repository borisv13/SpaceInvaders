package spaceinvaders;

import java.awt.*;

import javax.swing.*;

public class SplashScreen extends JWindow
{    
	private static final long serialVersionUID = 1L;

	// A simple little method to show a title screen in the center
    // of the screen for the amount of time given
    public void showSplashModal(int durationMS)
    {        
        JPanel content = (JPanel)getContentPane();
        content.setBackground(Color.white);
        
        // Set the window's bounds, centering the window
        int width = 720;
        int height = 480;
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screen.width-width)/2;
        int y = (screen.height-height)/2;
        setBounds(x,y,width,height);
        
        // Build the splash screen
        JLabel label = new JLabel(new ImageIcon("./resources/images/Sweng587SplashFinal.gif"));

        JLabel copyrt = new JLabel
                ("SWENG 587 Fall 2014 : Team Two - Dante Pettus, Boris Valerstein, Chris Westbrook", JLabel.CENTER);
        copyrt.setFont(new Font("Sans-Serif", Font.BOLD, 12));

        content.add(label, BorderLayout.CENTER);
        content.add(copyrt, BorderLayout.SOUTH);
        content.setBorder(BorderFactory.createLineBorder(Color.blue, 10));
        
        setVisible(true);
        
        try {
			Thread.sleep(durationMS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        
        setVisible(false);        
    }    
}
