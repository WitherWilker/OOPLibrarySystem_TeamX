package CampusLibrarySystem.service;

import CampusLibrarySystem.model.Transaction;
import CampusLibrarySystem.repository.TransactionRepository;

import java.util.ArrayList;
import java.time.LocalDate;
import java.util.List;

public class TransactionService {
    private final TransactionRepository repository;

    public TransactionService(TransactionRepository repository) {
        this.repository = repository;
    }

    public void createTransaction(String memberId, String isbn) {
        Transaction tx = new Transaction();
        tx.setId(generateNextId());
        tx.setMemberId(memberId);
        tx.setIsbn(isbn);
        tx.setBorrowDate(LocalDate.now());
        tx.setReturnDate(LocalDate.now().plusDays(7));
        tx.setStatus("Borrowed");
        tx.setFine(0);
        repository.add(tx);
    }

    public List<Transaction> findAllBorrowedByMember(String memberId) {
        List<Transaction> result = new ArrayList<>();
        for (Transaction transaction : repository.getAll()) {
            if (transaction.getMemberId().equalsIgnoreCase(memberId)
                    && transaction.getStatus().equalsIgnoreCase("Borrowed")) {
                result.add(transaction);
            }
        }
        return result;
    }

    public Transaction findBorrowedTransaction(String memberId, String isbn) {
        return repository.getAll().stream()
                .filter(t -> t.getMemberId().equals(memberId)
                        && t.getIsbn().equals(isbn)
                        && t.getStatus().equalsIgnoreCase("Borrowed"))
                .findFirst()
                .orElse(null);
    }

    public Transaction findLatestBorrowByMember(String memberId) {
        return repository.getAll().stream()
                .filter(tx -> tx.getMemberId().equals(memberId)
                        && tx.getStatus().equalsIgnoreCase("Borrowed"))
                .reduce((first, second) -> second)  // ambil yang paling akhir
                .orElse(null);
    }

    public void returnBook(String transactionId, LocalDate returnDate, long fine) {
        Transaction tx = repository.getById(transactionId);
        if (tx != null) {
            tx.setActualReturnDate(returnDate);
            tx.setStatus("Returned");
            tx.setFine((int) fine);
            repository.saveAll();
        }
    }

    public String generateNextId() {
        List<Transaction> all = repository.getAll();
        if (all.isEmpty()) return "T1001";
        String lastId = all.get(all.size() - 1).getId();
        int num = Integer.parseInt(lastId.substring(1)) + 1;
        return "T" + num;
    }
}