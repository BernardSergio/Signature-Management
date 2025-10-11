package Sign.sergio.esigns.controller;
import Sign.sergio.esigns.model.SignatureRequest;
import Sign.sergio.esigns.service.SignatureRequestEService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.stage.Window;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import lombok.Setter;

public class ManageSignatureController extends GenericSignatureController {
	@Setter
	Stage stage;

	@Setter
	Scene createViewScene;

	@Setter
	Scene editViewScene;

	@Setter
	Scene deleteViewScene;

	public ImageView ecommerceImage;
	@FXML
	public Button btnCreate;

	@FXML
	public Button btnEdit;

	@FXML
	public Button btnDelete;

	@FXML
	public Button btnClose;

	@FXML
	public Button imageButton;
	SignatureRequest selectedItem;

	@FXML
	private ListView<SignatureRequest> lvEcommerces;

		public void refresh() {
			SignatureRequest[] signatureRequests = SignatureRequestEService.getService().getAll();
			lvEcommerces.getItems().clear();
			lvEcommerces.getItems().addAll(signatureRequests);
			enableFields(false);
		}

	@Override
	public void init() {
		try {
			refresh();
		}
		catch (Exception e){
			showErrorDialog("Message: ", e.getMessage());
		}
	}

	public void onAction(MouseEvent mouseEvent) {
		GenericSignatureController.selectedItem = lvEcommerces.getSelectionModel().getSelectedItem();
		if(GenericSignatureController.selectedItem == null) {
			return;
		}
		setFields("Manage");
	}
	public void onCreate(ActionEvent actionEvent) throws Exception {
		Node node = ((Node) (actionEvent.getSource()));
		Scene currentScene = node.getScene();
		Window window = currentScene.getWindow();
		window.hide();

		if (createViewScene == null) {
			FXMLLoader fxmlLoader = new FXMLLoader(ManageSignatureJFXApp.class.getResource("/Sign/sergio/esigns/create-sign-view.fxml"));
			Parent root = fxmlLoader.load();
			CreateSignatureController controller = fxmlLoader.getController();
			controller.setStage(stage);
			controller.setManageEcommerceController(this);
			controller.setManageScene(manageScene);
			controller.setSplashScene(splashScene);

			createViewScene = new Scene(root);
		}

		stage.setTitle("Create Signature");
		stage.setScene(createViewScene);
		stage.sizeToScene();         // Automatically size to fit FXML
		stage.setResizable(false);   // Optional
		stage.centerOnScreen();      // ✅ This centers the window
		stage.show();
	}
	public void onEdit(ActionEvent actionEvent) throws Exception {
		if (GenericSignatureController.selectedItem == null) {
			showErrorDialog("Please select a Signature from the list", "Cannot edit");
			return;
		}

		Node node = ((Node) (actionEvent.getSource()));
		Scene currentScene = node.getScene();
		Window window = currentScene.getWindow();
		window.hide();

		if (editViewScene == null) {
			// Load the edit-sign-view.fxml file
			FXMLLoader fxmlLoader = new FXMLLoader(ManageSignatureJFXApp.class.getResource("/Sign/sergio/esigns/edit-sign-view.fxml"));
			Parent root = fxmlLoader.load();

			// Connect to controller
			EditSignatureController controller = fxmlLoader.getController();
			controller.setStage(stage);
			controller.setManageEcommerceController(this);
			controller.setManageScene(manageScene);
			controller.setSplashScene(splashScene);

			// Set a clean, centered scene size (like Manage View)
			editViewScene = new Scene(root, 800, 600); // Feel free to adjust width/height
		}

		stage.setTitle("Edit Signature");
		stage.setScene(editViewScene);
		stage.show();
	}
	public void onDelete(ActionEvent actionEvent)  throws Exception {
		if(GenericSignatureController.selectedItem == null){
			showErrorDialog("Please select an Signature from the list", "Cannot delete");
		return;
		}
		Node node = ((Node) (actionEvent.getSource()));
		Scene currentScene = node.getScene();
		Window window = currentScene.getWindow();
		window.hide();
		if(deleteViewScene == null){
			FXMLLoader fxmlLoader = new FXMLLoader(ManageSignatureJFXApp.class.getResource("/Sign/sergio/esigns/delete-sign-view.fxml"));
			Parent root = fxmlLoader.load();
			DeleteSignatureController controller = fxmlLoader.getController();
			controller.setStage(stage);
			deleteViewScene = new Scene(root, 300, 720);
			controller.setManageEcommerceController(this);
			controller.setManageScene(manageScene);
			controller.setSplashScene(splashScene);
		}
		stage.setTitle("Delete Signature");
		stage.setScene(deleteViewScene);
		stage.show();
	}
	public void onClose(ActionEvent actionEvent) {
		super.onClose(actionEvent);
	}
}
