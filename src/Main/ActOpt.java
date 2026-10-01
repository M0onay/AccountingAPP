package Main;

public class ActOpt {
	
	public static String[] getMainOptions() {
		
		return new String[] {
				"Investment",
				"Revenue/Sales",
				"Expense",
				"Purchase",
				"Drawings",
				"Collect Accounts Receivable",
				"Pay Accounts Payable",
				"Borrow Money"
		};
		
	}
	
	public static String[] getInvestmentOptions() {
		
		return new String[] {
				"Cash",
				"Equipment",
				"Supplies"
		};
		
	}
	
	public static String[] getRevenueOptions() {
		
		return new String [] {
				"Cash Sale",
				"Sale on Account"
		};
		
	}
	
	public static String[] getExpenseOptions() {
		
		return new String[] {
			"Rent Expense",
			"Salary Expense",
			"Utilities Expense",
			"Advertising Expense",
			"Supplies Expense"
		};
		
	}
	
	public static String[] getPurchaseOptions() {
		
		return new String[] {
			"Supplies",
			"Equipment"
		};
		
	}
	
	public static String[] getPaymentOptions() {
		
		return new String[] {
			"Cash",
			"On Account"
		};
		
	}

}
