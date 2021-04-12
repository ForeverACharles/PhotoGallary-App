package Photos55.app;
import java.io.*;
import java.util.*;

public class Album implements Serializable {

	private String name;
	private ArrayList<Photo> photos;
	public Album(String name) {
		this.name = name;
	}
	public String getName() {
		return this.name;
	}
	public int getLength() {
		return this.photos.size();
	}
	public ArrayList<Photo> getPhotos(){
		return this.photos;
	}
	public String getEarliest() {
		if (this.getLength()== 0) {
			return null;
		}
		Calendar current = photos.get(0).getDate();
		for (Photo photo : photos) {
			if (photo.getDate().before(current)) {
				current = photo.getDate();
			}
		}
		return current.toString();
		
	}
	public String getLatest() {
		if (this.getLength()== 0) {
			return null;
		}
		Calendar current = photos.get(0).getDate();
		for (Photo photo : photos) {
			if (photo.getDate().after(current)) {
				current = photo.getDate();
			}
		}
		return current.toString();
		
	}
}
