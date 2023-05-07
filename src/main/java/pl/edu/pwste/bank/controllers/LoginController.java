package pl.edu.pwste.bank.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import pl.edu.pwste.bank.Bank;
import pl.edu.pwste.bank.data.Transfer;
import pl.edu.pwste.bank.data.UserManager;
import pl.edu.pwste.bank.utils.LoadFXML;
import pl.edu.pwste.bank.utils.LocationWindow;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    private Parent panel;

    @FXML
    private Button btnExit;

    @FXML
    private AnchorPane mainPanel;

    @FXML
    private Button panelBtn;

    @FXML
    private Button transfersBtn;

    @FXML
    private AnchorPane mainBox;





    @FXML
    void exitApp(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void getPanel(ActionEvent event) {
        LoadFXML.loadPanel();
    }

    @FXML
    private void getTransfer(ActionEvent event) {
        LoadFXML.loadTransfer();
    }

    @FXML
    private void getUser(ActionEvent event) {
        LoadFXML.loadUserData();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("#LoginController");
        Bank.setMainBox(mainBox);
        Bank.getSql().getUser(UserManager.getUser().getEmail());
    }

    public void logout(ActionEvent actionEvent) {
        Parent main = null;
        try {
            main = FXMLLoader.load(Bank.class.getClassLoader().getResource("Login.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scene scene = new Scene(main);
        Bank.getStage().setScene(scene);
        LocationWindow.addCursorMove(main, Bank.getStage());
        Bank.getStage().show();
        UserManager.setUser(null);
    }
}

