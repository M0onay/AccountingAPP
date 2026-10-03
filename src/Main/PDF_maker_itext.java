package Main;

import java.io.FileOutputStream;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class PDF_maker_itext {

    public void generate(
            List<JournalEntry> entries,
            Ledger ledger) {

        try {

            Document document = new Document();

            PdfWriter.getInstance(
                    document,
                    new FileOutputStream("AccountingReport.pdf")
            );

            document.open();

            // =========================
            // TITLE
            // =========================

            document.add(
                    new Paragraph("ACCOUNTING REPORT")
            );

            document.add(
                    new Paragraph(" ")
            );


            // =========================
            // GENERAL JOURNAL
            // =========================

            document.add(
                    new Paragraph("GENERAL JOURNAL")
            );

            document.add(
                    new Paragraph(" ")
            );

            PdfPTable journal = new PdfPTable(4);

            journal.addCell("Date");
            journal.addCell("Account");
            journal.addCell("Debit");
            journal.addCell("Credit");

            for (JournalEntry entry : entries) {

                // Debit row

                journal.addCell(
                        entry.getDate()
                );

                journal.addCell(
                        entry.getDebitAccount()
                );

                journal.addCell(
                        String.format(
                                "₱%,.2f",
                                entry.getAmount()
                        )
                );

                journal.addCell("");


                // Credit row

                journal.addCell("");

                journal.addCell(
                        "    " + entry.getCreditAccount()
                );

                journal.addCell("");

                journal.addCell(
                        String.format(
                                "₱%,.2f",
                                entry.getAmount()
                        )
                );
            }

            document.add(journal);

            document.add(
                    new Paragraph(" ")
            );


            // =========================
            // GENERAL LEDGER
            // =========================

            document.add(
                    new Paragraph("GENERAL LEDGER")
            );

            document.add(
                    new Paragraph(" ")
            );

            for (String accountName :
                    ledger.getAccounts().keySet()) {

                document.add(
                        new Paragraph(accountName)
                );

                PdfPTable tAccount =
                        new PdfPTable(2);

                tAccount.addCell("DEBIT");
                tAccount.addCell("CREDIT");

                List<LedgerEntry> accountEntries =
                        ledger.getAccounts()
                             .get(accountName);

                for (LedgerEntry entry :
                        accountEntries) {

                    String debit = "";
                    String credit = "";

                    if (entry.getDebit() != 0) {

                        debit =
                                entry.getDate()
                                + "  "
                                + String.format(
                                    "₱%,.2f",
                                    entry.getDebit()
                                );
                    }

                    if (entry.getCredit() != 0) {

                        credit =
                                entry.getDate()
                                + "  "
                                + String.format(
                                    "₱%,.2f",
                                    entry.getCredit()
                                );
                    }

                    tAccount.addCell(debit);
                    tAccount.addCell(credit);
                }

                document.add(tAccount);

                document.add(
                        new Paragraph(" ")
                );
            }


            // =========================
            // TRIAL BALANCE
            // =========================

            document.add(
                    new Paragraph("TRIAL BALANCE")
            );

            document.add(
                    new Paragraph(" ")
            );

            PdfPTable trial =
                    new PdfPTable(3);

            trial.addCell("Account");
            trial.addCell("Debit");
            trial.addCell("Credit");

            double totalDebit = 0;
            double totalCredit = 0;

            for (String accountName :
                    ledger.getAccounts().keySet()) {

                double accountDebit = 0;
                double accountCredit = 0;

                for (LedgerEntry entry :
                        ledger.getAccounts()
                             .get(accountName)) {

                    accountDebit += entry.getDebit();
                    accountCredit += entry.getCredit();
                }

                double balance =
                        accountDebit - accountCredit;

                // Ignore accounts with zero balance

                if (balance == 0) {
                    continue;
                }

                trial.addCell(accountName);

                if (balance > 0) {

                    trial.addCell(
                            String.format(
                                "₱%,.2f",
                                balance
                            )
                    );

                    trial.addCell("");

                    totalDebit += balance;

                } else {

                    trial.addCell("");

                    trial.addCell(
                            String.format(
                                "₱%,.2f",
                                Math.abs(balance)
                            )
                    );

                    totalCredit +=
                            Math.abs(balance);
                }
            }


            // TOTAL

            trial.addCell("TOTAL");

            trial.addCell(
                    String.format(
                        "₱%,.2f",
                        totalDebit
                    )
            );

            trial.addCell(
                    String.format(
                        "₱%,.2f",
                        totalCredit
                    )
            );

            document.add(trial);


            // =========================
            // BALANCE STATUS
            // =========================

            document.add(
                    new Paragraph(" ")
            );

            if (totalDebit == totalCredit) {

                document.add(
                        new Paragraph(
                                "STATUS: BALANCED"
                        )
                );

            } else {

                document.add(
                        new Paragraph(
                                "STATUS: NOT BALANCED"
                        )
                );
            }


            // =========================
            // CLOSE PDF
            // =========================

            document.close();

            System.out.println();
            System.out.println(
                    "PDF created successfully!"
            );

            System.out.println(
                    "AccountingReport.pdf"
            );

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}