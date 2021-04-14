package Photos55.app;
import java.io.*;

public class Tag implements Serializable
{
	private String tag;
	private String value;
	
	public static final String storeDir = "data";
	public static final String storeFile = "tags.dat";
	static final long serialVersionUID = 1L;
	
	public Tag(String tag, String value)
	{
		this.tag = tag;
		this.value = value;
	}
	
	public String getTag()
	{
		return tag;
	}
	
	public String getValue()
	{
		return value;
	}
	
	public String toString()
	{
		return tag + ", " + value;
	}
	

	public void writeTag(Tag tag) throws IOException, ClassNotFoundException
	{
		ObjectOutputStream OOS = new ObjectOutputStream(
			new FileOutputStream(storeDir + File.separator + storeFile));
		OOS.writeObject(tag);
		OOS.close();
	}
	
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
