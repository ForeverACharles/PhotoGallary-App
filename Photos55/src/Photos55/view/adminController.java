package Photos55.view;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


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

public class adminController {
	@FXML ListView<String> users;
	@FXML Button logoutButton;
	@FXML TextField newuser;
	private ObservableList<String> Userlist;
	public ObservableList<String> loadUsers()
	{
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
		return null;
	}
	public void start(Stage mainstage) {
		try {
	        File songFile = new File("users.txt");
	        if (songFile.createNewFile()) {
	          Userlist = FXCollections.observableArrayList();
	        } else {
	  		Userlist = loadUsers();
	        }
	      } catch (IOException e) {
	        System.out.println("Error Loading File.");
	        e.printStackTrace();
	      }
		users.setItems(Userlist);

	}
	public void updateFile() {
		try {
			FileWriter songWriter = new FileWriter("users.txt");
			for (String user: Userlist) {
				songWriter.write(user + "\n");
			}
			songWriter.close();
			}
			catch (IOException e) {
				System.out.println("Error writing to file");
				e.printStackTrace();
			}		
	}
	//create and delete directories!!!!
    public void deleteuser() throws IOException {
		int index = users.getSelectionModel().getSelectedIndex();
		if(Userlist.size()==0 || index == -1) {
			Alert alert = new Alert(AlertType.ERROR, "List is Empty/Nothing Selected", ButtonType.OK);
			alert.showAndWait();
			return;
		}
		Userlist.remove(index);
		updateFile();
    }
    public void newuser() throws IOException {
		String user = newuser.getText();
		if(user.compareTo("admin")==0) {
			Alert alert = new Alert(AlertType.ERROR, "Cannot name a user admin", ButtonType.OK);
			alert.showAndWait();
			return;
		}
		if(Userlist.contains(user)) {
			Alert alert = new Alert(AlertType.ERROR, "User already exists", ButtonType.OK);
			alert.showAndWait();
			return;
		}
    	Userlist.add(user);
		updateFile();
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
