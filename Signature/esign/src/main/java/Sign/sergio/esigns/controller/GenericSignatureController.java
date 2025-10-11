package Sign.sergio.esigns.controller;
import Sign.sergio.esigns.model.SignatureRequest;
import Sign.sergio.esigns.model.SignableEDocument;
import Sign.sergio.esigns.service.SignableDocumentEService;
import Sign.sergio.esigns.model.DocumentECategory;
import Sign.sergio.esigns.service.DocumentCategoryEService;
import Sign.sergio.esigns.model.SignatureEStatus;
import Sign.sergio.esigns.service.SignatureStatusEService;
import Sign.sergio.esigns.model.DocumentEStorage;
import Sign.sergio.esigns.service.DocumentStorageEService;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.Window;
import lombok.Setter;
import javafx.util.StringConverter;
import java.net.URL;
import javafx.scene.Node;
import javafx.event.ActionEvent;

import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import java.util.Locale;

public class GenericSignatureController implements Initializable{
	@Setter
	CreateSignatureController createSignatureController;

	@Setter
	DeleteSignatureController deleteSignatureController;

	@Setter
	EditSignatureController editSignatureController;

	@Setter
	ManageSignatureController manageEcommerceController;

	@Setter
	Stage stage;

	@Setter
	Scene splashScene;

	@Setter
	Scene manageScene;

	@Setter
	public ListView<SignatureRequest> lvEcommerces;

	@Setter
	public static SignatureRequest selectedItem;
	public TextField txtId;
	public TextField txtName;
	public TextField txtDescription;
	public ComboBox<SignableEDocument> cmbProduct;
	public TextField txtProductName;
	public ComboBox<DocumentECategory> cmbCategory;
	public TextField txtCategoryName;
	public ComboBox<SignatureEStatus> cmbStatus;
	public TextField txtStatusName;
	public TextField txtQuantityAvailable;
	public ComboBox<DocumentEStorage> cmbInventory;
	public TextField txtInventoryName;
	public TextField txtPrice;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		SignableEDocument[] signableEDocuments =  (SignableEDocument[]) SignableDocumentEService.getService().getAll();
		cmbProduct.getItems().addAll(signableEDocuments);
		StringConverter<SignableEDocument> productConverter = new StringConverter<SignableEDocument>() {
			@Override
			public String toString(SignableEDocument product) {
			if(product==null)
				return "";
			else
				return product.toString();
			}
			@Override
			public SignableEDocument fromString(String s) {
				if(!s.isEmpty()){
					for (SignableEDocument product : signableEDocuments) {
						if (s.equals(product.getName())){
							return product;
						}
					}
				}
				return null;
			}
		};
		cmbProduct.setConverter(productConverter);
		DocumentECategory[] documentCategories =  (DocumentECategory[]) DocumentCategoryEService.getService().getAll();
		cmbCategory.getItems().addAll(documentCategories);
		StringConverter<DocumentECategory> categoryConverter = new StringConverter<DocumentECategory>() {
			@Override
			public String toString(DocumentECategory category) {
			if(category==null)
				return "";
			else
				return category.toString();
			}
			@Override
			public DocumentECategory fromString(String s) {
				if(!s.isEmpty()){
					for (DocumentECategory category : documentCategories) {
						if (s.equals(category.getName())){
							return category;
						}
					}
				}
				return null;
			}
		};
		cmbCategory.setConverter(categoryConverter);
		SignatureEStatus[] statusses =  (SignatureEStatus[]) SignatureStatusEService.getService().getAll();
		cmbStatus.getItems().addAll(statusses);
		StringConverter<SignatureEStatus> statusConverter = new StringConverter<SignatureEStatus>() {
			@Override
			public String toString(SignatureEStatus status) {
			if(status==null)
				return "";
			else
				return status.toString();
			}
			@Override
			public SignatureEStatus fromString(String s) {
				if(!s.isEmpty()){
					for (SignatureEStatus status : statusses) {
						if (s.equals(status.getName())){
							return status;
						}
					}
				}
				return null;
			}
		};
		cmbStatus.setConverter(statusConverter);
		DocumentEStorage[] documentEStorages =  (DocumentEStorage[]) DocumentStorageEService.getService().getAll();
		cmbInventory.getItems().addAll(documentEStorages);
		StringConverter<DocumentEStorage> inventoryConverter = new StringConverter<DocumentEStorage>() {
			@Override
			public String toString(DocumentEStorage inventory) {
			if(inventory==null)
				return "";
			else
				return inventory.toString();
			}
			@Override
			public DocumentEStorage fromString(String s) {
				if(!s.isEmpty()){
					for (DocumentEStorage inventory : documentEStorages) {
						if (s.equals(inventory.getName())){
							return inventory;
						}
					}
				}
				return null;
			}
		};
		cmbInventory.setConverter(inventoryConverter);
		init();
	}
	protected void init(){
		System.out.println("Invoked from Generic Controller");
	}
	protected SignatureRequest toObject(boolean isEdit){
		SignatureRequest signatureRequest = new SignatureRequest();
		try {
			if(isEdit) {
				signatureRequest.setId(Integer.parseInt(txtId.getText()));
			}
			signatureRequest.setDescription(txtDescription.getText());
			SignableEDocument signableEDocument = cmbProduct.getSelectionModel().getSelectedItem();
			DocumentECategory documentECategory = cmbCategory.getSelectionModel().getSelectedItem();
			signatureRequest.setCategoryId(documentECategory.getId());
			signatureRequest.setCategoryName(documentECategory.getName());
			SignatureEStatus signatureEStatus = cmbStatus.getSelectionModel().getSelectedItem();
			signatureRequest.setStatusId(signatureEStatus.getId());
			signatureRequest.setStatusName(signatureEStatus.getName());
			DocumentEStorage documentEStorage = cmbInventory.getSelectionModel().getSelectedItem();
		}catch (Exception e){
			showErrorDialog("Error" ,e.getMessage());
		}
		return signatureRequest;
	}
	protected void setFields(String action){
		String formattedDate;
		SignatureRequest signatureRequest = GenericSignatureController.selectedItem;
		SimpleDateFormat formatter = new SimpleDateFormat("mm/dd/yyyy", Locale.ENGLISH);
		txtId.setText(Integer.toString(signatureRequest.getId()));
		txtDescription.setText(signatureRequest.getDescription());
		if(action.equals("Create") || action.equals("Edit")){
			cmbProduct.setVisible(true);
			txtProductName.setVisible(false);
		}
		else{
			cmbProduct.setVisible(false);
			txtProductName.setVisible(true);
		}
		DocumentECategory documentECategory = DocumentCategoryEService.getService().get(signatureRequest.getCategoryId());
		cmbCategory.getSelectionModel().select(documentECategory);
		if(action.equals("Create") || action.equals("Edit")){
			cmbCategory.setVisible(true);
			txtCategoryName.setVisible(false);
			cmbCategory.getSelectionModel().select(documentECategory);
		}
		else{
			cmbCategory.setVisible(false);
			txtCategoryName.setVisible(true);
			txtCategoryName.setText(documentECategory.getName());
		}
		txtCategoryName.setText(signatureRequest.getCategoryName());
		SignatureEStatus signatureEStatus = SignatureStatusEService.getService().get(signatureRequest.getStatusId());
		cmbStatus.getSelectionModel().select(signatureEStatus);
		if(action.equals("Create") || action.equals("Edit")){
			cmbStatus.setVisible(true);
			txtStatusName.setVisible(false);
			cmbStatus.getSelectionModel().select(signatureEStatus);
		}
		else{
			cmbStatus.setVisible(false);
			txtStatusName.setVisible(true);
			txtStatusName.setText(signatureEStatus.getName());
		}
		txtStatusName.setText(signatureRequest.getStatusName());
		if(action.equals("Create") || action.equals("Edit")){
			cmbInventory.setVisible(true);
			txtInventoryName.setVisible(false);
		}
		else{
			cmbInventory.setVisible(false);
			txtInventoryName.setVisible(true);
		}

	}

	protected void clearFields(String action){
		txtId.setText("");
		//txtDescription.setText("");
		cmbProduct.getSelectionModel().clearSelection();
		txtProductName.setText("");
		if(action.equals("Create") || action.equals("Edit")){
			cmbProduct.setVisible(true);
			txtProductName.setVisible(false);
		}
		else{
			cmbProduct.setVisible(false);
			txtProductName.setVisible(true);
		}
		cmbCategory.getSelectionModel().clearSelection();
		txtCategoryName.setText("");
		if(action.equals("Create") || action.equals("Edit")){
			cmbCategory.setVisible(true);
			txtCategoryName.setVisible(false);
		}
		else{
			cmbCategory.setVisible(false);
			txtCategoryName.setVisible(true);
		}
		cmbStatus.getSelectionModel().clearSelection();
		txtStatusName.setText("");
		if(action.equals("Create") || action.equals("Edit")){
			cmbStatus.setVisible(true);
			txtStatusName.setVisible(false);
		}
		else{
			cmbStatus.setVisible(false);
			txtStatusName.setVisible(true);
		}
		cmbInventory.getSelectionModel().clearSelection();
		txtInventoryName.setText("");
		if(action.equals("Create") || action.equals("Edit")){
			cmbInventory.setVisible(true);
			txtInventoryName.setVisible(false);
		}
		else{
			cmbInventory.setVisible(false);
			txtInventoryName.setVisible(true);
		}
	}

	protected void enableFields(boolean enable){
		txtDescription.editableProperty().set(enable);
		cmbProduct.editableProperty().set(enable);
		cmbCategory.editableProperty().set(enable);
		cmbStatus.editableProperty().set(enable);
		cmbInventory.editableProperty().set(enable);
	}

	public int getId(){
		return Integer.parseInt(txtId.getText());
	}

	protected void showErrorDialog(String message, String expandedMessage){
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setHeaderText(message);
		alert.getDialogPane().setExpandableContent(new ScrollPane(new TextArea(expandedMessage)));
		alert.showAndWait();
	}
	public void onBack(ActionEvent actionEvent) {
		Node node = ((Node) (actionEvent.getSource()));
		Window window = node.getScene().getWindow();
		window.hide();
		stage.setScene(manageScene);
		stage.show();
	}
	public void onClose(ActionEvent actionEvent) {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Exit and loose changes? " , ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
		alert.showAndWait();
		if (alert.getResult() == ButtonType.YES) {
			Platform.exit();
		}
	}
	LocalDate toLocalDate(Date date){
		Instant instant = date.toInstant();
		ZoneId z = ZoneId.of("Singapore");
		ZonedDateTime zdt = instant.atZone( z );
		return zdt.toLocalDate();
	}
	protected Date toDate(LocalDate ld){
		ZoneId z = ZoneId.of("Singapore");
		ZonedDateTime zdt = ld.atStartOfDay(z);
		Instant instant  = zdt.toInstant();
		return Date.from(instant);
	}
}

