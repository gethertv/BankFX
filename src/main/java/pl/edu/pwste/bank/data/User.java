package pl.edu.pwste.bank.data;

import javafx.scene.input.DataFormat;

import java.util.ArrayList;
import java.util.List;

public class User {

    private String email;
    private String name;
    private String surName;
    private String numberAccount;
    private Long pesel;
    private int age;
    private int phone;
    private String city;
    private String street;
    private String postCode;
    private String homeNr;
    private Gender gender;
    private Double balance;
    private List<Transfer> transferList;

    public User(String email, Double balance, String name, String surName, String numberAccount, Long pesel, int age, int phone, String city, String street, String postCode, String homeNr, Gender gender) {
        this.email = email;
        this.balance = balance;
        this.name = name;
        this.surName = surName;
        this.numberAccount = numberAccount;
        this.pesel = pesel;
        this.age = age;
        this.phone = phone;
        this.city = city;
        this.street = street;
        this.postCode = postCode;
        this.homeNr = homeNr;
        this.gender = gender;
        this.transferList = new ArrayList<>();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setTransferList(List<Transfer> transferList) {
        this.transferList = transferList;
    }

    public List<Transfer> getTransferList() {
        return transferList;
    }

    public Gender getGender() {
        return gender;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String getNumberAccount() {
        return numberAccount;
    }

    public void setNumberAccount(String numberAccount) {
        this.numberAccount = numberAccount;
    }

    public Long getPesel() {
        return pesel;
    }

    public void setPesel(Long pesel) {
        this.pesel = pesel;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getHomeNr() {
        return homeNr;
    }

    public void setHomeNr(String homeNr) {
        this.homeNr = homeNr;
    }
}
