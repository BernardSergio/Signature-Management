package Sign.sergio.esigns.controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class ManageSignatureJFXApp extends Application {

@Override
public void start(Stage stage) throws IOException {
	System.out.println("SplashApp:start ");

	// Try to get the URL of the FXML resource
	java.net.URL fxmlUrl = ManageSignatureJFXApp.class.getResource("/Sign/sergio/esigns/splash-view.fxml");
	System.out.println("FXML URL: " + fxmlUrl);

	if (fxmlUrl == null) {
		throw new IOException("FXML file not found at /Sign/sergio/esigns/splash-view.fxml");
	}

	FXMLLoader fxmlLoader = new FXMLLoader(fxmlUrl);
	Parent root = fxmlLoader.load();
	SplashViewController splashViewController = fxmlLoader.getController();
	splashViewController.setStage(stage);
	Scene scene = new Scene(root, 420, 420);
	splashViewController.setSplashScene(scene);
	stage.setTitle("Signature Management!");
	stage.setScene(scene);
	stage.show();
}

}
