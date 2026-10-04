package Main;

import java.util.LinkedHashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Ledger {

	private Map<String, List<LedgerEntry>> accounts = new LinkedHashMap<>();
	
	public void post(JournalEntry entry) {
		
		addToAccount(entry.getDebitAccount(), new LedgerEntry(entry.getDate(), entry.getAmount(), 0, entry.getNote()));
		
		addToAccount(entry.getCreditAccount(), new LedgerEntry(entry.getDate(), 0, entry.getAmount(), entry.getNote()));
		
	}
	
	public void addToAccount(String accountName, LedgerEntry entry) {
		
		if(!accounts.containsKey(accountName)){
			accounts.put(accountName, new ArrayList<>());
		}
		
		accounts.get(accountName).add(entry);		
	}
	
	public Map<String, List<LedgerEntry>> getAccounts(){
		return accounts;
	}
	
	public void clear() {
		accounts.clear();
	}
	
}
