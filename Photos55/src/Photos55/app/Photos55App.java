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

public class Photos55App extends Application {

	public static ArrayList<User> userList = new ArrayList<User>();
	public static User user;
	
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
		
		try
		{
			userList = readPhotosApp();
		}
		catch(Exception e)
		{
			try
			{
				writePhotosApp();
			}
			catch (Exception f)
			{
				
			}
		}
		
		// TODO Auto-generated method stub
		launch(args);
		
		System.out.println("app quit, write to App");
	}
	
	public static User getUser(String username)
	{
		for(User user : userList)
		{
			if(user.getName().equals(username))
			{
				return user;
			}
		}
		return null;
	}
	
	public static ArrayList<User> readPhotosApp() throws IOException, ClassNotFoundException
	{
		ObjectInputStream OIS = new ObjectInputStream(
			new FileInputStream("data" + File.separator + "userList"));
		ArrayList<User> userList = (ArrayList<User>)OIS.readObject();
		OIS.close();
		return userList;
	}
	public static void writePhotosApp() throws IOException, ClassNotFoundException
	{
		ObjectOutputStream OOS = new ObjectOutputStream(
			new FileOutputStream("data" + File.separator + "userList"));
		OOS.writeObject(userList);
		OOS.close();
	}
	
	
}
