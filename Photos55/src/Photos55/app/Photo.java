package Photos55.app;
import java.io.*;
import java.time.LocalDate;
import java.util.*;
/**
 *  Object representation of Photo
 *  
 *  <code>Album</code> contains the characteristics and qualities of a <code>Photos55App</code> photo.
 *  A photo has file path of image, caption, tags, and associated functions
 *  
 *   
 *   @author Charles Li
 *   @author Max Sun
 *   @version 1.69 Apr 2021
*/
public class Photo implements Serializable {

	private String path;
	private LocalDate date;
	private String caption;
	private ArrayList<Tag> tags;
	/**
	 *  Serialization directory of <code>Photo</code>
	 */
	public static final String storeDir = "data";
	/**
	 *  Serialization file of <code>Photo</code>
	 */
	public static final String storeFile = "photos.dat";
	/**
	 *  Serialization ID of <code>Photo</code>
	 */
	static final long serialVersionUID = 1L;
	/**
	* 
	* Default <code>Photo</code> constructor,
	* 
	* @param path the local photo filepath of the <code>Photo</code>
	* @param date the date corresponding to the photo
	*/
	public Photo(String path, LocalDate date)
	{
		this.path = path;
		this.date = date;
		this.caption = "";
		tags = new ArrayList<Tag>();
	}
	/**
	* 
	* Returns the date of the <code>Photo</code>
	* 
	*/		
	public LocalDate getDate()
	{
		return date;
	}
	/**
	* 
	* Returns the filepath of the <code>Photo</code>
	* 
	*/		
	public String getPath() {
		return path;
	}
	/**
	* 
	* Changes the filepath of the <code>Photo</code>
	* @param path the new filepath of the <code>Photo</code>
	* 
	*/		
	public void setPath(String path)
	{
		this.path = path;
	}	
	/**
	* 
	* Returns the date of the <code>Photo</code> in printable format
	* 
	*/		
	public String printDate()
	{
		return date.toString();
	}
	/**
	* 
	* Returns the caption of the <code>Photo</code>
	* 
	*/		
	public String getCaption()
	{
		return caption;
	}
	/**
	* 
	* Changes the caption of the <code>Photo</code>
	* @param path the new caption of the <code>Photo</code>
	* 
	*/		
	public void reCaption(String newCaption)
	{
		caption = newCaption;
	}
	/**
	* 
	* Returns the list of <code>Tag</code>s contained in <code>Photo</code>
	* 
	*/		
	public ArrayList<Tag> getTags()
	{
		return tags;
	}
	/**
	* 
	* Adds a <code>Tag</code> to the list of tags associated with <code>Photo</code>
	* @param newTag the tag to be added
	* 
	*/		
	public void addTag(Tag newTag)
	{
		tags.add(newTag);
	}
	/**
	* 
	* Deletes a <code>Tag</code> from the list of tags associated with <code>Photo</code>
	* @param tag the tag to be removed
	* 
	*/			
	public void deleteTag(Tag tag)
	{
		tags.remove(tag);
	}
	/**
	* 
	* Checks if a <code>Tag</code> is contained in the list of tags associated with <code>Photo</code>
	* @param stag the tag to search for
	* 
	*/	
	public boolean containsTag(Tag stag) {
		for (Tag tag: tags) {
			if (tag.getTag().compareTo(stag.getTag())==0 && tag.getValue().compareTo(stag.getValue())==0) {
				return true;
			}
		}
		return false;
	}
	/**
	* 
	* Serialize <code>Photo</code> to storage
	* @param photo the <code>Photo</code> to be serialized
	* 
	*/	
	public void writePhoto(Photo photo) throws IOException, ClassNotFoundException
	{
		ObjectOutputStream OOS = new ObjectOutputStream(
			new FileOutputStream(storeDir + File.separator + storeFile));
		OOS.writeObject(photo);
		OOS.close();
	}
	/**
	* 
	* Unserialize <code>Photo</code> from storage
	* 
	*/		
	public Photo readPhoto() throws IOException, ClassNotFoundException
	{
		ObjectInputStream OIS = new ObjectInputStream(
			new FileInputStream(storeDir + File.separator + storeFile));
		Photo photo = (Photo)OIS.readObject();
		OIS.close();
		return photo;
	}
}
