package Main;

public class JournalEntry {

    private String date;
    private String debitAccount;
    private String creditAccount;
    private double amount;
    private String note;

    public JournalEntry(
            String date,
            String debitAccount,
            String creditAccount,
            double amount,
            String note) {

        this.date = date;
        this.debitAccount = debitAccount;
        this.creditAccount = creditAccount;
        this.amount = amount;
        this.note = note;
    }

    public String getDate() {
        return date;
    }

    public String getDebitAccount() {
        return debitAccount;
    }

    public String getCreditAccount() {
        return creditAccount;
    }

    public double getAmount() {
        return amount;
    }

    public String getNote() {
        return note;
    }
}