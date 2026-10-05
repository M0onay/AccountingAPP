package Main;

import java.io.IOException;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TBController{
	
	@FXML
	private VBox trialBalanceContainer;
	@FXML
	private Label paidbylabel;
	@FXML
	private Label ErrorLabel;
	@FXML
	private Button AddButton;
    @FXML
    private Button journalButton;
    @FXML
    private Button ledgerButton;
    @FXML
    private Button GenerateButton;
    @FXML
    private Button back;
    @FXML
    private ComboBox<String> actTitleCB;
    @FXML
    private ComboBox<String> paidby;
    @FXML
    private TextField dateTextField;
    @FXML
    private TextField amountTextField;
    @FXML
    private TextField noteTextField;
    private String transactionType;
    private Journal journal = AccountingData.getJournal();
    private Ledger ledger = AccountingData.getLedger();
    
    public void AddTransaction(ActionEvent event) {
    	
    	String date = dateTextField.getText();
    	String amount = amountTextField.getText();
    	String note = noteTextField.getText();
    	String accountTitle = actTitleCB.getValue();
    	
    	String paymentMethod = null;
    	boolean paymentRequired = false;
    	
    	if(paidby.isVisible()) {
    		paymentRequired = true;
    		paymentMethod = paidby.getValue();
    	}
    	
    	try {
    		
    		Transaction transaction = new Transaction(date, transactionType, accountTitle, amount, note, paymentMethod, paymentRequired);
    		
    		JournalEntry entry = Journalizing.journalize(transaction);
    		
    		journal.addEntry(entry);
    		ledger.post(entry);
    		
    		back.setVisible(false);
			back.setManaged(false);
			
			paidby.setVisible(false);
			paidby.setManaged(false);
			paidbylabel.setVisible(false);
			paidbylabel.setManaged(false);
    		
    		displayTrialBalance();
    		resetTransaction();
    		
    	} catch(IllegalArgumentException ex) {
    		showError(ex.getMessage());
    	}
    	
    }
    
    public void DeleteTransaction(ActionEvent event) {
        System.out.println("EDIT TRANSACTION FROM LEDGER");
    }
 
	public void GenerateReport(ActionEvent event) {
		PDF_maker_itext pdf =new PDF_maker_itext();
		pdf.generate(journal.genEntries(),ledger);
	}
	
	@FXML
	public void initialize() {
		
		actTitleCB.getItems().addAll(ActOpt.getMainOptions());
		
		back.setVisible(false);
		back.setManaged(false);
		 
		paidby.setVisible(false);
		paidby.setManaged(false);
		paidbylabel.setVisible(false);
		paidbylabel.setManaged(false);
		
		actTitleCB.setOnAction(e -> {
			 String selected = actTitleCB.getValue();
			 if(selected == null) {
				 return;
			 }
			 Platform.runLater(() -> {
				 if("Investment".equals(selected)) {
					 transactionType = "Investment";
					 back.setVisible(true);
					 back.setManaged(true);
					 actTitleCB.setPromptText("Investment Type");
					 actTitleCB.getItems().clear();
					 actTitleCB.getItems().addAll(ActOpt.getInvestmentOptions());
				 }
				 if("Revenue/Sales".equals(selected)) {
					 transactionType = "Revenue/Sales";
					 back.setVisible(true);
					 back.setManaged(true);
					 actTitleCB.setPromptText("Revenue/Sales Type");
					 actTitleCB.getItems().clear();
					 actTitleCB.getItems().addAll(ActOpt.getRevenueOptions());
				 }
				 if("Expense".equals(selected)) {
					 transactionType = "Expense";
					 back.setVisible(true);
					 back.setManaged(true);
					 actTitleCB.setPromptText("Expense Type");
					 actTitleCB.getItems().clear();
					 actTitleCB.getItems().addAll(ActOpt.getExpenseOptions());
					 
					 paidbylabel.setVisible(true);
					 paidbylabel.setManaged(true);
					 paidby.setVisible(true);
					 paidby.setManaged(true);
					 
					 paidby.getItems().clear();
					 paidby.getItems().addAll(ActOpt.getPaymentOptions());
				 }
				 if("Purchase".equals(selected)) {
					 transactionType = "Purchase";
					 back.setVisible(true);
					 back.setManaged(true);
					 actTitleCB.setPromptText("Purchase Type");
					 actTitleCB.getItems().clear();
					 actTitleCB.getItems().addAll(ActOpt.getPurchaseOptions());
					 
					 paidbylabel.setVisible(true);
					 paidbylabel.setManaged(true);
					 paidby.setVisible(true);
					 paidby.setManaged(true);
					 
					 paidby.getItems().clear();
					 paidby.getItems().addAll(ActOpt.getPaymentOptions());
				 }
				 if ("Drawings".equals(selected)) {
	                    transactionType = "Drawings";
	                    back.setVisible(true);
						back.setManaged(true);
	                }
				 if ("Collect Accounts Receivable".equals(selected)) {
	                    transactionType = "Collect Accounts Receivable";
	                    back.setVisible(true);
						back.setManaged(true);
	                }
				 if ("Pay Accounts Payable".equals(selected)) {
	                    transactionType = "Pay Accounts Payable";
	                    back.setVisible(true);
						back.setManaged(true);
	                }
				 if ("Borrow Money".equals(selected)) {
	                    transactionType = "Borrow Money";
	                    back.setVisible(true);
						back.setManaged(true);
	                }
			 });
		 });
		 
		 displayTrialBalance();
		
	}
	
	private void displayTrialBalance() {

		trialBalanceContainer.getChildren().clear();

		TableView<TrialBalanceRow> table = new TableView<>();
		
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		TableColumn<TrialBalanceRow, String> accountColumn = new TableColumn<>("ACCOUNT TITLE");

		TableColumn<TrialBalanceRow, Number> debitColumn = new TableColumn<>("DEBIT");

		TableColumn<TrialBalanceRow, Number> creditColumn = new TableColumn<>("CREDIT");

		 accountColumn.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getAccountTitle()));

		 debitColumn.setCellValueFactory(cell -> new javafx.beans.property.SimpleDoubleProperty(cell.getValue().getDebit()));

		 creditColumn.setCellValueFactory(cell -> new javafx.beans.property.SimpleDoubleProperty(cell.getValue().getCredit()));

		 table.getColumns().addAll(accountColumn, debitColumn, creditColumn);

		 ObservableList<TrialBalanceRow> rows = FXCollections.observableArrayList();

		 double grandTotalDebit = 0;
		 double grandTotalCredit = 0;
		 
		 for(String accountName : ledger.getAccounts().keySet()) {
			 double totalDebit = 0;
			 double totalCredit = 0;
			 for(LedgerEntry entry : ledger.getAccounts().get(accountName)) {
				 totalDebit += entry.getDebit();
				 totalCredit += entry.getCredit();
			 }
			 double balance = totalDebit - totalCredit;
			 if(balance > 0) {
				 rows.add(new TrialBalanceRow(accountName, balance, 0));
				 grandTotalDebit += balance;
			 }
			 else if(balance < 0) {
				 rows.add(new TrialBalanceRow(accountName, 0, Math.abs(balance)));
				 grandTotalCredit += Math.abs(balance);
			 }
		 }

		 rows.add(new TrialBalanceRow("TOTAL",grandTotalDebit,grandTotalCredit));

		 table.setItems(rows);

		 table.setPrefHeight(400);

		 table.setPrefWidth(600);

		 trialBalanceContainer.getChildren().add(table);
	}
	
	public void backTransaction(ActionEvent event) {
		
		System.out.println("BACK BUTTON CLICKED!!!");
		
		actTitleCB.getItems().clear();
		actTitleCB.getItems().addAll(ActOpt.getMainOptions());
		
		actTitleCB.setValue(null);
		actTitleCB.setPromptText("Select Transaction");
		
		paidby.getItems().clear();
		paidby.setValue(null);
		
		paidby.setVisible(false);
		paidbylabel.setVisible(false);
		
		back.setVisible(false);
		back.setManaged(false);
		
	}
	
	private void resetTransaction() {
		
		dateTextField.clear();
		amountTextField.clear();
		noteTextField.clear();
		
		actTitleCB.getItems().clear();
		actTitleCB.getItems().addAll(ActOpt.getMainOptions());
		
		actTitleCB.setValue(null);
		
		paidby.getItems().clear();
		paidby.setValue(null);
		
		paidby.setVisible(false);
		paidby.setManaged(false);
		
		transactionType = null;
		
	}
	
	@FXML
	 public void openJournal(ActionEvent event) throws IOException {
		 
			 FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
			 
			 Parent root = loader.load();
			 
			 Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
			 
			 stage.getScene().setRoot(root);
			 
			 stage.show();
			 
	}
	
	@FXML
	public void openLedger(ActionEvent event) throws IOException{
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ledger.fxml"));
		
		Parent root = loader.load();
		
		Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
		
		stage.getScene().setRoot(root);
		
		stage.show();
		
	}
	
	private void showError(String message) {

	    ErrorLabel.setText(message);
	    ErrorLabel.setVisible(true);
	    ErrorLabel.setManaged(true);

	}
	
	private void hideError() {

	    ErrorLabel.setText("");
	    ErrorLabel.setVisible(false);
	    ErrorLabel.setManaged(false);

	}

}
