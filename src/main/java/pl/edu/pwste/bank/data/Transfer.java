package pl.edu.pwste.bank.data;

import javafx.scene.input.DataFormat;

public class Transfer {

    private String data;
    private String accountFrom;
    private String accountTo;
    private String money;
    private String name;
    private String surname;
    private String title;

    public Transfer(String data, String accountFrom, String accountTo, String money, String name, String surname, String title) {
        this.data = data;
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.money = money;
        this.title = title;
        this.name = name;
        this.surname = surname;
    }




    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getTitle() {
        return title;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getAccountFrom() {
        return accountFrom;
    }

    public void setAccountFrom(String accountFrom) {
        this.accountFrom = accountFrom;
    }

    public String getAccountTo() {
        return accountTo;
    }

    public void setAccountTo(String accountTo) {
        this.accountTo = accountTo;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}
