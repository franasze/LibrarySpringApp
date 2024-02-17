package pl.franasze.wszib.SpringBookstoreApp.dao.impl.hibernate;

import jakarta.persistence.NoResultException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.franasze.wszib.SpringBookstoreApp.dao.IOrderDAO;
import pl.franasze.wszib.SpringBookstoreApp.model.BorrowBook;


import java.util.List;
import java.util.Optional;

@Repository
public class OrderDAO implements IOrderDAO {

    private final String GET_BY_ID = "FROM pl.franasze.wszib.SpringBookstoreApp.model.BorrowBook WHERE id = :id";
    private final String GET_ALL ="FROM pl.franasze.wszib.SpringBookstoreApp.model.BorrowBook";
    @Autowired
    SessionFactory sessionFactory;

    @Override
    public void persist(BorrowBook order) {
        Session session = this.sessionFactory.openSession();
        order.getBorrowedBookPositions().forEach(op -> op.setBook(session.merge(op.getBook())));
        try {
            session.beginTransaction();
            session.persist(order);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public Optional<BorrowBook> getOrderById(int id) {
        Session session = this.sessionFactory.openSession();
        Query<BorrowBook> query = session.createQuery(GET_BY_ID, BorrowBook.class);
        query.setParameter("id", id);
        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        } finally {
            session.close();
        }
    }

    @Override
    public List<BorrowBook> getAll() {
        Session session = this.sessionFactory.openSession();
        Query<BorrowBook> query = session.createQuery(GET_ALL,BorrowBook.class);
        List<BorrowBook> result = query.getResultList();
        session.close();
        return result;
    }


}