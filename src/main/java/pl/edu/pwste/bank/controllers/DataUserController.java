package pl.edu.pwste.bank.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import pl.edu.pwste.bank.data.UserManager;

import java.net.URL;
import java.util.ResourceBundle;

public class DataUserController implements Initializable {

    @FXML
    private Label age;

    @FXML
    private Label city;

    @FXML
    private Label email;

    @FXML
    private Label name;

    @FXML
    private Label nrAccount;

    @FXML
    private Label nrHome;

    @FXML
    private Label pesel;

    @FXML
    private Label phone;

    @FXML
    private Label postcode;

    @FXML
    private Label street;

    @FXML
    private Label surname;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.age.setText(""+UserManager.getUser().getAge());
        this.city.setText(""+UserManager.getUser().getCity());
        this.email.setText(""+UserManager.getUser().getEmail());
        this.name.setText(""+UserManager.getUser().getName());
        this.nrAccount.setText(""+UserManager.getUser().getNumberAccount());
        this.nrHome.setText(""+UserManager.getUser().getHomeNr());
        this.pesel.setText(""+UserManager.getUser().getPesel());
        this.phone.setText(""+UserManager.getUser().getPhone());
        this.postcode.setText(""+UserManager.getUser().getPostCode());
        this.street.setText(""+UserManager.getUser().getStreet());
        this.surname.setText(""+UserManager.getUser().getSurName());
    }
}
