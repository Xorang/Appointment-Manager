package AppointmentManager;

import Model.Appointment;
import Model.Customer;
import Model.User;
import Util.Constants;
import Util.Constants.Mode;
import View.AddEditAppointmentController;
import View.AddEditCustomerController;
import View.AppointmentsViewController;
import View.CustomerViewController;
import View.LoginController;
import View.ReportsViewController;
import java.io.IOException;
import java.util.Optional;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Main application to change screens from
 *
 * @author Zane Wheadon
 */
public class Main extends Application {
    
    private Stage primaryStage;
    private User sessionUser;
    private boolean initialView = false;
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.getIcons().add(new Image(Constants.APP_ICON));
        this.primaryStage.setTitle("Appointment Manager");

        showLoginScreen();
    }
    
    public void showLoginScreen() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/View/Login.fxml"));
            AnchorPane mainScreen = (AnchorPane)loader.load();

            LoginController controller = loader.getController();
            controller.setMain(this);

            Scene scene = new Scene(mainScreen);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void showAppointmentScreen() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/View/AppointmentsView.fxml"));
            Parent appointmentsScreenParent = loader.load();
            Scene appointmentsScreen = new Scene(appointmentsScreenParent);

            AppointmentsViewController controller = loader.getController();
            controller.setMainApp(this);

            primaryStage.setScene(appointmentsScreen);
            primaryStage.show();
            controller.checkForUpcomingApts();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void showAddEditAppointment(Mode mode, Appointment apt) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/View/AddEditAppointment.fxml"));
            Parent addEditApt = loader.load();
            Scene addEditAptScreen = new Scene(addEditApt);

            AddEditAppointmentController controller = loader.getController();
            controller.setMainApp(this);
            controller.setAppointment(mode, apt);

            primaryStage.setScene(addEditAptScreen);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void showCustomerScreen() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/View/CustomerView.fxml"));
            Parent customerScreenParent = loader.load();
            Scene customerScreen = new Scene(customerScreenParent);

            CustomerViewController controller = loader.getController();
            controller.setMainApp(this);

            primaryStage.setScene(customerScreen);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void showAddEditCustomer(Mode mode, Customer cust) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/View/AddEditCustomer.fxml"));
            Parent addEditCust = loader.load();
            Scene addEditCustScreen = new Scene(addEditCust);

            AddEditCustomerController controller = loader.getController();
            controller.setMainApp(this);
            controller.setCustomer(mode, cust);

            primaryStage.setScene(addEditCustScreen);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showReportScreen() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/View/ReportsView.fxml"));
            Parent reportScreenParent = loader.load();
            Scene reportScreen = new Scene(reportScreenParent);

            ReportsViewController controller = loader.getController();
            controller.setMainApp(this);

            primaryStage.setScene(reportScreen);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public User getSessionUser() {
        return sessionUser;
    }

    public void setSessionUser(User sessionUser) {
        this.sessionUser = sessionUser;
    }

    public boolean getInitialView() {
        return initialView;
    }

    public void setInitialView(boolean value) {
        this.initialView = value;
    }
    
    public boolean throwConfirmation(String action) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initOwner(primaryStage);
        alert.getDialogPane().getStylesheets().add(getClass().getResource(Constants.CSS_FILE).toExternalForm());
        alert.setTitle("Confirm");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to " + action + "?");

        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(yes, no);
        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == yes;
    }
    
    public void throwAlert(String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initOwner(primaryStage);
        alert.getDialogPane().getStylesheets().add(getClass().getResource(Constants.CSS_FILE).toExternalForm());
        alert.setTitle("Invalid fields");
        alert.setHeaderText("Please correct the following");
        alert.getDialogPane().setContent(new Label(errorMessage));
        alert.showAndWait();
    }
    
    public void throwNotification(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initOwner(primaryStage);
        alert.getDialogPane().getStylesheets().add(getClass().getResource(Constants.CSS_FILE).toExternalForm());
        alert.setTitle("Upcoming appointment");
        alert.setHeaderText("The following appointments start in the next 15 minutes");
        alert.getDialogPane().setContent(new Label(message));
        alert.showAndWait();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
}


