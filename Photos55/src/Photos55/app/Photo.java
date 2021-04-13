package Photos55.app;
import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class Photo implements Serializable {

	private String name;
	private String dir;
	private LocalDate date;
	private String captions;
	private ArrayList<Tag> tags;
	
	public Photo(String name, String dir, LocalDate date, String captions)
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
	
	public LocalDate getDate()
	{
		return date;
	}
	
	public String printDate()
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
	
	public ArrayList<Tag> getTags()
	{
		return tags;
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
