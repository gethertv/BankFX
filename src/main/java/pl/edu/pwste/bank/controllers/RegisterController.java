package pl.edu.pwste.bank.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import pl.edu.pwste.bank.Bank;
import pl.edu.pwste.bank.data.Gender;
import pl.edu.pwste.bank.utils.LoadFXML;
import pl.edu.pwste.bank.utils.LocationWindow;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.security.SecureRandom;
import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {

    @FXML
    private TextField age;

    @FXML
    private TextField city;

    @FXML
    private TextField email;

    @FXML
    private ChoiceBox<Gender> gender;

    @FXML
    private TextField name;

    @FXML
    private TextField nrHome;

    @FXML
    private TextField password;

    @FXML
    private TextField pesel;

    @FXML
    private TextField phone;

    @FXML
    private TextField postcode;

    @FXML
    private TextField street;

    @FXML
    private TextField surname;

    @FXML
    private AnchorPane alert;



    public void openLogin(ActionEvent actionEvent) {
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


    }

    public boolean checkNumber(String number)
    {
        try {
            double a = Double.parseDouble(number);
            return true;
        } catch (NumberFormatException e)
        {
            return false;
        }
    }

    private Random random = new Random(System.currentTimeMillis());

    public String getRandomNumber() {
        String number = "";
        while(number.length()!=26)
        {
            number += random.nextInt(9) + 1;
        }
        return number;
    }

    public void createAccount(ActionEvent actionEvent) {
        alert.getChildren().clear();
        if(!name.getText().isEmpty() && !surname.getText().isEmpty() && !age.getText().isEmpty() && !city.getText().isEmpty()
        && !email.getText().isEmpty() && !nrHome.getText().isEmpty() && !password.getText().isEmpty() && !pesel.getText().isEmpty()
        && !phone.getText().isEmpty() && !postcode.getText().isEmpty() && !street.getText().isEmpty() && gender.getSelectionModel().getSelectedItem()!=null)
        {
            String alertMessage = "";
            if(!checkNumber(pesel.getText()))
            {
                alertMessage += "Pesel musi składać się z liczb.\n";
            }
            if(!checkNumber(phone.getText()))
            {
                alertMessage += "Nr telefonu musi składać się z liczb.\n";
            }
            if(!checkNumber(nrHome.getText()))
            {
                alertMessage += "Nr domu musi składać się z liczb.\n";
            }
            if(!checkNumber(age.getText()))
            {
                alertMessage += "Wiek musi składać się z liczb.\n";
            }
            if(pesel.getText().length()!=11){
                alertMessage += "Podałeś nieprawidłowy pesel (11 znaków)\n";
            }
            if(phone.getText().length()!=9){
                alertMessage += "Podałeś nieprawidłowy nr telefonu (9 znaków)\n";
            }
            if(alertMessage.isEmpty())
            {
                String numberAccount = getRandomNumber();
                while (Bank.getSql().numberExists(numberAccount))
                {
                    numberAccount = getRandomNumber();
                }

                Bank.getSql().update("INSERT INTO `users` (email, password, name, surname, numberAccount, pesel, age, phone, city, street, postcode, gender, homeNr) " +
                        "VALUES ('"+email.getText()+"', '"+password.getText()+"', " +
                        "'"+name.getText()+"', '"+surname.getText()+"', " +
                        "'"+numberAccount+"', '"+pesel.getText()+"', '"+age.getText()+"', " +
                        "'"+phone.getText()+"', '"+city.getText()+"', '"+street.getText()+"', " +
                        "'"+postcode.getText()+"', '"+gender.getSelectionModel().getSelectedItem().name().toUpperCase(Locale.ROOT)+"', '"+nrHome.getText()+"')");
                alert.getChildren().add(new Label("Pomyślnie stworzono nowe konto."));
                age.setText(null);
                city.setText(null);
                postcode.setText(null);
                phone.setText(null);
                street.setText(null);
                nrHome.setText(null);
                surname.setText(null);
                name.setText(null);
                pesel.setText(null);
                password.setText(null);
                gender.setSelectionModel(null);
                email.setText(null);

            } else {
                alert.getChildren().add(new Label(alertMessage));
            }

        } else {
            alert.getChildren().add(new Label("Musisz wypełnic wszyskie pola!"));
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gender.getItems().add(Gender.K);
        gender.getItems().add(Gender.M);
    }
}
