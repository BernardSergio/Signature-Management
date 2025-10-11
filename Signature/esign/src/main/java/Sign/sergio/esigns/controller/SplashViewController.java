package Sign.sergio.esigns.controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.Window;
import lombok.Setter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SplashViewController implements Initializable {

	@Setter
	Stage stage;
	@Setter
	Scene manageScene = null;
	@Setter
	Scene splashScene = null;
	public ImageView imgHug;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
	}

	public void onClicked(ActionEvent actionEvent) throws IOException {
		System.out.println("SplashController :onClicked ");
		Node node = ((Node) (actionEvent.getSource()));
		Scene currentScene = node.getScene();
		Window window = currentScene.getWindow();
		window.hide();

		if (manageScene == null) {
			FXMLLoader fxmlLoader = new FXMLLoader(ManageSignatureJFXApp.class.getResource("/Sign/sergio/esigns/manage-sign-view.fxml"));
			Parent root = fxmlLoader.load();
			ManageSignatureController controller = fxmlLoader.getController();
			controller.setStage(stage);

			// ⬇️ Use Scene without setting fixed width/height
			manageScene = new Scene(root); // ✅ dynamically sized based on screen

			controller.setSplashScene(splashScene);
			controller.setManageScene(manageScene);
		}

		stage.setTitle("Signature Management");

		// ⬇️ Maximize the window (like a real full-screen app)
		stage.setScene(manageScene);
		stage.setMaximized(true); // ✅ Important: makes your BorderPane stretch across the screen

		stage.show();
	}
}
