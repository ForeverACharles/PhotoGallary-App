package Photos55.view;

/**
 *  Gui controller for user album interaction
 *  
 *  <code>userController</code> contains logic to handle user 
 *  interaction with the <code>Photos</code> application. The
 *  gui allows a user to view, create, and remove photo albums,
 *  as well as search all photos by date or tag.
 *   
 *   @author Charles Li
 *   @author Max Sun
 *   @version 1.69 Apr 2021
*/

import Photos55.app.*;

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

public class userController {
	
	/**
	 *  <code>User</code> of the current application session
	 */
	User user = Photos55App.user;
	
	/**
	 *  <code>Album</code>s belonging to the currently logged in <code>User</code>
	 */
	ArrayList<Album> theAlbums = user.getAlbums();
	
	@FXML TableView<TableDisplay> userAlbums;
	@FXML TableColumn<TableDisplay, String> Name;
	@FXML TableColumn<TableDisplay, String> PhotoCount;
	@FXML TableColumn<TableDisplay, String> Earliest;
	@FXML TableColumn<TableDisplay, String> Latest;
	
	/**
	 *  Table contents of a <code>User</code>'s albums, displayed in Name, Photo Count,
	 *  and Date format
	 */
	ObservableList<TableDisplay> tableContents;
	
	@FXML Button openAlbumButton;
	
	/**
	 *  <code>Album</code> representing the <code>User</code>'s currently selected Album
	 */
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
	
	/**
	 *  <code>Photo</code>s filtered by search criteria of the <code>User</code>
	 */
	public static ArrayList<Photo> filteredPhotos = new ArrayList<Photo>();
	
	@FXML Button logoutButton;
	
	/**
	 *  <code>userController</code> start method.
	 *  Loads contents for <code>User</code> interaction with their albums.
	 *  @param stage the stage used to set gui scene and pass into other controllers
	 */
	public void start(Stage stage)
	{	
		tableContents = getTable();
	
		Name.setCellValueFactory(new PropertyValueFactory<TableDisplay, String>("Name"));
		PhotoCount.setCellValueFactory(new PropertyValueFactory<TableDisplay, String>("PhotoCount"));
		Earliest.setCellValueFactory(new PropertyValueFactory<TableDisplay, String>("Earliest"));
		Latest.setCellValueFactory(new PropertyValueFactory<TableDisplay, String>("Latest"));
		
		userAlbums.setItems(tableContents);
	}
	
	/**
	 *  Reads relevant album information from the <code>User</code>'s albums
	 */
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
	
	/**
	 *  Opens the currently selected <code>Album</code> and gets the next scene. Reports error
	 *  when none are available or none selected
	 */
	public void openAlbum() throws IOException
	{
		int index = userAlbums.getSelectionModel().getSelectedIndex();
		if(theAlbums.size() == 0 || index == -1) {
			Alert alert = new Alert(AlertType.ERROR, "No albums available or album not selected", ButtonType.OK);
			alert.showAndWait();
			return;
		}
		viewAlbum = theAlbums.get(index);
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/Photos55/view/album.fxml"));
		Pane root = (Pane)loader.load();
		Stage stage = (Stage) openAlbumButton.getScene().getWindow();
    	Scene scene = new Scene(root);
    	albumController photosController = loader.getController();
		photosController.start(stage);
    	stage.setScene(scene);
    	stage.show();
	}
	
	/**
	 *  Creates <code>Album</code> with <code>User</code> provided Album name. Reports error
	 *  the Album cannot be added
	 */
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
	
	/**
	 *  Renames the currently selected <code>Album</code>. Reports error
	 *  when none are available, none selected, or album already exists
	 */
	public void renameAlbum() throws IOException, ClassNotFoundException
	{
		String requestedAlbum = albumEntry.getText();
		int index = userAlbums.getSelectionModel().getSelectedIndex();
		
		if(theAlbums.size() == 0 || index == -1) {
			Alert alert = new Alert(AlertType.ERROR, "No albums available or album not selected", ButtonType.OK);
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
	
	/**
	 *  Deletes the currently selected <code>Album</code>. Reports error
	 *  when none are available or none selected
	 */
	public void deleteAlbum() throws IOException, ClassNotFoundException
	{
		int index = userAlbums.getSelectionModel().getSelectedIndex();
		if(theAlbums.size() == 0 || index == -1) {
			Alert alert = new Alert(AlertType.ERROR, "Album list is empty or no album selected", ButtonType.OK);
			alert.showAndWait();
			return;
		}
		theAlbums.remove(theAlbums.get(index));
		
		Photos55App.writePhotosApp();
		tableContents = getTable();
		userAlbums.setItems(tableContents);
	}
	
	/**
	 *  Filters all <code>Photo</code>s belonging to the current User by Date. 
	 *  Sends User to filtered results scene to view filtered Photos. Reports
	 *  error if Date formatting is incorrect
	 */
	public void searchByDate() throws IOException
	{
		filteredPhotos.clear();
		tag1.clear();
		value1.clear();
		tag2.clear();
		value2.clear();
		
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
						if(photoDate != null && (photoDate.isEqual(start) || photoDate.isAfter(start)))
						{
							if(!(filteredPhotos.contains(photo))){
								filteredPhotos.add(photo);
							}
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
						if(photoDate != null && (photoDate.isBefore(end) || photoDate.isEqual(end)))
						{
							if(!(filteredPhotos.contains(photo))){
								filteredPhotos.add(photo);
							}
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
						if(photoDate != null && (photoDate.compareTo(start) >= 0 && photoDate.compareTo(end) <= 0))
						{
							if(!(filteredPhotos.contains(photo))){
								filteredPhotos.add(photo);
							}
						}
					}
				}
			}
		}
		
		if(filteredPhotos.isEmpty())
		{
			Alert alert = new Alert(AlertType.INFORMATION, "No photos found with these criteria", ButtonType.OK);
			alert.showAndWait();
			return;
		}
	
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/Photos55/view/search.fxml"));
		Pane root = (Pane)loader.load();
		Stage stage = (Stage) dateSearchButton.getScene().getWindow();
    	Scene scene = new Scene(root);
    	searchController photosController = loader.getController();
		photosController.start(stage);
    	stage.setScene(scene);
    	stage.show();
    	
	}
	
	/**
	 *  Filters all <code>Photo</code>s belonging to the current User by Tag 
	 *  Sends User to filtered results scene to view filtered Photos. Reports
	 *  error if Tag formatting is incorrect
	 */
	public void searchByTag() throws IOException
	{
		filteredPhotos.clear();
		startDate.getEditor().clear();
		endDate.getEditor().clear();
		
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
		
		if(filteredPhotos.isEmpty())
		{
			Alert alert = new Alert(AlertType.INFORMATION, "No photos found with these criteria", ButtonType.OK);
			alert.showAndWait();
			return;
		}
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/Photos55/view/search.fxml"));
		Pane root = (Pane)loader.load();
		Stage stage = (Stage) tagSearchButton.getScene().getWindow();
    	Scene scene = new Scene(root);
    	searchController photosController = loader.getController();
		photosController.start(stage);
    	stage.setScene(scene);
    	stage.show();
	}
	
	/**
	 *  Filters all <code>Photo</code>s belonging to the current User by Tag 
	 */
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
						if(photo.containsTag(pair))
						{
							if(!(filteredPhotos.contains(photo))){
								filteredPhotos.add(photo);
							}
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
						if(photo.containsTag(pair1) && photo.containsTag(pair2))
						{
							if(!(filteredPhotos.contains(photo))){
								filteredPhotos.add(photo);
							}
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
						if(photo.containsTag(pair1) || photo.containsTag(pair2))
						{
							if(!(filteredPhotos.contains(photo))){
								filteredPhotos.add(photo);
							}
						}
					}
				}
			}
		}
	}
	
	public void logout() throws IOException, ClassNotFoundException {
		Photos55App.writePhotosApp();
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


