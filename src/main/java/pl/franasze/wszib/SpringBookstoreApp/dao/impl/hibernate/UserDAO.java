package pl.franasze.wszib.SpringBookstoreApp.dao.impl.hibernate;

import jakarta.persistence.NoResultException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.franasze.wszib.SpringBookstoreApp.dao.IUserDAO;
import pl.franasze.wszib.SpringBookstoreApp.model.User;


import java.util.List;
import java.util.Optional;

@Repository
public class UserDAO implements IUserDAO {

    private final String GET_BY_LOGIN = "FROM pl.franasze.wszib.SpringBookstoreApp.model.User WHERE login = :login";
    private final String GET_BY_ID = "FROM pl.franasze.wszib.SpringBookstoreApp.model.User WHERE id = :id";
    private final String GET_ALL = "FROM pl.franasze.wszib.SpringBookstoreApp.model.User";

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public Optional<User> getById(int id) {
        Session session= this.sessionFactory.openSession();
        Query<User> query = session.createQuery(GET_BY_ID, User.class);
        query.setParameter("id", id);
        try {
            return Optional.of(query.getSingleResult());
        } catch (Exception e) {
            return Optional.empty();
        } finally {
            session.close();
        }
    }

    @Override
    public Optional<User> getByLogin(String login) {
        Session session = this.sessionFactory.openSession();
        Query<User> query = session.createQuery(GET_BY_LOGIN, User.class);
        query.setParameter("login", login);
        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        } finally {
            session.close();
        }
    }

    @Override
    public List<User> getAll() {
        Session session = this.sessionFactory.openSession();
        Query<User> query = session.createQuery(GET_ALL, User.class);
        List<User> result = query.getResultList();
        session.close();
        return result;
    }

    @Override
    public void persist(User user) {
        Session session = this.sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.persist(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
//            session.close();
        }
    }
}
