package CampusLibrarySystem.repository;

import CampusLibrarySystem.model.Transaction;
import CampusLibrarySystem.util.CSVUtil;
import CampusLibrarySystem.util.DateUtil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TransactionRepository {

    private final String FILE_PATH = "resources/data/transactions.csv";
    private final List<Transaction> transactions = new ArrayList<>();

    public TransactionRepository() {
        loadAll();
    }

    public List<Transaction> getAll() {
        return transactions;
    }

    public void saveAll() {
        List<String[]> data = new ArrayList<>();
        for (Transaction t : transactions) {
            data.add(new String[]{
                    t.getId(),
                    t.getMemberId(),
                    t.getIsbn(),
                    DateUtil.format(t.getBorrowDate()),
                    DateUtil.format(t.getReturnDate()),
                    t.getActualReturnDate() != null ? DateUtil.format(t.getActualReturnDate()) : "",
                    t.getStatus(),
                    String.valueOf(t.getFine())
            });
        }
        CSVUtil.write(FILE_PATH, data);
    }

    public void loadAll() {
        transactions.clear();
        List<String[]> rows = CSVUtil.read(FILE_PATH);
        for (String[] row : rows) {
            try {
                String id = row[0];
                String memberId = row[1];
                String isbn = row[2];
                LocalDate borrowDate = DateUtil.parse(row[3]);
                LocalDate returnDate = DateUtil.parse(row[4]);

                LocalDate actualReturnDate = (row[5] == null || row[5].isEmpty() || row[5].equalsIgnoreCase("null"))
                        ? null : DateUtil.parse(row[5]);

                String status = row[6];
                int fine = Integer.parseInt(row[7]);

                Transaction tx = new Transaction(id, memberId, isbn, borrowDate, returnDate, actualReturnDate, status, fine);
                transactions.add(tx);
            } catch (Exception e) {
                System.err.println("Failed to parse transaction row: " + String.join(",", row));
                e.printStackTrace();
            }
        }
    }

    public void add(Transaction tx) {
        transactions.add(tx);
        saveAll();
    }

    public Transaction getById(String id) {
        return transactions.stream()
                .filter(t -> t.getId().equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);
    }
}