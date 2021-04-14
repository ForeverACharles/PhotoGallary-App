package Photos55.view;

import javafx.beans.property.SimpleStringProperty;

public class tagDisplay {
	
	private SimpleStringProperty tag;
	private SimpleStringProperty value;
	
	public tagDisplay(String tag, String value)
	{
		this.tag = new SimpleStringProperty(tag);
		this.value = new SimpleStringProperty(value);
	}
	public String getTag()
	{
		return tag.get();
	}
	public String getValue()
	{
		return value.get();
	}
}
