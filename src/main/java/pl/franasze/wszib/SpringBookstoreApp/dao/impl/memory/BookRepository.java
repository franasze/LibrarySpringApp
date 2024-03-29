package pl.franasze.wszib.SpringBookstoreApp.dao.impl.memory;

import org.springframework.stereotype.Repository;
import pl.franasze.wszib.SpringBookstoreApp.model.Book;
import pl.franasze.wszib.SpringBookstoreApp.dao.IBookDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//@Repository
public class BookRepository implements IBookDAO {
    private final List<Book> books = new ArrayList<>();

    public BookRepository() {
        this.books.add(
                new Book(1, "Lalka",
                        "Boleslaw Prus", "978-83-289-0140-7",
                        10));
        this.books.add(
                new Book(2, "Pan Tadeusz",
                        "Adam Mickiewicz",
                        "978-83-289-0161-2",
                        10));
        this.books.add(
                new Book(3, "Miecz przeznaczenia",
                        "Andrzej Sapkowski", "978-83-832-2156-4",
                        10));
        this.books.add(
                new Book(4, "Kordian",
                        "Juliusz Slowacki", "978-83-283-9984-6",
                        10));
    }

    @Override
    public Optional<Book> getById(final int id) {
        return this.books.stream().filter(b -> b.getId() == id).findFirst();
    }

    @Override
    public List<Book> getAll() {
        return this.books;
    }

    @Override
    public void delete(final int id) {
        this.books.removeIf(b -> b.getId() == id);
    }

    @Override
    public void update(Book book) {
    }

    @Override
    public void persist(final Book book) {
    }

    @Override
    public List<Book> getByPattern(String pattern) {
        return this.books.stream()
                .filter(b -> b.getAuthor().toLowerCase().contains(pattern.toLowerCase()) ||
                        b.getTitle().toLowerCase().contains(pattern.toLowerCase()))
                .toList();
    }
}
