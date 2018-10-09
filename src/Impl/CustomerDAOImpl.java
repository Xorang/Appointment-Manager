package Impl;

import DAO.CustomerDAO;
import DAO.DbConnectionManager;
import Model.Address;
import Model.City;
import Model.Country;
import Model.Customer;
import Util.Constants;
import com.mysql.jdbc.Statement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.ZoneId;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * MySQL implementation of the CustomerDAO interface. Provides methods for
 * manipulating customer data.
 */
public class CustomerDAOImpl implements CustomerDAO {

    @Override
    public ObservableList<Customer> getActiveCustomers() {
        String query = "select cu.customerid as custId, cu.customerName as custName, cu.active as custActive, cu.createDate as custCreate, cu.createdBy as custCreatedBy, cu.lastUpdate as custLastUpd, cu.lastUpdateBy as custLastUpdBy, "
                + "ad.addressid as addId, ad.address as addAdd1, ad.address2 as addAdd2, ad.postalCode as addPostal, ad.phone as addPhone, ad.createDate as addCreate, ad.createdBy as addCreatedBy, ad.lastUpdate as addLastUpd, ad.lastUpdateBy as addLastUpdBy, "
                + "ci.cityid as cityId, ci.city as cityCity, ci.createDate as cityCreate, ci.createdBy as cityCreatedBy, ci.lastUpdate as cityLastUpd, ci.lastUpdateBy as cityLastUpdBy, "
                + "co.countryid as coId, co.country as coCountry, co.createDate as coCreate, co.createdBy as coCreatedBy, co.lastUpdate as coLastUpd, co.lastUpdateBy as coLastUpdBy "
                + "from " + Constants.DBUSER + ".customer cu "
                + "inner join " + Constants.DBUSER + ".address ad "
                + "on cu.addressId = ad.addressid "
                + "inner join " + Constants.DBUSER + ".city ci "
                + "on ad.cityId = ci.cityid "
                + "inner join " + Constants.DBUSER + ".country co "
                + "on ci.countryId = co.countryId "
                + "where cu.active = 1;";

        ObservableList<Customer> customers = getCustomers(query);
        return customers;
    }

    @Override
    public ObservableList<Customer> getAllCustomers() {
        String query = "select cu.customerid as custId, cu.customerName as custName, cu.active as custActive, cu.createDate as custCreate, cu.createdBy as custCreatedBy, cu.lastUpdate as custLastUpd, cu.lastUpdateBy as custLastUpdBy, "
                + "ad.addressid as addId, ad.address as addAdd1, ad.address2 as addAdd2, ad.postalCode as addPostal, ad.phone as addPhone, ad.createDate as addCreate, ad.createdBy as addCreatedBy, ad.lastUpdate as addLastUpd, ad.lastUpdateBy as addLastUpdBy, "
                + "ci.cityid as cityId, ci.city as cityCity, ci.createDate as cityCreate, ci.createdBy as cityCreatedBy, ci.lastUpdate as cityLastUpd, ci.lastUpdateBy as cityLastUpdBy, "
                + "co.countryid as coId, co.country as coCountry, co.createDate as coCreate, co.createdBy as coCreatedBy, co.lastUpdate as coLastUpd, co.lastUpdateBy as coLastUpdBy "
                + "from " + Constants.DBUSER + ".customer cu "
                + "inner join " + Constants.DBUSER + ".address ad "
                + "on cu.addressId = ad.addressid "
                + "inner join " + Constants.DBUSER + ".city ci "
                + "on ad.cityId = ci.cityid "
                + "inner join " + Constants.DBUSER + ".country co "
                + "on ci.countryId = co.countryId;";

        ObservableList<Customer> customers = getCustomers(query);
        return customers;
    }

    private ObservableList<Customer> getCustomers(String query) {
        ObservableList<Customer> result = FXCollections.observableArrayList();
        DbConnectionManager db = DbConnectionManager.getInstance();
        try {
            Connection con = db.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Country country = new Country();
                City city = new City();
                Address address = new Address();
                Customer customer = new Customer();

                country.setCountryId(rs.getInt("coId"));
                country.setCountry(rs.getString("coCountry"));
                country.setCreateDate(rs.getDate("coCreate").toLocalDate());
                country.setCreatedBy(rs.getString("coCreatedBy"));
                country.setLastUpdate(rs.getTimestamp("coLastUpd").toLocalDateTime().atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime());
                country.setLastUpdateBy(rs.getString("coLastUpdBy"));

                city.setCityId(rs.getInt("cityId"));
                city.setCityName(rs.getString("cityCity"));
                city.setCountry(country);
                city.setCreateDate(rs.getDate("cityCreate").toLocalDate());
                city.setCreatedBy(rs.getString("cityCreatedBy"));
                city.setLastUpdate(rs.getTimestamp("cityLastUpd").toLocalDateTime().atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime());
                city.setLastUpdateBy(rs.getString("cityLastUpdBy"));

                address.setAddressId(rs.getInt("addId"));
                address.setAddressLine1(rs.getString("addAdd1"));
                address.setAddressLine2(rs.getString("addAdd2"));
                address.setCity(city);
                address.setCreateDate(rs.getDate("addCreate").toLocalDate());
                address.setCreatedBy(rs.getString("addCreatedBy"));
                address.setLastUpdate(rs.getTimestamp("addLastUpd").toLocalDateTime().atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime());
                address.setLastUpdateBy(rs.getString("addLastUpdBy"));
                address.setPhone(rs.getString("addPhone"));
                address.setPostalCode(rs.getString("addPostal"));

                customer.setCustomerId(rs.getInt("custId"));
                customer.setCustomerName(rs.getString("custName"));
                customer.setActive(rs.getInt("custActive"));
                customer.setAddress(address);
                customer.setCreateDate(rs.getDate("custCreate").toLocalDate());
                customer.setCreatedBy(rs.getString("custCreatedBy"));
                customer.setLastUpdate(rs.getTimestamp("custLastUpd").toLocalDateTime().atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime());
                customer.setLastUpdateBy(rs.getString("custLastUpdBy"));

                result.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.closeConnection();
        }

        return result;
    }

    @Override
    public boolean addCustomer(Customer cust) {
        boolean result = false;
        int countryId = checkCountry(cust.getAddress().getCity().getCountry());
        cust.getAddress().getCity().getCountry().setCountryId(countryId);
        int cityId = checkCity(cust.getAddress().getCity());
        cust.getAddress().getCity().setCityId(cityId);
        int addressId = checkAddress(cust.getAddress());
        cust.getAddress().setAddressId(addressId);

        String query = "insert into " + Constants.DBUSER + ".customer "
                + "(customerId, customerName, addressId, active, createDate, createdBy, lastUpdate, lastUpdateBy) "
                + "values (?, ?, ?, ?, ?, ?, ?, ?);";

        DbConnectionManager db = DbConnectionManager.getInstance();
        try (Connection con = db.getConnection()) {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, cust.getCustomerId());
            ps.setString(2, cust.getCustomerName());
            ps.setInt(3, addressId);
            ps.setInt(4, cust.getActive());
            ps.setDate(5, Date.valueOf(cust.getCreateDate()));
            ps.setString(6, cust.getCreatedBy());
            ps.setTimestamp(7, Timestamp.valueOf(cust.getLastUpdate().atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime()));
            ps.setString(8, cust.getLastUpdateBy());

            int indicator = ps.executeUpdate();
            result = indicator > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    private int checkCountry(Country country) {
        int id = -1;
        String query = "select countryid from " + Constants.DBUSER + ".country where country = ?;";
        DbConnectionManager db = DbConnectionManager.getInstance();
        try (Connection con = db.getConnection()) {
            PreparedStatement select = con.prepareStatement(query);
            select.setString(1, country.getCountry());
            ResultSet rs = select.executeQuery();
            if (rs.next()) {
                id = rs.getInt("countryid");
                rs.close();
                select.close();
            } else {
                String insertQuery = "insert into " + Constants.DBUSER + ".country "
                        + "(country, createDate, createdBy, lastUpdate, lastUpdateBy)"
                        + "values (?, ?, ?, ?, ?);";
                PreparedStatement insert = con.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
                insert.setString(1, country.getCountry()); // country
                insert.setDate(2, Date.valueOf(country.getCreateDate())); // createDate
                insert.setString(3, country.getCreatedBy()); // createdBy
                insert.setTimestamp(4, Timestamp.valueOf(country.getLastUpdate().atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime())); // lastUpdate
                insert.setString(5, country.getLastUpdateBy()); // lastUpdateBy
                int indicator = insert.executeUpdate();
                if (indicator > 0) {
                    ResultSet generatedKeys = insert.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        id = generatedKeys.getInt(1);
                    }
                }
                insert.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return id;
    }

    private int checkCity(City city) {
        int id = -1;
        String query = "select cityid from " + Constants.DBUSER + ".city where city = ? and countryId = ?";
        DbConnectionManager db = DbConnectionManager.getInstance();
        try (Connection con = db.getConnection()) {
            PreparedStatement select = con.prepareStatement(query);
            select.setString(1, city.getCityName());
            select.setInt(2, city.getCountry().getCountryId());
            ResultSet rs = select.executeQuery();
            if (rs.next()) {
                id = rs.getInt("cityid");
                rs.close();
                select.close();
            } else {
                String insertQuery = "insert into " + Constants.DBUSER + ".city "
                        + "(city, countryId, createDate, createdBy, lastUpdate, lastUpdateBy)"
                        + "values (?, ?, ?, ?, ?, ?);";
                PreparedStatement insert = con.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
                insert.setString(1, city.getCityName()); // city
                insert.setInt(2, city.getCountry().getCountryId()); // countryId
                insert.setDate(3, Date.valueOf(city.getCreateDate())); // createDate
                insert.setString(4, city.getCreatedBy()); // createdBy
                insert.setTimestamp(5, Timestamp.valueOf(city.getLastUpdate().atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime())); // lastUpdate
                insert.setString(6, city.getLastUpdateBy()); // lastUpdateBy
                int indicator = insert.executeUpdate();
                if (indicator > 0) {
                    ResultSet generatedKeys = insert.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        id = generatedKeys.getInt(1);
                    }
                }
                insert.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return id;
    }

    private int checkAddress(Address address) {
        int id = -1;
        String query = "select addressid from " + Constants.DBUSER + ".address where address = ? and address2 = ? and cityId = ? and postalCode = ? and phone = ?;";
        DbConnectionManager db = DbConnectionManager.getInstance();
        try (Connection con = db.getConnection()) {
            PreparedStatement select = con.prepareStatement(query);
            select.setString(1, address.getAddressLine1());
            select.setString(2, address.getAddressLine2());
            select.setInt(3, address.getCity().getCityId());
            select.setString(4, address.getPostalCode());
            select.setString(5, address.getPhone());
            ResultSet rs = select.executeQuery();
            if (rs.next()) {
                id = rs.getInt("addressid");
                rs.close();
                select.close();
            } else {
                String insertQuery = "insert into " + Constants.DBUSER + ".address "
                        + "(address, address2, cityId, postalCode, phone, createDate, createdBy, lastUpdate, lastUpdateBy)"
                        + "values (?, ?, ?, ?, ?, ?, ?, ?, ?);";
                PreparedStatement insert = con.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
                insert.setString(1, address.getAddressLine1()); // address
                insert.setString(2, address.getAddressLine2()); // address2
                insert.setInt(3, address.getCity().getCityId()); // cityId
                insert.setString(4, address.getPostalCode()); // postalCode
                insert.setString(5, address.getPhone()); // phone
                insert.setDate(6, Date.valueOf(address.getCreateDate())); // createDate
                insert.setString(7, address.getCreatedBy()); // createdBy
                insert.setTimestamp(8, Timestamp.valueOf(address.getLastUpdate().atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime())); // lastUpdate
                insert.setString(9, address.getLastUpdateBy()); // lastUpdateBy
                int indicator = insert.executeUpdate();
                if (indicator > 0) {
                    ResultSet generatedKeys = insert.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        id = generatedKeys.getInt(1);
                    }
                }
                insert.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return id;
    }

    @Override
    public boolean updateCustomer(Customer cust) {
        boolean result = false;
        int countryId = checkCountry(cust.getAddress().getCity().getCountry());
        cust.getAddress().getCity().getCountry().setCountryId(countryId);
        int cityId = checkCity(cust.getAddress().getCity());
        cust.getAddress().getCity().setCityId(cityId);
        int addressId = checkAddress(cust.getAddress());
        cust.getAddress().setAddressId(addressId);

        String query = "update " + Constants.DBUSER + ".customer set "
                + "customerId = ?, "
                + "customerName = ?, "
                + "addressId = ?, "
                + "active = ?, "
                + "lastUpdate = ?, "
                + "lastUpdateBy = ? "
                + "where customerId = ?;";
        DbConnectionManager db = DbConnectionManager.getInstance();
        try (Connection con = db.getConnection()) {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, cust.getCustomerId());
            ps.setString(2, cust.getCustomerName()); // customerName
            ps.setInt(3, cust.getAddress().getAddressId()); // addressId
            ps.setInt(4, cust.getActive()); // active
            ps.setTimestamp(5, Timestamp.valueOf(cust.getLastUpdate().atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime()));// lastUpdate
            ps.setString(6, cust.getLastUpdateBy());// lastUpdateBy
            ps.setInt(7, cust.getCustomerId());

            int indicator = ps.executeUpdate();
            result = indicator > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public boolean deleteCustomer(Customer cust) {
        boolean result = false;
        String query = "delete from " + Constants.DBUSER + ".customer where customerid = ?;";
        DbConnectionManager db = DbConnectionManager.getInstance();
        try (Connection con = db.getConnection()) {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, cust.getCustomerId());
            int indicator = ps.executeUpdate();
            result = indicator > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
}
