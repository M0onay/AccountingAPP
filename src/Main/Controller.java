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
	private JournalEntry selectedEntry = null;

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
	        
	        if(selectedEntry == null) {
	        	journal.addEntry(entry);
	        }else {
	        	journal.removeEntry(selectedEntry);
	        	journal.addEntry(entry);
	        	selectedEntry = null;
	        }
	        
	        for(JournalEntry journalentry : journal.genEntries()) {
	        	ledger.post(entry);
	        }
	        
	        TableRow.clear();
	        
	        for(JournalEntry journalentry : journal.genEntries()) {
	        	TableRow.add(new TableRow(entry.getDate(), entry.getDebitAccount(), entry.getAmount(), 0, entry.getNote()));
		        
		        TableRow.add(new TableRow("", entry.getCreditAccount(), 0, entry.getAmount(),""));
	        }
	        
	        journalTable.refresh();
	        resetTransaction();

	    } catch(IllegalArgumentException ex) {
	        System.out.println(ex.getMessage());
	    }
	}
	
	public void DeleteTransaction(ActionEvent event) {
		System.out.println("WOW WORKING");
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
		
		journalTable.setOnMouseClicked(event -> {

		    if(event.getClickCount() == 1) {
		        TableRow selectedRow = journalTable.getSelectionModel().getSelectedItem();
		        if(selectedRow == null) {
		            return;
		        }

		        int selectedIndex = journalTable.getSelectionModel().getSelectedIndex();

		        if(selectedIndex % 2 != 0) {
		            return;
		        }

		        int journalIndex = selectedIndex / 2;

		        if(journalIndex >= journal.genEntries().size()) {
		            return;
		        }

		        selectedEntry = journal.genEntries().get(journalIndex);

		        dateTextField.setText(selectedEntry.getDate());
		        amountTextField.setText(String.valueOf(selectedEntry.getAmount()));
		        noteTextField.setText(selectedEntry.getNote());

		        loadSelectedTransaction(selectedEntry);

		    }

		});
		
	}
	
	private void loadSelectedTransaction(JournalEntry entry) {
		
		String debit = entry.getDebitAccount();
		String credit = entry.getCreditAccount();
		
		if ("Capital".equals(credit)) {
	        transactionType = "Investment";
	        
	        actTitleCB.getItems().clear();
	        actTitleCB.getItems().addAll(ActOpt.getInvestmentOptions());
	        actTitleCB.setValue(debit);

	        back.setVisible(true);
	        back.setManaged(true);

	        return;
	    }
		
		if ("Sales".equals(credit)) {

	        transactionType = "Revenue/Sales";

	        actTitleCB.getItems().clear();
	        actTitleCB.getItems().addAll(ActOpt.getRevenueOptions());

	        if ("Cash".equals(debit)) {
	            actTitleCB.setValue("Cash Sale");
	        } else if ("Accounts Receivable".equals(debit)) {
	        	actTitleCB.setValue("Sale on Account");
	        }

	        back.setVisible(true);
	        back.setManaged(true);

	        return;
	    }
		
		if ("Cash".equals(credit) || "Accounts Payable".equals(credit)) {

	        if (ActOpt.getExpenseOptions()!= null) {
	            for (String expense :ActOpt.getExpenseOptions()) {

	                if (expense.equals(debit)) {
	                    transactionType = "Expense";

	                    actTitleCB.getItems().clear();
	                    actTitleCB.getItems().addAll(ActOpt.getExpenseOptions());
	                    actTitleCB.setValue(debit);

	                    paidbylabel.setVisible(true);
	                    paidbylabel.setManaged(true);

	                    paidby.setVisible(true);
	                    paidby.setManaged(true);

	                    paidby.getItems().clear();
	                    paidby.getItems().addAll(ActOpt.getPaymentOptions());

	                    if ("Cash".equals(credit)) {
	                        paidby.setValue("Cash");
	                    } else {
	                        paidby.setValue("On Account");
	                    }

	                    back.setVisible(true);
	                    back.setManaged(true);

	                    return;
	                }
	            }
	        }
	    }
		
		if ("Cash".equals(credit) || "Accounts Payable".equals(credit)) {
	        for (String purchase : ActOpt.getPurchaseOptions()) {
	            if (purchase.equals(debit)) {
	                transactionType = "Purchase";

	                actTitleCB.getItems().clear();
	                actTitleCB.getItems().addAll(ActOpt.getPurchaseOptions());
	                actTitleCB.setValue(debit);

	                paidbylabel.setVisible(true);
	                paidbylabel.setManaged(true);

	                paidby.setVisible(true);
	                paidby.setManaged(true);
	                paidby.getItems().clear();
	                paidby.getItems().addAll(ActOpt.getPaymentOptions());

	                if ("Cash".equals(credit)) {
	                    paidby.setValue("Cash");
	                } else {
	                    paidby.setValue("On Account");
	                }

	                back.setVisible(true);
	                back.setManaged(true);

	                return;
	            }
	        }
	    }
		
		if ("Drawings".equals(debit) && "Cash".equals(credit)) {
	        transactionType = "Drawings";

	        actTitleCB.getItems().clear();
	        actTitleCB.getItems().add("Drawings");
	        actTitleCB.setValue("Drawings");

	        back.setVisible(true);
	        back.setManaged(true);

	        return;
	    }
		
		if ("Cash".equals(debit) && "Accounts Receivable".equals(credit)) {
	        transactionType = "Collect Accounts Receivable";

	        actTitleCB.getItems().clear();
	        actTitleCB.getItems().add("Collect Accounts Receivable");
	        actTitleCB.setValue("Collect Accounts Receivable");

	        back.setVisible(true);
	        back.setManaged(true);

	        return;
	    }
		
		if ("Accounts Payable".equals(debit) && "Cash".equals(credit)) {
	        transactionType = "Pay Accounts Payable";

	        actTitleCB.getItems().clear();
	        actTitleCB.getItems().add("Pay Accounts Payable");
	        actTitleCB.setValue("Pay Accounts Payable");

	        back.setVisible(true);
	        back.setManaged(true);

	        return;
	    }

		if ("Cash".equals(debit) && "Loan Payable".equals(credit)) {
	        transactionType = "Borrow Money";

	        actTitleCB.getItems().clear();
	        actTitleCB.getItems().add("Borrow Money");
	        actTitleCB.setValue("Borrow Money");

	        back.setVisible(true);
	        back.setManaged(true);
	    }
		
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
	
	@FXML
	public void openTrialBalance(ActionEvent event) throws IOException{
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("trialbalance.fxml"));
		
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
		
		selectedEntry = null;
		
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
