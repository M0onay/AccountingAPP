package Main;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Controller {
	
	@FXML
	private Button AddButton;
	@FXML
	private Button EditButton;
	@FXML
	private Button GenerateButton;
	@FXML
	private Button back;
	@FXML
	private ComboBox<String> actTitleCB;
	@FXML
	private ComboBox<String> paidby;
	@FXML
	private Label paidbylabel;
	@FXML
	private TextField dateTextField;
	@FXML
	private TextField amountTextField;
	@FXML
	private TextField noteTextField;

	public void AddTransaction(ActionEvent event) {

	    String date = dateTextField.getText();
	    String amount = amountTextField.getText();
	    String note = noteTextField.getText();
	    String accountTitle = actTitleCB.getValue();

	    String Pmethod = null;
	    boolean paymentRequired = false;

	    if (paidby.isVisible()) {
	        paymentRequired = true;
	        Pmethod = paidby.getValue();
	    }

	    try {

	        Transaction transaction = new Transaction(
	            date,
	            accountTitle,
	            amount,
	            note,
	            Pmethod,
	            paymentRequired
	        );

	        System.out.println("New Transaction!");
	        System.out.println("Date: " + transaction.getdate());
	        System.out.println("Account Title: " + transaction.getAccountTitle());
	        System.out.println("Amount: " + transaction.getAmount());
	        System.out.println("Payment Method: " + transaction.getPaymentMethod());
	        System.out.println("Note: " + transaction.getNote());

	        resetTransaction();

	    } catch(IllegalArgumentException ex) {

	        System.out.println(ex.getMessage());
	    }
	}
	
	public void EditTransaction(ActionEvent event) {
		System.out.println("WOW WORKING");
	}
	
	public void GenerateReport(ActionEvent event) {
		System.out.println("TANGINA NAGANA");
	}
	
	@FXML
	public void initialize() {
		
		actTitleCB.getItems().addAll(ActOpt.getMainOptions());		
		
		back.setVisible(false);
		
		actTitleCB.setOnAction(e -> {
			String selected = actTitleCB.getValue();
			Platform.runLater(() -> {
				if("Investment".equals(selected)) {
					back.setVisible(true);
					actTitleCB.setPromptText("Investment Type");
					actTitleCB.getItems().clear();
					actTitleCB.getItems().addAll(ActOpt.getInvestmentOptions());
				}
				if("Revenue/Sales".equals(selected)) {
					back.setVisible(true);
					actTitleCB.setPromptText("Revenue/Sales Type");
					actTitleCB.getItems().clear();
					actTitleCB.getItems().addAll(ActOpt.getRevenueOptions());
				}
				if("Expense".equals(selected)) {
					back.setVisible(true);
					actTitleCB.setPromptText("Expense Type");
					actTitleCB.getItems().clear();
					actTitleCB.getItems().addAll(ActOpt.getExpenseOptions());
					
					paidbylabel.setVisible(true);
					paidby.setVisible(true);
					
					paidby.getItems().clear();
					paidby.getItems().addAll(ActOpt.getPaymentOptions());
				}
				if("Purchase".equals(selected)) {
					back.setVisible(true);
					actTitleCB.setPromptText("Purchase Type");
					actTitleCB.getItems().clear();
					actTitleCB.getItems().addAll(ActOpt.getPurchaseOptions());
					
					paidbylabel.setVisible(true);
					paidby.setVisible(true);
					
					paidby.getItems().clear();
					paidby.getItems().addAll(ActOpt.getPaymentOptions());
				}
			});
		});
	}
	
	public void backTransaction(ActionEvent event) {
		
		actTitleCB.getItems().clear();
		
	}
	
	private void resetTransaction() {
		
		dateTextField.clear();
		amountTextField.clear();
		noteTextField.clear();
		
		actTitleCB.getItems().clear();
		actTitleCB.getItems().addAll(ActOpt.getMainOptions());
		actTitleCB.setValue(null);
		actTitleCB.setPromptText("Select Transaction");
		
		paidby.getItems().clear();
		paidby.setValue(null);
		
		paidby.setVisible(false);
		paidbylabel.setVisible(false);
		
	}
	
}
