package Photos55.app;
import java.io.*;
/**
 *  Object representation of Photo Tag
 *  
 *  <code>Tag</code> contains the characteristics and qualities of a <code>Photos55App</code> photo tag.
 *  A tag has type and value.
 *  
 *   
 *   @author Charles Li
 *   @author Max Sun
 *   @version 1.69 Apr 2021
*/
public class Tag implements Serializable
{
	private String tag;
	private String value;
	/**
	 *  Serialization directory of <code>Tag</code>
	 */
	public static final String storeDir = "data";
	/**
	 *  Serialization file of <code>Tag</code>
	 */
	public static final String storeFile = "tags.dat";
	/**
	 *  Serialization ID of <code>Tag</code>
	 */
	static final long serialVersionUID = 1L;
	/**
	* 
	* Default <code>User</code> constructor,
	* 
	* @param tag the type of the <code>Tag</code>
	* @param value the value of the <code>Tag</code>
	*/
	public Tag(String tag, String value)
	{
		this.tag = tag;
		this.value = value;
	}
	/**
	* 
	* Returns the type of the <code>Tag</code>
	* 
	*/	
	public String getTag()
	{
		return tag;
	}
	/**
	* 
	* Returns the value of the <code>Tag</code>
	* 
	*/	
	public String getValue()
	{
		return value;
	}
	/**
	* 
	*Converts <code>Tag</code> to printable output
	* 
	*/	
	public String toString()
	{
		return tag + ", " + value;
	}
	
	/**
	* 
	* Serialize <code>Tag</code> to storage
	* @param tag the <code>Tag</code> to be serialized
	* 
	*/	
	public void writeTag(Tag tag) throws IOException, ClassNotFoundException
	{
		ObjectOutputStream OOS = new ObjectOutputStream(
			new FileOutputStream(storeDir + File.separator + storeFile));
		OOS.writeObject(tag);
		OOS.close();
	}
	/**
	* 
	* Read <code>Tag</code> from serialization
	* 
	*/		
	public Tag readTag() throws IOException, ClassNotFoundException
	{
		ObjectInputStream OIS = new ObjectInputStream(
			new FileInputStream(storeDir + File.separator + storeFile));
		Tag tag = (Tag)OIS.readObject();
		OIS.close();
		return tag;
	}
	/*
	
	public void writeObject(ObjectOutputStream out) throws IOException
	{
		out.defaultWriteObject();
		out.writeObject(tag.get());
		out.writeObject(value.get());
	}
	
	public void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException
	{
		in.defaultReadObject();
		tag = new SimpleStringProperty((String) in.readObject());
		value = new SimpleStringProperty((String) in.readObject());
	}
	*/
}
