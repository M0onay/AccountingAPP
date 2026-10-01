package Main;

public class TableRow {

	private String date;
	private String accountTitle;
	private double debit;
	private double credit;
	private String note;
	
	public TableRow(String date, String accountTitle, double debit, double credit, String note) {
		
		this.date = date;
		this.accountTitle = accountTitle;
		this.debit = debit;
		this.credit = credit;
		this.note = note;
		
	}
	
	public String getDate() {
		return date;
	}
	
	public String getAccountTitle() {
		return accountTitle;
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
