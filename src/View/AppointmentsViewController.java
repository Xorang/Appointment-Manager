package View;

import AppointmentManager.Main;
import DAO.AppointmentDAO;
import Impl.AppointmentDAOImpl;
import Model.Appointment;
import Util.AccessLogger;
import Util.Constants;
import Util.Constants.Mode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Controller for viewing appointments
 */
public class AppointmentsViewController {

    @FXML
    private ToggleButton byMonthButton;
    @FXML
    private ToggleButton byWeekButton;

    @FXML
    private Button leftButton;
    @FXML
    private Label rangeLabel;
    @FXML
    private Button rightButton;

    @FXML
    private Button newAppointmentButton;

    private ObservableList<Appointment> appointments = FXCollections.observableArrayList();
    @FXML
    private TableView<Appointment> appointmentTable;
    @FXML
    private TableColumn<Appointment, String> startTimeColumn;
    @FXML
    private TableColumn<Appointment, String> endTimeColumn;
    @FXML
    private TableColumn<Appointment, String> customerColumn;
    @FXML
    private TableColumn<Appointment, String> consultantColumn;
    @FXML
    private TableColumn<Appointment, String> locationColumn;
    @FXML
    private TableColumn<Appointment, String> typeColumn;
    @FXML
    private TableColumn<Appointment, Boolean> actionColumn;

    @FXML
    private Button appointmentsButton;
    @FXML
    private Button customersButton;
    @FXML
    private Button reportsButton;
    @FXML
    private Button logoutButton;

    private Main Main;
    private AppointmentDAO dao;
    private LocalDate curDate;

    public AppointmentsViewController() {
    }

    @FXML
    private void initialize() {
        leftButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream(Constants.LEFT_ICON))));
        rightButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream(Constants.RIGHT_ICON))));
        newAppointmentButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream(Constants.ADD_ICON))));
        logoutButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream(Constants.LOGOUT_ICON))));
        dao = new AppointmentDAOImpl();
        curDate = LocalDate.now();
        paintMonthAppointmentInfo();
        // REQUIREMENT G - Use lambdas to efficiently set column cell values
        startTimeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStart().format(DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a"))));
        endTimeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEnd().format(DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a"))));
        customerColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCustomer().getCustomerName()));
        consultantColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUser().getUserName()));
        locationColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLocation()));
        typeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType()));
        // only show buttons for rows with data
        actionColumn.setCellValueFactory((TableColumn.CellDataFeatures<Appointment, Boolean> p) -> new SimpleBooleanProperty(p.getValue() != null));
        // add buttons to cell
        actionColumn.setCellFactory((TableColumn<Appointment, Boolean> p) -> new EditDeleteAppointment(Main, appointmentTable));
        actionColumn.setSortable(false);
    }

    public void setMainApp(Main Main) {
        this.Main = Main;
    }

    // REQUIREMENT H
    public void checkForUpcomingApts() {
        if (Main.getInitialView()) {
            Main.setInitialView(false);
            String message = "";
            List<Appointment> soonApts = new ArrayList<>();
            appointments.stream().filter(
                    apt -> apt.getStart().isAfter(LocalDateTime.now())
                    && apt.getStart().isBefore(LocalDateTime.now().plusMinutes(15)))
                    .forEach(soonApts::add); // REQUIREMENT G - Use a stream and lambda to efficiently find just the appointments in the next 15 minutes
            if (soonApts.size() > 0) {
                for (Appointment apt : soonApts) {
                    message += apt.getTitle() + " with "
                            + apt.getCustomer().getCustomerName() + " at "
                            + apt.getStart().format(DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a")) + "\n";
                }
                Main.throwNotification(message);
            }
        }
    }
    
    // REQUIREMENT D
    public void handleByMonthButton() {
        paintMonthAppointmentInfo();
    }

    // REQUIREMENT D
    public void handleByWeekButton() {
        while (curDate.getDayOfWeek() != DayOfWeek.SUNDAY) {
            curDate = curDate.minusDays(1);
        }
        paintWeekAppointmentInfo();
    }

    public void handleLeftButton() {
        if (byMonthButton.isSelected()) {
            curDate = curDate.minusMonths(1);
            paintMonthAppointmentInfo();
        } else if (byWeekButton.isSelected()) {
            curDate = curDate.minusWeeks(1);
            paintWeekAppointmentInfo();
        }
    }

    public void handleRightButton() {
        if (byMonthButton.isSelected()) {
            curDate = curDate.plusMonths(1);
            paintMonthAppointmentInfo();
        } else if (byWeekButton.isSelected()) {
            curDate = curDate.plusWeeks(1);
            paintWeekAppointmentInfo();
        }
    }

    public void handleAddButton() {
        Appointment tempApt = null;
        Main.showAddEditAppointment(Mode.ADD, tempApt);
    }

    public void handleCustomersButton() {
        Main.showCustomerScreen();
    }

    public void handleReportsButton() {
        Main.showReportScreen();
    }

    public void handleLogoutButton() {
        boolean logout = Main.throwConfirmation("log out");
        if (logout) {
            AccessLogger.logSignOut(Main.getSessionUser().getUserName());
            Main.showLoginScreen();
        }
    }

    private void paintMonthAppointmentInfo() {
        appointments = dao.getAppointmentsByMonth(curDate);
        rangeLabel.setText(curDate.getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault()) + " " + curDate.getYear());
        appointmentTable.setItems(appointments);
    }

    private void paintWeekAppointmentInfo() {
        appointments = dao.getAppointmentsByWeek(curDate);
        rangeLabel.setText(curDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)) + " - " + curDate.plusDays(6).format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)));
        appointmentTable.setItems(appointments);
    }

}
