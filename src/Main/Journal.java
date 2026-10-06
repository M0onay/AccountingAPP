package Main;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Journal {

	private List<JournalEntry> entries = new ArrayList<>();
	
	public void addEntry(JournalEntry entry) {
		entries.add(entry);
	}
	public void removeEntry(JournalEntry entry) {
		entries.remove(entry);
	}
	public List<JournalEntry> genEntries(){ 

	    entries.sort((entry1, entry2) -> {
	        LocalDate date1 = LocalDate.parse(
	            entry1.getDate(),
	            DateTimeFormatter.ofPattern("MM/dd/yyyy")
	        );

	        LocalDate date2 = LocalDate.parse(
	            entry2.getDate(),
	            DateTimeFormatter.ofPattern("MM/dd/yyyy")
	        );

	        return date1.compareTo(date2);
	    });

	    return entries; 
	}
	
}
