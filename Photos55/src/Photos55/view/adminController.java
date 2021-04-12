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

public class adminController {
	
	@FXML ListView<String> users;
	@FXML Button logoutButton;
	@FXML TextField newuser;
	private ObservableList<String> userList;
	
	public void start(Stage mainstage) {
		try {
	        File songFile = new File("users.txt");
	        if (songFile.createNewFile()) {
	          userList = FXCollections.observableArrayList();
	        } 
	        else {
	        	userList = loadUsers();
	        }
	    } 
		catch (IOException e) {
	        System.out.println("Error Loading File.");
	        e.printStackTrace();
	    }
		users.setItems(userList);
	}
	
	public ObservableList<String> loadUsers()
	{
		File dir = new File("users");
		ArrayList<String> users = new ArrayList<String>();
		users.addAll(Arrays.asList(dir.list(
			new FilenameFilter()
			{
				@Override
				public boolean accept(File curr, String user)
				{
					return new File(curr, user).isDirectory();
				}
			}	
		)));
		
		if(!users.contains("stock"))
		{
			users.add("stock");
			File stock = new File("users/stock");
			stock.mkdirs();	
			try
			{
				File gitKeep = new File("users/stock/.gitkeep");
				gitKeep.createNewFile();
			}
			catch (IOException e)
			{
				System.out.println("Error creating stock user");
				e.printStackTrace();
			}
		}
		
		ObservableList<String> result = FXCollections.observableArrayList();
		for(String user : users)
		{
			result.add(user);
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
	
	public void updateFile() {
		
		try {
			FileWriter songWriter = new FileWriter("users.txt");
			for (String user: userList) {
				songWriter.write(user + "\n");
			}
			songWriter.close();
			}
			catch (IOException e) {
				System.out.println("Error writing to file");
				e.printStackTrace();
			}		
		
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
		//updateFile();
		File dir = new File("users/" + user);
		dir.mkdirs();
		userList.add(user);
	}
	
    public void deleteUser() throws IOException {
		int index = users.getSelectionModel().getSelectedIndex();
		if(userList.size() == 0 || index == -1) {
			Alert alert = new Alert(AlertType.ERROR, "List is Empty or Nothing is Selected", ButtonType.OK);
			alert.showAndWait();
			return;
		}
		
		if(userList.get(index).equals("stock"))
		{
			Alert alert = new Alert(AlertType.ERROR, "Cannot remove stock user", ButtonType.OK);
			alert.showAndWait();
			return;
		}
		//updateFile();
		File dir = new File("users/" + userList.get(index));
		dir.delete();
		userList.remove(index);
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
