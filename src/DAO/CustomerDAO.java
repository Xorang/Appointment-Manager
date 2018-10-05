package DAO;

import Model.Customer;
import javafx.collections.ObservableList;

/**
 * Interface for DAOs accessing customer data
 */
public interface CustomerDAO {

    ObservableList<Customer> getActiveCustomers();

    ObservableList<Customer> getAllCustomers();

    boolean deleteCustomer(Customer cust);

    boolean addCustomer(Customer cust);

    boolean updateCustomer(Customer cust);
}
