package Main;

public class AccountingData {

	private static final Journal journal = new Journal();
	private static final Ledger ledger = new Ledger();
	
	public static Journal getJournal() {
		return journal;
	}
	
	public static Ledger getLedger() {
		return ledger;
	}
}
