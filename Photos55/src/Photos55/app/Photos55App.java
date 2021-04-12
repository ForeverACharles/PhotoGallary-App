package Photos55.app;

import javafx.application.Application;
import javafx.collections.ObservableList;

import java.io.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import Photos55.view.loginController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Photos55App extends Application implements Serializable {

	public static Photos55App app;
	public static ArrayList<User> userList;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
			
		// TODO Auto-generated method stub
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/Photos55/view/Login.fxml"));
		Pane root = (Pane)loader.load();
		loginController photosController = loader.getController();
		photosController.start(primaryStage);

		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show(); 
	}
	
	public static void main(String[] args) {
		
		File data = new File("data");
    	if(!data.exists())
    	{
    		data.mkdirs();
    	}
		
		Photos55App app = new Photos55App();
		try
		{
			app = readPhotosApp();
		}
		catch(Exception e)
		{
			try
			{
				writePhotosApp(app);
			}
			catch (Exception f)
			{
				
			}
		}
		
		// TODO Auto-generated method stub
		launch(args);
		
		System.out.println("app quit, write to App");
	}
	
	public static Photos55App readPhotosApp() throws IOException, ClassNotFoundException
	{
		ObjectInputStream OIS = new ObjectInputStream(
			new FileInputStream("data" + File.separator + "appSession"));
		return (Photos55App)OIS.readObject();
	}
	
	public static void writePhotosApp(Photos55App app) throws IOException, ClassNotFoundException
	{
		ObjectOutputStream OOS = new ObjectOutputStream(
			new FileOutputStream("data" + File.separator + "appSession"));
		OOS.writeObject(app);
	}
	
	
}
