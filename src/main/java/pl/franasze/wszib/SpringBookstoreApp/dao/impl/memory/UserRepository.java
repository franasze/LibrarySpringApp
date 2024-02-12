package pl.franasze.wszib.SpringBookstoreApp.dao.impl.memory;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Repository;
import pl.franasze.wszib.SpringBookstoreApp.dao.IUserDAO;
import pl.franasze.wszib.SpringBookstoreApp.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//@Repository
public class UserRepository implements IUserDAO {

    private final List<User> users = new ArrayList<>();

    public UserRepository() {
        this.users.add(new User(1, "admin", DigestUtils.md5Hex("admin123")));
        this.users.add(new User(2, "janusz", DigestUtils.md5Hex("janusz123")));
    }

    @Override
    public Optional<User> getById(final int id) {
        return this.users.stream()
                .filter(user -> user.getId() == id)
                .findFirst();
    }

    @Override
    public Optional<User> getByLogin(final String login) {
        return this.users.stream()
                .filter(user -> user.getLogin().equals(login))
                .findFirst();
    }

    @Override
    public List<User> getAll() {
        return this.users;
    }

    @Override
    public void persist(final User user) {
        this.users.stream()
                .map(User::getId)
                .max(Integer::compare)
                .ifPresentOrElse(
                        maxId -> user.setId(maxId+1),
                        () -> user.setId(1)
                );
        this.users.add(user);
    }
}
