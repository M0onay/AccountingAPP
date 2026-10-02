package Main;

import java.io.IOException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;



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
	@FXML
	private TableView<TableRow> journalTable;
	@FXML
	private TableColumn<TableRow, String> dateColumn;
	@FXML
	private TableColumn<TableRow, String> accountColumn;
	@FXML
	private TableColumn<TableRow, Number> debitColumn;
	@FXML
	private TableColumn<TableRow, Number> creditColumn;
	@FXML
	private TableColumn<TableRow, String> noteColumn;
	private String transactionType;
	private ObservableList<TableRow> TableRow = FXCollections.observableArrayList();
	private Journal journal = AccountingData.getJournal();
	private Ledger ledger = AccountingData.getLedger();

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

	    	Transaction transaction = new Transaction(date, transactionType, accountTitle, amount, note, Pmethod,paymentRequired);
	        
	        JournalEntry entry = Journalizing.journalize(transaction);
	        journal.addEntry(entry);
	        ledger.post(entry);
	        
	        TableRow.add(new TableRow(entry.getDate(), entry.getDebitAccount(), entry.getAmount(), 0, entry.getNote()));
	        
	        TableRow.add(new TableRow("", entry.getCreditAccount(), 0, entry.getAmount(),""));
	        
	        journalTable.refresh();
	        resetTransaction();

	    } catch(IllegalArgumentException ex) {
	        System.out.println(ex.getMessage());
	    }
	}
	
	public void EditTransaction(ActionEvent event) {
		System.out.println("WOW WORKING");
	}
	
	public void GenerateReport(ActionEvent event) {
//		printLedger();
		System.out.println("TANGINA NAGANA");
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
			  if (selected == null) {
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
				if("Drawings".equals(selected)) {
					transactionType = "Drawings";
					back.setVisible(true);
					back.setManaged(true);
				}
				if("Collect Accounts Receivable".equals(selected)) {
					transactionType = "Collect Accounts Receivable";
					back.setVisible(true);
					back.setManaged(true);
				}
				if("Pay Accounts Payable".equals(selected)) {
					transactionType = "Pay Accounts Payable";
					back.setVisible(true);
					back.setManaged(true);
				}
				if("Borrow Money".equals(selected)) {
					transactionType = "Borrow Money";
					back.setVisible(true);
					back.setManaged(true);
				}
			});
		});
		
		dateColumn.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getDate()));
		
		accountColumn.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getAccountTitle()));
		
		debitColumn.setCellValueFactory(cell -> new javafx.beans.property.SimpleDoubleProperty(cell.getValue().getDebit()));
		
		creditColumn.setCellValueFactory(cell -> new javafx.beans.property.SimpleDoubleProperty(cell.getValue().getCredit()));
		
		noteColumn.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getNote()));
		
		
		for(JournalEntry entry : journal.genEntries()) {
			
			TableRow.add(new TableRow(entry.getDate(), entry.getDebitAccount(), entry.getAmount(), 0, entry.getNote()));
			TableRow.add(new TableRow("", entry.getCreditAccount(), 0, entry.getAmount(), ""));
			
		}

		journalTable.setItems(TableRow);
		
	}
	
	@FXML
	public void openLedger(ActionEvent event) throws IOException{
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ledger.fxml"));
		
		Parent root = loader.load();
		
		Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
		
		Scene scene = new Scene(root);
		
		stage.setScene(scene);
		stage.show();
		
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
	
//	private void printLedger() {
//
//	    System.out.println();
//	    System.out.println("========================================");
//	    System.out.println("                 LEDGER");
//	    System.out.println("========================================");
//
//	    for (String account : ledger.getAccounts().keySet()) {
//
//	        System.out.println();
//	        System.out.println("ACCOUNT: " + account);
//	        System.out.println("----------------------------------------");
//
//	        System.out.printf(
//	                "%-15s %-15s %-15s%n",
//	                "DATE",
//	                "DEBIT",
//	                "CREDIT"
//	        );
//
//	        System.out.println("----------------------------------------");
//
//	        for (LedgerEntry entry : ledger.getAccounts().get(account)) {
//
//	            System.out.printf(
//	                    "%-15s %-15.2f %-15.2f%n",
//	                    entry.getDate(),
//	                    entry.getDebit(),
//	                    entry.getCredit()
//	            );
//	        }
//
//	        System.out.println("----------------------------------------");
//	    }
//	}
	
}
