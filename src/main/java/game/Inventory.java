package game;

import java.util.ArrayList;

/**
 * The Inventory class represents a characters inventory keeping track of money
 * goods and plots
 * 
 * @author grant
 **/
public class Inventory 
{
	public int food;
	public int energy;
	public int ore;
	public int crystite;
	public int money;
	
	public ArrayList<Plot> ownedPlots;

   /**
    * The Inventory constructor instantiates the ArrayList that holds the
    * player's plots
    * 
    * 
    **/
    public Inventory() 
    {
    	ownedPlots = new ArrayList<Plot>();
    }
}
