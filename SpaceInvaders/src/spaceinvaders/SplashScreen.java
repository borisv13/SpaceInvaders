package spaceinvaders;

import java.awt.*;
import javax.swing.*;

public class SplashScreen extends JWindow
{
    private int duration;
    
    public SplashScreen(int d)
    {
        duration = d;
    }  // end of constructor
    
    // A simple little method to show a title screen in the center
    // of the screen for the amount of time given in the constructor
    public void showSplash()
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
        //JButton button = new JButton(new ImageIcon("images/Sweng587SplashFinal.gif"));

        JLabel copyrt = new JLabel
                ("SWENG 587 Fall 2014 : Team Two - Dante Pettus, Boris Valerstein, Chris Westbrook", JLabel.CENTER);
        copyrt.setFont(new Font("Sans-Serif", Font.BOLD, 12));
        content.add(label, BorderLayout.CENTER);
        content.add(copyrt, BorderLayout.SOUTH);
        content.setBorder(BorderFactory.createLineBorder(Color.blue, 10));
        
        // Display it
        setVisible(true);
        
        // Wait a little while, maybe while loading resources
        try
        {
            Thread.sleep(duration);
        } // end of try

        catch (Exception e) {}
        
        setVisible(false);
        
    } // end of showSlpash
    
    public void showSplashAndExit()
    {
        
        showSplash();
        //System.exit(0); comment this out to let program run the next step
        
    } // end of showSplashAndExit
    
    /*
    public static void main(String[] args)
    {
        
        // Throw a nice little title page up on the screen first
        SplashScreen splash = new SplashScreen(15000);

        // Normally, we'd call splash.showSplash() and get on 
        // with the program. But, since this is only a test...
        splash.showSplashAndExit();
        
    } // end of main
    */


} // end of SplashScreen
