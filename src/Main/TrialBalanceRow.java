package Main;

public class TrialBalanceRow {

	private String accountTitle;
	private double debit;
	private double credit;
	
	public TrialBalanceRow(String accountTitle, double debit, double credit) {
		
		this.accountTitle = accountTitle;
		this.debit = debit;
		this.credit = credit;
		
	}
	
	public String getAccountTitle() {
		return accountTitle;
	}
	
	public double getDebit () {
		return debit;
	}
	
	public double getCredit() {
		return credit;
	}
	
}
