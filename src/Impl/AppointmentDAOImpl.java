package Impl;

import DAO.AppointmentDAO;
import DAO.DbConnectionManager;
import Model.Address;
import Model.Appointment;
import Model.AptTypeSummary;
import Model.City;
import Model.Country;
import Model.Customer;
import Model.User;
import Util.Constants;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * MySQL implementation of the AppointmentDAO interface. Provides methods for
 * manipulating appointment data.
 */
public class AppointmentDAOImpl implements AppointmentDAO {

    @Override
    public ObservableList<Appointment> getAppointmentsByMonth(LocalDate date) {
        String query = "select ap.appointmentid as aptId, ap.title as aptTitle, ap.description as aptDesc, ap.location as aptLoc, "
                + "ap.contact as aptCon, ap.url as aptUrl, ap.start as aptStart, ap.end as aptEnd, ap.createDate as aptCreate, "
                + "ap.createdBy as aptCreatedBy, ap.lastUpdate as aptLastUpd, ap.lastUpdateBy as aptLastUpdBy, ap.type as aptType, "
                + "cu.customerid as custId, cu.customerName as custName, cu.active as custActive, cu.createDate as custCreate, "
                + "cu.createdBy as custCreatedBy, cu.lastUpdate as custLastUpd, cu.lastUpdateBy as custLastUpdBy, "
                + "us.userid as userId, us.userName as userName, us.password as userPwd, us.active as userActive, "
                + "us.createDate as userCreate, us.createBy as userCreatedBy, us.lastUpdate as userLastUpd, us.lastUpdatedBy as userLastUpdBy, "
                + "ad.addressid as addId, ad.address as addAdd1, ad.address2 as addAdd2, ad.postalCode as addPostal, ad.phone as addPhone, "
                + "ad.createDate as addCreate, ad.createdBy as addCreatedBy, ad.lastUpdate as addLastUpd, ad.lastUpdateBy as addLastUpdBy, "
                + "ci.cityid as cityId, ci.city as cityCity, ci.createDate as cityCreate, ci.createdBy as cityCreatedBy, ci.lastUpdate as cityLastUpd, ci.lastUpdateBy as cityLastUpdBy, "
                + "co.countryid as coId, co.country as coCountry, co.createDate as coCreate, co.createdBy as coCreatedBy, co.lastUpdate as coLastUpd, co.lastUpdateBy as coLastUpdBy "
                + "from " + Constants.DBUSER + ".appointment ap "
                + "inner join " + Constants.DBUSER + ".customer cu "
                + "on ap.customerId = cu.customerid "
                + "inner join " + Constants.DBUSER + ".user us "
                + "on ap.userid = us.userid "
                + "inner join " + Constants.DBUSER + ".address ad "
                + "on cu.addressId = ad.addressid "
                + "inner join " + Constants.DBUSER + ".city ci "
                + "on ad.cityId = ci.cityid "
                + "inner join " + Constants.DBUSER + ".country co "
                + "on ci.countryId = co.countryid "
                + "where ap.start like '" + date.getYear() + "-" + extractMonth(date) + "%'"
                + "order by ap.start;";

        ObservableList<Appointment> result = getAppointments(query);
        return result;
    }

    @Override
    public ObservableList<Appointment> getAppointmentsByWeek(LocalDate date) {
        String query = "select ap.appointmentid as aptId, ap.title as aptTitle, ap.description as aptDesc, ap.location as aptLoc, "
                + "ap.contact as aptCon, ap.url as aptUrl, ap.start as aptStart, ap.end as aptEnd, ap.createDate as aptCreate, "
                + "ap.createdBy as aptCreatedBy, ap.lastUpdate as aptLastUpd, ap.lastUpdateBy as aptLastUpdBy, ap.type as aptType, "
                + "cu.customerid as custId, cu.customerName as custName, cu.active as custActive, cu.createDate as custCreate, "
                + "cu.createdBy as custCreatedBy, cu.lastUpdate as custLastUpd, cu.lastUpdateBy as custLastUpdBy, "
                + "us.userid as userId, us.userName as userName, us.password as userPwd, us.active as userActive, "
                + "us.createDate as userCreate, us.createBy as userCreatedBy, us.lastUpdate as userLastUpd, us.lastUpdatedBy as userLastUpdBy, "
                + "ad.addressid as addId, ad.address as addAdd1, ad.address2 as addAdd2, ad.postalCode as addPostal, ad.phone as addPhone, "
                + "ad.createDate as addCreate, ad.createdBy as addCreatedBy, ad.lastUpdate as addLastUpd, ad.lastUpdateBy as addLastUpdBy, "
                + "ci.cityid as cityId, ci.city as cityCity, ci.createDate as cityCreate, ci.createdBy as cityCreatedBy, ci.lastUpdate as cityLastUpd, ci.lastUpdateBy as cityLastUpdBy, "
                + "co.countryid as coId, co.country as coCountry, co.createDate as coCreate, co.createdBy as coCreatedBy, co.lastUpdate as coLastUpd, co.lastUpdateBy as coLastUpdBy "
                + "from " + Constants.DBUSER + ".appointment ap "
                + "inner join " + Constants.DBUSER + ".customer cu "
                + "on ap.customerId = cu.customerid "
                + "inner join " + Constants.DBUSER + ".user us "
                + "on ap.userid = us.userid "
                + "inner join " + Constants.DBUSER + ".address ad "
                + "on cu.addressId = ad.addressid "
                + "inner join " + Constants.DBUSER + ".city ci "
                + "on ad.cityId = ci.cityid "
                + "inner join " + Constants.DBUSER + ".country co "
                + "on ci.countryId = co.countryid "
                + "where ap.start >= '" + date + "'"
                + "and ap.start <= '" + date.plusDays(6) + "'"
                + "order by ap.start;";

        ObservableList<Appointment> result = getAppointments(query);
        return result;
    }

    @Override
    public ObservableList<Appointment> getAppointmentsByDay(LocalDate date) {
        String query = "select ap.appointmentid as aptId, ap.title as aptTitle, ap.description as aptDesc, ap.location as aptLoc, "
                + "ap.contact as aptCon, ap.url as aptUrl, ap.start as aptStart, ap.end as aptEnd, ap.createDate as aptCreate, "
                + "ap.createdBy as aptCreatedBy, ap.lastUpdate as aptLastUpd, ap.lastUpdateBy as aptLastUpdBy, ap.type as aptType, "
                + "cu.customerid as custId, cu.customerName as custName, cu.active as custActive, cu.createDate as custCreate, "
                + "cu.createdBy as custCreatedBy, cu.lastUpdate as custLastUpd, cu.lastUpdateBy as custLastUpdBy, "
                + "us.userid as userId, us.userName as userName, us.password as userPwd, us.active as userActive, "
                + "us.createDate as userCreate, us.createBy as userCreatedBy, us.lastUpdate as userLastUpd, us.lastUpdatedBy as userLastUpdBy, "
                + "ad.addressid as addId, ad.address as addAdd1, ad.address2 as addAdd2, ad.postalCode as addPostal, ad.phone as addPhone, "
                + "ad.createDate as addCreate, ad.createdBy as addCreatedBy, ad.lastUpdate as addLastUpd, ad.lastUpdateBy as addLastUpdBy, "
                + "ci.cityid as cityId, ci.city as cityCity, ci.createDate as cityCreate, ci.createdBy as cityCreatedBy, ci.lastUpdate as cityLastUpd, ci.lastUpdateBy as cityLastUpdBy, "
                + "co.countryid as coId, co.country as coCountry, co.createDate as coCreate, co.createdBy as coCreatedBy, co.lastUpdate as coLastUpd, co.lastUpdateBy as coLastUpdBy "
                + "from " + Constants.DBUSER + ".appointment ap "
                + "inner join " + Constants.DBUSER + ".customer cu "
                + "on ap.customerId = cu.customerid "
                + "inner join " + Constants.DBUSER + ".user us "
                + "on ap.userid = us.userid "
                + "inner join " + Constants.DBUSER + ".address ad "
                + "on cu.addressId = ad.addressid "
                + "inner join " + Constants.DBUSER + ".city ci "
                + "on ad.cityId = ci.cityid "
                + "inner join " + Constants.DBUSER + ".country co "
                + "on ci.countryId = co.countryid "
                + "where ap.start >= '" + date + "'"
                + "and ap.start <= '" + date.plusDays(1) + "'"
                + "order by ap.start;";

        ObservableList<Appointment> result = getAppointments(query);
        return result;
    }

    @Override
    public ArrayList<ObservableList> getAppointmentTypesByMonth(ArrayList<ObservableList> dataList, String year) {
        String query = "select MONTH(start) as month, type, count(*) as count from appointment "
                + "where YEAR(start) = ? "
                + "group by month, type;";

        DbConnectionManager db = DbConnectionManager.getInstance();
        try (Connection con = db.getConnection()) {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, year);
            ResultSet rs = ps.executeQuery();
            if (!rs.isBeforeFirst()) {
                return null;
            } else {
                while (rs.next()) {
                    AptTypeSummary data = new AptTypeSummary();
                    data.setType(rs.getString("type"));
                    data.setCount(rs.getInt("count"));
                    dataList.get((rs.getInt("month") - 1)).add(data);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dataList;
    }

    public ObservableList<Appointment> getAppointmentsByConsultant(User con) {
        String query = "select ap.appointmentid as aptId, ap.title as aptTitle, ap.description as aptDesc, ap.location as aptLoc, "
                + "ap.contact as aptCon, ap.url as aptUrl, ap.start as aptStart, ap.end as aptEnd, ap.createDate as aptCreate, "
                + "ap.createdBy as aptCreatedBy, ap.lastUpdate as aptLastUpd, ap.lastUpdateBy as aptLastUpdBy, ap.type as aptType, "
                + "cu.customerid as custId, cu.customerName as custName, cu.active as custActive, cu.createDate as custCreate, "
                + "cu.createdBy as custCreatedBy, cu.lastUpdate as custLastUpd, cu.lastUpdateBy as custLastUpdBy, "
                + "us.userid as userId, us.userName as userName, us.password as userPwd, us.active as userActive, "
                + "us.createDate as userCreate, us.createBy as userCreatedBy, us.lastUpdate as userLastUpd, us.lastUpdatedBy as userLastUpdBy, "
                + "ad.addressid as addId, ad.address as addAdd1, ad.address2 as addAdd2, ad.postalCode as addPostal, ad.phone as addPhone, "
                + "ad.createDate as addCreate, ad.createdBy as addCreatedBy, ad.lastUpdate as addLastUpd, ad.lastUpdateBy as addLastUpdBy, "
                + "ci.cityid as cityId, ci.city as cityCity, ci.createDate as cityCreate, ci.createdBy as cityCreatedBy, ci.lastUpdate as cityLastUpd, ci.lastUpdateBy as cityLastUpdBy, "
                + "co.countryid as coId, co.country as coCountry, co.createDate as coCreate, co.createdBy as coCreatedBy, co.lastUpdate as coLastUpd, co.lastUpdateBy as coLastUpdBy "
                + "from " + Constants.DBUSER + ".appointment ap "
                + "inner join " + Constants.DBUSER + ".customer cu "
                + "on ap.customerId = cu.customerid "
                + "inner join " + Constants.DBUSER + ".user us "
                + "on ap.userid = us.userid "
                + "inner join " + Constants.DBUSER + ".address ad "
                + "on cu.addressId = ad.addressid "
                + "inner join " + Constants.DBUSER + ".city ci "
                + "on ad.cityId = ci.cityid "
                + "inner join " + Constants.DBUSER + ".country co "
                + "on ci.countryId = co.countryid "
                + "where ap.userId = " + con.getUserId() + " "
                + "order by ap.start;";

        ObservableList<Appointment> result = getAppointments(query);
        return result;
    }

    public ObservableList<Appointment> getAppointmentsByCustomer(Customer cust) {
        String query = "select ap.appointmentid as aptId, ap.title as aptTitle, ap.description as aptDesc, ap.location as aptLoc, "
                + "ap.contact as aptCon, ap.url as aptUrl, ap.start as aptStart, ap.end as aptEnd, ap.createDate as aptCreate, "
                + "ap.createdBy as aptCreatedBy, ap.lastUpdate as aptLastUpd, ap.lastUpdateBy as aptLastUpdBy, ap.type as aptType, "
                + "cu.customerid as custId, cu.customerName as custName, cu.active as custActive, cu.createDate as custCreate, "
                + "cu.createdBy as custCreatedBy, cu.lastUpdate as custLastUpd, cu.lastUpdateBy as custLastUpdBy, "
                + "us.userid as userId, us.userName as userName, us.password as userPwd, us.active as userActive, "
                + "us.createDate as userCreate, us.createBy as userCreatedBy, us.lastUpdate as userLastUpd, us.lastUpdatedBy as userLastUpdBy, "
                + "ad.addressid as addId, ad.address as addAdd1, ad.address2 as addAdd2, ad.postalCode as addPostal, ad.phone as addPhone, "
                + "ad.createDate as addCreate, ad.createdBy as addCreatedBy, ad.lastUpdate as addLastUpd, ad.lastUpdateBy as addLastUpdBy, "
                + "ci.cityid as cityId, ci.city as cityCity, ci.createDate as cityCreate, ci.createdBy as cityCreatedBy, ci.lastUpdate as cityLastUpd, ci.lastUpdateBy as cityLastUpdBy, "
                + "co.countryid as coId, co.country as coCountry, co.createDate as coCreate, co.createdBy as coCreatedBy, co.lastUpdate as coLastUpd, co.lastUpdateBy as coLastUpdBy "
                + "from " + Constants.DBUSER + ".appointment ap "
                + "inner join " + Constants.DBUSER + ".customer cu "
                + "on ap.customerId = cu.customerid "
                + "inner join " + Constants.DBUSER + ".user us "
                + "on ap.userid = us.userid "
                + "inner join " + Constants.DBUSER + ".address ad "
                + "on cu.addressId = ad.addressid "
                + "inner join " + Constants.DBUSER + ".city ci "
                + "on ad.cityId = ci.cityid "
                + "inner join " + Constants.DBUSER + ".country co "
                + "on ci.countryId = co.countryid "
                + "where ap.customerId = " + cust.getCustomerId() + " "
                + "order by ap.start;";

        ObservableList<Appointment> result = getAppointments(query);
        return result;
    }

    private ObservableList<Appointment> getAppointments(String query) {
        ObservableList<Appointment> result = FXCollections.observableArrayList();
        DbConnectionManager db = DbConnectionManager.getInstance();
        try {
            Connection con = db.getConnection();
            PreparedStatement ps = con.prepareStatement(query, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User user = new User();
                Country country = new Country();
                City city = new City();
                Address address = new Address();
                Customer customer = new Customer();
                Appointment appt = new Appointment();

                user.setUserId(rs.getInt("userId"));
                user.setUserName(rs.getString("userName"));
                user.setPassword(rs.getString("userPwd"));
                user.setActive(rs.getInt("userActive"));
                user.setCreateDate(rs.getDate("userCreate").toLocalDate());
                user.setCreatedBy(rs.getString("userCreatedBy"));
                user.setLastUpdate(rs.getTimestamp("userLastUpd").toLocalDateTime().atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime());
                user.setLastUpdateBy(rs.getString("userLastUpdBy"));

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
                address.setPostalCode(rs.getString("addPostal"));
                address.setPhone(rs.getString("addPhone"));
                address.setCreateDate(rs.getDate("addCreate").toLocalDate());
                address.setCreatedBy(rs.getString("addCreatedBy"));
                address.setLastUpdate(rs.getTimestamp("addLastUpd").toLocalDateTime().atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime());
                address.setLastUpdateBy(rs.getString("addLastUpdBy"));

                customer.setCustomerId(rs.getInt("custId"));
                customer.setCustomerName(rs.getString("custName"));
                customer.setAddress(address);
                customer.setActive(rs.getInt("custActive"));
                customer.setCreateDate(rs.getDate("custCreate").toLocalDate());
                customer.setCreatedBy(rs.getString("custCreatedBy"));
                customer.setLastUpdate(rs.getTimestamp("custLastUpd").toLocalDateTime().atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime());
                customer.setLastUpdateBy(rs.getString("custLastUpdBy"));

                appt.setAppointmentId(rs.getInt("aptId"));
                appt.setCustomer(customer);
                appt.setUser(user);
                appt.setTitle(rs.getString("aptTitle"));
                appt.setDescription(rs.getString("aptDesc"));
                appt.setLocation(rs.getString("aptLoc"));
                appt.setContact(rs.getString("aptCon"));
                appt.setType(rs.getString("aptType"));
                appt.setUrl(rs.getString("aptUrl"));
                // REQUIREMENT E
                appt.setStart(rs.getTimestamp("aptStart").toLocalDateTime().atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime());
                appt.setEnd(rs.getTimestamp("aptEnd").toLocalDateTime().atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime());
                appt.setCreateDate(rs.getDate("aptCreate").toLocalDate());
                appt.setCreatedBy(rs.getString("aptCreatedBy"));
                appt.setLastUpdate(rs.getTimestamp("aptLastUpd").toLocalDateTime().atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime());
                appt.setLastUpdateBy(rs.getString("aptLastUpdBy"));

                result.add(appt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.closeConnection();
        }

        return result;
    }

    @Override
    public boolean updateAppointment(Appointment apt) {
        boolean result = false;
        String query = "update " + Constants.DBUSER + ".appointment set "
                + "customerId = ?, " // 1 
                + "title = ?, " // 2
                + "description = ?, " // 3
                + "location = ?, " // 4
                + "contact = ?, " // 5
                + "start = ?, " // 6
                + "end = ?, " // 7
                + "lastUpdate = ?, " // 8
                + "lastUpdateBy = ?, " // 9
                + "userid = ?, " // 10
                + "type = ? " // 11
                + "where appointmentid = ?;"; // 12
        DbConnectionManager db = DbConnectionManager.getInstance();
        try {
            Connection con = db.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, apt.getCustomer().getCustomerId());
            ps.setString(2, apt.getTitle());
            ps.setString(3, apt.getDescription());
            ps.setString(4, apt.getLocation());
            ps.setString(5, apt.getContact());
            // REQUIREMENT E
            ps.setTimestamp(6, Timestamp.valueOf(apt.getStart().atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime()));
            ps.setTimestamp(7, Timestamp.valueOf(apt.getEnd().atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime()));
            ps.setTimestamp(8, Timestamp.valueOf(apt.getLastUpdate().atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime()));
            ps.setString(9, apt.getLastUpdateBy());
            ps.setInt(10, apt.getUser().getUserId());
            ps.setString(11, apt.getType());
            ps.setInt(12, apt.getAppointmentId());

            int indicator = ps.executeUpdate();
            result = indicator > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.closeConnection();
        }

        return result;
    }

    @Override
    public boolean deleteAppointment(Appointment apt) {
        boolean result = false;
        String query = "delete from " + Constants.DBUSER + ".appointment where appointmentid = ?;";
        DbConnectionManager db = DbConnectionManager.getInstance();
        try {
            Connection con = db.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, apt.getAppointmentId());
            int indicator = ps.executeUpdate();
            result = indicator > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.closeConnection();
        }

        return result;
    }

    @Override
    public boolean addAppointment(Appointment apt) {
        boolean result = false;
        String query = "insert into " + Constants.DBUSER + ".appointment "
                + "(customerId, title, description, location, contact, url, start, end, createDate, createdBy, lastUpdate, lastUpdateBy, userid, type) "
                + "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        DbConnectionManager db = DbConnectionManager.getInstance();
        try {
            Connection con = db.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, apt.getCustomer().getCustomerId());
            ps.setString(2, apt.getTitle());
            ps.setString(3, apt.getDescription());
            ps.setString(4, apt.getLocation());
            ps.setString(5, apt.getContact());
            ps.setString(6, apt.getUrl());
            // REQUIREMENT E
            ps.setTimestamp(7, Timestamp.valueOf(apt.getStart().atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime()));
            ps.setTimestamp(8, Timestamp.valueOf(apt.getEnd().atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime()));
            ps.setDate(9, Date.valueOf(apt.getCreateDate()));
            ps.setString(10, apt.getCreatedBy());
            ps.setTimestamp(11, Timestamp.valueOf(apt.getLastUpdate().atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime()));
            ps.setString(12, apt.getLastUpdateBy());
            ps.setInt(13, apt.getUser().getUserId());
            ps.setString(14, apt.getType());

            int indicator = ps.executeUpdate();
            result = indicator > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.closeConnection();
        }

        return result;
    }

    @Override
    public ObservableList<String> getYearList() {
        String query = "select distinct YEAR(start) as year from appointment order by year desc;";

        ObservableList<String> yearList = FXCollections.observableArrayList();
        DbConnectionManager db = DbConnectionManager.getInstance();
        try (Connection con = db.getConnection()) {
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                yearList.add(rs.getString("year"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return yearList;
    }

    private String extractMonth(LocalDate date) {
        int candidate = date.getMonthValue();
        if (candidate < 10) {
            return "0" + candidate;
        } else {
            return String.valueOf(candidate);
        }
    }

}