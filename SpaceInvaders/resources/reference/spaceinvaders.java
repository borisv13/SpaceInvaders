/**
 * @(#)spaceinvaders.java
 *
 * spaceinvaders Applet application
 *
 * @author 
 * @version 1.00 2008/5/12
 */
import java.util.*;
import java.awt.event.*;
import java.awt.image.*;
import java.awt.*;
import java.applet.*;
import java.awt.geom.*;

public class spaceinvaders extends Applet implements Runnable, MouseListener, MouseMotionListener {

int rx;
int ry;

int bgy = -630;

private Image dbImage;
private Graphics dbg;

boolean start = false;

Image bgImage;
Image rocketImage;
Image rocketBullet1;
Image rocketBullet2;
Image rocketBullet3;
Image alienImage1;
Image alienImage2;
Image alienImage3;
Image alienImage4;
Image alienBullet;
Image heart;
Image mothershipImage;
Image powerUpBomb;
Image powerUp1;
Image powerUp2;
Image powerUp3;
Image barracadeImage;

Image rbulletImage;

ArrayList<alienInvader> alienInvaders = new ArrayList<alienInvader>();
ArrayList<rBullet> rBullets = new ArrayList<rBullet>();
ArrayList<mothership> motherships = new ArrayList<mothership>();
ArrayList<aBullet> aBullets = new ArrayList<aBullet>();
ArrayList<powerUp> powerUps = new ArrayList<powerUp>();
int mothershipLaunchTime = 1000;
int mothershipLaunch = mothershipLaunchTime;
int shootTime = 0;
boolean mouseDown = false;
boolean remove = false;
int level = 0;
int levelStall = 75;
int levelMax = 12;
int alienMove = 1;
int power = 1;
int weapon = 1;
int bomb = 0;
int exhaust = 100;
int exhaustValue = 0;
int lives = 5;

int j = 1;
boolean game = false;

Image image = Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(16, 16, new int[16 * 16], 0, 16));
Cursor transparentCursor = Toolkit.getDefaultToolkit().createCustomCursor(image, new Point(0, 0), "transparentCursor");

Random generator = new Random();

	public void init() {
		//set cursor
    	setCursor(transparentCursor);
    	//set all images
    	alienImage1 = getImage(getCodeBase(),"alien1.gif");
    	alienImage2 = getImage(getCodeBase(),"alien2.gif");
    	alienImage3 = getImage(getCodeBase(),"alien3.gif");
    	alienImage4 = getImage(getCodeBase(),"alien4.gif");
    	alienBullet = getImage(getCodeBase(),"alienBullet.gif");
    	bgImage = getImage(getCodeBase(),"spacebg.gif");
    	rocketImage = getImage(getCodeBase(),"rocketCursor.gif");
    	rocketBullet1 = getImage(getCodeBase(),"rocketBullet1.gif");
    	rocketBullet2 = getImage(getCodeBase(),"rocketBullet2.gif");
    	rocketBullet3 = getImage(getCodeBase(),"rocketBullet3.gif");
    	heart = getImage(getCodeBase(),"heart.gif");
    	mothershipImage = getImage(getCodeBase(),"mothership.gif");
    	powerUpBomb = getImage(getCodeBase(),"powerUpBomb.gif");
    	powerUp1 = getImage(getCodeBase(),"powerUp1.gif");
    	powerUp2 = getImage(getCodeBase(),"powerUp2.gif");
    	powerUp3 = getImage(getCodeBase(),"powerUp3.gif");
    	barracadeImage = getImage(getCodeBase(),"barracade.gif");
    	MediaTracker tracker = new MediaTracker(this);
    	tracker.addImage(bgImage,0);
    	tracker.addImage(rocketImage,1);
    	tracker.addImage(rocketBullet1,2);
    	tracker.addImage(alienImage1,3);
    	tracker.addImage(alienImage1,4);
    	tracker.addImage(alienImage1,5);
    	tracker.addImage(alienBullet,6);
    	tracker.addImage(heart,7);
    	tracker.addImage(mothershipImage,8);
    	tracker.addImage(powerUpBomb,9);
    	tracker.addImage(powerUp1,10);
    	tracker.addImage(powerUp1,11);
    	tracker.addImage(powerUp2,12);
    	tracker.addImage(barracadeImage,13);
    	tracker.addImage(alienImage4, 14);
    	tracker.addImage(rocketBullet3, 15);
    	tracker.addImage(rocketBullet2, 16);
    	try
    	{
    		tracker.waitForAll();
    	}
    	catch(InterruptedException e)
    	{
    		System.out.println("Something happened while reading image...");
    	}
    	Thread th = new Thread(this);
		th.start();
		this.setSize(900, 550);
		addMouseListener(this);
		addMouseMotionListener(this);
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

	public void mouseMoved(MouseEvent evt)
	{
		rx = evt.getX();
		ry = evt.getY();
		//int i = 1;
		/*for (int i = 1; i <= power; i++)
		{
			for(rBullet rb : rBullets)
			{
				if(rb.getType() == 3) 
				{
					rb.setX(((rx+(rocketImage.getWidth(null)/2))-(rocketBullet3.getWidth(null)/2)));
					rb.setY(ry-rocketBullet3.getHeight(null));
				}
			}
			//rbulletImage = rocketBullet3;
			//rBullets.add(new rBullet(((rx+(rocketImage.getWidth(null)/2))-(((rocketBullet3.getWidth(null)*power)/2)))+((rocketBullet3.getWidth(null)*(i-1))), ry-rocketBullet3.getHeight(null), 3, power));
		}*/
		j = 1;
		for(rBullet rb : rBullets)
		{
			if(rb.getType() == 3) 
			{
				rb.setX((rx+(rocketImage.getWidth(null)/2))-(rocketBullet3.getWidth(null)*rBullets.size()/2)+(rocketBullet3.getWidth(null)*(j-1)));
				rb.setY(ry-rocketBullet3.getHeight(null));
				j++;
			}
		}
		j = 1;
		repaint();
	}
	public void mouseDragged(MouseEvent evt) 
	{
		rx = evt.getX();
		ry = evt.getY();
		/*for(rBullet rb : rBullets)
			{
				if(rb.getType() == 3) 
				{
					rb.setX(((rx+(rocketImage.getWidth(null)/2))-(rocketBullet3.getWidth(null)/2)));
					rb.setY(ry-rocketBullet3.getHeight(null));
				}
			}*/
		j = 1;
		for(rBullet rb : rBullets)
		{
			if(rb.getType() == 3) 
			{
				rb.setX((rx+(rocketImage.getWidth(null)/2))-(rocketBullet3.getWidth(null)*rBullets.size()/2)+(rocketBullet3.getWidth(null)*(j-1)));
				rb.setY(ry-rocketBullet3.getHeight(null));
				j++;
			}
		}
		j = 1;
		repaint();
	}
	public void mouseEntered(MouseEvent evt) {}
	public void mouseExited(MouseEvent evt) {}
	public void mouseClicked(MouseEvent evt) {}
	public void mouseReleased(MouseEvent evt) 
	{
		mouseDown = false;
	}
	
	public void mousePressed(MouseEvent evt) 
	{
		mouseDown = true;
		rx = evt.getX();
		ry = evt.getY();
		if(evt.isMetaDown())
		{
			mouseDown = false;
			if (bomb > 0)
			{
				alienInvaders.clear();
				bomb--;
			}
		}
		if (!game)
		{
			if ((evt.getX() >= this.getWidth()/3 && evt.getX() <= (this.getWidth()/3)+100) && (evt.getY() >= this.getHeight()/2 && evt.getY() <= (this.getHeight()/2)+40))
			{
				game = true;
				setCursor(transparentCursor);
			}
		}
	}
		
	public void run()
	{
		Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
		while(true)
		{
			try
			{
				Thread.sleep(30);
				
			}
			catch (InterruptedException ex) { }
			Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
		if (game)
		{
			exhaust += 5;
			if (exhaust >= 100) exhaust = 100;
			if (exhaust <= 0) 
			{
				exhaust = 0;
				exhaustValue = 100;
			}
			if(mouseDown && exhaust > 0 && exhaustValue == 0) 
			{
				exhaust -= 5;
				if (weapon == 3) exhaust -=10;
				if (shootTime <= 0 )
				{
				switch (weapon)
				{
					case 3: if (rBullets.isEmpty())
							{
								for (int i = 1; i <= power; i++)
								{
									rbulletImage = rocketBullet3;
									rBullets.add(new rBullet(((rx+(rocketImage.getWidth(null)/2))-(((rocketBullet3.getWidth(null)*power)/2)))+((rocketBullet3.getWidth(null)*(i-1))), ry-rocketBullet3.getHeight(null), 3, power));
								}
								exhaust -= 5;
								shootTime = 10;
							}
					break;
					case 2: rbulletImage = rocketBullet2;
							for (int i = 1; i <= power; i++)
							{
								rBullets.add(new rBullet(((rx+(rocketImage.getWidth(null)/2))-(((rocketBullet2.getWidth(null)*power)/2)))+((rocketBullet2.getWidth(null)*(i-1))), ry-rocketBullet2.getHeight(null), 2, power));
							}
							exhaust -= 12;
							shootTime = 5;
					break;
					case 1: rbulletImage = rocketBullet1;
							rBullets.add(new rBullet(((rx+(rocketImage.getWidth(null)/2))-(rocketBullet1.getWidth(null)/2)), ry-rocketBullet1.getHeight(null), 1, power));
							exhaust -= 8;
							shootTime = 3;
					break;
				}
				}
			}
			j = 1;
			for(Iterator it = rBullets.iterator(); it.hasNext();)
			{
				rBullet rb = (rBullet) it.next();
				if(rb.getType() == 3) 
				{
					if (exhaust > 0)
					{
						rb.setX((rx+(rocketImage.getWidth(null)/2))-(rocketBullet3.getWidth(null)*rBullets.size()/2)+(rocketBullet3.getWidth(null)*(j-1)));
						rb.setY(ry-rocketBullet3.getHeight(null));
						//rb.setX(((rx+(rocketImage.getWidth(null)/2))-(rocketBullet3.getWidth(null)/2)));
						//rb.setY(ry-rocketBullet3.getHeight(null));
						j++;
					}
					else it.remove();
				}
			}
			j = 1;
			bgy++;
			shootTime--;
			if (exhaustValue > 0) exhaustValue--;
			mothershipLaunch--;
			if((mothershipLaunch <= 0) && !alienInvaders.isEmpty())
			{
				motherships.add(new mothership(1, 1, 1));
				mothershipLaunch = mothershipLaunchTime;
			}
			if (alienInvaders.isEmpty() && motherships.isEmpty())
			{
				if(levelStall <=0)
				{
					level++;
					if(level > levelMax) level = 0;
					create(level);
					levelStall = 75;
				}
				else levelStall--;
			}
			alienMove = 1;
			for(alienInvader ai : alienInvaders)
			{
				if (ai.getX() <= 0 || ai.getX()+alienImage1.getWidth(null) >= this.getWidth()) alienMove = -1;
			}
			//check if bullet hits mothership
			for(Iterator itOne = rBullets.iterator(); itOne.hasNext();)
			{
				rBullet objOne = (rBullet) itOne.next();
				objOne.move();
				if (objOne.getType() == 3 && mouseDown == false) itOne.remove(); 
				if (objOne.getY()+rocketBullet2.getHeight(null) <= 0 && objOne.getType() != 3) itOne.remove();
				for(Iterator itTwo = motherships.iterator(); itTwo.hasNext();)
				{
					mothership objTwo = (mothership) itTwo.next();
					if(((((objOne.getY() > objTwo.getY()) && (objOne.getY() < (objTwo.getY()+mothershipImage.getHeight(null)))) ||
					((objOne.getY() < objTwo.getY()) && (objOne.getY() > (objTwo.getY()-rbulletImage.getHeight(null))))) &&
					(((objOne.getX() > objTwo.getX()) && (objOne.getX() < (objTwo.getX()+mothershipImage.getWidth(null)))) ||
					((objOne.getX() < objTwo.getX()) && (objOne.getX() > (objTwo.getX()-rbulletImage.getWidth(null)))))))
					{
						powerUps.add(new powerUp(objTwo.getX(), objTwo.getY(), (generator.nextInt(4)+1)));
						itTwo.remove();
						remove = true;
					}
				}
				if(remove && objOne.getType() != 3) itOne.remove();
				remove = false;
			}
			//check if bullets hit alien
			for(Iterator itOne = rBullets.iterator(); itOne.hasNext();)
			{
				rBullet objOne = (rBullet) itOne.next();
				for(Iterator itTwo = alienInvaders.iterator(); itTwo.hasNext();)
				{
					alienInvader objTwo = (alienInvader) itTwo.next();
					if(((((objOne.getY() >= objTwo.getY()) && (objOne.getY() <= (objTwo.getY()+alienImage1.getHeight(null)))) ||
					((objOne.getY() <= objTwo.getY()) && (objOne.getY() >= (objTwo.getY()-rbulletImage.getHeight(null))))) &&
					(((objOne.getX() >= objTwo.getX()) && (objOne.getX() <= (objTwo.getX()+alienImage1.getWidth(null)))) ||
					((objOne.getX() <= objTwo.getX()) && (objOne.getX() >= (objTwo.getX()-rbulletImage.getWidth(null)))))))
					{
						objTwo.setLife(objOne.getPower());
						remove = true;
					}
				}
				if(remove) 
				{
					if (objOne.getType() != 3) itOne.remove();
					if (alienInvaders.size() < 10 && alienInvaders.size() > 0)
					{
						for(alienInvader ai : alienInvaders)
						{
							ai.setVelocity(1.5);					
						}
					}
				}
				remove = false;
			}
			for(Iterator itOne = aBullets.iterator(); itOne.hasNext();)
			{
				aBullet objOne = (aBullet) itOne.next();
				objOne.move();
				if (objOne.getY() >= this.getHeight()) itOne.remove();
				if(((((objOne.getY() >= ry) && (objOne.getY() <= (ry+rocketImage.getHeight(null)))) ||
				((objOne.getY() <= ry) && (objOne.getY() >= (ry-alienBullet.getHeight(null))))) &&
				(((objOne.getX() >= rx) && (objOne.getX() <= (rx+rocketImage.getWidth(null)))) ||
				((objOne.getX() <= rx) && (objOne.getX() >= (rx-alienBullet.getWidth(null)))))))
				{
					lives--;
					//sheild()
					itOne.remove();
				}
			}
			for(Iterator itTwo = alienInvaders.iterator(); itTwo.hasNext();)
			{
				alienInvader objTwo = (alienInvader) itTwo.next();
				objTwo.move(alienMove);
				if (objTwo.shoot(alienInvaders.size()) > 0)
				switch (objTwo.getType())
				{
					case 1: aBullets.add(new aBullet(objTwo.getX()+11, objTwo.getY()+34, objTwo.getType()));
					break;
					case 2: aBullets.add(new aBullet(objTwo.getX()-4, objTwo.getY()+34, objTwo.getType()));
							aBullets.add(new aBullet(objTwo.getX()+26, objTwo.getY()+34, objTwo.getType()));
					break;
				}
				if (objTwo.getY() >= this.getSize().height) itTwo.remove();
				if (objTwo.getLife() <= 0) itTwo.remove();
				if(((((ry >= objTwo.getY()) && (ry <= (objTwo.getY()+alienImage1.getHeight(null)))) ||
				((ry <= objTwo.getY()) && (ry >= (objTwo.getY()-rocketImage.getHeight(null))))) &&
				(((rx >= objTwo.getX()) && (rx <= (objTwo.getX()+alienImage1.getWidth(null)))) ||
				((rx <= objTwo.getX()) && (rx >= (objTwo.getX()-rocketImage.getWidth(null)))))))
				{
					objTwo.setLife(1);
					lives--;
					//sheild();
					if (alienInvaders.size() < 10 && alienInvaders.size() > 0)
					{
						for(alienInvader ai : alienInvaders)
						{
							ai.setVelocity(1.5);					
						}
					}
				}
			}
			for(Iterator itTwo = motherships.iterator(); itTwo.hasNext();)
			{
					mothership objTwo = (mothership) itTwo.next();
					if (level < 25) objTwo.move(level);
					else objTwo.move(25);
					if ((objTwo.getX() >= this.getWidth()) || (objTwo.getX()+mothershipImage.getWidth(null) <= 0)) itTwo.remove();
					if(((((ry > objTwo.getY()) && (ry < (objTwo.getY()+mothershipImage.getHeight(null)))) ||
					((ry < objTwo.getY()) && (ry > (objTwo.getY()-rocketImage.getHeight(null))))) &&
					(((rx > objTwo.getX()) && (rx < (objTwo.getX()+mothershipImage.getWidth(null)))) ||
					((rx < objTwo.getX()) && (rx > (objTwo.getX()-rocketImage.getWidth(null)))))))
					{
						powerUps.add(new powerUp(objTwo.getX(), objTwo.getY(), (generator.nextInt(4)+1)));
						itTwo.remove();
						lives--;
					}
			}
			//if rocket collects powerup
			//20 = powerupimage height and width (square)
			for(Iterator itOne = powerUps.iterator(); itOne.hasNext();)
			{
				powerUp objOne = (powerUp) itOne.next();
				objOne.move();
				if (objOne.getY() >= this.getHeight()) itOne.remove();
				if(((((objOne.getY() >= ry) && (objOne.getY() <= (ry+rocketImage.getHeight(null)))) ||
				((objOne.getY() <= ry) && (objOne.getY() >= (ry-20)))) &&
				(((objOne.getX() >= rx) && (objOne.getX() <= (rx+rocketImage.getWidth(null)))) ||
				((objOne.getX() <= rx) && (objOne.getX() >= (rx-20))))))
				{
					if (objOne.getType() == 4) bomb++;
					if (objOne.getType() == weapon) power++;
					if (objOne.getType() != 3 && weapon == 3) rBullets.clear();
					if (objOne.getType() > 0 && objOne.getType() != 4) 
					{
						weapon = objOne.getType();
						rBullets.clear();
					}
					itOne.remove();
				}
			}
			if(lives <= 0) 
			{
				clear();
			}
		}
		repaint();
			if (!game) 
			{
				setCursor(Cursor.getDefaultCursor());
			}
		}
	}
	
	public void update (Graphics g)
	{
		//double buffering
		if (dbImage == null)
		{
			dbImage = createImage(this.getSize().width, this.getSize().height);
			dbg = dbImage.getGraphics();
		}
		dbg.setColor(getBackground());
		dbg.fillRect(0, 0, this.getSize().width, this.getSize().height);
		dbg.setColor(getForeground());
		paint(dbg);
		g.drawImage(dbImage, 0, 0, this); 
	}
	
	public boolean handleEvent(Event evt)
	{
		if (evt.id == Event.WINDOW_DESTROY) System.exit(0);
		return super.handleEvent(evt);
	}
	
	public void paint(Graphics g) {
   		g.drawImage(bgImage, 0, bgy, this);
   		g.drawImage(bgImage, 0, (bgy+bgImage.getHeight(null))-1, this);
		if (game)
		{
		if (bgy >= 0) bgy = -630;
   		g.drawImage(bgImage, 0, bgy, this);
   		g.drawImage(bgImage, 0, (bgy+bgImage.getHeight(null))-1, this);
		g.drawImage(rocketImage, rx, ry, this);
		for(aBullet ab : aBullets)
			g.drawImage(alienBullet, ab.getX(), ab.getY(), this);
		for(powerUp pu : powerUps)
			switch (pu.getType())
			{
				case 4:	g.drawImage(powerUpBomb, pu.getX(), pu.getY(), this);
				break;
				case 3: g.drawImage(powerUp3, pu.getX(), pu.getY(), this);
				break;
				case 2: g.drawImage(powerUp2, pu.getX(), pu.getY(), this);
				break;
				case 1: g.drawImage(powerUp1, pu.getX(), pu.getY(), this);
				break;
			}
		for(mothership ms : motherships)
			g.drawImage(mothershipImage, ms.getX(), ms.getY(), this);
		for(alienInvader ai : alienInvaders)
			switch (ai.getType())
			{
				case 1: switch (ai.getLife())
						{
							case 3:	g.drawImage(alienImage3, ai.getX(), ai.getY(), this);
							break;
							case 2: g.drawImage(alienImage2, ai.getX(), ai.getY(), this);
							break;
							case 1: g.drawImage(alienImage1, ai.getX(), ai.getY(), this);
							break;
						}
				break;
				case 2: g.drawImage(alienImage4, ai.getX(), ai.getY(), this);
			}
		}
		/*Graphics2D g2 = (Graphics2D) g;
		AffineTransform tx = new AffineTransform();
		tx.rotate(Math.toRadians(180));
		g2.transform(tx);*/
		for(rBullet rb : rBullets)
		{
			//Matrix mx = new Matrix();
			switch (rb.getType())
			{
				case 3: /*mx.Rotate(-30);
				g.Transform = mx;*/
					g.drawImage(rocketBullet3, rb.getX(), rb.getY(), this);
				break;
				case 2: g.drawImage(rocketBullet2, rb.getX(), rb.getY(), this);
				break;
				case 1: g.drawImage(rocketBullet1, rb.getX(), rb.getY(), this);
				break;
			}
		}
		if(alienInvaders.isEmpty() && motherships.isEmpty())
		{
			g.setColor(Color.red);
			g.drawString("Level: "+(level+1)+" in "+levelStall, this.getWidth()/2, this.getHeight()/2);
			g.drawLine(1,350,this.getWidth(),350);
		}
		g.setColor(Color.cyan);
		g.drawString("Aliens:"+alienInvaders.size(), this.getWidth()-55,this.getHeight()-10);
		g.drawString("Weapon:"+weapon, this.getWidth()-500,this.getHeight()-10);
		g.drawString("Power:"+power, this.getWidth()-550,this.getHeight()-10);
		g.drawString("Bomb:"+bomb, this.getWidth()-600,this.getHeight()-10);
		for (int i = 0; i < lives; i++)
			g.drawImage(heart, 1 + (heart.getWidth(null)*i), 1, this);
		g.drawString("Exhaust:", 1 ,this.getHeight()-10);
		g.setColor(Color.red);
		for (int i = 0; i < exhaust; i++)
			g.drawLine(50+i, this.getHeight()-20, 50+i, this.getHeight()-10);
		if (!game)
		{
			g.setColor(Color.white);
			g.fillRect(this.getWidth()/3,this.getHeight()/2,100,40);
			g.setColor(Color.black);
			g.drawString("Play Again",(this.getWidth()/3)+22,(this.getHeight()/2)+22);
		}
	}
	
	public void create(int tlevel)
	{
		int hits = 1;
		int leveltempint = 0;
		switch (tlevel)
		{
			case 1: for (int ay = alienImage1.getHeight(null); ay < (this.getSize().height/5)+alienImage1.getHeight(null); ay += alienImage1.getHeight(null)*2)
					{
						for (int ax = alienImage1.getWidth(null)*2; ax+alienImage1.getWidth(null) < this.getSize().width; ax += alienImage1.getWidth(null)*3)
						{
							alienInvaders.add(new alienInvader(ax, ay, 1, 1));
						}
					}
					break;	
			case 2:	for(int i = 0; i < 9; i++)
					{
						for(int ax = ((this.getSize().width)/5)+alienImage1.getWidth(null)*i; ax < (((this.getSize().width)*4)/5)-alienImage1.getWidth(null)*i; ax += alienImage1.getWidth(null)*2)
							alienInvaders.add(new alienInvader(ax, alienImage1.getHeight(null) + alienImage1.getHeight(null)*i, 1, 1));
					}
					break;
			case 3: for(int i = 1; i < 9; i++)
					{
						for(int ax = ((this.getSize().width)/2)-((alienImage1.getWidth(null)/2)+(alienImage1.getWidth(null)*i)); ax < ((this.getSize().width)/2)+((alienImage1.getWidth(null)/2)+(alienImage1.getWidth(null)*2*i)); ax += ((alienImage1.getWidth(null)/2)+(alienImage1.getWidth(null)*i)))
							alienInvaders.add(new alienInvader(ax, alienImage1.getHeight(null) + alienImage1.getHeight(null)*i, 1, 1));
					}
					break;
			case 4: for (int ay = alienImage1.getHeight(null); ay < (this.getSize().height/5)+alienImage1.getHeight(null); ay += alienImage1.getHeight(null)*2)
					{
						for (int ax = alienImage1.getWidth(null)*2; ax+alienImage1.getWidth(null) < this.getSize().width; ax += alienImage1.getWidth(null)*3)
						{
							hits++;
							if(hits%3 == 0) hits++;
							alienInvaders.add(new alienInvader(ax, ay, 1, hits%3));
						}
					}
					break;
			case 5:	for(int i = 0; i < 9; i++)
					{
						for(int ax = ((this.getSize().width)/5)+alienImage1.getWidth(null)*i; ax < (((this.getSize().width)*4)/5)-alienImage1.getWidth(null)*i; ax += alienImage1.getWidth(null)*2)
						{
							hits++;
							if(hits%3 == 0) hits++;
							alienInvaders.add(new alienInvader(ax, alienImage1.getHeight(null) + alienImage1.getHeight(null)*i, 1, hits%3));
						}
								
					}
					break;
			case 6: for(int i = 1; i < 9; i++)
					{
						for(int ax = ((this.getSize().width)/2)-((alienImage1.getWidth(null)/2)+(alienImage1.getWidth(null)*i)); ax < ((this.getSize().width)/2)+((alienImage1.getWidth(null)/2)+(alienImage1.getWidth(null)*2*i)); ax += ((alienImage1.getWidth(null)/2)+(alienImage1.getWidth(null)*i)))
						{
							hits++;
							if(hits%3 == 0) hits++;
							alienInvaders.add(new alienInvader(ax, alienImage1.getHeight(null) + alienImage1.getHeight(null)*i, 1, hits%3));
						}
					}
					break;
			case 7: for (int ay = alienImage1.getHeight(null); ay < (this.getSize().height/5)+alienImage1.getHeight(null); ay += alienImage1.getHeight(null)*2)
					{
						for (int ax = alienImage1.getWidth(null)*2; ax+alienImage1.getWidth(null) < this.getSize().width; ax += alienImage1.getWidth(null)*3)
						{
							hits++;
							if(hits%4 == 0) hits++;
							alienInvaders.add(new alienInvader(ax, ay, 1, hits%4));
						}
					}
					break;
			case 8:	for(int i = 0; i < 9; i++)
					{
						for(int ax = ((this.getSize().width)/5)+alienImage1.getWidth(null)*i; ax < (((this.getSize().width)*4)/5)-alienImage1.getWidth(null)*i; ax += alienImage1.getWidth(null)*2)
						{
							hits++;
							if(hits%4 == 0) hits++;
							alienInvaders.add(new alienInvader(ax, alienImage1.getHeight(null) + alienImage1.getHeight(null)*i, 1, hits%4));
						}
								
					}
					break;
			case 9: for(int i = 1; i < 9; i++)
					{
						for(int ax = ((this.getSize().width)/2)-((alienImage1.getWidth(null)/2)+(alienImage1.getWidth(null)*i)); ax < ((this.getSize().width)/2)+((alienImage1.getWidth(null)/2)+(alienImage1.getWidth(null)*2*i)); ax += ((alienImage1.getWidth(null)/2)+(alienImage1.getWidth(null)*i)))
						{
							hits++;
							if(hits%4 == 0) hits++;
							alienInvaders.add(new alienInvader(ax, alienImage1.getHeight(null) + alienImage1.getHeight(null)*i, 1, hits%4));
						}
					}
					break;
			case 10: for(int i = 0; i < 3; i++)
					{
						if (i%3 == 1) leveltempint = 2;
						else leveltempint = 4;
						for(int ax = (alienImage1.getWidth(null)/2)+(alienImage1.getWidth(null)*i); ax < this.getWidth()-((alienImage1.getHeight(null)/2)+(alienImage1.getWidth(null)*i)); ax += alienImage1.getWidth(null)*leveltempint)						{
							hits++;
							if(hits%4 == 0) hits++;
							alienInvaders.add(new alienInvader(ax, alienImage1.getHeight(null)+(alienImage1.getHeight(null)*(i%4)), 1, hits%4));
						}
					}
					break;
			case 11: for (int i = 1; i <= 9; i+=2)
					{
						motherships.add(new mothership(1, mothershipImage.getHeight(null)*i, 1));
					 	motherships.add(new mothership(this.getWidth()-1, mothershipImage.getHeight(null)*(i+1), -1));
					}
					break;
			case 12: for (int ay = alienImage1.getHeight(null); ay < (this.getSize().height/5)+alienImage1.getHeight(null); ay += alienImage1.getHeight(null)*2)
					{
						for (int ax = alienImage1.getWidth(null)*2; ax+alienImage1.getWidth(null) < this.getSize().width; ax += alienImage1.getWidth(null)*3)
						{
							alienInvaders.add(new alienInvader(ax, ay, 2, 5));
						}
					}
					break;				
		}
	}
	public void clear()
	{
		/*for(Iterator it = rBullets.iterator(); it.hasNext();)
			it.remove();
		for(Iterator it = aBullets.iterator(); it.hasNext();)
			it.remove();
		for(Iterator it = motherships.iterator(); it.hasNext();)
			it.remove();
		for(Iterator it = alienInvaders.iterator(); it.hasNext();)
			it.remove();*/
		rBullets.clear();
		aBullets.clear();
		motherships.clear();
		alienInvaders.clear();
		game = false;
		level = 0;
		lives = 5;
		power = 1;
		weapon = 1;
		shootTime = 0;
		levelStall = 0;
		alienMove = 1;
		mothershipLaunchTime = 1000;
	}
}