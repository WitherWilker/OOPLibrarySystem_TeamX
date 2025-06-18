package CampusLibrarySystem.service;

import CampusLibrarySystem.model.Transaction;

import java.time.LocalDate;
import java.util.List;

public class ReportService {
    private final List<Transaction> allTransactions;

    public ReportService(List<Transaction> transactions) {
        this.allTransactions = transactions;
    }

    public long getTotalBorrowsThisMonth() {
        return allTransactions.stream()
                .filter(tx -> tx.getBorrowDate().getMonth() == LocalDate.now().getMonth())
                .count();
    }

    public long getTotalReturnsThisMonth() {
        return allTransactions.stream()
                .filter(tx -> tx.getReturnDate() != null &&
                        tx.getReturnDate().getMonth() == LocalDate.now().getMonth())
                .count();
    }

    public int getTotalFinesThisMonth() {
        return allTransactions.stream()
                .filter(tx -> tx.getReturnDate() != null &&
                        tx.getReturnDate().getMonth() == LocalDate.now().getMonth())
                .mapToInt(Transaction::getFine).sum();
    }

    public List<Transaction> getCurrentlyBorrowedBooks() {
        return allTransactions.stream()
                .filter(tx -> tx.getStatus().equals("Borrowed"))
                .toList();
    }
}