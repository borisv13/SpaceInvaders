class aBullet{
	int x, y, power;
	public aBullet(int bx, int by, int bpower)
	{
		x = bx;
		y = by;
		power = bpower;
	}
	public void move()
	{
		y += 10;
	}
	public int getX()
	{
		return x;
	}
	public int getY()
	{
		return y;
	}
	public int getPower()
	{
		return power;
	}
}
