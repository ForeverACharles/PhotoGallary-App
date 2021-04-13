package Photos55.view;

import javafx.beans.property.SimpleStringProperty;

public class TableDisplay
{
	private SimpleStringProperty Name;
	private SimpleStringProperty PhotoCount;
	private SimpleStringProperty Earliest;
	private SimpleStringProperty Latest;
	
	public TableDisplay(String Name, int PhotoCount, String Earliest, String Latest)
	{
		this.Name = new SimpleStringProperty(Name);
		this.PhotoCount = new SimpleStringProperty(Integer.toString(PhotoCount));
		this.Earliest = new SimpleStringProperty(Earliest);
		this.Latest = new SimpleStringProperty(Latest);
	}
	
	public String getName()
	{
		return Name.get();
	}
	public String getPhotoCount()
	{
		return PhotoCount.get();
	}
	public String getEarliest()
	{
		return Earliest.get();
	}
	public String getLatest()
	{
		return Latest.get();
	}
}