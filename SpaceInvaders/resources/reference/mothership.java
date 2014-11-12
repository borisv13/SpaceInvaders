import java.util.*;
import java.awt.event.*;
import java.awt.image.*;
import java.awt.*;
import java.applet.*;
class mothership{
	int x, y, orientation;
	public mothership(int mx, int my, int morientation)
	{
		x = mx;
		y = my;
		orientation = morientation;
	}
	public void move(int speed)
	{
		x += speed*orientation;
	}
	public int getX()
	{
		return x;
	}
	public int getY()
	{
		return y;
	}
}
