package Photos55.app;
import java.io.*;
import java.util.*;
/**
 *  Object representation of Photo App User
 *  
 *  <code>User</code> contains the characteristics and qualities of a <code>Photos55App</code> application user.
 *  A user has username and owns a list of albums.
 *  
 *   
 *   @author Charles Li
 *   @author Max Sun
 *   @version 1.69 Apr 2021
*/

public class User implements Serializable {
	
	private ArrayList<Album> albums;
	private String name;
	/**
	 *  Serialization directory of <code>User</code>
	 */
	public static final String storeDir = "data";
	/**
	 *  Serialization file of <code>User</code>
	 */
	public static final String storeFile = "users.dat";
	/**
	 *  Serialization ID of <code>User</code>
	 */
	static final long serialVersionUID = 1L;
	/**
	* 
	* Default <code>User</code> constructor,
	* 
	* @param name the username of the <code>User</code>
	*/
	public User(String name)
	{
		albums = new ArrayList<Album>();
		this.name = name;
	}
	/**
	* 
	* Returns the username of the <code>User</code>
	* 
	*/	
	public String getName() {
		return this.name;
	}
	/**
	* 
	* Returns the list of <code>Album</code>s belonging to the <code>User</code>
	* 
	*/	
	public ArrayList<Album> getAlbums()
	{
		return albums;
	}
	/**
	* 
	* Adds an <code>Album</code> belonging to the <code>User</code>
	* @param name the name of the new <code>Album</code>
	* 
	*/	
	public void addAlbum(String name)
	{
		albums.add(new Album(name));
	}
	/**
	* 
	* Deletes an <code>Album</code> belonging to the <code>User</code>
	* @param name the name of the <code>Album</code> to be deleted
	* 
	*/	
	public void deleteAlbum(String name)
	{
		for(Album album : albums)
		{
			if(album.getName().equals(name))
			{
				albums.remove(album);
				break;
			}
		}
	}
	
	/**
	* 
	* Serialize <code>User</code> to storage
	* @param user the <code>User</code> to be serialized
	* 
	*/	
	public void writeUser(User user) throws IOException, ClassNotFoundException
	{
		ObjectOutputStream OOS = new ObjectOutputStream(
			new FileOutputStream(storeDir + File.separator + storeFile));
		OOS.writeObject(user);
		OOS.close();
	}
	/**
	* 
	* Read <code>User</code> from serialization
	*  
	*/		
	public User readUser() throws IOException, ClassNotFoundException
	{
		ObjectInputStream OIS = new ObjectInputStream(
			new FileInputStream(storeDir + File.separator + storeFile));
		User user = (User)OIS.readObject();
		OIS.close();
		return user;
	}
}
