package Main;

public class Journalizing {
	
	public static JournalEntry journalize(Transaction transaction) {
		
		String debitAccount = determineDebit(transaction);
		String creditAccount = determineCredit(transaction);
		
		return new JournalEntry(transaction.getdate(), debitAccount, creditAccount, transaction.getAmount(), transaction.getNote());
		
	}
	
	private static String determineDebit(Transaction transaction) {
		
		String type = transaction.getTransactionType();
		String account = transaction.getAccountTitle();
		
		if("Investment".equals(type)) {
			return account;
		}
		if("Revenue/Sales".equals(type)) {
			if("Cash Sale".equals(account)) {
				return "Cash";
			}
			if("Sale on Account".equals(account)) {
				return "Accounts Receivable";
			}
		}
		if("Expense".equals(type)) {
			return account;
		}
		if("Purchase".equals(type)) {
			return account;
		}
		if("Drawings".equals(type)) {
			return "Drawings";
		}
		if("Collect Accounts Receivable".equals(type)) {
			return "Cash";
		}
		if("Pay Accounts Payable".equals(type)) {
			return "Accounts Payable";
		}
		if("Borrow Money".equals(type)) {
			return "Cash";
		}
		return "";
	}
	
	private static String determineCredit(Transaction transaction) {
		
		String type = transaction.getTransactionType();
		String account = transaction.getAccountTitle();
		String paymentMethod = transaction.getPaymentMethod();
		
		if("Investment".equals(type)) {
			return "Capital";
		}
		if ("Revenue/Sales".equals(type)) {
            return "Sales";
        }
        if ("Expense".equals(type)) {
            if ("Cash".equals(paymentMethod)) {
                return "Cash";
            }
            if ("On Account".equals(paymentMethod)) {
                return "Accounts Payable";
            }
        }
        if ("Purchase".equals(type)) {
            if ("Cash".equals(paymentMethod)) {
                return "Cash";
            }
            if ("On Account".equals(paymentMethod)) {
                return "Accounts Payable";
            }
        }
        if ("Drawings".equals(type)) {
            return "Cash";
        }
        if ("Collect Accounts Receivable".equals(type)) {
            return "Accounts Receivable";
        }
        if ("Pay Accounts Payable".equals(type)) {
            return "Cash";
        }
        if ("Borrow Money".equals(type)) {
            return "Loan Payable";
        }
        return "";
	}

}
