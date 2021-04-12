package Photos55.app;
import java.io.*;
import java.util.*;


public class User implements Serializable {
	
	private ArrayList<Album> albums;
	private String dir;
	private String name;
	
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
			new FileOutputStream(dir + name));
		OOS.writeObject(user);
	}
	
	public User readUser() throws IOException, ClassNotFoundException
	{
		ObjectInputStream OIS = new ObjectInputStream(
			new FileInputStream(dir + File.separator + name));
		return (User)OIS.readObject();
	}
}
