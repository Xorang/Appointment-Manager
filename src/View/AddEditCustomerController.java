package View;

import AppointmentManager.Main;
import DAO.CustomerDAO;
import Impl.CustomerDAOImpl;
import Model.Address;
import Model.City;
import Model.Country;
import Model.Customer;
import Model.User;
import Util.AccessLogger;
import Util.Constants.Mode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * Controller for adding and editing customers. Fulfills REQUIREMENT B
 */
public class AddEditCustomerController {
    
    @FXML
    private Label actionLabel;
    @FXML
    private TextField customerNameField;
    @FXML
    private CheckBox activeFlag;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField addr1Field;
    @FXML
    private TextField addr2Field;
    @FXML
    private TextField countryField;
    @FXML
    private TextField cityField;
    @FXML
    private TextField postalCodeField;
    @FXML
    private Button cancelButton;
    @FXML
    private Button saveButton;
    
    private Customer cust;
    private Main Main;
    private Mode mode;
    
    private CustomerDAO custDao;

    public AddEditCustomerController() {
    }
    
    @FXML
    public void initialize() {
        custDao = new CustomerDAOImpl();
    }
    
    public void setMainApp(Main Main) {
        this.Main = Main;
    }
    
    public void setCustomer(Mode mode, Customer cust) {
        this.mode = mode;
        this.cust = cust;
        
        switch (mode) {
            case ADD:
                actionLabel.setText("Add Customer");
                activeFlag.setSelected(true);
                break;
            case EDIT:
                actionLabel.setText("Edit Customer");
                customerNameField.setText(cust.getCustomerName());
                if(cust.getActive() == 1) {
                    activeFlag.setSelected(true);
                } else {
                    activeFlag.setSelected(false);
                }
                phoneField.setText(cust.getAddress().getPhone());
                addr1Field.setText(cust.getAddress().getAddressLine1());
                addr2Field.setText(cust.getAddress().getAddressLine2());
                countryField.setText(cust.getAddress().getCity().getCountry().getCountry());
                cityField.setText(cust.getAddress().getCity().getCityName());
                postalCodeField.setText(cust.getAddress().getPostalCode());
                break;
        }
    }
    
    public void handleCancel() {
        if(Main.throwConfirmation("cancel")) {
            Main.showCustomerScreen();
        }
    }
    
    public void handleSave() {
        if (isInputValid()) {
            User sessionUser = Main.getSessionUser();
            if(cust == null) {
                Country country = new Country();
                country.setCreateDate(LocalDate.now());
                country.setCreatedBy(sessionUser.getUserName());
                City city = new City();
                city.setCountry(country);
                city.setCreateDate(LocalDate.now());
                city.setCreatedBy(sessionUser.getUserName());
                Address address = new Address();
                address.setCity(city);
                address.setCreateDate(LocalDate.now());
                address.setCreatedBy(sessionUser.getUserName());
                cust = new Customer();
                cust.setAddress(address);
                cust.setCreateDate(LocalDate.now());
                cust.setCreatedBy(sessionUser.getUserName());                
            }
            cust.setCustomerName(customerNameField.getText());
            if(activeFlag.isSelected()) {
                cust.setActive(1);
            } else {
                cust.setActive(0);
            }
            cust.getAddress().setPhone(phoneField.getText());
            cust.getAddress().setAddressLine1(addr1Field.getText());
            cust.getAddress().setAddressLine2(addr2Field.getText());
            cust.getAddress().getCity().getCountry().setCountry(countryField.getText());
            cust.getAddress().getCity().setCityName(cityField.getText());
            cust.getAddress().setPostalCode(postalCodeField.getText());
            
            cust.getAddress().getCity().getCountry().setLastUpdate(LocalDateTime.now());
            cust.getAddress().getCity().getCountry().setLastUpdateBy(sessionUser.getUserName());
            cust.getAddress().getCity().setLastUpdate(LocalDateTime.now());
            cust.getAddress().getCity().setLastUpdateBy(sessionUser.getUserName());
            cust.getAddress().setLastUpdate(LocalDateTime.now());
            cust.getAddress().setLastUpdateBy(sessionUser.getUserName());
            cust.setLastUpdate(LocalDateTime.now());
            cust.setLastUpdateBy(sessionUser.getUserName());
            
            boolean success = false;
            switch (mode) {
                case ADD:
                    success = custDao.addCustomer(cust);
                    AccessLogger.logAction(sessionUser.getUserName(), "created a new customer record");
                    break;
                case EDIT:
                    success = custDao.updateCustomer(cust);
                    AccessLogger.logAction(sessionUser.getUserName(), "modified a customer record");
                    break;
            }
            if(success) {
                Main.showCustomerScreen();
            } else {
                Main.throwAlert("Unable to update database. Please try again.");
            }
        }
    }
    
    private boolean isInputValid() {
        String errorMessage = "";
        String phoneFormat = "\\d{3}-\\d{3}-\\d{4}";
        // REQUIREMENT F3
        if (customerNameField.getText() == null || customerNameField.getText().length() == 0) {
            errorMessage += "You must enter a customer name";
        }
        if (phoneField.getText() == null || phoneField.getText().length() == 0) {
            errorMessage += "You must enter a phone number";
        } else {
            if(!phoneField.getText().matches(phoneFormat)) {
                errorMessage += "You must enter a valid phone number in ###-###-#### format";
            }
        }
        if (addr1Field.getText() == null || addr1Field.getText().length() == 0) {
            errorMessage += "You must enter an address";
        }
        if (countryField.getText() == null || countryField.getText().length() == 0) {
            errorMessage += "You must enter a country";
        }
        if (cityField.getText() == null || cityField.getText().length() == 0) {
            errorMessage += "You must enter a city";
        }
        if (postalCodeField.getText() == null || postalCodeField.getText().length() == 0) {
            errorMessage += "You must enter a postal code";
        }
        
        if (errorMessage.length() == 0) {
            return true;
        } else {
            Main.throwAlert(errorMessage);
            return false;
        }
    }
    
}
