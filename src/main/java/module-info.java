module pl.edu.pwste.bank {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires Simple.Yaml;


    opens pl.edu.pwste.bank to javafx.fxml;
    exports pl.edu.pwste.bank;
    exports pl.edu.pwste.bank.controllers;
    exports pl.edu.pwste.bank.data;
    opens pl.edu.pwste.bank.data to javafx.fxml;
    opens pl.edu.pwste.bank.controllers to javafx.fxml;
}