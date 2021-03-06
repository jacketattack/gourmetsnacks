package game.state;

import game.Difficulty;
import game.Map;
import game.Session;
import ui.Window;
import ui.panel.GamePanel;
import core.StateSelector;

/**
 * The GameSetupState manages all the data
 * received from the user when they begin a new game
 */
public class GameSetupState implements State 
{
    /** The current game map */
    private Map map;

    /** Represents how many players are playing */
    private int numPlayers;

    private Session session;

    private Difficulty difficulty;
    
    /**
     * Instantiate all the variables in the constructor
     *
     * @param session - the session of the game
     */
    public GameSetupState(Session session)
    {
        this.session = session;
    }

    /**
     * not used
     */
    public void update() 
    {
    }
    
    /**
     * Set the game's difficulty
     * @param difficulty The game's difficulty
     */
    public void setDifficulty(Difficulty difficulty)
    {
        this.difficulty = difficulty;
    }
    
    /**
     * Set the number of players participating in the game
     * @param num The number of players
     */
    public void setNumPlayers(int num) 
    {
        numPlayers = num;
        session.createPlayers(numPlayers);
    }
    
    /**
     * Get the number of players participating in the game
     * @return The number of players
     */
    public int getNumPlayers()
    {
        return numPlayers;
    }
    
    /**
     * Set the game map
     * @param map The game map
     */
    public void setMap(Map map)
    {
        this.map = map;
    }
    
    /**
     * Assemble the game session from all the user data  and starts the game
     */
    public void completeSession()
    {       
        session.setMap(map);
        
        GameState gameState = new GameState(session);

        // switch to the game state
        StateSelector stateSelector = StateSelector.getInstance();
        stateSelector.setState(gameState);
        
        // switch to the game panel
        Window window = Window.getInstance();
        difficulty = (Difficulty)difficulty;
        window.setPanel(new GamePanel());
    }
    
    public Session getSession()
    {
        return session;
    }
}
