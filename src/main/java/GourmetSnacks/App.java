package GourmetSnacks;

import java.awt.EventQueue;

import ui.Window;

import core.EventLoop;
import core.NameGenerator;
import core.db.MongoDB;
import core.db.DB;

/**
 * Entry point of the application
 */
public class App 
{
    public static void main( String[] args )
    {
    	DB db = DB.getInstance();
    	db.use(new MongoDB());
    	
    	NameGenerator.getName();
    	
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{		
				Window window = Window.getInstance();
				window.open();
			}
		});
		
    	EventLoop eventLoop = EventLoop.getInstance();
    	eventLoop.start();
    }
}
