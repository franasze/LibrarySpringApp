package pl.franasze.wszib.SpringBookstoreApp.services;

import pl.franasze.wszib.SpringBookstoreApp.model.BorrowBook;

import java.util.List;

public interface ICartService {
    void addBookToCart(int bookId);
    boolean order(String firstName, String lastName);
    List<BorrowBook> getAll();
    List<BorrowBook> actualBorrowers();
}
