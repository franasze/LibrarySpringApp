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
import java.util.Optional;
import java.util.Set;

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
        while(cartIterator.hasNext()) {
            BorrowedBookPosition position = cartIterator.next();
            Optional<Book> bookFromDb = this.bookDAO.getById(position.getBook().getId());
            if(bookFromDb.isEmpty() || bookFromDb.get().getQuantity() < position.getQuantity()) {
                cartIterator.remove();
                return false;
            }
        }

        BorrowBook order = new BorrowBook();
        order.setUser(this.sessionObject.getLoggedUser());
        order.setDate(LocalDateTime.now());
//        order.setStatus(BorrowBook.Status.NEW);
        order.setBorrowedBookPositions(cart);
        order.setFirstName(firstName);
        order.setLastName(lastName);

        cart.forEach(orderPosition -> {
            Book book = this.bookDAO.getById(orderPosition.getBook().getId()).get();
            book.setQuantity(book.getQuantity() - orderPosition.getQuantity());
            orderPosition.setBook(book);
        });

        this.orderDAO.persist(order);
        this.sessionObject.getCart().clear();
        return true;
    }
}
