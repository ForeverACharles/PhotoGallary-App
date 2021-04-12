package Photos55.view;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import Photos55.app.*;

public class adminController {
	
	@FXML ListView<String> users;
	@FXML Button logoutButton;
	@FXML TextField newuser;
	private ObservableList<String> userList;
	
	public void start(Stage mainstage) {
	    userList = loadUsers();
		users.setItems(userList);
	}
	
	public ObservableList<String> loadUsers()
	{
		ObservableList<String> result = FXCollections.observableArrayList();
		for(User user : Photos55App.userList)
		{
			result.add(user.getName());
		}
		return result;
			
		/*
		//NOTE: users.txt must be saved at the root project directory of Photos55!!!
		String path = "users.txt";	
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
		*/
	}
	
	public void addUser() throws IOException {
		String user = newuser.getText();
		if(user.equals("admin")) {
			Alert alert = new Alert(AlertType.ERROR, "Cannot name a user admin", ButtonType.OK);
			alert.showAndWait();
			return;
		}
		
		if(userList.contains(user)) {
			Alert alert = new Alert(AlertType.ERROR, "User already exists", ButtonType.OK);
			alert.showAndWait();
			return;
		}
		Photos55App.userList.add(new User(user));
	    userList = loadUsers();
	}
	
    public void deleteUser() throws IOException {
		int index = users.getSelectionModel().getSelectedIndex();
		if(userList.size() == 0 || index == -1) {
			Alert alert = new Alert(AlertType.ERROR, "List is Empty or Nothing is Selected", ButtonType.OK);
			alert.showAndWait();
			return;
		}
		//updateFile();
		Photos55App.userList.remove(index);
		userList = loadUsers();
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
}
