package pl.edu.pwste.bank.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import pl.edu.pwste.bank.Bank;
import pl.edu.pwste.bank.data.UserManager;

import java.io.IOException;

public class LoadFXML {


    public static void loadPanel()
    {

        try {
            Bank.getMainContainer().getChildren().clear();
            Parent main = FXMLLoader.load(Bank.class.getClassLoader().getResource("Panel.fxml"));
            Bank.getMainContainer().getChildren().add(main);
            Bank.getMainBox().getChildren().clear();
            Parent panelMain = FXMLLoader.load(Bank.class.getClassLoader().getResource("PanelMain.fxml"));
            Bank.getMainBox().getChildren().add(panelMain);


        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static void loadTransfer()
    {

        try {
            Bank.getMainBox().getChildren().clear();
            Parent panelMain = FXMLLoader.load(Bank.class.getClassLoader().getResource("Transfer.fxml"));
            Bank.getMainBox().getChildren().add(panelMain);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void loadUserData()
    {

        try {
            Bank.getMainBox().getChildren().clear();
            Parent panelMain = FXMLLoader.load(Bank.class.getClassLoader().getResource("DataUser.fxml"));
            Bank.getMainBox().getChildren().add(panelMain);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void loadRegister()
    {

        try {
            Bank.getMainContainer().getChildren().clear();
            Parent main = FXMLLoader.load(Bank.class.getClassLoader().getResource("Register.fxml"));
            Bank.getMainContainer().getChildren().add(main);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
