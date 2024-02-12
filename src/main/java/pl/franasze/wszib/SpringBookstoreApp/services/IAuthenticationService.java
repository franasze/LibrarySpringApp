package pl.franasze.wszib.SpringBookstoreApp.services;

public interface IAuthenticationService {
    void login(String login, String password);
    void logout();
}
