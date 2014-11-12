class rBullet{
	int x, y, type, power;
	public rBullet(int bx, int by, int btype, int bpower)
	{
		x = bx;
		y = by;
		type = btype;
		power = bpower;
	}
	public void move()
	{
		if (type != 3) y -= 10;
	}
	public int getX()
	{
		return x;
	}
	public void setX(int bx)
	{
		x = bx;
	}
	public int getY()
	{
		return y;
	}
	public void setY(int by)
	{
		y = by;
	}
	public int getPower()
	{
		return power;
	}
	public int getType()
	{
		return type;
	}
}
