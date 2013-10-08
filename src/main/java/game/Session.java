package game;

import java.util.ArrayList;

/**
 * Holds all the data pertaining to the current game being played.
 * Using a game session allows for the game to be easily saved.
 * To save the game, serialize the session using something like GSON (http://code.google.com/p/google-gson/).
 * To load the game, deserialize the session and give it to the current round (will require modification to the game state)
 * 
 * @author grant
 * @author trevor
 */
public class Session 
{
	private int roundAt;
	private ArrayList<Character> characters;
	private Map map;
        /**
         * The Session constructor associates a new session with a group
         * of players
         * 
         * 
         * @param characters -the character in the game
         */
	public Session(ArrayList<Character> characters)
	{
		this.characters = characters;
		roundAt = 1;
	}
	/**
         * 
         * The getCharacters gives an ArrayList of all characters
         * 
         * @return  ArrayList characters - the characters in the game
         */
	public ArrayList<Character> getCharacters()
	{
		return characters;
	}
       /**
        * The getRound method returns the current round.
        * 
        * @return int roundAt- the current round of the game.
        */
	public int getRoundAt()
	{
		return roundAt;
	}
	/**
         * 
         * The incrementRound increments roundAt.
         * 
         */
	public void incrementRound()
	{
		roundAt++;
	}
	/**
         * The setMap method associates a map with the current session
         * 
         * @param map-the map associated with this session 
         */
        public void setMap(Map map) 
        {
            this.map = map;
        }
        /**
         * The getMap method returns the map of the session
         * 
         * 
         * @return Map map - the map of the session 
         */
        public Map getMap()
        {
            return map;
        }
        /**
         * The toString gives a representation of the current state of the game.
         * 
         * @return String toString - a string representing the core data of the game
         */
	public String toString()
	{
		String mapText = "";
		
		for (int a = 0; a < 5; a++)
		{
			for (int b = 0; b < 9; b++)
			{
				mapText += map.getPlot(a, b).getType() + " ";
			}
			mapText += "\n";
		}
		
		String charactersText = "";
		for (Character character : characters)
		{
			charactersText += character + "\n\n";
		}
		
		return "[Session]" +
				"\nRound: " + roundAt +
				"\n" + 
				"\n" + charactersText +
				"\n[Map]" +
				"\n" + mapText;
	}
}
