package Photos55.app;
import java.io.*;

public class Tag implements Serializable
{
	private String type;
	private String value;
	public static final String storeDir = "data";
	public static final String storeFile = "tags.dat";
	static final long serialVersionUID = 1L;
	
	public Tag(String type, String value)
	{
		this.type = type;
		this.value = value;
	}
	
	public String toString()
	{
		return type + ", " + value;
	}
}
