package Photos55.app;

import javafx.application.Application;

import Photos55.view.loginController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;



public class Photos55App extends Application{
	@Override
	public void start(Stage primaryStage)
			throws Exception {
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
		// TODO Auto-generated method stub
		launch(args);
	
	}
}
