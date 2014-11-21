import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.Timer;
import javax.swing.JWindow;


public class SplashScreen extends JWindow {

  private static final long serialVersionUID = -6470090944414208496L;
  
  private static JProgressBar progressBar;
  private static int count = 1;
  private static int TIMER_PAUSE = 100;
  private static int PROGBAR_MAX = 105;
  private static Timer progressBarTimer;

  public SplashScreen() {
    createSplash();
  }

  public void createSplash() {

    JPanel panel = new JPanel();
    panel.setLayout(new BorderLayout());
    JLabel splashImage = 
     new JLabel (new ImageIcon(getClass().getResource("/images/BorisIntro.gif")));
    panel.add(splashImage);

    progressBar = new JProgressBar();
    progressBar.setMaximum(PROGBAR_MAX);
    progressBar.setBorder(BorderFactory.createLineBorder(Color.black));
    panel.add(progressBar, BorderLayout.SOUTH);

    this.setContentPane(panel);
    this.pack();
    this.setLocationRelativeTo(null);
    this.setVisible(true);

    startProgressBar();
  } // end createSplash

  private void startProgressBar()
  {

    progressBarTimer = new Timer(TIMER_PAUSE, new ActionListener()
    {
      public void actionPerformed(ActionEvent arg0)
      {
        progressBar.setValue(count);

        if(PROGBAR_MAX == count)
        {
          SplashScreen.this.dispose();
          progressBarTimer.stop();
        }
        count++;
      }
    });
    progressBarTimer.start();

  }

  /*public static void main(String[] args)
  {
    new SplashScreen();

  } // end of main
  */
  
} // end of SplashScree


