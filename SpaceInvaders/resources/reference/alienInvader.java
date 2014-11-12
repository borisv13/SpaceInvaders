import java.util.*;
class alienInvader{
	int x, y, type, life;
	double velocity = 1;
	public alienInvader (int mx, int my, int mtype, int mlife)
	{
		x = mx;
		y = my;
		type = mtype;
		life = mlife;
	}
	public void move(int move)
	{
		if (move < 0)
				{
						velocity *= -1;
						//y += alienImage1.getHeight(null)/2;
						y += 34/2;
						x += velocity;
				}
		if (move > 0)	x += velocity;
	}
	public void setVelocity(double mv)
	{
		if(velocity > 0) velocity += mv;
		if(velocity < 0) velocity -= mv;
	}
	public int shoot(int randomParam)
	{
		Random generator = new Random();
		int i = generator.nextInt(randomParam*25);
		if (i == 1) return type;
		else return 0;
	}
	public void setLife(int hit)
	{
		life -= hit;
	}
	public int getLife()
	{
		return life;
	}
	public int getType()
	{
		return type;
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
