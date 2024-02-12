package pl.franasze.wszib.SpringBookstoreApp.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.franasze.wszib.SpringBookstoreApp.dao.IBookDAO;
import pl.franasze.wszib.SpringBookstoreApp.model.Book;
import pl.franasze.wszib.SpringBookstoreApp.services.IBookService;

import java.util.List;

@Service
public class BookService implements IBookService {

    @Autowired
    IBookDAO bookDAO;

    @Override
    public List<Book> getAll() {
        return this.bookDAO.getAll();
    }

    @Override
    public void persist(Book book) {
        this.bookDAO.persist(book);
    }

    @Override
    public List<Book> getByPattern(String pattern) {
        return bookDAO.getByPattern(pattern);
    }


}
