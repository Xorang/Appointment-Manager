package DAO;

import Model.Appointment;
import Model.Customer;
import Model.User;
import java.time.LocalDate;
import java.util.ArrayList;
import javafx.collections.ObservableList;

/**
 * Interface for DAOs accessing appointment data
 */
public interface AppointmentDAO {

    ObservableList<Appointment> getAppointmentsByMonth(LocalDate date);

    ObservableList<Appointment> getAppointmentsByWeek(LocalDate date);

    ObservableList<Appointment> getAppointmentsByDay(LocalDate date);

    ObservableList<Appointment> getAppointmentsByConsultant(User con);

    ObservableList<Appointment> getAppointmentsByCustomer(Customer cust);

    ArrayList<ObservableList> getAppointmentTypesByMonth(ArrayList<ObservableList> dataList, String year);

    boolean updateAppointment(Appointment apt);

    boolean deleteAppointment(Appointment apt);

    boolean addAppointment(Appointment apt);

    ObservableList<String> getYearList();
}
