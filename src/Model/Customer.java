package Model;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Storing customer information
*/
public class Customer {

    private int customerId;
    private String customerName;
    private Address address;
    private int active;
    private LocalDate createDate;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String lastUpdateBy;

    public Customer(int customerId, String customerName, Address address, int active, LocalDate createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdateBy) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.address = address;
        this.active = active;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBy = lastUpdateBy;
    }

    public Customer() {
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int activeCode) {
        this.active = activeCode;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    /**
     * Override equals to allow more portability between objects and lists
     *
     * @param obj
     * @return true if both objects have the same customerId.
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Customer) || obj == null) {
            return false;
        }
        Customer otherCust = (Customer) obj;
        return this.getCustomerId() == otherCust.getCustomerId();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + this.customerId;
        return hash;
    }

}