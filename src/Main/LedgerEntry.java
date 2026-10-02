package Main;

public class LedgerEntry {

	private String date;
	private double debit;
	private double credit;
	private String note;
	
	public LedgerEntry(String date, double debit, double credit, String note) {
		
		this.date =date;
		this.debit = debit;
		this.credit = credit;
		this.note = note;
		
	}
	
	public String getDate() {
		return date;
	}
	
	public double getDebit() {
		return debit;
	}
	
	public double getCredit() {
		return credit;
	}
	
	public String getNote() {
		return note;
	}
	
}
