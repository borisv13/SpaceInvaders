/* 
 *Boris Valerstin
 *bv49@drexel.edu
 *CS338:GUI, Project
 *March 8th, 2011
 */
//Written and developed by Boris Valerstein
import java.util.*;
import java.awt.event.*;
import java.awt.image.*;
import java.awt.*;
import java.applet.*;
import java.awt.geom.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
public class brickapp extends Panel implements Runnable
{
	int i;
	static int score = 600;
	static int lives = 5;
	static int level = 0;
	static int maxlevel = 6;
	static boolean pause = true;
	static boolean game = false;
	static int bwidth;
	static int bheight;
	static int count = 0;
	static int current = -1;
	static int bx;
	static int by;
	static int tempox;
	static int tempoy;
	ArrayList<brick> bricks = new ArrayList<brick>();
	ArrayList<powerup> powerups = new ArrayList<powerup>();
	private Image dbImage;
	private Graphics dbg;
	static int h;
	static int w;
	static int or = 8;
	static int ox;
	static int oy;
	static int oxspeed = 0;	static int oyspeed = 0;
	static int rwidth;
	static int rheight;
	static int rx;
	static int ry;
	static int rspeed = 0;
	static int tempr = 0;
	static int pausetemprspeed = 0;
	static int pausetempoxspeed = 2;
	static int pausetempoyspeed = 3;
	static boolean remove = false;
	
	//Images
	static Image brick1, brick2, brick3, brickdoom;
	static Image brick1blue, brick2blue, brick3blue, brick1red, brick2red, brick3red, brick1green, brick2green, brick3green, brick1purple, brick2purple, brick3purple;
	
	static Image platformnormal, platformsmall, platformlarge, platformcurrent;
	static Image platformnormalblue, platformsmallblue, platformlargeblue, platformnormalred, platformsmallred, platformlargered, platformnormalgreen, platformsmallgreen, platformlargegreen,platformnormalpurple, platformsmallpurple, platformlargepurple, platformnormalgrey, platformsmallgrey, platformlargegrey;
	
	static Image xframe, yframe;
	static Image redxframe, redyframe, purplexframe, purpleyframe, greenxframe, greenyframe, bluexframe, blueyframe;
	
	static Image powerupLP, powerupSP, powerupLB, powerupSB, powerupp50, powerupp100, powerupp150, powerupm50, powerupm100, powerupm150;
	static Image image = Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(16, 16, new int[16 * 16], 0, 16));
	static Cursor transparentCursor = Toolkit.getDefaultToolkit().createCustomCursor(image, new Point(0, 0), "transparentCursor");
	static Random generator = new Random();
	
	static Menu menu;
	static brickapp ba;
	static JFrame f;
	
	static int SPEED = 12;
	static String HSNAME = "";
	static String SKIN = "Default";
	static String BGCOLOR = "Cyan";
	static String BRICKCOLOR = "Purple";
	static String XFRAMECOLOR = "Red";
	static String YFRAMECOLOR = "Red";
	static String PLATFORMCOLOR = "Grey";
	static Color bg = Color.cyan;
	
	static boolean HSOPEN = false;
	static boolean PREFOPEN = false;
	static boolean EXITOPEN = false;
	static boolean MENUOPEN = false;
	static boolean HELPOPEN = false;
	
	
public static void main(String[] args) 
{
	f = new JFrame();
	f.setTitle("Boris' Breakout Bonanza");
	f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	f.addWindowListener(new java.awt.event.WindowAdapter() {
	   public void windowClosing(java.awt.event.WindowEvent e) {
	   		//Pause and open exit menu
	   		rspeed = 0;
			if(!pause)
				pause();
			if(!EXITOPEN)
				new Exit();
	   };
	 });
	ba = new brickapp();
	ba.init();
	//add the game to the frame
	f.add(ba);
	f.pack();
	//get the usable graphic space on the screen, takes windows bar into account
	GraphicsEnvironment graphicsEnvironment=GraphicsEnvironment.getLocalGraphicsEnvironment();
	Rectangle maximumWindowBounds=graphicsEnvironment.getMaximumWindowBounds();
	f.setMaximizedBounds(maximumWindowBounds);
	f.setExtendedState(f.getExtendedState() | f.MAXIMIZED_BOTH);
	//sets max size screen
	f.setSize(maximumWindowBounds.width, maximumWindowBounds.height);
	//set it to unresizeable, the screen size is fixed!
	f.setResizable(false);
	ba.setSize(f.getWidth(), f.getHeight());
	f.show();
	//set an easy variable for height and width of the screen, for future reference
	h = ba.getHeight();
	w = ba.getWidth();
	//set the ball location
	ox = w/2 - 175;
	oy = h/2;
	//set platform to normal one and its width/height and it's location in center
	platformcurrent = platformnormal;
	rwidth = platformcurrent.getWidth(null);
	rheight = platformcurrent.getHeight(null);
	rx = w/2 - rwidth/2;
	ry = h - rheight;
	//set ball location
	bwidth = brick1.getWidth(null);
	bheight = brick1.getHeight(null);
	//open menu
	try { menu = new Menu(); } catch (Exception aRef) {}
}
public void init()
{
	//set cursor, images, wait for all images and ensure no errors
    setCursor(transparentCursor);
	setBackground(bg);
	brick1purple = getToolkit().getImage("Bricks/Purple3.gif");
    brick2purple = getToolkit().getImage("Bricks/Purple2.gif");
    brick3purple = getToolkit().getImage("Bricks/Purple1.gif");
    brick1blue = getToolkit().getImage("Bricks/Blue3.gif");
    brick2blue = getToolkit().getImage("Bricks/Blue2.gif");
    brick3blue = getToolkit().getImage("Bricks/Blue1.gif");
    brick1green = getToolkit().getImage("Bricks/Green3.gif");
    brick2green = getToolkit().getImage("Bricks/Green2.gif");
    brick3green = getToolkit().getImage("Bricks/Green1.gif");
    brick1red = getToolkit().getImage("Bricks/Red3.gif");
    brick2red = getToolkit().getImage("Bricks/Red2.gif");
    brick3red = getToolkit().getImage("Bricks/Red1.gif");
    brickdoom = getToolkit().getImage("Bricks/BRICK_OF_DOOM_REBORN.gif");
    platformnormalgrey = getToolkit().getImage("Platforms/Normal132px.gif");
    platformsmallgrey = getToolkit().getImage("Platforms/Normal72px.gif");
    platformlargegrey = getToolkit().getImage("Platforms/Normal192px.gif");
    platformnormalred = getToolkit().getImage("Platforms/redNormal132px.gif");
    platformsmallred = getToolkit().getImage("Platforms/redNormal72px.gif");
    platformlargered = getToolkit().getImage("Platforms/redNormal192px.gif");
    platformnormalblue = getToolkit().getImage("Platforms/blueNormal132px.gif");
    platformsmallblue = getToolkit().getImage("Platforms/blueNormal72px.gif");
    platformlargeblue = getToolkit().getImage("Platforms/blueNormal192px.gif");
    platformnormalgreen = getToolkit().getImage("Platforms/greenNormal132px.gif");
    platformsmallgreen = getToolkit().getImage("Platforms/greenNormal72px.gif");
    platformlargegreen = getToolkit().getImage("Platforms/greenNormal192px.gif");
    platformnormalpurple = getToolkit().getImage("Platforms/purpleNormal132px.gif");
    platformsmallpurple = getToolkit().getImage("Platforms/purpleNormal72px.gif");
    platformlargepurple = getToolkit().getImage("Platforms/purpleNormal192px.gif");
    redxframe = getToolkit().getImage("Frames/redxframe.gif");
    redyframe = getToolkit().getImage("Frames/redyframe.gif");
    greenxframe = getToolkit().getImage("Frames/greenxframe.gif");
    greenyframe = getToolkit().getImage("Frames/greenyframe.gif");
    bluexframe = getToolkit().getImage("Frames/bluexframe.gif");
    blueyframe = getToolkit().getImage("Frames/blueyframe.gif");
    purplexframe = getToolkit().getImage("Frames/purplexframe.gif");
    purpleyframe = getToolkit().getImage("Frames/purpleyframe.gif");
    powerupLP = getToolkit().getImage("Powerups/powerupLP.gif");
    powerupSP = getToolkit().getImage("Powerups/powerupSP.gif");
    powerupLB = getToolkit().getImage("Powerups/powerupLB.gif");
    powerupSB = getToolkit().getImage("Powerups/powerupSB.gif");
    powerupp50 = getToolkit().getImage("Powerups/powerup+50.gif");
    powerupp100 = getToolkit().getImage("Powerups/powerup+100.gif");
    powerupp150 = getToolkit().getImage("Powerups/powerup+150.gif");
    powerupm50 = getToolkit().getImage("Powerups/powerup-50.gif");
    powerupm100 = getToolkit().getImage("Powerups/powerup-100.gif");
    powerupm150 = getToolkit().getImage("Powerups/powerup-150.gif");
    MediaTracker tracker = new MediaTracker(this);
    tracker.addImage(brick1red,1);
    tracker.addImage(brick2red,2);
    tracker.addImage(brick3red,3);
    tracker.addImage(brick1blue,14);
    tracker.addImage(brick2blue,15);
    tracker.addImage(brick3blue,16);
    tracker.addImage(brick1green,17);
    tracker.addImage(brick2green,18);
    tracker.addImage(brick3green,19);
    tracker.addImage(brick1purple,20);
    tracker.addImage(brick2purple,21);
    tracker.addImage(brick3purple,22);
    tracker.addImage(platformnormalred,4);
    tracker.addImage(platformsmallred,5);
    tracker.addImage(platformlargered,6);
    tracker.addImage(platformnormalblue,23);
    tracker.addImage(platformsmallblue,24);
    tracker.addImage(platformlargeblue,25);
    tracker.addImage(platformnormalgreen,26);
    tracker.addImage(platformsmallgreen,27);
    tracker.addImage(platformlargegreen,28);
    tracker.addImage(platformnormalpurple,29);
    tracker.addImage(platformsmallpurple,30);
    tracker.addImage(platformlargepurple,31);
    tracker.addImage(platformnormalgrey,32);
    tracker.addImage(platformsmallgrey,33);
    tracker.addImage(platformlargegrey,34);
    tracker.addImage(brickdoom,7);
    tracker.addImage(redxframe,8);
    tracker.addImage(redyframe,9);
    tracker.addImage(purplexframe,35);
    tracker.addImage(purpleyframe,36);
    tracker.addImage(bluexframe,37);
    tracker.addImage(blueyframe,38);
    tracker.addImage(greenxframe,39);
    tracker.addImage(greenyframe,40);
    tracker.addImage(powerupLP,10);
    tracker.addImage(powerupSP,11);
    tracker.addImage(powerupLB,12);
    tracker.addImage(powerupSB,13);
    try{ tracker.waitForAll(); }
    catch(InterruptedException e) {	System.out.println("Something happened while reading image..."); System.exit(0); }
    
    //set default images
    brick1 = brick1purple;
    brick2 = brick2purple;
    brick3 = brick3purple;
    platformnormal = platformnormalgrey;
    platformsmall = platformsmallgrey;
    platformlarge = platformlargegrey;
    xframe = redxframe;
    yframe = redyframe;
    
    
    pause = true;
    //start game, set it's size to max
    Thread th = new Thread(this);
	th.start();
	this.setSize(h, w);
	
}	
public void start()
{
}
public void stop()
{
}
public void destroy()
{
}
//when the app is closed
public boolean handleEvent(Event evt)
{
	if (evt.id == Event.WINDOW_DESTROY) 
	{
		try 
		{
		}
		catch (Exception aRef) {}
		System.exit(0);
	}
	return super.handleEvent(evt);
}
//when a key is pressed down
public boolean keyDown(Event evt, int key)
{
	//if the game is on, 
	if (game)
	{
		//set platform speed
		int platformspeed = 0;
		if (!pause) 
			platformspeed = ((evt.modifiers & Event.SHIFT_MASK) == 0) ? 10 : 20;
		if (key == Event.LEFT)
		{
			rspeed = -1*platformspeed;
		}
		else if (key == Event.RIGHT)
		{
			rspeed = platformspeed;
		}
		//space! -- pause
		else if (key == 32)
		{
			rspeed = 0;
			if (!pause)
			{
				pause();
			}
			else if (pause)
			{
				unpause();
			}
		}
		//pause and open menu
		else if( key == Event.ESCAPE)
		{
			rspeed = 0;
			if(!pause)
				pause();
			menu = new Menu();
		}
		//skip level
		else if( key == Event.DELETE)
		{
			level++; lives = 5; pause = true; 
			pausetempoxspeed = 2; pausetempoyspeed = 3; pausetemprspeed = 0;
			oxspeed = 0; oyspeed = 0; rspeed = 0; ox = w/2 - 175; oy = h/2; 
			platformcurrent = platformnormal; rwidth = platformcurrent.getWidth(null); 
			rheight = platformcurrent.getHeight(null); rx = w/2 - rwidth/2;
			ry = h - rheight; or = 8;
			bricks.clear();
			powerups.clear();
			count = 0;
			level();
		}
		else //some other button was pressed - dont let it affect motion
		{
			if(rspeed > 0)
				rspeed = platformspeed;
			else
				rspeed = -1 * platformspeed;
			return false;
		}
		return true;
	}
	else return false;
}

public static void pause()
{
	pause = true;
	pausetempoxspeed = oxspeed;
	pausetempoyspeed = oyspeed;
	pausetemprspeed = rspeed;
	oxspeed = 0; oyspeed = 0; rspeed = 0;
}

public void unpause()
{
	pause = false;
	oxspeed = pausetempoxspeed;
	oyspeed = pausetempoyspeed;
	rspeed = pausetemprspeed;
	pausetempoxspeed = 0; pausetempoyspeed = 0; pausetemprspeed = 0;
}

//when a key is lifted
public boolean keyUp(Event evt, int key)
{
	if (key == Event.LEFT)
	{
		if(rspeed < 0)
			rspeed = 0;
	}
	else if (key == Event.RIGHT)
	{
		if(rspeed > 0)
			rspeed = 0;
	}
	return true;
}

public void run()
{
	Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
	
	while(true)
	{
		if (game)
		{
			//if no bricks, recreate the level, reset the field with speeds, locations, sizes
			//clear bricks in case there are BoD! and powerups too.
			if (count <= 0) 
			{
				pause = true; 
				pausetempoxspeed = 2; pausetempoyspeed = 3; pausetemprspeed = 0;
				oxspeed = 0; oyspeed = 0; rspeed = 0; ox = w/2 - 175; oy = h/2; 
				platformcurrent = platformnormal; rwidth = platformcurrent.getWidth(null); 
				rheight = platformcurrent.getHeight(null); rx = w/2 - rwidth/2;
				ry = h - rheight; or = 8;
				bricks.clear();
				powerups.clear();
				level++;
				if (level > maxlevel) level = 1;
				level();
			}
			//move platform, if it goes off the edge place it at the edge
			rx += rspeed;
			if (rx + rwidth >= w-yframe.getWidth(null)) { rspeed = 0; rx = w-yframe.getWidth(null)-rwidth; }
			if (rx <= yframe.getWidth(null)) { rspeed = 0; rx = yframe.getWidth(null); }
			//move ball, if it hits the edges the ball bounces and is placed at the edge 
			//(in case it went past the edge)
			ox += oxspeed;
			oy += oyspeed;
			if (ox + or >= w-yframe.getWidth(null)) { oxspeed *= -1; ox = w-yframe.getWidth(null)-or; }
			if (ox - or <= yframe.getWidth(null)) { oxspeed *= -1; ox = or+yframe.getWidth(null); }
			if (oy - or <= xframe.getHeight(null)+20) { oyspeed *= -1; oy = or+xframe.getHeight(null)+20; }
			//if the ball hits the bottom, lose a life, pause, reset platform and ball (lose powerups)
			if (oy + or >= h) 
			{ 
				lives--; pause = true; 
				pausetempoxspeed = 2; pausetempoyspeed = 3; pausetemprspeed = 0;
				oxspeed = 0; oyspeed = 0; rspeed = 0; ox = w/2 - 175; oy = h/2; 
				platformcurrent = platformnormal; rwidth = platformcurrent.getWidth(null); 
				rheight = platformcurrent.getHeight(null); rx = w/2 - rwidth/2;
				ry = h - rheight; or = 8;
			}
			//if no more lives, ask for name for the highscores and then quit
			if (lives <= 0) 
			{
				if(score >= 500)
				{
					if(HSNAME.trim().equals("") || HSNAME == null)
					{
						try { highscores(score, input("Enter your name: ")); }
						catch (Exception aRef) {}
					}
					else
					{
						try { highscores(score, HSNAME); }
						catch (Exception aRef) {}
					}
				}	
				level = 0; score = 0; lives = 5; pause = true; 
				pausetempoxspeed = 2; pausetempoyspeed = 3; pausetemprspeed = 0;
				oxspeed = 0; oyspeed = 0; rspeed = 0; ox = w/2 - 175; oy = h/2; 
				platformcurrent = platformnormal; rwidth = platformcurrent.getWidth(null); 
				rheight = platformcurrent.getHeight(null); rx = w/2 - rwidth/2;
				ry = h - rheight; or = 8;
				bricks.clear();
				powerups.clear();
				count = 0;
				level();
			}
			//if it hits the platform the ball bounces up vertically and 
			//the horiz speed of the ball is set porportionally to where on the platform it hits
			if ((oyspeed > 0) && (oy + or >= h - rheight) && (ox + or >= rx) && (ox - or <= rx + rwidth)) 
			{
				oyspeed *= -1;
				oxspeed = (ox - ((rwidth/2)+rx))/10;
			}
			//check every brick
			if (!pause)
			{
				for (Iterator it = bricks.iterator(); it.hasNext();)
				{
					//get next brick
					brick br = (brick) it.next();
					//set to check bottom-right corner
					tempox = ox + or; tempoy = oy + or;
					//if the ball is inside the brick, remove = true
					if (br.getX() <= tempox && tempox <= br.getX() + bwidth && br.getY() <= tempoy && tempoy <= br.getY() + bheight) { remove = true;}
					//if the previous corner was not in the brick, set to check bottom-right corner
					else { tempox = ox + or*-1; tempoy = oy + or; }
					if (br.getX() <= tempox && tempox <= br.getX() + bwidth && br.getY() <= tempoy && tempoy <= br.getY() + bheight) { remove = true;}
					//if not, set to top-right corner
					else { tempox = ox + or; tempoy = oy + or*-1; }
					if (br.getX() <= tempox && tempox <= br.getX() + bwidth && br.getY() <= tempoy && tempoy <= br.getY() + bheight) { remove = true;}
					//if not, set to top-left corner
					else { tempox = ox + or*-1; tempoy = oy + or*-1; }
					if (br.getX() <= tempox && tempox <= br.getX() + bwidth && br.getY() <= tempoy && tempoy <= br.getY() + bheight) { remove = true;}
					if (remove)
					{
						//check every edge and if it's next to that edge (vert or horiz) it will bounce back 
						//in that direction (x or y, respectively
						//this ensures that the ball will bounce off of any edge or corner correctly
						if (Math.abs(tempox-(br.getX())) <= Math.abs(oxspeed) && oxspeed > 0) oxspeed *= -1;
						if (Math.abs(tempox-(br.getX()+bwidth)) <= Math.abs(oxspeed) && oxspeed < 0) oxspeed *= -1;
						if (Math.abs(tempoy-(br.getY())) <= Math.abs(oyspeed) && oyspeed > 0) oyspeed *= -1;
						if (Math.abs(tempoy-(br.getY()+bheight)) <= Math.abs(oyspeed) && oyspeed < 0) oyspeed *= -1;
						//if the brick was not an indestructible BoD, brick's health decreases and score increases
						if (br.getLife() != -1) { br.setlife(); score += 20; }
					}
					//if brick has no life left
					if (br.getLife() == 0) 
					{ 
						//create a new powerup of random type
						powerups.add(new powerup(br.getX()+brick1.getWidth(null)/2-5, br.getY()+brick1.getHeight(null)-5, generator.nextInt(10)+1));
						//remove the brick -> the brick is gone from the ArrayList
						it.remove(); 
						//decrement the brick count
						count--; 
					}
					remove = false;
				}
				//check every powerup
				for (Iterator it = powerups.iterator(); it.hasNext();)
				{
					//get next powerup
					powerup pu = (powerup) it.next();
					//move the powerup
					pu.move();
					//if the powerup hits the bottom of the screen, remove it
					if (pu.getY() >= h) try { it.remove(); } catch (Exception aRef) {}
					//if powerup hits platform (user collects it)
					if ((pu.getY() + powerupLP.getHeight(null) >= h - rheight) && pu.getY() < h && (pu.getX() + powerupLP.getWidth(null) >= rx) && (pu.getX() <= rx + rwidth))
					{
						//depending on the powerup's type, act
						switch (pu.getType())
						{
							//make platform big
							case 1: platformcurrent = platformlarge; 
								rx -= (platformlarge.getWidth(null)-rwidth)/2;
								break;
							//make platform small
							case 2: platformcurrent = platformsmall; 
								rx -= (platformsmall.getWidth(null)-rwidth)/2;
								break;
							//make ball bigger
							case 3: or = 12; break;
							//make ball smaller
							case 4: or = 4; break;
							case 5: score += 50; break;
							case 6: score += 100; break;
							case 7: score += 150; break;
							case 8: score -= 50; break;
							case 9: score -= 100; break;
							case 10: score -= 150; break;
						}
						//set platform width and height to prevent stale data
						rwidth = platformcurrent.getWidth(null);
						rheight = platformcurrent.getHeight(null);
						//remove the powerup
						try { it.remove(); } catch (Exception aRef) {}
					}
				}
			}
			try
			{
				//wait before re-running
				Thread.sleep(SPEED);
			}
			catch (InterruptedException ex) { }
			Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
			//call the paint again
			repaint();
		}
	}
}
//double buffering code
public void update (Graphics g)
{
	if (dbImage == null)
	{
	dbImage = createImage(this.getSize().width, this.getSize().height);
	dbg = dbImage.getGraphics();
	}
	dbg.setColor(bg);
	dbg.fillRect(0, 0, this.getSize().width, this.getSize().height);
	dbg.setColor(getForeground());
	paint(dbg);
	g.drawImage(dbImage, 0, 0, this);
}
//the paint method
public void paint(Graphics g)
{
	g.setColor(Color.black);
	//print the lives and score in upper-left corner
	g.drawString("Lives: " + lives, 6, 15);
	g.drawString("Score: " + score, 75, 15);
	//paint oval (ball) at its location
	g.fillOval(ox - or, oy - or, 2 * or, 2 * or);
	//paint platform image (whatever size it may be) at its location
	g.drawImage(platformcurrent, rx, ry, this);
	//for all bricks, paint the brick at its location, the image depends on the brick's life
	for (brick br : bricks)
	{
		switch (br.getLife())
		{
			case -1: g.drawImage(brickdoom, br.getX(), br.getY(), this); break;
			case 1:	g.drawImage(brick1, br.getX(), br.getY(), this); break;
			case 2: g.drawImage(brick2, br.getX(), br.getY(), this); break;
			case 3: g.drawImage(brick3, br.getX(), br.getY(), this); break;
		}
	}
	//for all powerups, paint the powerup at its location, the image depends on the powerup's type
	for(powerup pu : powerups)
	{
		switch (pu.getType())
		{
			case 1:	g.drawImage(powerupLP, pu.getX(), pu.getY(), this);	break;
			case 2: g.drawImage(powerupSP, pu.getX(), pu.getY(), this);	break;
			case 3: g.drawImage(powerupLB, pu.getX(), pu.getY(), this);	break;
			case 4: g.drawImage(powerupSB, pu.getX(), pu.getY(), this);	break;
			case 5: g.drawImage(powerupp50, pu.getX(), pu.getY(), this);	break;
			case 6: g.drawImage(powerupp100, pu.getX(), pu.getY(), this);	break;
			case 7: g.drawImage(powerupp150, pu.getX(), pu.getY(), this);	break;
			case 8: g.drawImage(powerupm50, pu.getX(), pu.getY(), this);	break;
			case 9: g.drawImage(powerupm100, pu.getX(), pu.getY(), this);	break;
			case 10: g.drawImage(powerupm150, pu.getX(), pu.getY(), this);	break;
		}
	}
	//paint the frame! horiz once at the top, vert twice, one on each side of the screen
	g.drawImage(xframe, 0, 20, this);
	g.drawImage(yframe, 0, 20, this);
	g.drawImage(yframe, w-yframe.getWidth(null), 20, this);
	g.setColor(Color.black);
	if (pause)
		try 
		{ 
			g.drawLine(ox + (Math.abs(pausetempoxspeed)/pausetempoxspeed)*or,  
					oy + (Math.abs(pausetempoyspeed)/pausetempoyspeed)*or,
					ox + pausetempoxspeed*2 + (Math.abs(pausetempoxspeed)/pausetempoxspeed)*or,
					oy + pausetempoyspeed*2 + (Math.abs(pausetempoyspeed)/pausetempoyspeed)*or); 
		}
		//Going vertically (can't divide by 0)
		catch (Exception aRef) 
		{
			g.drawLine(ox, oy + pausetempoyspeed*or, ox, oy + 2*pausetempoyspeed);
		}
}
//creates the levels (brick layouts)
public void level()
{
	int hits = 0;
	switch (level)
	{
		case 1: for (by = 30; by < h/5; by += bheight+1)
					for (bx = (w-(((w-20)/bwidth)* bwidth))/2; bx < w-10-bwidth; bx += bwidth)
					{
						bricks.add(new brick(bx, by, 1));
						count++;
					}
				break;
		case 2: for (by = 30; by < h/5; by += bheight+1)
					for (bx = (w-(((w-20)/bwidth)* bwidth))/2; bx < w-10-bwidth; bx += bwidth)
					{
						bricks.add(new brick(bx, by, (hits%3)+1));
						count++;
						hits++;
					}
				break;
		case 3: for (by = 30; by < h/5; by += bheight+1)
				{
					for (bx = (w-(((w-20)/bwidth)* bwidth))/2; bx < w-10-bwidth; bx += bwidth)
					{
						bricks.add(new brick(bx, by, hits+1));
						count++;
					}
					hits++;
				}
				break;
		case 4: for (bx = ((w-(((w-20)/bwidth)* bwidth))/2)+bwidth; bx < w-10-bwidth*2; bx += bwidth*2)
				{
					bricks.add(new brick(bx, 30, -1));
					bricks.add(new brick(bx, 180, -1));
				}
				for (int i = 0; i < 3; i++)
					for (bx = (w-(((w-20)/bwidth)* bwidth))/2; bx < w-10-bwidth; bx += bwidth*2)
					{	
						bricks.add(new brick(bx, 30+bheight*(i+1), 2));
						count++;
					}
				break;
		case 5: for (by = 30; by < bheight*6; by += bheight+1)
					for (bx = ((w-(((w-20)/bwidth)* bwidth))/2)+bwidth*2; bx < w-10-bwidth*2; bx += bwidth*4)
						bricks.add(new brick(bx, by, -1));
				for (by = 30; by < bheight*6; by += bheight+1)
					for (bx = (w-(((w-20)/bwidth)* bwidth))/2; bx < w-10-bwidth; bx += bwidth)
					{
						bricks.add(new brick(bx, by, 1));
						count++;
						if (bx == ((w-(((w-20)/bwidth)* bwidth))/2)+bwidth) bx += bwidth;
						if (bx == ((w-(((w-20)/bwidth)* bwidth))/2)+bwidth*5) bx += bwidth;
					}
				break;
		case 6: for (bx = ((w-(((w-20)/bwidth)* bwidth))/2)+bwidth*2; bx < w-10-bwidth*2; bx += bwidth*4)
						bricks.add(new brick(bx, 30+bheight*6+6, -1));
				for (by = 30; by < bheight*6; by += bheight+1)
					bricks.add(new brick(((w-(((w-20)/bwidth)* bwidth))/2)+bwidth*4, by, -1));
				for (by = 30; by < bheight*6; by += bheight+1)
					for (bx = (w-(((w-20)/bwidth)* bwidth))/2; bx < w-10-bwidth; bx += bwidth)
					{
						bricks.add(new brick(bx, by, 2));
						count++;
						if (bx == ((w-(((w-20)/bwidth)* bwidth))/2)+bwidth*3) bx += bwidth;
					}
				break;
	}
	repaint();
}

	public static class Menu extends JDialog implements ActionListener {
	  
	  public Menu() {
	  	//remove invisible cursor, set booleans
	    ba.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	    game = false;
	    MENUOPEN = true;
	    
	    setTitle("Menu");
	    
	    JPanel panel = new JPanel();
	    panel.setLayout(new GridLayout(0,1,6,6));	    
	    
	    JButton play = new JButton("Play Game");
		play.setActionCommand("play");
		play.addActionListener(this);
		JButton preferences = new JButton("Preferences");
		preferences.setActionCommand("pref");
		preferences.addActionListener(this);
		JButton highscores = new JButton("High Scores");
		highscores.setActionCommand("hs");
		highscores.addActionListener(this);
		JButton help = new JButton("Help");
		help.setActionCommand("help");
		help.addActionListener(this);
		JButton exit = new JButton("Exit Game");
		exit.setActionCommand("exit");
		exit.addActionListener(this);
		
		panel.add(play);
		panel.add(preferences);
		panel.add(highscores);
		panel.add(help);
		panel.add(exit);
		
		panel.setBorder(BorderFactory.createEmptyBorder(12,12,12,12));
		getContentPane().add(panel, BorderLayout.CENTER);
	    setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
	    pack();
	    setLocation((w/2) - (getWidth()/2), (h/2) - (getHeight()/2)); 
	    setAlwaysOnTop(true);
	    setResizable(false);
	    setVisible(true);
	  }
	  public void actionPerformed(ActionEvent e) {
	    	if ("play".equals(e.getActionCommand())) 
	    	{
	    		//only allow play game if all windows are closed
	    		if(!HSOPEN && !PREFOPEN && !EXITOPEN && !HELPOPEN)
	    		{
		    		game = true;
			        setVisible(false); 
		    		dispose();
		    		MENUOPEN = false;
		    		f.requestFocus();
		    		ba.requestFocus();
		    		ba.setCursor(transparentCursor);
	    		}
    		}
    		else if ("hs".equals(e.getActionCommand()))
    		{
    			if(!HSOPEN)
    				new HS();
    		}
    		else if ("pref".equals(e.getActionCommand()))
    		{
    			if(!PREFOPEN)
    				new Preferences();
    		}
    		else if ("help".equals(e.getActionCommand()))
    		{
    			if(!HELPOPEN)
    				new Help();
    		}
    		else if ("exit".equals(e.getActionCommand()))
    		{
    			if(score >=500)
    			{
    				if(!EXITOPEN)
    					new Exit();
    			}
    			else
    				System.exit(0);
    		}
	  }
	}
	
	public static class HS extends JDialog implements ActionListener {
	  public HS() {
	  	HSOPEN = true;
		setTitle("High Scores");
		
	    JPanel panel = new JPanel();
	    panel.setLayout(new BorderLayout(6,6));	    
	    
	    JTextArea textArea = null;
	    textArea = new JTextArea ();
	    textArea.setEditable (false);

	    //Write highscores
		textArea.append("HIGH SCORES: \n\n");
		try
		{
			FileReader freader = new FileReader("scores.txt");
			BufferedReader inputFile=new BufferedReader(freader);
			//store the whole file into the array of strings
			int i = 0;
			while (i < 25)
			{
				textArea.append(i+1 + ":  " + inputFile.readLine()+"\n");
				i++;
			}
			inputFile.close();
			freader.close();
		}
		catch(Exception e)
		{
			textArea.append("Something went wrong with the file reading.");
		}
		
		JButton close = new JButton("Close");
		close.setActionCommand("close");
		close.addActionListener(this);
		
		panel.add(textArea, BorderLayout.CENTER);
		panel.add(close, BorderLayout.SOUTH);
		panel.setBorder(BorderFactory.createEmptyBorder(12,12,12,12));
		getContentPane().add(panel, BorderLayout.CENTER);
		//getContentPane().add(close, BorderLayout.SOUTH);
		addWindowListener(new java.awt.event.WindowAdapter() {
		   public void windowClosing(java.awt.event.WindowEvent e) {
		   		setVisible(false); 
	    		dispose(); 
	    		HSOPEN = false;
		   };
		 });
	    //setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
	    pack(); 
	    setVisible(true);
	    setAlwaysOnTop(true);
	    setResizable(false);
	  }
	  public void actionPerformed(ActionEvent e) {
	    	if ("close".equals(e.getActionCommand())) 
	    	{
		        setVisible(false); 
	    		dispose(); 
	    		HSOPEN = false;
    		}
	  }
	}
	
	public static class Preferences extends JDialog implements ActionListener {
	  JSpinner speeds;
	  JComboBox skins, bgcolors, platformcolors, brickcolors, xframecolors, yframecolors;
	  JTextField name;
	  
	  public Preferences() {
	  	PREFOPEN = true;
		setTitle("Preferences");
		
	    JPanel panel = new JPanel();
	    panel.setLayout(new GridLayout(0,1,6,6));	    
	    
	    JPanel speed = new JPanel();
	    speed.setLayout(new GridLayout(1, 0));
	    speed.add(new JLabel(" Speed: "));
	    String[] speedValues = { "1", "2", "3", "4", "5", "6", "7", "8", "9"};
	    SpinnerListModel speedModel = new SpinnerListModel(speedValues);
	    speeds = new JSpinner(speedModel);
	    speed.add(speeds);
		
		JPanel skin = new JPanel();
	    skin.setLayout(new GridLayout(1, 0));
	    skin.add(new JLabel(" Skin: "));
	    String[] skinValues = { "Default", "Custom" };
	  	skins = new JComboBox(skinValues);
	    skin.add(skins);
	    skins.setActionCommand("skins");
	    skins.addActionListener(this);
		
		JPanel backgroundcolor = new JPanel();
	    backgroundcolor.setLayout(new GridLayout(1, 0));
	    backgroundcolor.add(new JLabel("        Background Color: "));
	    String[] bgcolorValues = { "Cyan", "Red", "Green", "Purple", "Blue" };
	    bgcolors = new JComboBox(bgcolorValues);
	    backgroundcolor.add(bgcolors);
	    
	    JPanel platformcolor = new JPanel();
	    platformcolor.setLayout(new GridLayout(1, 0));
	    platformcolor.add(new JLabel("        Platform Color: "));
	    String[] platformcolorValues = { "Grey", "Red", "Green", "Purple", "Blue" };
	    platformcolors = new JComboBox(platformcolorValues);
	    platformcolor.add(platformcolors);
	    
	    JPanel brickcolor = new JPanel();
	    brickcolor.setLayout(new GridLayout(1, 0));
	    brickcolor.add(new JLabel("        Brick Color: "));
	    String[] regcolorValues = { "Red", "Green", "Purple", "Blue" };
	    brickcolors = new JComboBox(regcolorValues);
	    brickcolor.add(brickcolors);
	    
	    JPanel xframecolor = new JPanel();
	    xframecolor.setLayout(new GridLayout(1, 0));
	    xframecolor.add(new JLabel("        Horizontal Frame Color: "));
	    xframecolors = new JComboBox(regcolorValues);
	    xframecolor.add(xframecolors);
	    
	    JPanel yframecolor = new JPanel();
	    yframecolor.setLayout(new GridLayout(1, 0));
	    yframecolor.add(new JLabel("        Vertical Frame Color: "));
	    yframecolors = new JComboBox(regcolorValues);
	    yframecolor.add(yframecolors);
	    
		JPanel highscoresname = new JPanel();
	    highscoresname.setLayout(new GridLayout(1, 0));
	    highscoresname.add(new JLabel(" High Scores Name: "));
	    name = new JTextField(20);
	    name.setText(HSNAME);
	    highscoresname.add(name);
		
		panel.add(speed);
		panel.add(skin);
		panel.add(backgroundcolor);
		panel.add(platformcolor);
		panel.add(brickcolor);
		panel.add(xframecolor);
		panel.add(yframecolor);
		panel.add(highscoresname);
		
		JPanel buttons = new JPanel();
	    buttons.setLayout(new GridLayout(1, 0,6,6));
	    		
		JButton cancel = new JButton("Close");
		cancel.setActionCommand("cancel");
		cancel.addActionListener(this);
		JButton save = new JButton("Save");
		save.setActionCommand("save");
		save.addActionListener(this);
		JButton reset = new JButton("Reset");
		reset.setActionCommand("reset");
		reset.addActionListener(this);
		
		buttons.add(save);
		buttons.add(reset);
		buttons.add(cancel);
		
		load();
		panel.setBorder(BorderFactory.createEmptyBorder(12,12,12,12));
		getContentPane().add(panel, BorderLayout.CENTER);
		getContentPane().add(buttons, BorderLayout.SOUTH);
		
		addWindowListener(new java.awt.event.WindowAdapter() {
		   public void windowClosing(java.awt.event.WindowEvent e) {
		   		setVisible(false); 
	    		dispose(); 
	    		PREFOPEN = false;
		   };
		 });
	    //setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
	    pack(); 
	    setLocation(w - getWidth(), 0);
	    setAlwaysOnTop(true);
	    setResizable(false);
	    setVisible(true);
	  }
	  public void load()
	  {
	  	name.setText(HSNAME);
	  	speeds.setValue(Integer.toString((SPEED-17)*-1));
	  	skins.setSelectedItem(SKIN);
	  	bgcolors.setSelectedItem(BGCOLOR);
	  	platformcolors.setSelectedItem(PLATFORMCOLOR);
	    brickcolors.setSelectedItem(BRICKCOLOR);
	    xframecolors.setSelectedItem(XFRAMECOLOR);
	    yframecolors.setSelectedItem(YFRAMECOLOR);
	    if(!SKIN.equalsIgnoreCase("Custom"))
	    {
	    	bgcolors.setEnabled(false);
	    	platformcolors.setEnabled(false);
	    	brickcolors.setEnabled(false);
	    	xframecolors.setEnabled(false);
	    	yframecolors.setEnabled(false);
	    }	  	
	  }
	  public void actionPerformed(ActionEvent e) {
	    	if ("cancel".equals(e.getActionCommand())) 
	    	{
		        setVisible(false); 
	    		dispose(); 
	    		PREFOPEN = false;
    		}
    		else if ("save".equals(e.getActionCommand())) 
	    	{
	    		SPEED = (Integer.parseInt((String)speeds.getValue())*-1)+17;
	    		SKIN = (String)skins.getSelectedItem();
		        BGCOLOR = (String)bgcolors.getSelectedItem();
		        PLATFORMCOLOR = (String)platformcolors.getSelectedItem();
		        BRICKCOLOR = (String)brickcolors.getSelectedItem();
		        XFRAMECOLOR = (String)xframecolors.getSelectedItem();
		        YFRAMECOLOR = (String)yframecolors.getSelectedItem();
		        HSNAME = name.getText();
		        if(BGCOLOR.equalsIgnoreCase("Red"))
		        {
		        	bg = Color.RED;
		        }
		        else if(BGCOLOR.equalsIgnoreCase("Blue"))
		        {
		        	bg = Color.BLUE;
		        }
		        else if(BGCOLOR.equalsIgnoreCase("Green"))
		        {
			        bg = Color.GREEN;
		        }
		        else if(BGCOLOR.equalsIgnoreCase("Purple"))
		        {
		        	bg = Color.MAGENTA;
		        }
		        else if(BGCOLOR.equalsIgnoreCase("Cyan"))
		        {
		        	bg = Color.CYAN;
		        }
		        ba.setBackground(bg);
		        if(PLATFORMCOLOR.equalsIgnoreCase("Red"))
		        {
		        	platformsmall = platformsmallred;
		        	platformlarge = platformlargered;
		        	platformnormal = platformnormalred;
		        }
		        else if(PLATFORMCOLOR.equalsIgnoreCase("Blue"))
		        {
		        	platformsmall = platformsmallblue;
		        	platformlarge = platformlargeblue;
		        	platformnormal = platformnormalblue;
		        }
		        else if(PLATFORMCOLOR.equalsIgnoreCase("Green"))
		        {
		        	platformsmall = platformsmallgreen;
		        	platformlarge = platformlargegreen;
		        	platformnormal = platformnormalgreen;
		        }
		        else if(PLATFORMCOLOR.equalsIgnoreCase("Purple"))
		        {
		        	platformsmall = platformsmallpurple;
		        	platformlarge = platformlargepurple;
		        	platformnormal = platformnormalpurple;
		        }
		        else if(PLATFORMCOLOR.equalsIgnoreCase("Grey"))
		        {
		        	platformsmall = platformsmallgrey;
		        	platformlarge = platformlargegrey;
		        	platformnormal = platformnormalgrey;
		        }
		        if(platformcurrent.getWidth(null) == platformnormal.getWidth(null))
		        	platformcurrent = platformnormal;
		        else if(platformcurrent.getWidth(null) == platformsmall.getWidth(null))
		        	platformcurrent = platformsmall;
		        else if(platformcurrent.getWidth(null) == platformlarge.getWidth(null))
		        	platformcurrent = platformlarge;
		        if(BRICKCOLOR.equalsIgnoreCase("Red"))
		        {
		        	brick1 = brick1red;
		        	brick2 = brick2red;
		        	brick3 = brick3red;
		        }
		        else if(BRICKCOLOR.equalsIgnoreCase("Green"))
		        {
		        	brick1 = brick1green;
		        	brick2 = brick2green;
		        	brick3 = brick3green;
		        }
		        else if(BRICKCOLOR.equalsIgnoreCase("Blue"))
		        {
		        	brick1 = brick1blue;
		        	brick2 = brick2blue;
		        	brick3 = brick3blue;
		        }
		        else if(BRICKCOLOR.equalsIgnoreCase("Purple"))
		        {
		        	brick1 = brick1purple;
		        	brick2 = brick2purple;
		        	brick3 = brick3purple;
		        }
		        if(XFRAMECOLOR.equalsIgnoreCase("Red"))
		        {
		        	xframe = redxframe;
		        }
		        else if(XFRAMECOLOR.equalsIgnoreCase("Green"))
		        {
		        	xframe = greenxframe;
		        }
		        else if(XFRAMECOLOR.equalsIgnoreCase("Blue"))
		        {
		        	xframe = bluexframe;
		        }
		        else if(XFRAMECOLOR.equalsIgnoreCase("Purple"))
		        {
		        	xframe = purplexframe;
		        }
		        if(YFRAMECOLOR.equalsIgnoreCase("Red"))
		        {
		        	yframe = redyframe;
		        }
		        else if(YFRAMECOLOR.equalsIgnoreCase("Green"))
		        {
		        	yframe = greenyframe;
		        }
		        else if(YFRAMECOLOR.equalsIgnoreCase("Blue"))
		        {
		        	yframe = blueyframe;
		        }
		        else if(YFRAMECOLOR.equalsIgnoreCase("Purple"))
		        {
		        	yframe = purpleyframe;
		        }
		        ba.repaint();
    		}
    		else if ("reset".equals(e.getActionCommand())) 
	    	{
		        load();
    		}
    		else if ("skins".equals(e.getActionCommand())) 
	    	{
		        if(((String)skins.getSelectedItem()).equalsIgnoreCase("Custom"))
		        {
		        	bgcolors.setEnabled(true);
			    	platformcolors.setEnabled(true);
			    	brickcolors.setEnabled(true);
			    	xframecolors.setEnabled(true);
			    	yframecolors.setEnabled(true);
		        }
		        else
		        {
		        	bgcolors.setEnabled(false);
			    	platformcolors.setEnabled(false);
			    	brickcolors.setEnabled(false);
			    	xframecolors.setEnabled(false);
			    	yframecolors.setEnabled(false);
		        }
		        if(((String)skins.getSelectedItem()).equalsIgnoreCase("Default"))
		        {
		        	bgcolors.setSelectedItem("Cyan");
				  	platformcolors.setSelectedItem("Grey");
				    brickcolors.setSelectedItem("Purple");
				    xframecolors.setSelectedItem("Red");
				    yframecolors.setSelectedItem("Red");
		        }
    		}
	  }
	}
	
	public static class Exit extends JDialog implements ActionListener {
	  
	  public Exit() {
	  	ba.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	  	game = false;
	  	EXITOPEN = true;
		setTitle("Exit");
		
		JPanel panel = new JPanel();
	    panel.setLayout(new BorderLayout(6,6));	
	    	
		JPanel buttons = new JPanel();
	    buttons.setLayout(new GridLayout(1, 0, 6, 6));
	    		
		JButton cancel = new JButton("Cancel");
		cancel.setActionCommand("cancel");
		cancel.addActionListener(this);
		JButton save = new JButton("Save Score and Exit");
		save.setActionCommand("save");
		save.addActionListener(this);
		JButton exit = new JButton("Exit");
		exit.setActionCommand("exit");
		exit.addActionListener(this);
		
		buttons.add(save);
		buttons.add(exit);
		buttons.add(cancel);
		
		if(score < 500)
			save.setEnabled(false);
		
		panel.setBorder(BorderFactory.createEmptyBorder(12,12,12,12));
		
		panel.add(new JLabel("Are you sure you want to quit?"), BorderLayout.CENTER);
		panel.add(buttons, BorderLayout.SOUTH);
		
		getContentPane().add(panel, BorderLayout.CENTER);
		addWindowListener(new java.awt.event.WindowAdapter() {
		   public void windowClosing(java.awt.event.WindowEvent e) {
		   		if(!MENUOPEN)
		   		{
		   			game = true;
		   			ba.setCursor(transparentCursor);
		   		}
		   		setVisible(false); 
	    		dispose(); 
	    		EXITOPEN = false;
		   };
		 });
	    pack(); 
	   	setLocation((w/2) - (getWidth()/2), (h/2) - (getHeight()/2));
	    setAlwaysOnTop(true);
	    setResizable(false);
	    setVisible(true);
	  }
	  public void actionPerformed(ActionEvent e) {
	    	if ("cancel".equals(e.getActionCommand())) 
	    	{
	    		if(!MENUOPEN)
	    		{
	    			game = true;
	    			ba.setCursor(transparentCursor);
	    		}
		        setVisible(false); 
	    		dispose(); 
	    		EXITOPEN = false;
    		}
    		else if ("save".equals(e.getActionCommand())) 
	    	{
	    		if(HSNAME.trim().equals("") || HSNAME == null)
				{
					try { highscores(score, input("Enter your name: ")); }
					catch (Exception aRef) {}
				}
				else
				{
					try { highscores(score, HSNAME); }
					catch (Exception aRef) {}
				}
				System.exit(0);
    		}
    		else if ("exit".equals(e.getActionCommand())) 
	    	{
	    		System.exit(0);
    		}
	  }
	}
	
	public static class Help extends JDialog implements ActionListener {
	  
	  public Help() {
	  	game = false;
	  	HELPOPEN = true;
		setTitle("Help");
		
		JPanel panel = new JPanel();
	    panel.setLayout(new BorderLayout(6,6));	
	    
	    		
		JButton close = new JButton("Close");
		close.setActionCommand("close");
		close.addActionListener(this);
		
		JPanel textArea = new JPanel();
		textArea.setLayout(new GridLayout(1,0,6,6));
		
		String buff = "&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp";
		
		JPanel controls = new JPanel();
		controls.setLayout(new GridLayout(0,1,6,6));
		controls.add(new JLabel("<html>"+buff+" <u>Buttons</u></html>"));
		controls.add(new JLabel("<html>"+buff+" Arrows"));
		controls.add(new JLabel("<html>"+buff+" Hold Shift"));
		controls.add(new JLabel("<html>"+buff+" Space"));
		controls.add(new JLabel("<html>"+buff+" Escape"));
		controls.add(new JLabel(""));
		
		controls.add(new JLabel("<html>"+buff+" <u>Items</u></html>"));
		JLabel l = new JLabel(new ImageIcon(powerupLP));
		l.setSize(powerupLP.getWidth(null), l.getHeight());
		l.setAlignmentX(JTextField.LEFT);
		controls.add(new JLabel(new ImageIcon(powerupLP)));
		controls.add(new JLabel(new ImageIcon(powerupSP)));
		controls.add(new JLabel(new ImageIcon(powerupLB)));
		controls.add(new JLabel(new ImageIcon(powerupSB)));
		controls.add(new JLabel(new ImageIcon(powerupp50)));
		controls.add(new JLabel(new ImageIcon(powerupp100)));
		controls.add(new JLabel(new ImageIcon(powerupp150)));
		controls.add(new JLabel(new ImageIcon(powerupm50)));
		controls.add(new JLabel(new ImageIcon(powerupm100)));
		controls.add(new JLabel(new ImageIcon(powerupm150)));
		
		JPanel defs = new JPanel();
		defs.setLayout(new GridLayout(0,1,6,6));
		defs.add(new JLabel("<html><u>Actions</u></html>"));
		defs.add(new JLabel("Move Platform"));
		defs.add(new JLabel("Increase Platform Speed"));
		defs.add(new JLabel("Pause"));
		defs.add(new JLabel("Open Menu"));
		defs.add(new JLabel(""));
		defs.add(new JLabel("<html><u>Effects</u></html>"));
		defs.add(new JLabel("Large Platform"));
		defs.add(new JLabel("Small Platform"));
		defs.add(new JLabel("Large Ball"));
		defs.add(new JLabel("Small Ball"));
		defs.add(new JLabel("Plus 50 Points"));
		defs.add(new JLabel("Plus 100 Points"));
		defs.add(new JLabel("Plus 150 Points"));
		defs.add(new JLabel("Minus 50 Points"));
		defs.add(new JLabel("Minus 100 Points"));
		defs.add(new JLabel("Minus 150 Points"));
		
		textArea.add(controls);
		textArea.add(defs);
		
		panel.setBorder(BorderFactory.createEmptyBorder(12,12,12,12));
		panel.add(textArea, BorderLayout.CENTER);
		panel.add(close, BorderLayout.SOUTH);
		
		getContentPane().add(panel, BorderLayout.CENTER);
		addWindowListener(new java.awt.event.WindowAdapter() {
		   public void windowClosing(java.awt.event.WindowEvent e) {
		   		setVisible(false); 
	    		dispose(); 
	    		HELPOPEN = false;
		   };
		 });
	    pack(); 
	    setVisible(true);
	    setAlwaysOnTop(true);
	    setResizable(false);
	  }
	  public void actionPerformed(ActionEvent e) {
	    	if ("close".equals(e.getActionCommand())) 
	    	{
		        setVisible(false); 
	    		dispose(); 
	    		HELPOPEN = false;
    		}
	  }
	}
//does the highscores implementation
public static void highscores (int score, String name) throws IOException
{
	if (name == null || name.equals(""))
		name = "Unnamed";
	//capacity of list in highscores is 25
	String[] hsstrings = new String[25];
	int i = 0;
	FileReader freader=new FileReader("scores.txt");
	BufferedReader inputFile=new BufferedReader(freader);
	//store the whole file into the array of strings
	while (i < 25)
	{
		hsstrings[i] = inputFile.readLine();
		i++;
	}
	inputFile.close();
	freader.close();
	
	FileWriter fwriter = new FileWriter("scores.txt");
	PrintWriter outputfile = new PrintWriter(fwriter);
	boolean finished = false;
	int spot = 0;
	i = 0;
	//check every string that was loaded
	while (i < 25)
	{
		//find where the dash "-" is
		spot = hsstrings[i].indexOf("-");
		//get the substring from 2 spaces past the dash to the end of the string to get
		//the number that was in the string, then compare that to the user's score
		//if the user's score is greater, write the user's first, followed by the others
		//finished is set to true, so the user's info cannot be written twice
		if (!finished && score > Integer.parseInt(hsstrings[i].substring(spot+2).trim()))
		{
			outputfile.println(name + " - " + score);
			finished = true;
		}
		outputfile.println(hsstrings[i]);
		i++;
	}	
	outputfile.close();
	fwriter.close();
}
//get input from user (used to get their name)
public static String input(String output)
    {
		int amount = 0;
		String inputAmount = null;
		boolean amountdone = false;
    	while(!amountdone)
    	{
	    	try
	    	{
	    		inputAmount = JOptionPane.showInputDialog(output);
	    		amountdone = true;
	    	}
	    	catch (Exception aRef) {}
    	}
    	return inputAmount;
    }
	
}
