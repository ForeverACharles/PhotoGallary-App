package Photos55.view;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import Photos55.app.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class loginController {
	@FXML TextField usernameField;
	@FXML Button loginButton;
    public void start(Stage mainstage) {
		// set up the view controllers

    }
    public ArrayList<String> loadUsers()
	{
		//NOTE: users.txt must be saved at the root project directory of Photos55!!!
		ArrayList<String> result = new ArrayList<String>();
		for(User user : Photos55App.userList)
		{
			result.add(user.getName());
		}
		return result;
    	/*  String path = "users.txt";	
		File songsTXT = new File(path);
		if(songsTXT.isFile())
		{
			
			try 
			{
				ObservableList<String> result = FXCollections.observableArrayList();
				BufferedReader txtReader = new BufferedReader(new FileReader(path));
				if(txtReader != null)
				{
					String currLine;
					while((currLine = txtReader.readLine()) != null)
					{				
						result.add(currLine);
					}
				}
				txtReader.close();
				return result;
				
			} 
			catch (IOException e) 
			{
				System.out.println("Error, reading from txt file");
				return null;
			}
			
		}
		return null;
		*/
	}
    public void login() throws IOException {
    	String username = usernameField.getText();
    	if(username.compareTo("admin")==0) {
	    	FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/Photos55/view/admin.fxml"));
			Pane root = (Pane)loader.load();
	    	Stage stage = (Stage) loginButton.getScene().getWindow();
	    	Scene scene = new Scene(root);
	    	adminController photosController = loader.getController();
			photosController.start(stage);
	    	stage.setScene(scene);
	    	stage.show();
    	}
    	if(loadUsers().contains(username)) {
    		System.out.println("Successful Login");
    	}
    }
}
