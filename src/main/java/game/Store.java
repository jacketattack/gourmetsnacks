package game;

import core.render.Renderable;


public abstract class Store implements Renderable
{
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	protected Session session;
	
	public abstract void act();
	
	public boolean inBounds(int x, int y) 
	{
		return  x > this.x && x < this.x + width && y > this.y && y < this.y + height;
	}
	
	public int getX()
	{
		return x;
	}
	
	public void setX(int x) 
	{
		this.x = x;	
	}
	
	public int getY()
	{
		return y;
	}
	
	public void setY(int y) 
	{
		this.y = y;	
	}
	
	public void setWidth(int x)
	{
		width = x;
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public void setHeight(int y)
	{
		height = y;
	}
	
	public int getHeight() 
	{
		return height;
	}
	
	public void setSession(Session session)
	{
		this.session = session;
	}
	
	public Session getSession()
	{
		return session;
	}
}
