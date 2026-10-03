package Main;

import java.io.IOException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;


public class LedgerController {
	
	@FXML
	private Button AddButton;
	@FXML
	private Button GenerateButton;
	@FXML
	private Button journalButton;
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
	@FXML
	private VBox ledgerContainer;
	private String transactionType;
	private Journal journal = AccountingData.getJournal();
	private Ledger ledger = AccountingData.getLedger();
	
	public void AddTransaction (ActionEvent event) {
		
		String date = dateTextField.getText();
		String amount = amountTextField.getText();
		String note = noteTextField.getText();
		String accountTitle = actTitleCB.getValue();
		
		String Pmethod = null;
		boolean paymentRequired = false;
		
		if(paidby.isVisible()) {
			paymentRequired = true;
			Pmethod = paidby.getValue();
		}
		
		try {
			
			Transaction transaction = new Transaction(date, transactionType, accountTitle, amount, note, Pmethod, paymentRequired);
			
			JournalEntry entry = Journalizing.journalize(transaction);	
			
			journal.addEntry(entry);
			ledger.post(entry);
			
			displayLedger();
			
			 	System.out.println("Transaction added from Ledger scene!");
	            System.out.println("DATE: " + entry.getDate());
	            System.out.println("DEBIT: " + entry.getDebitAccount());
	            System.out.println("CREDIT: " + entry.getCreditAccount());
	            System.out.println("AMOUNT: " + entry.getAmount());
	            
	            resetTransaction();
			
		} catch(IllegalArgumentException ex) {
		    ex.printStackTrace();
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
		 
		 displayLedger();
		 
	 }
	 
	 @FXML
	 public void openJournal(ActionEvent event) throws IOException {
		 
			 FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
			 
			 Parent root = loader.load();
			 
			 Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
			 
			 Scene scene = new Scene(root);
			 
			 stage.setScene(scene);
			 stage.show();
			 
	 }
	 
	 @FXML
		public void openTrialBalance(ActionEvent event) throws IOException{
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("trialbalance.fxml"));
			
			Parent root = loader.load();
			
			Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
			
			Scene scene = new Scene(root);
			
			stage.setScene(scene);
			stage.show();
			
		}
	 
	 private void displayLedger() {
		 
		 ledgerContainer.getChildren().clear();
		 
		 for(String accountName : ledger.getAccounts().keySet()) {
			 
			 Label accountLabel = new Label(accountName);
			 
			 TableView<LedgerEntry> table = new TableView<>();
			 TableColumn<LedgerEntry, String> dateColumn = new TableColumn<>("DATE");
			 TableColumn<LedgerEntry, Number> debitColumn = new TableColumn<>("DEBIT");
			 TableColumn<LedgerEntry, Number> creditColumn = new TableColumn<>("CREDIT");
			 TableColumn<LedgerEntry, String> noteColumn = new TableColumn<>("NOTE");
			 
			 dateColumn.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getDate()));
			 debitColumn.setCellValueFactory(cell -> new javafx.beans.property.SimpleDoubleProperty(cell.getValue().getDebit()));
			 creditColumn.setCellValueFactory(cell -> new javafx.beans.property.SimpleDoubleProperty(cell.getValue().getCredit()));
			 noteColumn.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getNote()));
			 
			 table.getColumns().add(dateColumn);
			 table.getColumns().add(debitColumn);
			 table.getColumns().add(creditColumn);
			 table.getColumns().add(noteColumn);
			 
			 table.getItems().addAll(ledger.getAccounts().get(accountName));
			 
			 table.setPrefHeight(150);
			 table.setPrefWidth(600);
			 
			 ledgerContainer.getChildren().add(accountLabel);
			 ledgerContainer.getChildren().add(table);
			 
		 }
		 
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
			actTitleCB.setPromptText("Select Transaction");
			
			paidby.getItems().clear();
			paidby.setValue(null);
			
			paidby.setVisible(false);
			paidbylabel.setVisible(false);
			
		}
	
}