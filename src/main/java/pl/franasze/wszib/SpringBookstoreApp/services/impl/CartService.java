package pl.franasze.wszib.SpringBookstoreApp.services.impl;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.franasze.wszib.SpringBookstoreApp.dao.IBookDAO;
import pl.franasze.wszib.SpringBookstoreApp.dao.IOrderDAO;
import pl.franasze.wszib.SpringBookstoreApp.model.Book;
import pl.franasze.wszib.SpringBookstoreApp.model.BorrowBook;
import pl.franasze.wszib.SpringBookstoreApp.model.BorrowedBookPosition;
import pl.franasze.wszib.SpringBookstoreApp.services.ICartService;
import pl.franasze.wszib.SpringBookstoreApp.session.SessionObject;


import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CartService implements ICartService {

    @Autowired
    IBookDAO bookDAO;

    @Autowired
    IOrderDAO orderDAO;

    @Resource
    SessionObject sessionObject;

    @Override
    public void addBookToCart(int bookId) {
        Optional<Book> bookBox = this.bookDAO.getById(bookId);
        bookBox.ifPresent(book -> this.sessionObject.getCart().stream()
                .filter(op -> op.getBook().getId() == bookId)
                .findFirst()
                .ifPresentOrElse(
                        BorrowedBookPosition::incrementQuantity,
                        () -> this.sessionObject.getCart()
                                .add(new BorrowedBookPosition(book, 1))));
    }

    @Override
    public boolean order(String firstName, String lastName) {
        Set<BorrowedBookPosition> cart = this.sessionObject.getCart();
        Iterator<BorrowedBookPosition> cartIterator = cart.iterator();
        while (cartIterator.hasNext()) {
            BorrowedBookPosition position = cartIterator.next();
            Optional<Book> bookFromDb = this.bookDAO.getById(position.getBook().getId());
            if (bookFromDb.isEmpty() || bookFromDb.get().getQuantity() < position.getQuantity()) {
                cartIterator.remove();
                return false;
            }
        }

        BorrowBook order = new BorrowBook();
        order.setUser(this.sessionObject.getLoggedUser());
        order.setDate(LocalDateTime.now());
        order.setFirstName(firstName);
//        System.out.println(order.getFirstName());
        order.setLastName(lastName);
        order.setBorrowedBookPositions(cart);


        cart.forEach(orderPosition -> {
            Book book = this.bookDAO.getById(orderPosition.getBook().getId()).get();
            book.setQuantity(book.getQuantity() - orderPosition.getQuantity());
            orderPosition.setBook(book);
//            System.out.println(book);
//            System.out.println(book.getQuantity());
        });

        this.orderDAO.persist(order);
        this.sessionObject.getCart().clear();
        return true;
    }

    @Override
    public List<BorrowBook> getAll() {
        return this.orderDAO.getAll();
    }

    @Override
    public List<BorrowBook> actualBorrowers() {
        return getAll().stream()
                .filter(borrowBook -> borrowBook.getReturned() == null)
                .collect(Collectors.toList());
    }
}
