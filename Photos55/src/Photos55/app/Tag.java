package Photos55.app;
import java.io.*;

public class Tag implements Serializable
{
	private String type;
	private String value;
	
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
