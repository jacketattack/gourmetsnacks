package core.db;

import game.Session;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

public class MongoDB implements Database
{
	private static final String URI = "paulo.mongohq.com";	
	private static final int PORT = 10068;
	
	private MongoClient mongo;
	private DB db;
	
	private String user;
	private String password;

	public MongoDB()
	{
		try 
		{
			loadConfig();
			
			mongo = new MongoClient(new ServerAddress(URI, PORT));
			
			db = mongo.getDB("MULE");
			boolean authenticated = db.authenticate(user, password.toCharArray());
			
			if (!authenticated)
			{
				throw new Exception("not authenticated");
			}
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public String save(String id, Session session)
	{
		try
	    {	
	        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
	        objectOutputStream.writeObject(session.getSaveCopy());

	        DBCollection collection = db.getCollection("save");
	        BasicDBObject save = new BasicDBObject("id", id);
	        
	        if (exists("save", id))
	        {
	        	collection.remove(save);
	        }

	        save.append("data", byteArrayOutputStream.toByteArray());
	        
            collection.insert(save);
	    }
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return id;
	}
	
	public Session load(String id)
	{
		Session session = null;
		
	    try
	    {   
	        DBCollection collection = db.getCollection("save");
	        BasicDBObject save = new BasicDBObject("id", new BasicDBObject("$regex", id));
	        DBObject dbObject = collection.findOne(save);

            if (dbObject == null)
            {
                return null;
            }
	              
	        DBObject dbSave = collection.findOne(save);
	        byte[] bytes = (byte[]) dbSave.get("data");
	        
			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
	        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
	        session = (Session) objectInputStream.readObject();	        
	    }
		catch(Exception e)
		{
			e.printStackTrace();
		}
	    
		return session;
	}
	
	public boolean exists(String collectionId, String objectID)
	{
		DBCollection collection = db.getCollection(collectionId);
        BasicDBObject object = new BasicDBObject("id",objectID);
        DBObject dbObject = collection.findOne(object);
        
        if (dbObject != null)
        {
        	return true;
        }
        return false;
	}

	private void loadConfig() throws Exception
	{
	    File file = new File("mongo.cred");
		Scanner scanner = new Scanner(file);
		
		if (scanner.hasNext())
			user = scanner.nextLine();
		
		if (scanner.hasNext())
			password = scanner.nextLine();
		
		scanner.close();
	}
}
