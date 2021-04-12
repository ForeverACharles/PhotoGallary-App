package Photos55.app;
import java.io.*;
import java.util.*;

import Photos55.view.Album;

public class User implements Serializable {
	
	private ArrayList<Album> albums;
	private String dir;
	private String name;
	
	public User(String dir, String name)
	{
		albums = new ArrayList<Album>();
		this.dir = dir;
		this.name = name;
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
