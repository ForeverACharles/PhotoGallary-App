package Photos55.view;

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
	Photo clickedPhoto = null;
	Image clickedImage = null;
	Stage mainstage;
	
	@FXML TableView<tagDisplay> tagTable;
	@FXML TableColumn<tagDisplay, String> Tag;
	@FXML TableColumn<tagDisplay, String> Value;
	ObservableList<tagDisplay> tagTableContents;
	
	@FXML TextField tagToAdd;
	@FXML TextField valueToAdd;
	
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
	}
	
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
	
	public void addPhoto() throws IOException, ClassNotFoundException{
		FileChooser chooser = new FileChooser();
		int failedImports = 0;
		
		chooser.getExtensionFilters().add(new ExtensionFilter("Pictures", "*.jpg","*.png","*.bmp","*.gif"));
	    List<File> files = chooser.showOpenMultipleDialog(logoutButton.getScene().getWindow());
	    for(File file : files)
	    {
	    	if (file != null) {
		    	Photo newPhoto = new Photo(file.toString(), Instant.ofEpochMilli(file.lastModified()).atZone(ZoneId.systemDefault()).toLocalDate());
		    	
		        System.out.println(newPhoto.getPath());
		        System.out.println(newPhoto.printDate());
		        
		        Boolean duplicate = false;
		        ArrayList<Album> albums = Photos55App.user.getAlbums();
		        for (Album album: albums) {
		        	ArrayList<Photo> photos = album.getPhotos();
		        	for(Photo photo : photos)
	        		{
	        			if(photo.getPath().equals(newPhoto.getPath()))
        				{
        					duplicate = true;
        					failedImports++;
        					break;
        				}
	        		}
		        }
		        if(duplicate == false)
		        {
		        	userController.viewAlbum.getPhotos().add(newPhoto);
		        }
	    	}
	    }
	    
	    if(failedImports >= 1)
	    {
	    	Alert alert = new Alert(AlertType.WARNING, failedImports + " photo(s) already imported into the album. Skipping.", ButtonType.OK);
			alert.showAndWait();
	    }
	    
	    makeGrid();
	    //Photos55App.writePhotosApp();
	}
	
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
	
	public void copyPhoto() {
		if(clickedPhoto == null) {
			Alert alert = new Alert(AlertType.ERROR, "No photo selected", ButtonType.OK);
			alert.showAndWait();
			return;
		}
		Album selectedAlbum = dropdownMenu.getSelectionModel().getSelectedItem();
		if(selectedAlbum == null) {
			return;
		}
		if(selectedAlbum.getPhotos().contains(clickedPhoto)) {
			Alert alert = new Alert(AlertType.ERROR, "Photo already in destination album", ButtonType.OK);
			alert.showAndWait();
			return;
		}
		selectedAlbum.getPhotos().add(clickedPhoto);
		dropdownMenu.setValue(null);
		//Photos55App.writePhotosApp();
	}
	
	public void movePhoto() throws ClassNotFoundException, IOException {
		copyPhoto();
		removePhoto();
	}
	public ObservableList<tagDisplay> getTagTable()
	{
		ObservableList<tagDisplay> result = FXCollections.observableArrayList();
		for(Tag tag : clickedPhoto.getTags())
		{
			result.add(new tagDisplay(tag.getTag(), tag.getValue()));
		}
		return result;
	}
	
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
	    
	public void quit() throws IOException, ClassNotFoundException {
    	Photos55App.writePhotosApp();
    	System.exit(0);
	}
}
