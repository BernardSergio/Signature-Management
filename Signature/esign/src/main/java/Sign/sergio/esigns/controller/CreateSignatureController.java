package Sign.sergio.esigns.controller;
import Sign.sergio.esigns.model.SignatureRequest;
import Sign.sergio.esigns.service.SignatureRequestEService;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Window;
import javafx.scene.image.ImageView;

public class CreateSignatureController extends GenericSignatureController {
	public ImageView imgEcommerce;
	@Override
	public void init() {
		clearFields("Edit");
		enableFields(true);
	}
	public void onSubmit(ActionEvent actionEvent) {
		try {
			SignatureRequest signatureRequest = toObject(false);
			SignatureRequest newSignatureRequest = SignatureRequestEService.getService().create(signatureRequest);
			manageEcommerceController.refresh();
			Node node = ((Node) (actionEvent.getSource()));
			Window window = node.getScene().getWindow();
			window.hide();
			stage.setTitle("Manage Signature");
			stage.setScene(manageScene);
			stage.show();
		}
		catch (Exception e){
			showErrorDialog("Error encountered creating signature", e.getMessage());
		}
	}
	public void onClose(ActionEvent actionEvent) {
		super.onClose(actionEvent);
	}
}
