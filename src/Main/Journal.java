package Main;

import java.util.ArrayList;
import java.util.List;

public class Journal {

	private List<JournalEntry> entries = new ArrayList<>();
	
	public void addEntry(JournalEntry entry) {
		entries.add(entry);
	}
	public void removeEntry(JournalEntry entry) {
		entries.remove(entry);
	}
	public List<JournalEntry> genEntries(){
		return entries;
	}
	
}
