package Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
    	try {
    		Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
	        Scene scene = new Scene(root);
	        stage.setTitle("My JavaFX App");
	        stage.setScene(scene);
	        stage.show();
    	} catch(Exception e) {
    			e.printStackTrace();
    		}
    	}

    public static void main(String[] args) {
//    	 
//        Ledger ledger = new Ledger();
//
//        JournalEntry entry1 = new JournalEntry(
//            "10/01/2026",
//            "Cash",
//            "Capital",
//            10000,
//            "Owner investment"
//        );
//
//        JournalEntry entry2 = new JournalEntry(
//            "10/02/2026",
//            "Rent Expense",
//            "Cash",
//            2000,
//            "Paid rent"
//        );
//
//        ledger.post(entry1);
//        ledger.post(entry2);
//        
//        for (String account : ledger.getAccounts().keySet()) {
//
//            System.out.println("ACCOUNT: " + account);
//
//            for (LedgerEntry entry : ledger.getAccounts().get(account)) {
//
//                System.out.println(
//                    entry.getDate()
//                    + " | Debit: " + entry.getDebit()
//                    + " | Credit: " + entry.getCredit()
//                );
//            }
//
//            System.out.println();
//        }
    	launch();
    }
}
