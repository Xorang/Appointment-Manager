package View;

import AppointmentManager.Main;
import DAO.AppointmentDAO;
import DAO.CustomerDAO;
import DAO.UserDAO;
import Impl.AppointmentDAOImpl;
import Impl.CustomerDAOImpl;
import Impl.UserDAOImpl;
import Model.Appointment;
import Model.AptTypeSummary;
import Model.Customer;
import Model.User;
import Util.AccessLogger;
import Util.Constants;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.StringConverter;

/**
 * Controller for all available reports. Fulfills REQUIREMENT I.
*/
public class ReportsViewController {
    
    // Fields for Appointment Types by Month tab - REQUIREMENT I1
    ObservableList<AptTypeSummary> janAptData = FXCollections.observableArrayList();
    @FXML
    private TableView<AptTypeSummary> janAptTypes;
    @FXML
    private TableColumn<AptTypeSummary, String> janTypes;
    @FXML
    private TableColumn<AptTypeSummary, Integer> janCount;
    
    ObservableList<AptTypeSummary> febAptData = FXCollections.observableArrayList();
    @FXML
    private TableView<AptTypeSummary> febAptTypes;
    @FXML
    private TableColumn<AptTypeSummary, String> febTypes;
    @FXML
    private TableColumn<AptTypeSummary, Integer> febCount;
    
    ObservableList<AptTypeSummary> marAptData = FXCollections.observableArrayList();
    @FXML
    private TableView<AptTypeSummary> marAptTypes;
    @FXML
    private TableColumn<AptTypeSummary, String> marTypes;
    @FXML
    private TableColumn<AptTypeSummary, Integer> marCount;
    
    ObservableList<AptTypeSummary> aprAptData = FXCollections.observableArrayList();
    @FXML
    private TableView<AptTypeSummary> aprAptTypes;
    @FXML
    private TableColumn<AptTypeSummary, String> aprTypes;
    @FXML
    private TableColumn<AptTypeSummary, Integer> aprCount;
    
    ObservableList<AptTypeSummary> mayAptData = FXCollections.observableArrayList();
    @FXML
    private TableView<AptTypeSummary> mayAptTypes;
    @FXML
    private TableColumn<AptTypeSummary, String> mayTypes;
    @FXML
    private TableColumn<AptTypeSummary, Integer> mayCount;
    
    ObservableList<AptTypeSummary> junAptData = FXCollections.observableArrayList();
    @FXML
    private TableView<AptTypeSummary> junAptTypes;
    @FXML
    private TableColumn<AptTypeSummary, String> junTypes;
    @FXML
    private TableColumn<AptTypeSummary, Integer> junCount;
    
    ObservableList<AptTypeSummary> julAptData = FXCollections.observableArrayList();
    @FXML
    private TableView<AptTypeSummary> julAptTypes;
    @FXML
    private TableColumn<AptTypeSummary, String> julTypes;
    @FXML
    private TableColumn<AptTypeSummary, Integer> julCount;
    
    ObservableList<AptTypeSummary> augAptData = FXCollections.observableArrayList();
    @FXML
    private TableView<AptTypeSummary> augAptTypes;
    @FXML
    private TableColumn<AptTypeSummary, String> augTypes;
    @FXML
    private TableColumn<AptTypeSummary, Integer> augCount;
    
    ObservableList<AptTypeSummary> sepAptData = FXCollections.observableArrayList();
    @FXML
    private TableView<AptTypeSummary> sepAptTypes;
    @FXML
    private TableColumn<AptTypeSummary, String> sepTypes;
    @FXML
    private TableColumn<AptTypeSummary, Integer> sepCount;
    
    ObservableList<AptTypeSummary> octAptData = FXCollections.observableArrayList();
    @FXML
    private TableView<AptTypeSummary> octAptTypes;
    @FXML
    private TableColumn<AptTypeSummary, String> octTypes;
    @FXML
    private TableColumn<AptTypeSummary, Integer> octCount;
    
    ObservableList<AptTypeSummary> novAptData = FXCollections.observableArrayList();
    @FXML
    private TableView<AptTypeSummary> novAptTypes;
    @FXML
    private TableColumn<AptTypeSummary, String> novTypes;
    @FXML
    private TableColumn<AptTypeSummary, Integer> novCount;
    
    ObservableList<AptTypeSummary> decAptData = FXCollections.observableArrayList();
    @FXML
    private TableView<AptTypeSummary> decAptTypes;
    @FXML
    private TableColumn<AptTypeSummary, String> decTypes;
    @FXML
    private TableColumn<AptTypeSummary, Integer> decCount;
    
    private ArrayList<ObservableList> dataList = new ArrayList<>();
    private ObservableList<String> availableYears = FXCollections.observableArrayList();
    @FXML
    private ChoiceBox<String> yearChoiceBox;
    @FXML
    private Button aptRefreshButton;
    
    // Fields for Schedule by Consultant - REQUIREMENT I2
    private ObservableList<User> users = FXCollections.observableArrayList();
    @FXML
    private ChoiceBox conChoiceBox;
    @FXML
    private Button conRefreshButton;
    private ObservableList<Appointment> conApts = FXCollections.observableArrayList();
    @FXML
    private TableView<Appointment> conTable;
    @FXML
    private TableColumn<Appointment, String> conStartColumn;
    @FXML
    private TableColumn<Appointment, String> conEndColumn;
    @FXML
    private TableColumn<Appointment, String> conCustColumn;
    @FXML
    private TableColumn<Appointment, String> conConColumn;
    @FXML
    private TableColumn<Appointment, String> conLocColumn;
    @FXML
    private TableColumn<Appointment, String> conTypeColumn;
    
    // Fields for Schedule by Customer - REQUIREMENT I3
    private ObservableList<Customer> customers = FXCollections.observableArrayList();
    @FXML
    private ChoiceBox custChoiceBox;
    @FXML
    private Button custRefreshButton;
    private ObservableList<Appointment> custApts = FXCollections.observableArrayList();
    @FXML
    private TableView<Appointment> custTable;
    @FXML
    private TableColumn<Appointment, String> custStartColumn;
    @FXML
    private TableColumn<Appointment, String> custEndColumn;
    @FXML
    private TableColumn<Appointment, String> custCustColumn;
    @FXML
    private TableColumn<Appointment, String> custConColumn;
    @FXML
    private TableColumn<Appointment, String> custLocColumn;
    @FXML
    private TableColumn<Appointment, String> custTypeColumn;
    
    // General fields
    @FXML
    private Button appointmentsButton;
    @FXML
    private Button customersButton;
    @FXML
    private Button logoutButton;
    
    private Main Main;
    private AppointmentDAO aptDao;
    private CustomerDAO custDao;
    private UserDAO userDao;
    
    public ReportsViewController() {
    }
    
    @FXML
    public void initialize() {
        logoutButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream(Constants.LOGOUT_ICON))));
        aptDao = new AppointmentDAOImpl();
        custDao = new CustomerDAOImpl();
        userDao = new UserDAOImpl();
        
        // Appointment Types by Month section - REQUIREMENT I1
        availableYears = aptDao.getYearList();
        yearChoiceBox.setItems(availableYears);
        yearChoiceBox.getSelectionModel().selectFirst();
        aptRefreshButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream(Constants.REFRESH_ICON))));
        
        dataList.add(janAptData);
        dataList.add(febAptData);
        dataList.add(marAptData);
        dataList.add(aprAptData);
        dataList.add(mayAptData);
        dataList.add(junAptData);
        dataList.add(julAptData);
        dataList.add(augAptData);
        dataList.add(sepAptData);
        dataList.add(octAptData);
        dataList.add(novAptData);
        dataList.add(decAptData);
        
        refreshAptData();
        // REQUIREMENT G - Use lambdas to efficiently set column cell values
        janTypes.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType()));
        janCount.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getCount()).asObject());
        febTypes.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType()));
        febCount.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getCount()).asObject());
        marTypes.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType()));
        marCount.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getCount()).asObject());
        aprTypes.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType()));
        aprCount.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getCount()).asObject());
        mayTypes.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType()));
        mayCount.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getCount()).asObject());
        junTypes.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType()));
        junCount.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getCount()).asObject());
        julTypes.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType()));
        julCount.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getCount()).asObject());
        augTypes.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType()));
        augCount.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getCount()).asObject());
        sepTypes.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType()));
        sepCount.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getCount()).asObject());
        octTypes.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType()));
        octCount.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getCount()).asObject());
        novTypes.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType()));
        novCount.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getCount()).asObject());
        decTypes.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType()));
        decCount.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getCount()).asObject());
        
        // Schedule by Consultant section - REQUIREMENT I2
        users = userDao.getActiveUsers();
        conChoiceBox.setConverter(new StringConverter<User>() {
            @Override
            public String toString(User object) {
                return object.getUserName();
            }
            
            @Override
            public User fromString(String string) {
                return null; // no conversion from string needed
            }
        });
        conChoiceBox.setItems(users);
        conRefreshButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream(Constants.REFRESH_ICON))));
        
        // Schedule by Customer section - REQUIREMENT I3
        customers = custDao.getActiveCustomers();
        custChoiceBox.setConverter(new StringConverter<Customer>() {
            @Override
            public String toString(Customer object) {
                return object.getCustomerName();
            }
            @Override
            public Customer fromString(String string) {
                return null; // no conversion from string needed
            }
        });
        custChoiceBox.setItems(customers);
        custRefreshButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream(Constants.REFRESH_ICON))));
    }
    
    public void setMainApp(Main Main) {
        this.Main = Main;
    }
    
    public void handleAppointmentsButton() {
        Main.showAppointmentScreen();
    }
    
    public void handleCustomersButton() {
        Main.showCustomerScreen();
    }
    
    public void handleLogoutButton() {
        boolean logout = Main.throwConfirmation("log out");
        if (logout) {
            AccessLogger.logSignOut(Main.getSessionUser().getUserName());
            Main.showLoginScreen();
        }
    }
    
    /********************
     * Appointment Types by Month tab - REQUIREMENT I1
    ********************/
    
    public void handleAptRefreshButton() {
        janAptData.clear();
        febAptData.clear();
        marAptData.clear();
        aprAptData.clear();
        mayAptData.clear();
        junAptData.clear();
        julAptData.clear();
        augAptData.clear();
        sepAptData.clear();
        octAptData.clear();
        novAptData.clear();
        decAptData.clear();
        refreshAptData();
    }
    
    private void refreshAptData() {
        dataList = aptDao.getAppointmentTypesByMonth(dataList, yearChoiceBox.getValue());
        janAptTypes.setItems(janAptData);
        febAptTypes.setItems(febAptData);
        marAptTypes.setItems(marAptData);
        aprAptTypes.setItems(aprAptData);
        mayAptTypes.setItems(mayAptData);
        junAptTypes.setItems(junAptData);
        julAptTypes.setItems(julAptData);
        augAptTypes.setItems(augAptData);
        sepAptTypes.setItems(sepAptData);
        octAptTypes.setItems(octAptData);
        novAptTypes.setItems(novAptData);
        decAptTypes.setItems(decAptData);
    }
    
    /********************
     * Schedule by Consultant tab - REQUIREMENT I2
    ********************/
    
    public void handleConRefreshButton() {
        conApts = aptDao.getAppointmentsByConsultant((User) conChoiceBox.getValue());
        conTable.setItems(conApts);
        // REQUIREMENT G - Use lambdas to efficiently set column cell values
        conStartColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStart().format(DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a"))));
        conEndColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEnd().format(DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a"))));
        conCustColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCustomer().getCustomerName()));
        conConColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUser().getUserName()));
        conLocColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLocation()));
        conTypeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));
    }
    
    /********************
     * Schedule by Customer tab - REQUIREMENT I3
    ********************/
    
    public void handleCustRefreshButton() {
        custApts = aptDao.getAppointmentsByCustomer((Customer) custChoiceBox.getValue());
        custTable.setItems(custApts);
        // REQUIREMENT G - Use lambdas to efficiently set column cell values
        custStartColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStart().format(DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a"))));
        custEndColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEnd().format(DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a"))));
        custCustColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCustomer().getCustomerName()));
        custConColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUser().getUserName()));
        custLocColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLocation()));
        custTypeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));
    }
}
