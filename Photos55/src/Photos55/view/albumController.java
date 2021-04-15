package Photos55.view;

/**
 *  Gui controller for user interaction with a single Album
 *  
 *  <code>albumController</code> contains logic to handle user 
 *  interaction with their <code>Photos</code>. The
 *  gui allows a user to view, add, and remove Photos from an Album,
 *  as well as modify Photo captions and tags. User may also copy or
 *  move selected Photos to another of their Albums
 *   
 *   @author Charles Li
 *   @author Max Sun
 *   @version 1.69 Apr 2021
*/

import Photos55.app.*;

import java.io.IOException;
import java.io.InputStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class albumController {
	
	@FXML Button logoutButton;
	@FXML GridPane imageGrid;
	@FXML ImageView currentView;
	@FXML Text dateField;
	@FXML TextField captionField;
	@FXML ChoiceBox<Album> dropdownMenu;
	
	/**
	 *  <code>Photo</code> that is currently selected by <code>User</code>
	 */
	Photo clickedPhoto = null;
	
	/**
	 *  <code>Image</code> belonging to currently selected Photo by <code>User</code>
	 */
	Image clickedImage = null;
	
	/**
	 *  <code>Stage</code> representing the current scene viewed by <code>User</code>
	 */
	Stage mainstage;
	
	@FXML TableView<tagDisplay> tagTable;
	@FXML TableColumn<tagDisplay, String> Tag;
	@FXML TableColumn<tagDisplay, String> Value;
	
	/**
	 *  Table contents of a <code>Photo</code>'s Tags in Tag type and Value format
	 */
	ObservableList<tagDisplay> tagTableContents;
	
	@FXML TextField tagToAdd;
	@FXML TextField valueToAdd;
	
	@FXML Button presentButton;
	
	/**
	 *  <code>albumController</code> start method.
	 *  Loads contents for <code>User</code> interaction with their Photos.
	 *  @param stage the stage used to set gui scene and pass into other controllers
	 */
	public void start(Stage stage) throws FileNotFoundException
	{
		mainstage = stage;
		ObservableList<Album> albums = FXCollections.observableArrayList();
		for (Album album: Photos55App.user.getAlbums()) {
			if (album != userController.viewAlbum)
				albums.add(album);
		}
		dropdownMenu.setItems(albums);
		makeGrid();
	}
	
	/**
	 *  Sets the currently selected Photo and Image
	 *  @param photo the Photo for selection
	 *  @param image the Image assocaited with the Photo for selection
	 */
	public void clicked(Photo photo, Image image) throws FileNotFoundException {
		
		clickedPhoto = photo;
		clickedImage = image;
		//System.out.println("selected: " + photo.getCaption());
		currentView.setImage(image);
		
		Tag.setCellValueFactory(new PropertyValueFactory<tagDisplay, String>("Tag"));
		Value.setCellValueFactory(new PropertyValueFactory<tagDisplay, String>("Value"));
		
		if(photo == null)
		{
			dateField.setText("");
			captionField.setText("");
			tagTable.setItems(null);
		}
		else
		{
			dateField.setText(photo.printDate());
			captionField.setText(photo.getCaption());
			
			tagTableContents = getTagTable();
			tagTable.setItems(tagTableContents);
		}
	}
	/**
	 *  Reads relevant Tag information from the selected <code>Photo</code>
	 */
	public ObservableList<tagDisplay> getTagTable()
	{
		ObservableList<tagDisplay> result = FXCollections.observableArrayList();
		for(Tag tag : clickedPhoto.getTags())
		{
			result.add(new tagDisplay(tag.getTag(), tag.getValue()));
		}
		return result;
	}
	
	/**
	 *  Displays the <code>Photo</code> display for all Photos in the current
	 *  <code>User</code>'s album. Reports if any Photos failed to be located
	 */
	public void makeGrid() throws FileNotFoundException {
		
		ArrayList<Photo> photoList = userController.viewAlbum.getPhotos();
		imageGrid.getChildren().clear();
		ColumnConstraints column1 = new ColumnConstraints();
		column1.setPercentWidth(33);
		ColumnConstraints column2 = new ColumnConstraints();
		column2.setPercentWidth(33);
		ColumnConstraints column3 = new ColumnConstraints();
		column3.setPercentWidth(33);
		//ColumnConstraints column4 = new ColumnConstraints();
		//column4.setPercentWidth(25);
		imageGrid.getColumnConstraints().clear();
		//imageGrid.getColumnConstraints().addAll(column1, column2, column3, column4);
		imageGrid.getColumnConstraints().addAll(column1, column2, column3);
		imageGrid.getRowConstraints().clear();
		
		imageGrid.setHgap(10); //horizontal gap in pixels => that's what you are asking for
		imageGrid.setVgap(10); //vertical gap in pixels
		imageGrid.setPadding(new Insets(10, 10, 10, 10));
		
		int row = 0;
		int col = 0;
		int failedLocates = 0;
		InputStream stream = null;
		for (Photo photo: photoList) {
			//photo.reCaption(""+ row);
			try
			{
				String filePath = photo.getPath();
				if(photo.getPath().contains("Photos55" + File.separator + "data"))
		    	{
		    		filePath = "data" + File.separator + filePath.substring(filePath.lastIndexOf(File.separator) + 1).trim();
		    	}
				stream = new FileInputStream(filePath);
			}
			catch (FileNotFoundException e)
			{
				//Input Stream could not be created from missing file, load a photo with error
				failedLocates++;
				stream = new FileInputStream("data" + File.separator + "PhotoNotFound.png");
			}
			
			Image image = new Image(stream);
			ImageView imageView = new ImageView();
			//Setting image to the image view
			
			imageView.setImage(image);
			imageView.setOnMouseClicked(e -> 
			{
				try 
				{
					clicked(photo, image);
				} 
				catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			});
			
			//Setting the image view parameters
			imageView.setPreserveRatio(true);
			imageView.setSmooth(true);
			imageView.setFitWidth(95);
			imageView.setFitHeight(95);
			imageGrid.add(imageView, col, 2*row);
			imageGrid.setHalignment(imageView, HPos.CENTER);
			imageGrid.setValignment(imageView, VPos.BOTTOM);
			
			Text t = new Text(0, 0, photo.getCaption());
			imageGrid.add(t, col, 2*row + 1);
			imageGrid.setHalignment(t, HPos.CENTER);
			imageGrid.setValignment(t, VPos.TOP);
			col++;
			if(col == 3) {
				col = 0;
				row++;
			}
		}
		mainstage.show();
		if(failedLocates > 0)
		{
			Alert alert = new Alert(AlertType.WARNING, failedLocates + " photo(s) were not able to be located", ButtonType.OK);
			alert.showAndWait();
		}
	}
	
	/**
	 * Sets the caption of the currently selected <code>Photo</code>
	 */
	public void changeCaption() throws IOException, ClassNotFoundException{
		
		if(clickedPhoto == null) {
			Alert alert = new Alert(AlertType.ERROR, "No photo selected", ButtonType.OK);
			alert.showAndWait();
			return;
		}
		
		clickedPhoto.reCaption(captionField.getText());
		//Photos55App.writePhotosApp();
		makeGrid();
	}
	
	/**
	 *  Adds <code>Photo</code> from User's File System to the application
	 *  Prevents duplicates of a Photo within the same Album. Notifies
	 *  User if some photos already exist
	 */
	public void addPhoto() throws IOException, ClassNotFoundException{
		FileChooser chooser = new FileChooser();
		int failedImports = 0;
		chooser.getExtensionFilters().add(new ExtensionFilter("Pictures", "*.jpg","*.png","*.bmp","*.gif"));
	    List<File> files = chooser.showOpenMultipleDialog(logoutButton.getScene().getWindow());
	    if(files != null)
	    {
	    	for(File file : files)
		    {
		    	if (file != null) {
		    	
		    		//check all photos of all albums if the file matches
		    		Boolean duplicate = false;
		    		for(Album album : Photos55App.user.getAlbums())
		    		{
		    			for(Photo photo : album.getPhotos())
		    			{
		    				if(file.getCanonicalFile().equals(new File(photo.getPath()).getCanonicalFile()))
		    				{
		    					System.out.println("we found duplicate in: "+ album.getName());
		    					if(album.equals(userController.viewAlbum))
		    					{
		    						failedImports++;
		    					}
		    					else if(!userController.viewAlbum.getPhotos().contains(photo))
		    					{
			    					userController.viewAlbum.getPhotos().add(photo);
			    					failedImports = 0;
		    					}
		    					duplicate = true;
		    				}
		    			}
		    		}
		    			
		    		if(duplicate == false)
		    		{
		    			Photo newPhoto = new Photo(file.toString(), Instant.ofEpochMilli(file.lastModified()).atZone(ZoneId.systemDefault()).toLocalDate());
					    userController.viewAlbum.getPhotos().add(newPhoto);
					    System.out.println(newPhoto.getPath());
						System.out.println(newPhoto.printDate());
		    		}
		    	}
		    }
	    }
	   
	    if(failedImports > 0)
	    {
	    	Alert alert = new Alert(AlertType.WARNING, failedImports + " photo(s) already imported into the album", ButtonType.OK);
			alert.showAndWait();
	    }
	    
	    makeGrid();
	    //Photos55App.writePhotosApp();
	}
	
	/**
	 *  Removes currently selected <code>Photo</code> from the current Album
	 *  Reports error if no Photo selected
	 */
	public void removePhoto() throws IOException, ClassNotFoundException {
		
		if(clickedPhoto == null) {
			Alert alert = new Alert(AlertType.ERROR, "No photo selected", ButtonType.OK);
			alert.showAndWait();
			return;
		}
		
		userController.viewAlbum.getPhotos().remove(clickedPhoto);
		makeGrid();
		//Photos55App.writePhotosApp();
		clicked(null, null);
	}
	
	/**
	 *  Copies <code>Photo</code> from current Album to another one of User's Albums
	 *  Reports error if Photo already exists in requested Album. Returns boolean if
	 *  successful
	 */
	public boolean copyPhoto() {
		if(clickedPhoto == null) {
			Alert alert = new Alert(AlertType.ERROR, "No photo selected", ButtonType.OK);
			alert.showAndWait();
			return false;
		}
		Album selectedAlbum = dropdownMenu.getSelectionModel().getSelectedItem();
		if(selectedAlbum == null) {
			Alert alert = new Alert(AlertType.ERROR, "No destination album selected", ButtonType.OK);
			alert.showAndWait();
			return false;
		}
		if(selectedAlbum.getPhotos().contains(clickedPhoto)) {
			Alert alert = new Alert(AlertType.ERROR, "Photo already in destination album", ButtonType.OK);
			alert.showAndWait();
			return false;
		}
		selectedAlbum.getPhotos().add(clickedPhoto);
		dropdownMenu.setValue(null);
		//Photos55App.writePhotosApp();
		return true;
	}
	
	/**
	 *  Moves <code>Photo</code> from current Album to another one of User's Albums
	 *  Reports error if Photo already exists in requested Album.
	 */
	public void movePhoto() throws ClassNotFoundException, IOException {
		if(copyPhoto())
		{
			removePhoto();
		}
	}
	
	/**
	 *  Adds <code>Tag</code> to currently selected <code>Photo</code>
	 *  Reports error if Tag already exits
	 */
	public void addTag() throws IOException, ClassNotFoundException
	{
		Tag TagToAdd = new Tag(tagToAdd.getText(), valueToAdd.getText());
		Photo selectedPhoto = clickedPhoto;
		Image selectedImage = clickedImage;
		
		if(clickedPhoto == null)
		{
			Alert alert = new Alert(AlertType.ERROR, "No photo selected", ButtonType.OK);
			alert.showAndWait();
			return;
		}
		
		if(TagToAdd.getTag().isEmpty() || TagToAdd.getValue().isEmpty())
		{
			Alert alert = new Alert(AlertType.ERROR, "Cannot add incomplete tag", ButtonType.OK);
			alert.showAndWait();
			return;
		}
		
		if(clickedPhoto.getTags().contains(TagToAdd))
		{
			Alert alert = new Alert(AlertType.ERROR, "This tag already exists", ButtonType.OK);
			alert.showAndWait();
			return;
		}
		clickedPhoto.getTags().add(TagToAdd);
		tagToAdd.clear();
		valueToAdd.clear();
		
		//Photos55App.writePhotosApp();
		tagTableContents = getTagTable();
		tagTable.setItems(tagTableContents);
	}
	
	/**
	 *  Deletes <code>Tag</code> from currently selected <code>Photo</code>
	 */
	public void deleteTag() throws IOException, ClassNotFoundException
	{
		int index = tagTable.getSelectionModel().getSelectedIndex();
		if(clickedPhoto.getTags().size() == 0 || index == -1) {
			Alert alert = new Alert(AlertType.ERROR, "Tag list is empty or no tag selected", ButtonType.OK);
			alert.showAndWait();
			return;
		}
		clickedPhoto.getTags().remove(clickedPhoto.getTags().get(index));
		
		//Photos55App.writePhotosApp();
		tagTableContents = getTagTable();
		tagTable.setItems(tagTableContents);
	}
	
	/**
	 *  Sends <code>User</code> presentation view of the current Album
	 */
	public void present() throws IOException, ClassNotFoundException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/Photos55/view/presentation.fxml"));
		Pane root = (Pane)loader.load();
		Stage stage = (Stage) presentButton.getScene().getWindow();
		Scene scene = new Scene(root);
		presentationController photosController = loader.getController();
		photosController.start(mainstage);
		stage.setScene(scene);
		stage.show();
	}
	
	/**
	 *  Logs current <code>User</code> out of current application session.
	 *  Saves the User's session and returns to login scene
	 */
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
	
	/**
	 *  Returns <code>User</code> to scene displaying all their Albums
	 */
	public void back() throws IOException {
		//Photos55App.writePhotosApp();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/Photos55/view/nonAdmin.fxml"));
		Pane root = (Pane)loader.load();
    	Stage stage = (Stage) logoutButton.getScene().getWindow();
    	Scene scene = new Scene(root);
    	userController photosController = loader.getController();
		photosController.start(stage);
    	stage.setScene(scene);
    	stage.show();	
	}
	    
	/**
	 *  Saves the session and closes the application
	 */
	public void quit() throws IOException, ClassNotFoundException {
    	Photos55App.writePhotosApp();
    	System.exit(0);
	}
}
