package pl.franasze.wszib.SpringBookstoreApp.dao.impl.memory;

import pl.franasze.wszib.SpringBookstoreApp.dao.IOrderDAO;
import pl.franasze.wszib.SpringBookstoreApp.model.Book;
import pl.franasze.wszib.SpringBookstoreApp.model.BorrowBook;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//@Repository
public class OrderRepository /* implements IOrderDAO*/ {
    private final List<BorrowBook> orders = new ArrayList<>();

//    @Override
    public void persist(BorrowBook order) {
        this.orders.add(order);
    }

//    @Override
    public Optional<BorrowBook> getOrderById(int id) {
        return Optional.empty();
    }

}
