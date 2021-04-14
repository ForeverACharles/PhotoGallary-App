package Photos55.app;
import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class Album implements Serializable {

	private String name;
	private ArrayList<Photo> photos;
	public static final String storeDir = "data";
	public static final String storeFile = "albums.dat";
	static final long serialVersionUID = 1L;
	
	public Album(String name) {
		this.name = name;
		photos = new ArrayList<Photo>();
	}
	
	public String toString() {
		return name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public int getNumber() {
		if(photos != null && photos.size() > 0)
		{
			return photos.size();
		}
		else 
		{
			return 0;
		}
	}
	
	public ArrayList<Photo> getPhotos(){
		return photos;
	}
	
	public String getEarliest() {
		if(getNumber() == 0) {
			return null;
		}
		LocalDate current = photos.get(0).getDate();
		for (Photo photo : photos) {
			if (photo.getDate().isBefore(current)) {
				current = photo.getDate();
			}
		}
		return current.toString();
	}
	
	public String getLatest() {
		if (getNumber() == 0) {
			return null;
		}
		LocalDate current = photos.get(0).getDate();
		for (Photo photo : photos) {
			if (photo.getDate().isAfter(current)) {
				current = photo.getDate();
			}
		}
		return current.toString();
	}
	
}
