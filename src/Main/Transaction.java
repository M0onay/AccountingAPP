package Main;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Transaction {

	private String date;
	private String accountTitle;
	private double amount;
	private String note;
	private String paymentMethod;
	private String transactionType;
	
	public Transaction(String date, String transactionType, String accountTitle, String amount, String note, String paymentMethod, boolean paymentRequired) {
		
		if (!isValidDate(date)) {
			throw new IllegalArgumentException("ILLEGAL DATE FORMAT");
		}
		if (!isValidAmount(amount)) {
			throw new IllegalArgumentException("NUMBERS ONLY!");
		}
		if(!isValidTransaction(accountTitle)) {
			throw new IllegalArgumentException("PLEASE SELECT A TRANSACTION");
		}
		if (paymentRequired && !isValidPaymentMethod(paymentMethod)) {
	        throw new IllegalArgumentException("PLEASE SELECT PAYMENT METHOD");
	    }
		
		this.date = date;
		this.transactionType = transactionType;
		this.accountTitle = accountTitle;
		this.amount = Double.parseDouble(amount);
		this.note = note;
		this.paymentMethod = paymentMethod;
		
	}
	
	private boolean isValidDate(String date) {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		
		try { 
			LocalDate.parse(date, formatter);
			return true;
		}
		catch (DateTimeParseException e) {
			return false;
		}
		
		
	}
	
	private boolean isValidTransaction(String accountTitle) {
		
		if(accountTitle == null || accountTitle.isEmpty()) {
			return false;
		}
		
		return true;
		
	}
	
	private boolean isValidAmount(String amount) {
		
		try {
			Double.parseDouble(amount);
			return true;
		}
		catch(NumberFormatException e) {
			return false;
		}
		
	}
	
	private boolean isValidPaymentMethod(String Pmethod) {
		
		if(Pmethod == null || Pmethod.isEmpty()) {
			return false;
		}
		
		return true;
		
	}
	
	public String getdate() {
		return date;
	}
	
	public String getAccountTitle() {
		return accountTitle;
	}
	
	public String getTransactionType() {
		return transactionType;
	}
	
	public double getAmount() {
		return amount;
	}
	
	public String getNote() {
		return note;
	}
	
	public String getPaymentMethod() {
		return paymentMethod;
	}
	
}
