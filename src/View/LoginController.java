package View;

import AppointmentManager.Main;
import DAO.UserDAO;
import Exceptions.InvalidCredentialsException;
import Impl.UserDAOImpl;
import Model.User;
import Util.AccessLogger;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * Controller for logging in to the system. Fulfills REQUIREMENT A
*/
public class LoginController {

    @FXML
    private Label applicationLabel;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private Button loginButton;

    private Main Main;
    private UserDAO dao;
    private ResourceBundle rb;

    public LoginController() {
    }

    @FXML
    public void initialize() {
        // REQUIREMENT A
        Locale locale = Locale.getDefault(); // Obtain default locale of host
        //Loale locale = new Locale("en", "US"); // swap the commmenting on this line
        //Locale locale = new Locale("jp"); // and this line to change locale manually
        rb = ResourceBundle.getBundle("Util/lang", locale);
        applicationLabel.setText(rb.getString("title"));
        usernameLabel.setText(rb.getString("username"));
        passwordLabel.setText(rb.getString("password"));
        loginButton.setText(rb.getString("loginButton"));
        dao = new UserDAOImpl();
    }

    public void setMain(Main Main) {
        this.Main = Main;
    }

    public void handleLoginButton() {
        if (isInputValid()) {
            try {
                User candidate = dao.getUser(usernameField.getText(), passwordField.getText());
                Main.setSessionUser(candidate);
                Main.setInitialView(true);
                AccessLogger.logAccessAttempt(candidate.getUserName(), true);
                Main.showAppointmentScreen();
            } catch (InvalidCredentialsException ex) {
                Main.throwAlert(rb.getString("invalidCredentials"));
            }
        }

    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (usernameField.getText() == null || usernameField.getText().length() == 0) {
            errorMessage += rb.getString("missingUsername");
        }
        if (passwordField.getText() == null || usernameField.getText().length() == 0) {
            errorMessage += rb.getString("missingPassword");
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            Main.throwAlert(errorMessage);
            return false;
        }
    }

}