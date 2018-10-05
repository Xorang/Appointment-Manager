package View;

import AppointmentManager.Main;
import DAO.CustomerDAO;
import Impl.CustomerDAOImpl;
import Model.Customer;
import Util.Constants;
import Util.Constants.Mode;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * Customized TableCell that includes an edit and delete button for customers.
 * Part of REQUIREMENT C.
 */
public class EditDeleteCustomer extends TableCell<Customer, Boolean> {

    final Button editButton = new Button();
    final Button deleteButton = new Button();
    final HBox container = new HBox();
    private CustomerDAO dao;
    private Main Main;

    public EditDeleteCustomer(Main Main, final TableView table) {
        this.Main = Main;
        dao = new CustomerDAOImpl();
        editButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream(Constants.EDIT_ICON))));
        // REQUIREMENT G - Use lambdas to efficiently set button action
        editButton.setOnAction((ActionEvent actionEvent) -> {
            Customer cust = getTableView().getItems().get(getIndex());
            Main.showAddEditCustomer(Mode.EDIT, cust);
        });
        deleteButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream(Constants.DELETE_ICON))));
        // REQUIREMENT G - Use lambdas to efficiently set button action
        deleteButton.setOnAction((ActionEvent actionEvent) -> {
            Customer cust = getTableView().getItems().get(getIndex());
            boolean proceed = Main.throwConfirmation("delete");
            if (proceed) {
                boolean result = dao.deleteCustomer(cust);
                if (result) {
                    table.getItems().remove(cust);
                }
            }
        });
        container.getChildren().addAll(editButton, deleteButton);
        container.setAlignment(Pos.CENTER);
        container.setSpacing(4);
    }

    @Override
    protected void updateItem(Boolean item, boolean empty) {
        super.updateItem(item, true);
        if (!empty) {
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            setGraphic(container);
        } else {
            setGraphic(null);
        }
    }

}
