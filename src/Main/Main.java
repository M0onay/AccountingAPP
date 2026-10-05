//PENIS
//SORRY FOR THE MESSY CODE

package Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
    	try {
    		Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
	        Scene scene = new Scene(root);
	        stage.setTitle("My JavaFX App");
	        stage.setScene(scene);
	        stage.show();
    	} catch(Exception e) {
    			e.printStackTrace();
    		}
    	}

    public static void main(String[] args) {
    	launch();
    }
}
