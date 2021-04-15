package Photos55.view;

/**
 *  Gui controller for user Admin services
 *  
 *  <code>adminController</code> contains logic to handle Admin services,
 *  which include creating or deleting <code>User</code>s from the application
 *   
 *   @author Charles Li
 *   @author Max Sun
 *   @version 1.69 Apr 2021
*/

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
	
	/**
	 * List of <code>User</code>s who are stored in the application
	*/
	private ObservableList<String> userList;
	
	/**
	 *  <code>adminController</code> start method.
	 *  Loads <code>User</code> list for Admin to manage.
	 *  @param mainstage the stage used to set gui scene and pass into other controllers
	 */
	public void start(Stage mainstage) {
	    userList = loadUsers();
		users.setItems(userList);
	}
	
	/**
	 *  Loads <code>User</code> list for Admin to manage
	 */
	public ObservableList<String> loadUsers()
	{
		ObservableList<String> result = FXCollections.observableArrayList();
		for(User user : Photos55App.userList)
		{
			result.add(user.getName());
		}
		return result;
	}
	
	/**
	 *  Adds a <code>User</code> to the list of Users. Reports error
	 *  if User name not entered or User already exists
	 */
	public void addUser() throws IOException {
		String user = newuser.getText();
		
		if(user.isEmpty()) 
		{
			Alert alert = new Alert(AlertType.ERROR, "Cannot create user with an empty name", ButtonType.OK);
			alert.showAndWait();
			return;
		}
		
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
		newuser.clear();
		
	    userList = loadUsers();
	    users.setItems(userList);
	}
	
	/**
	 *  Deletes a <code>User</code> from the list of Users. Reports error
	 *  if User does not exist
	 */
    public void deleteUser() throws IOException {
		int index = users.getSelectionModel().getSelectedIndex();
		if(userList.size() == 0 || index == -1) {
			Alert alert = new Alert(AlertType.ERROR, "List is empty or no user selected", ButtonType.OK);
			alert.showAndWait();
			return;
		}
		Photos55App.userList.remove(index);
		
		userList = loadUsers();
		users.setItems(userList);
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
	 *  Saves the session and closes the application
	 */
    public void quit() throws IOException, ClassNotFoundException {
    	Photos55App.writePhotosApp();
    	System.exit(0);
    }
}
