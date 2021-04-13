package Photos55.view;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Slider;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import Photos55.app.*;

public class userController {
	
	User user = Photos55App.user;
	ArrayList<Album> theAlbums = user.getAlbums();
	
	@FXML TableView<TableDisplay> userAlbums;
	@FXML TableColumn<TableDisplay, String> Name;
	@FXML TableColumn<TableDisplay, String> PhotoCount;
	@FXML TableColumn<TableDisplay, String> Earliest;
	@FXML TableColumn<TableDisplay, String> Latest;
	ObservableList<TableDisplay> tableContents;
	
	@FXML Button openAlbumButton;
	public static Album viewAlbum;
	
	@FXML TextField albumEntry;
	
	@FXML DatePicker startDate;
	@FXML DatePicker endDate;
	@FXML Button dateSearchButton;
	
	@FXML TextField tag1;
	@FXML TextField value1;
	@FXML TextField tag2;
	@FXML TextField value2;
	@FXML Slider tagSlider;
	@FXML Button tagSearchButton;
	
	public static ArrayList<Photo> filteredPhotos = new ArrayList<Photo>();
	
	@FXML Button logoutButton;
	
	public void start(Stage stage)
	{
		System.out.println(user.getName());
		
		tableContents = getTable();
	
		Name.setCellValueFactory(new PropertyValueFactory<TableDisplay, String>("Name"));
		PhotoCount.setCellValueFactory(new PropertyValueFactory<TableDisplay, String>("PhotoCount"));
		Earliest.setCellValueFactory(new PropertyValueFactory<TableDisplay, String>("Earliest"));
		Latest.setCellValueFactory(new PropertyValueFactory<TableDisplay, String>("Latest"));
		
		userAlbums.setItems(tableContents);
	}
	
	public ObservableList<TableDisplay> getTable()
	{
		ObservableList<TableDisplay> result = FXCollections.observableArrayList();
		
		for(Album album : theAlbums)
		{
			result.add(new TableDisplay(
				album.getName(),
				album.getNumber(),
				album.getEarliest(),
				album.getLatest()
			));
		}
		return result;
	}
	
	public void openAlbum() throws IOException
	{
		int index = userAlbums.getSelectionModel().getSelectedIndex();
		if(theAlbums.size() == 0 || index == -1) {
			Alert alert = new Alert(AlertType.ERROR, "List is Empty or Nothing is Selected", ButtonType.OK);
			alert.showAndWait();
			return;
		}
		viewAlbum = theAlbums.get(index);
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/Photos55/view/openAlbum.fxml"));
		Pane root = (Pane)loader.load();
		Stage stage = (Stage) openAlbumButton.getScene().getWindow();
    	Scene scene = new Scene(root);
    	stage.setScene(scene);
    	stage.show();
	}
	
	public void createAlbum() throws IOException, ClassNotFoundException
	{
		String requestedAlbum = albumEntry.getText();
		
		if(requestedAlbum.isEmpty()) 
		{
			Alert alert = new Alert(AlertType.ERROR, "Cannot create album with an empty name", ButtonType.OK);
			alert.showAndWait();
			return;
		}
		
		for(Album album : theAlbums)
		{
			if(album.getName().equals(requestedAlbum))
			{
				Alert alert = new Alert(AlertType.ERROR, "This album already exists", ButtonType.OK);
				alert.showAndWait();
				return;
			}	
		}
		
		theAlbums.add(new Album(requestedAlbum));
		albumEntry.clear();
		
		Photos55App.writePhotosApp();
		tableContents = getTable();
		userAlbums.setItems(tableContents);
	}
	
	public void renameAlbum() throws IOException, ClassNotFoundException
	{
		String requestedAlbum = albumEntry.getText();
		int index = userAlbums.getSelectionModel().getSelectedIndex();
		
		if(theAlbums.size() == 0 || index == -1) {
			Alert alert = new Alert(AlertType.ERROR, "List is empty or no album selected", ButtonType.OK);
			alert.showAndWait();
			return;
		}
		
		if(requestedAlbum.isEmpty()) 
		{
			Alert alert = new Alert(AlertType.ERROR, "Cannot rename album with an empty name", ButtonType.OK);
			alert.showAndWait();
			return;
		}
		
		if(theAlbums.get(index).getName().equals(requestedAlbum))
		{
			Alert alert = new Alert(AlertType.ERROR, "This album already exists", ButtonType.OK);
			alert.showAndWait();
			return;
		}
		
		for(Album album : theAlbums)
		{
			if(album.getName().equals(requestedAlbum))
			{
				Alert alert = new Alert(AlertType.ERROR, "Another album with same name already exists", ButtonType.OK);
				alert.showAndWait();
				return;
			}	
		}
		
		theAlbums.get(index).setName(requestedAlbum);
		albumEntry.clear();
		
		Photos55App.writePhotosApp();
		tableContents = getTable();
		userAlbums.setItems(tableContents);
	}
	
	public void deleteAlbum() throws IOException, ClassNotFoundException
	{
		int index = userAlbums.getSelectionModel().getSelectedIndex();
		if(theAlbums.size() == 0 || index == -1) {
			Alert alert = new Alert(AlertType.ERROR, "List is Empty or Nothing is Selected", ButtonType.OK);
			alert.showAndWait();
			return;
		}
		theAlbums.remove(theAlbums.get(index));
		
		Photos55App.writePhotosApp();
		tableContents = getTable();
		userAlbums.setItems(tableContents);
	}
	
	public void searchByDate() throws IOException
	{
		LocalDate start = startDate.getValue();
		LocalDate end = endDate.getValue();
		
		if(start == null && end == null) {
			Alert alert = new Alert(AlertType.ERROR, "No dates entered", ButtonType.OK);
			alert.showAndWait();
			return;
		}
		
		if(start != null && end == null)	//only start date provided
		{
			for(Album album : theAlbums) {
				if(album.getNumber() > 0) {
					for(Photo photo : album.getPhotos()) {
						LocalDate photoDate = photo.getDate();
						
						if(photoDate != null && (photoDate.isEqual(start) && photoDate.isAfter(start)))
						{
							filteredPhotos.add(photo);
						}
					}
				}
			}	
			
		}
		
		else if(start == null && end != null)	//only end date provided
		{
			for(Album album : theAlbums) {
				if(album.getNumber() > 0) {
					for(Photo photo : album.getPhotos()) {
						LocalDate photoDate = photo.getDate();
						
						if(photoDate != null && (photoDate.isEqual(end) && photoDate.isBefore(end)))
						{
							filteredPhotos.add(photo);
						}
					}
				}
			}	
		}
		
		else
		{
			if(start.isAfter(end))		//error start after end date
			{
				Alert alert = new Alert(AlertType.ERROR, "Start date must be on or before End date", ButtonType.OK);
				alert.showAndWait();
				return;
			}
			
			for(Album album : theAlbums) {		//check between date ranges
				if(album.getNumber() > 0) {
					for(Photo photo : album.getPhotos()) {
						LocalDate photoDate = photo.getDate();
						
						//System.out.println(photoDate + "between: " + start + ", and: " + end);
						if(photoDate != null && (photoDate.compareTo(start) >= 0 && photoDate.compareTo(end) <= 0))
						{
							filteredPhotos.add(photo);
						}
					}
				}
			}
		}
		
		/*
		for(Photo photo : filteredPhotos)
		{
			System.out.println(photo.getName() + " | " + photo.getDate().toString());
		}
		*/
	
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/Photos55/view/searchResults.fxml"));
		Pane root = (Pane)loader.load();
		Stage stage = (Stage) dateSearchButton.getScene().getWindow();
    	Scene scene = new Scene(root);
    	stage.setScene(scene);
    	stage.show();
    	
	}
	
	public void searchByTag() throws IOException
	{
		String Tag1 = tag1.getText();
		String Value1 = value1.getText();
		String Tag2 = tag2.getText();
		String Value2 = value2.getText();
		
		Tag pair1 = null;
		Tag pair2 = null;
		
		if(!Tag1.isEmpty() && !Value1.isEmpty())
		{
			pair1 = new Tag(Tag1, Value1);
		}
		
		if(!Tag2.isEmpty() && !Value2.isEmpty())
		{
			pair2 = new Tag(Tag2, Value2);
		}
		
		if((int)tagSlider.getValue() == 1)	//single tag pair search
		{
			if(pair1 == null && pair2 == null)
			{
				Alert alert = new Alert(AlertType.ERROR, "No tags entered or incomplete tag detected", ButtonType.OK);
				alert.showAndWait();
				return;
			}
			
			if((!Tag1.isEmpty() && !Tag2.isEmpty()) || (!Value1.isEmpty() && !Value2.isEmpty()))
			{
				Alert alert = new Alert(AlertType.ERROR, "Only 1 complete tag may be used for filtering in this mode", ButtonType.OK);
				alert.showAndWait();
				return;
			}
		
			tagFilter(pair1, pair2, 1);
		}
		
		else if((int)tagSlider.getValue() == 0)		//AND filter
		{
			if(pair1 == null || pair2 == null)
			{
				Alert alert = new Alert(AlertType.ERROR, "AND search requires both tags to be filled", ButtonType.OK);
				alert.showAndWait();
				return;
			}
			tagFilter(pair1, pair2, 0);
		}
		
		else if((int)tagSlider.getValue() == 2)		//OR filter
		{
			if(pair1 == null || pair2 == null)
			{
				Alert alert = new Alert(AlertType.ERROR, "OR search requires both tags to be filled", ButtonType.OK);
				alert.showAndWait();
				return;
			}
			tagFilter(pair1, pair2, 2);
		}
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/Photos55/view/searchResults.fxml"));
		Pane root = (Pane)loader.load();
		Stage stage = (Stage) tagSearchButton.getScene().getWindow();
    	Scene scene = new Scene(root);
    	stage.setScene(scene);
    	stage.show();
	}
	
	public void tagFilter(Tag pair1, Tag pair2, int slider)		//helper function for searchByTag()
	{
		
		if(slider == 1)
		{
			Tag pair = pair1;
			if(pair1 == null)
			{
				pair = pair2;
			}
			
			for(Album album : theAlbums) {
				if(album.getNumber() > 0) {
					for(Photo photo : album.getPhotos()) {
						if(photo.getTags().contains(pair))
						{
							filteredPhotos.add(photo);
						}	
					}
				}
			}
		}
		
		else if(slider == 0)
		{
			for(Album album : theAlbums) {
				if(album.getNumber() > 0) {
					for(Photo photo : album.getPhotos()) {
						if(photo.getTags().contains(pair1) && photo.getTags().contains(pair2))
						{
							filteredPhotos.add(photo);
						}
					}
				}
			}
		}
		
		else if(slider == 2)
		{
			for(Album album : theAlbums) {
				if(album.getNumber() > 0) {
					for(Photo photo : album.getPhotos()) {
						if(photo.getTags().contains(pair1) || photo.getTags().contains(pair2))
						{
							filteredPhotos.add(photo);
						}
					}
				}
			}
		}
	}
	
	public void logout() throws IOException {
    	FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/Photos55/view/Login.fxml"));
		Pane root = (Pane)loader.load();
		Stage stage = (Stage) logoutButton.getScene().getWindow();
    	Scene scene = new Scene(root);
    	stage.setScene(scene);
    	stage.show();
    }
	
	public void quit() throws IOException, ClassNotFoundException {
    	Photos55App.writePhotosApp();
    	System.exit(0);
    }
}


