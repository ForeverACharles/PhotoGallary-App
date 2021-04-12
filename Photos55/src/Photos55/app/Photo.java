package Photos55.app;
import java.io.*;
import java.util.*;

public class Photo implements Serializable {

	private String name;
	private String dir;
	private Calendar date;
	private String captions;
	private ArrayList<Tag> tags;
	
	public Photo(String name, String dir, Calendar date, String captions)
	{
		this.name = name;
		this.dir = dir;
		this.date = date;
		this.captions = captions;
		tags = new ArrayList<Tag>();
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public String getDate()
	{
		return date.toString();
	}
	
	public String getCaptions()
	{
		return captions;
	}
	
	public void reCaption(String newCaptions)
	{
		captions = newCaptions;
	}
	
	public void addTag(Tag newTag)
	{
		tags.add(newTag);
	}
	
	public void deleteTag(Tag tag)
	{
		tags.remove(tag);
	}
}
