package pl.franasze.wszib.SpringBookstoreApp.session;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;
import pl.franasze.wszib.SpringBookstoreApp.model.BorrowedBookPosition;
import pl.franasze.wszib.SpringBookstoreApp.model.User;

import java.util.HashSet;
import java.util.Set;

@Component
@SessionScope
public class SessionObject {
    private User loggedUser;
    private Set<BorrowedBookPosition> cart = new HashSet<>();

    public boolean isLogged() {
        return loggedUser != null;
    }

    public User getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
    }

    public Set<BorrowedBookPosition> getCart() {
        return cart;
    }

    public void setCart(Set<BorrowedBookPosition> cart) {
        this.cart = cart;
    }
}
