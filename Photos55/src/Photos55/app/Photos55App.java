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
/**
 *  Primary Application class
 *  
 *  <code>Photos55App</code> contains the logic and code needed to initialize the entire application.
 *  The Photos55App is able to upload and manage photos from the local filesystem
 *  
 *   
 *   @author Charles Li
 *   @author Max Sun
 *   @version 1.69 Apr 2021
*/
public class Photos55App extends Application {
	/**
	 *  The list of <code>User</code>s belonging to <code>Photos55App</code>
	 */
	public static ArrayList<User> userList = new ArrayList<User>();
	/**
	 *  The current <code>User</code>s logged into <code>Photos55App</code>
	 */
	public static User user;
	
	@Override
	/**
	 *  <code>Photos55App</code> start method.
	 *  Loads contents for <code>Photos55App</code> application.
	 *  @param stage the stage used to set gui scene and pass into other controllers
	 */
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
	/**
	* 
	* <code>Photos55App</code> main method.
	* Starts the app and unserializes any saved session data
	* 
	*/	
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
	/**
	* 
	* Returns the <code>User</code> corresponding to the given username if existing
	* @param username the username to be searched for
	* 
	*/		
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
	/**
	* 
	* Read <code>Photos55App</code> session info from serialization
	*  
	*/	
	public static ArrayList<User> readPhotosApp() throws IOException, ClassNotFoundException
	{
		ObjectInputStream OIS = new ObjectInputStream(
			new FileInputStream("data" + File.separator + "userList"));
		ArrayList<User> userList = (ArrayList<User>)OIS.readObject();
		OIS.close();
		return userList;
	}
	/**
	* 
	* Serialize <code>Photos55App</code> session info
	*  
	*/	
	public static void writePhotosApp() throws IOException, ClassNotFoundException
	{
		ObjectOutputStream OOS = new ObjectOutputStream(
			new FileOutputStream("data" + File.separator + "userList"));
		OOS.writeObject(userList);
		OOS.close();
	}
	
	
}
