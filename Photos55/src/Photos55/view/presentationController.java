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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

import java.time.Instant;
import java.time.ZoneId;
import java.util.*;
/**
 *  Gui controller for user album interaction
 *  
 *  <code>presentationController</code> contains logic to handle user 
 *  interaction with the <code>Photos</code> application. The
 *  gui allows a user to present a manual slideshow of photos in an <code>Album</code>
 *   
 *   @author Charles Li
 *   @author Max Sun
 *   @version 1.69 Apr 2021
*/

public class presentationController {
	
	@FXML ImageView imageViewer;
	Stage mainstage;
	@FXML Button logoutButton;
	@FXML TextField tagToAdd;
	@FXML TextField valueToAdd;
	
	int current = 0;
	ObservableList<Image> images = FXCollections.observableArrayList();
	/**
	 *  <code>presentationController</code> start method.
	 *  Loads contents for <code>Photo</code> presentation.
	 *  @param stage the stage used to set gui scene and pass into other controllers
	 */	
	public void start(Stage stage) throws FileNotFoundException
	{
		mainstage = stage;
		InputStream stream = null;
		for (Photo photo: userController.viewAlbum.getPhotos()) {
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
			//Setting image to the image view
			images.add(image);
		}
		if(!(images.isEmpty())) {
			
			//imageViewer.setPreserveRatio(true);
			imageViewer.setSmooth(true);
			imageViewer.setFitHeight(250);
			double width = 0;

            double ratioX = imageViewer.getFitWidth() / images.get(0).getWidth();
            double ratioY = imageViewer.getFitHeight() / images.get(0).getHeight();

            double scaleRatio = 0;
            if(ratioX >= ratioY) 
            {
                scaleRatio = ratioY;
            } 
            else {
                scaleRatio = ratioX;
            }

            width = images.get(0).getWidth() * scaleRatio;

            imageViewer.setX((imageViewer.getFitWidth() - width) / 2);
            //imageViewer.setY((imageViewer.getFitHeight() - h) / 2);
            imageViewer.setImage(images.get(0));
		}
	}
	/**
	 *displays the previous image
	 */		
	public void goback() {
		current--;
		if (current < 0) {
			current = 0;
			return;
		}
		imageViewer.setImage(images.get(current));
	}
	/**
	 *displays the next image
	 */		
	public void goforward() {
		current++;
		if (current == images.size()) {
			current--;
			return;
		}
		imageViewer.setImage(images.get(current));
	}

	/**
	 *logs the user out and goes back to the login screen
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
	 *goes back to the album view screen
	 */		
	public void back() throws IOException {
		//Photos55App.writePhotosApp();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/Photos55/view/album.fxml"));
		Pane root = (Pane)loader.load();
    	Stage stage = (Stage) logoutButton.getScene().getWindow();
    	Scene scene = new Scene(root);
    	albumController photosController = loader.getController();
		photosController.start(stage);
    	stage.setScene(scene);
    	stage.show();	
	}
	/**
	 *quits the application serializing session data
	 */		    
	public void quit() throws IOException, ClassNotFoundException {
    	Photos55App.writePhotosApp();
    	System.exit(0);
	}
}
