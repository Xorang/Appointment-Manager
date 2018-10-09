package View;

import AppointmentManager.Main;
import DAO.AppointmentDAO;
import DAO.CustomerDAO;
import DAO.UserDAO;
import Impl.AppointmentDAOImpl;
import Impl.CustomerDAOImpl;
import Impl.UserDAOImpl;
import Model.Appointment;
import Model.Customer;
import Model.User;
import Util.AccessLogger;
import Util.Constants;
import Util.Constants.Mode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

/**
 * Controller for adding and editing appointments. Fulfills REQUIREMENT C.
 */
public class AddEditAppointmentController {

    @FXML
    private Label actionLabel;
    @FXML
    private ChoiceBox customerChoice;
    @FXML
    private DatePicker startDateField;
    @FXML
    private TextField startTimeField;
    @FXML
    private TextField endTimeField;
    @FXML
    private TextField locationField;
    @FXML
    private ChoiceBox consultantChoice;
    @FXML
    private TextField typeField;
    @FXML
    private TextField titleField;
    @FXML
    private TextArea descriptionField;
    @FXML
    private Button cancelButton;
    @FXML
    private Button saveButton;

    private Appointment apt;
    private Main Main;
    private Mode mode;
    private int editingAptId;

    private AppointmentDAO aptDao;
    private UserDAO userDao;
    private CustomerDAO custDao;

    private ObservableList<User> users = FXCollections.observableArrayList();
    private ObservableList<Customer> customers = FXCollections.observableArrayList();

    public AddEditAppointmentController() {
    }

    @FXML
    private void initialize() {
        editingAptId = -1;
        aptDao = new AppointmentDAOImpl();
        userDao = new UserDAOImpl();
        custDao = new CustomerDAOImpl();

        users = userDao.getActiveUsers();
        customers = custDao.getActiveCustomers();
        consultantChoice.setConverter(new StringConverter<User>() {
            @Override
            public String toString(User object) {
                return object.getUserName();
            }

            @Override
            public User fromString(String string) {
                return null; // no conversion from string needed
            }
        });
        consultantChoice.setItems(users);
        customerChoice.setConverter(new StringConverter<Customer>() {
            @Override
            public String toString(Customer object) {
                return object.getCustomerName();
            }

            @Override
            public Customer fromString(String string) {
                return null; // no conversion from string needed
            }
        });
        customerChoice.setItems(customers);
    }

    public void setMainApp(Main Main) {
        this.Main = Main;
    }

    public void setAppointment(Mode mode, Appointment apt) {
        this.mode = mode;
        this.apt = apt;

        switch (mode) {
            case ADD:
                actionLabel.setText("Add Appointment");
                break;
            case EDIT:
                editingAptId = apt.getAppointmentId();
                actionLabel.setText("Edit Appointment");
                customerChoice.setValue(apt.getCustomer());
                startDateField.setValue(apt.getStart().toLocalDate());
                startTimeField.setText(apt.getStart().format(DateTimeFormatter.ofPattern("hh:mm a")));
                endTimeField.setText(apt.getEnd().format(DateTimeFormatter.ofPattern("hh:mm a")));
                locationField.setText(apt.getLocation());
                consultantChoice.setValue(apt.getUser());
                typeField.setText(apt.getType());
                titleField.setText(apt.getTitle());
                descriptionField.setText(apt.getDescription());
                break;
        }
    }

    @FXML
    private void handleCancel() {
        if (Main.throwConfirmation("cancel")) {
            Main.showAppointmentScreen();
            editingAptId = -1;
        }
    }

    @FXML
    private void handleSave() {
        if (isInputValid()) {
            LocalDateTime start = LocalDateTime.of(startDateField.getValue(), LocalTime.parse(startTimeField.getText(), DateTimeFormatter.ofPattern("hh:mm a")));
            LocalDateTime end = LocalDateTime.of(startDateField.getValue(), LocalTime.parse(endTimeField.getText(), DateTimeFormatter.ofPattern("hh:mm a")));
            if (apt == null) {
                apt = new Appointment();
                apt.setCreateDate(LocalDate.now());
                apt.setCreatedBy(Main.getSessionUser().getUserName());
            }
            apt.setContact(((User) consultantChoice.getValue()).getUserName());
            apt.setCustomer((Customer) customerChoice.getValue());
            apt.setDescription(descriptionField.getText());
            apt.setEnd(end);
            apt.setLastUpdate(LocalDateTime.now());
            apt.setLastUpdateBy(Main.getSessionUser().getUserName());
            apt.setLocation(locationField.getText());
            apt.setStart(start);
            apt.setTitle(titleField.getText());
            apt.setType(typeField.getText());
            apt.setUrl("");
            apt.setUser((User) consultantChoice.getValue());
            boolean success = false;
            switch (mode) {
                case ADD:
                    success = aptDao.addAppointment(apt);
                    AccessLogger.logAction(Main.getSessionUser().getUserName(), "created a new appointment record");
                    break;
                case EDIT:
                    success = aptDao.updateAppointment(apt);
                    AccessLogger.logAction(Main.getSessionUser().getUserName(), "modified a appointment record");
                    break;
            }
            if (success) {
                Main.showAppointmentScreen();
            } else {
                Main.throwAlert("Unable to update database. Please try again.");
            }
        }
    }

    private boolean isInputValid() {
        String errorMessage = "";
        LocalTime startTime = null;
        LocalTime endTime = null;
        // REQUIREMENT F3
        if (customerChoice.getSelectionModel().isEmpty()) {
            errorMessage += "You must select a customer.\n";
        }
        if (startDateField.getValue() == null) {
            errorMessage += "You must select a date.\n";
        }
        if (startTimeField.getText() == null || startTimeField.getText().length() == 0) {
            errorMessage += "You must enter a start time.\n";
        } else {
            try {
                startTime = LocalTime.parse(startTimeField.getText(), DateTimeFormatter.ofPattern("hh:mm a"));
                // REQUIREMENT F1
                if (startTime.isBefore(LocalTime.of(Constants.START_OF_BUSINESS, 0))
                        || startTime.isAfter(LocalTime.of(Constants.CLOSE_OF_BUSINESS, 0))) {
                    errorMessage += "Start time must be during business hours, 8AM - 5PM.\n";
                }
            } catch (DateTimeParseException e) {
                errorMessage += "You must enter a valid start time in hh:mm AM/PM format.\n";
            }
        }
        if (endTimeField.getText() == null || endTimeField.getText().length() == 0) {
            errorMessage += "You must enter an end time.\n";
        } else {
            try {
                endTime = LocalTime.parse(endTimeField.getText(), DateTimeFormatter.ofPattern("hh:mm a"));
                if (endTime.isBefore(startTime)) {
                    errorMessage += "End time cannot be before start time.\n";
                }
            } catch (DateTimeParseException e) {
                errorMessage += "You must enter a valid end time in hh:mm AM/PM format.\n";
            }
        }
        if (locationField.getText() == null || locationField.getText().length() == 0) {
            errorMessage += "You must enter a location.\n";
        }
        if (consultantChoice.getSelectionModel().isEmpty()) {
            errorMessage += "You must select a consultant.\n";
        }
        if (typeField.getText() == null || typeField.getText().length() == 0) {
            errorMessage += "You must enter a type.\n";
        }
        if (titleField.getText() == null || titleField.getText().length() == 0) {
            errorMessage += "You must enter a title.\n";
        }
        if (descriptionField.getText() == null || descriptionField.getText().length() == 0) {
            errorMessage += "You must enter a description.\n";
        }

        if (errorMessage.length() == 0) {
            // REQUIREMENT F2
            boolean conflicts = checkForConflicts(startTime, endTime);
            if (conflicts) {
                Main.throwAlert("This appointment conflicts with an existing appointment");
                return false;
            } else {
                return true;
            }
        } else {
            Main.throwAlert(errorMessage);
            return false;
        }
    }

    private boolean checkForConflicts(LocalTime startTime, LocalTime endTime) {
        
        List<Appointment> relevantAppointments = aptDao.getAppointmentsByCustomer((Customer) customerChoice.getValue());
        relevantAppointments.addAll(aptDao.getAppointmentsByConsultant((User) consultantChoice.getValue()));
        if (relevantAppointments.size() > 0) {
            // REQUIREMENT G - Using a lambda to more efficiently check the proposed appointment times against existing appointments for conflicts
                if (relevantAppointments.stream().anyMatch(((possibleConflict)-> ( 
                        (possibleConflict.getAppointmentId() == editingAptId ) || (((possibleConflict.getStart().toLocalTime().isBefore(startTime)) &&
                        possibleConflict.getEnd().toLocalTime().isBefore(startTime)) ||
                        (possibleConflict.getStart().toLocalTime().isAfter(startTime)) &&
                        possibleConflict.getStart().toLocalTime().isAfter(endTime)))))) {
                return false;
            } else {
                return true;
            } 
        }
        return false;
    }
}
