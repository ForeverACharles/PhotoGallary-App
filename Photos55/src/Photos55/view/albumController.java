package Photos55.view;

import java.io.IOException;
import java.io.InputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import Photos55.app.Photo;
import Photos55.app.Photos55App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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
import java.util.ArrayList;

public class albumController {
	@FXML Button logoutButton;
	@FXML GridPane imageGrid;
	@FXML ImageView currentView;
	@FXML Text dateField;
	@FXML TextField captionField;
	Photo clickedPhoto = null;
	Stage mainstage;
	public void clicked(Photo photo, Image image) throws FileNotFoundException {
		clickedPhoto = photo;
		System.out.println(image);
		currentView.setImage(image);
		dateField.setText(photo.printDate());
		captionField.setText(photo.getCaption());
	}
	public void makeGrid() throws FileNotFoundException {
		ArrayList<Photo> photoList = userController.viewAlbum.getPhotos();
		imageGrid.getChildren().clear();
		ColumnConstraints column1 = new ColumnConstraints();
		column1.setPercentWidth(25);
		ColumnConstraints column2 = new ColumnConstraints();
		column2.setPercentWidth(25);
		ColumnConstraints column3 = new ColumnConstraints();
		column3.setPercentWidth(25);
		ColumnConstraints column4 = new ColumnConstraints();
		column4.setPercentWidth(25);
		imageGrid.getColumnConstraints().clear();
		imageGrid.getColumnConstraints().addAll(column1, column2,column3,column4);
		imageGrid.getRowConstraints().clear();
		int row = 0;
		int col = 0;
		for (Photo photo: photoList) {
				//photo.reCaption(""+ row);
		      InputStream stream = new FileInputStream(photo.getPath());
		      Image image = new Image(stream);
		      ImageView imageView = new ImageView();
		      //Setting image to the image view
		      imageView.setImage(image);
		      imageView.setOnMouseClicked(e->{
				try {
					clicked(photo,image);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			});
		      //Setting the image view parameters
		      imageView.setFitWidth(75);
		      imageView.setFitHeight(75);
		      imageGrid.add(imageView, col, 2*row);
		      Text t = new Text(10,20, photo.getCaption());
		      imageGrid.add(t, col, 2*row+1);
		      col++;
		      if(col == 4) {
		    	  col = 0;
		    	  row++;
		      }
		}
		mainstage.show();
	}
	public void start(Stage stage) throws FileNotFoundException
	{
		mainstage = stage;
		makeGrid();

	}
	public void changeCaption() throws FileNotFoundException {
		clickedPhoto.reCaption(captionField.getText());
		makeGrid();
	}
	
	public void addPhoto() throws FileNotFoundException {
		FileChooser chooser = new FileChooser();
		chooser.getExtensionFilters().add(new ExtensionFilter("Pictures", "*.jpg","*.png","*.bmp","*.gif"));
        File file = chooser.showOpenDialog(logoutButton.getScene().getWindow());
        if (file != null) {
        	Photo newphoto = new Photo(file.toString(), Instant.ofEpochMilli(file.lastModified()).atZone(ZoneId.systemDefault()).toLocalDate());
            System.out.println(newphoto.getPath());
            System.out.println(newphoto.printDate());
            userController.viewAlbum.getPhotos().add(newphoto);
        }
        makeGrid();
	}
	public void removePhoto() throws FileNotFoundException{
		userController.viewAlbum.getPhotos().remove(clickedPhoto);
		makeGrid();
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
