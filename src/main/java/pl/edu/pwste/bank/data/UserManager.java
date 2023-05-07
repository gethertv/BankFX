package pl.edu.pwste.bank.data;

import javafx.scene.input.DataFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public class UserManager {

    // zalogowany uzytkownik
    private static User user = null;

    //private static HashMap<String, User> dataUser = new HashMap<>();


    /*public static void setUser(User user)
    {
        dataUser.put(user.getEmail() ,user);
    }*/



    public static void addTransfer(User user, String accountTo, String money, String name, String surname, String title, String data)
    {
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        Transfer temp = new Transfer(data, user.getNumberAccount(), accountTo, money, name, surname, title);
        user.getTransferList().add(temp);
    }

    public static void setUser(User temp)
    {
        user = temp;
    }


    public static User getUser() {
        return user;
    }
}
