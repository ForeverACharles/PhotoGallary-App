package Photos55.app;
import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class Photo implements Serializable {

	private String path;
	private LocalDate date;
	private String caption;
	private ArrayList<Tag> tags;
	public static final String storeDir = "data";
	public static final String storeFile = "photos.dat";
	static final long serialVersionUID = 1L;
	public Photo(String path, LocalDate date)
	{
		this.path = path;
		this.date = date;
		this.caption = "";
		tags = new ArrayList<Tag>();
	}
	
	
	public LocalDate getDate()
	{
		return date;
	}
	
	public String getPath() {
		return path;
	}
	public String printDate()
	{
		return date.toString();
	}
	
	public String getCaption()
	{
		return caption;
	}
	
	public void reCaption(String newCaption)
	{
		caption = newCaption;
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
