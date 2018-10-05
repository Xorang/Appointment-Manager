package DAO;

import Exceptions.InvalidCredentialsException;
import Model.User;
import javafx.collections.ObservableList;

public interface UserDAO {

    User getUser(String username, String password) throws InvalidCredentialsException;

    ObservableList<User> getActiveUsers();
}
