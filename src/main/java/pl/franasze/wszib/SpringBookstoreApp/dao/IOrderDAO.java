package pl.franasze.wszib.SpringBookstoreApp.dao;



import pl.franasze.wszib.SpringBookstoreApp.model.BorrowBook;

import java.util.Optional;

public interface IOrderDAO {
    void persist(BorrowBook order);
    Optional<BorrowBook> getOrderById(int id);
}
