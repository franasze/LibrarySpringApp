package pl.franasze.wszib.SpringBookstoreApp.services;

import pl.franasze.wszib.SpringBookstoreApp.model.Book;

import java.util.List;

public interface IBookService {
    List<Book> getAll();

    void persist(Book book);

    List<Book> getByPattern(String pattern);
}
