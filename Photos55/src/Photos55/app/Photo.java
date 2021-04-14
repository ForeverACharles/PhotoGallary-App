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
	
	public void setPath(String path)
	{
		this.path = path;
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
	
	public boolean containsTag(Tag stag) {
		for (Tag tag: tags) {
			if (tag.getTag().compareTo(stag.getTag())==0 && tag.getValue().compareTo(stag.getValue())==0) {
				return true;
			}
		}
		return false;
	}
	public void writePhoto(Photo photo) throws IOException, ClassNotFoundException
	{
		ObjectOutputStream OOS = new ObjectOutputStream(
			new FileOutputStream(storeDir + File.separator + storeFile));
		OOS.writeObject(photo);
		OOS.close();
	}
	
	public Photo readPhoto() throws IOException, ClassNotFoundException
	{
		ObjectInputStream OIS = new ObjectInputStream(
			new FileInputStream(storeDir + File.separator + storeFile));
		Photo photo = (Photo)OIS.readObject();
		OIS.close();
		return photo;
	}
}
