import java.util.*;
import java.awt.event.*;
import java.awt.image.*;
import java.awt.*;
import java.applet.*;
class powerUp{
	int x, y, type;
	public powerUp(int bx, int by, int btype)
	{
		x = bx;
		y = by;
		type = btype;
	}
	public void move()
	{
		y += 5;
	}
	public int getX()
	{
		return x;
	}
	public int getY()
	{
		return y;
	}
	public int getType()
	{
		return type;
	}
}
