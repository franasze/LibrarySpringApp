package pl.franasze.wszib.SpringBookstoreApp.services;

public interface ICartService {
    void addBookToCart(int bookId);
    boolean order(String firstName, String lastName);
}
