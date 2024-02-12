package pl.franasze.wszib.SpringBookstoreApp.services.impl;

import jakarta.annotation.Resource;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.franasze.wszib.SpringBookstoreApp.dao.IUserDAO;
import pl.franasze.wszib.SpringBookstoreApp.model.User;
import pl.franasze.wszib.SpringBookstoreApp.services.IAuthenticationService;
import pl.franasze.wszib.SpringBookstoreApp.session.SessionObject;

import java.util.Optional;

@Service
public class AuthenticationService implements IAuthenticationService {

    @Autowired
    IUserDAO userDAO;

    @Resource
    SessionObject sessionObject;

    @Override
    public void login(String login, String password) {
        Optional<User> userBox = this.userDAO.getByLogin(login);
        if(userBox.isPresent() &&
                userBox.get().getPassword().equals(DigestUtils.md5Hex(password))) {
            this.sessionObject.setLoggedUser(userBox.get());
        }
    }

    @Override
    public void logout() {
        this.sessionObject.setLoggedUser(null);
    }
}
