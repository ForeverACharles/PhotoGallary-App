package Photos55.app;
import java.io.*;
import java.util.*;


public class User implements Serializable {
	
	private ArrayList<Album> albums;
	private String name;
	
	public static final String storeDir = "data";
	public static final String storeFile = "users.dat";
	
	public User(String name)
	{
		albums = new ArrayList<Album>();
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void writeUser(User user) throws IOException, ClassNotFoundException
	{
		ObjectOutputStream OOS = new ObjectOutputStream(
			new FileOutputStream(storeDir + File.separator + storeFile));
		OOS.writeObject(user);
	}
	
	public User readUser() throws IOException, ClassNotFoundException
	{
		ObjectInputStream OIS = new ObjectInputStream(
			new FileInputStream(storeDir + File.separator + storeFile));
		return (User)OIS.readObject();
	}
}
