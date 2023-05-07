package pl.edu.pwste.bank.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.AnchorPane;
import pl.edu.pwste.bank.Bank;
import pl.edu.pwste.bank.data.UserManager;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class TransferController implements Initializable {

    @FXML
    public TextField numberSend;

    @FXML
    private Label balance;

    @FXML
    private Label balanceText;

    @FXML
    private AnchorPane btn;

    @FXML
    private Label nrAccount;

    @FXML
    private TextField price;

    @FXML
    private TextField title;

    @FXML
    private AnchorPane alert;



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

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    public void copyNumberAccount(ActionEvent mouseEvent) {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent content = new ClipboardContent();
        content.putString(UserManager.getUser().getNumberAccount());
        clipboard.setContent(content);
    }


    @FXML
    private void sendTransfer(ActionEvent event) {
        alert.getChildren().clear();
        if(!numberSend.getText().isEmpty() && !price.getText().isEmpty() && !title.getText().isEmpty())
        {
            if(checkNumber(price.getText()))
            {
                Double sendMoney = round(Double.parseDouble(price.getText()), 2);
                System.out.println("Kasa: "+UserManager.getUser().getBalance());
                System.out.println("Kasa1: "+sendMoney);
                if(UserManager.getUser().getBalance()>=sendMoney)
                {
                    if(numberSend.getText().equalsIgnoreCase(UserManager.getUser().getNumberAccount()))
                    {
                        alert.getChildren().add(new Label("Nie mozesz wyslac przelewu na swoje konto!"));
                        return;
                    }
                    if(!Bank.getSql().numberExists(numberSend.getText()))
                    {
                        alert.getChildren().add(new Label("Podany numer konta nie istnieje."));
                        return;
                    }



                    LocalDateTime dateTime = LocalDateTime.now();

                    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                    Bank.getSql().update("INSERT INTO transfer (data, numberTo, numberFrom, name, surname, title, money) " +
                                                                "VALUES ('"+dateTime.format(format)+"', '"+UserManager.getUser().getNumberAccount()+"', " +
                                                    "'"+numberSend.getText()+"', '"+UserManager.getUser().getName()+"', '"+UserManager.getUser().getSurName()+"', " +
                                                    "'"+title.getText()+"', '"+sendMoney+"')");

                    Bank.getSql().update("UPDATE `users` SET balance = balance + '"+sendMoney+"' WHERE numberAccount = '"+numberSend.getText()+"'");
                    Bank.getSql().update("UPDATE `users` SET balance = balance - '"+sendMoney+"' WHERE numberAccount = '"+UserManager.getUser().getNumberAccount()+"'");

                    UserManager.getUser().setBalance(UserManager.getUser().getBalance()-sendMoney);
                    balance.setText(""+round(UserManager.getUser().getBalance(),2));
                    numberSend.setText(null);
                    title.setText(null);
                    price.setText(null);

                    alert.getChildren().add(new Label("Pomyślnie zrealizowano przelew."));
                } else {
                    alert.getChildren().add(new Label("Nie posiadasz tyle pieniędzy!"));
                }
            } else {
                alert.getChildren().add(new Label("Podana kwota, nie jest liczba."));
            }
        } else {
            alert.getChildren().add(new Label("Wypełnij wszystkie pola"));
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nrAccount.setText(""+UserManager.getUser().getNumberAccount());
        balance.setText(""+round(UserManager.getUser().getBalance(), 2));
    }
}
