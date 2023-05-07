package pl.edu.pwste.bank;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.simpleyaml.configuration.file.YamlFile;
import org.w3c.dom.events.MouseEvent;
import pl.edu.pwste.bank.data.User;
import pl.edu.pwste.bank.storage.Mysql;
import pl.edu.pwste.bank.utils.LoadFXML;
import pl.edu.pwste.bank.utils.LocationWindow;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Bank extends Application implements Initializable {


    private static Mysql sql = null;
    private static Bank instance = null;
    private static Stage stage = null;
    private static BorderPane borderPane = null;
    private static Parent main = null;

    private static BorderPane mainContainer;
    private static AnchorPane mainBox;

    @FXML
    public BorderPane mainPane;

    public static int pageLimit = 5;
    public static int page = 1;
    public static int lastPage = 1;

    @FXML
    private Button btnExit;

    @FXML
    private Button btnLogin;

    @FXML
    private Label btnRecoverPass;

    @FXML
    private TextField email;

    @FXML
    private PasswordField password;

    public static final YamlFile yamlFile = new YamlFile("config.yml");

    @FXML
    public void exitApp() {
        System.exit(0);
    }


    @FXML
    public void login()
    {
        String email = this.email.getText();
        String password = this.password.getText();


        // sprawdzenie czy user istnieje -> boolean
        if(Bank.getSql().playerExists(email, password))
        {
            Bank.getSql().getUser(email);
            LoadFXML.loadPanel();
        }

    }

    @Override
    public void start(Stage stageStart) throws IOException {
        instance = this;
        stage = stageStart;
        loadFile();
        setupSql();

        if(!sql.isConnected())
        {
            System.out.println("Nie udało się polaczyc z mysql");
            System.exit(0);
            return;
        }

        //mainContainer = new BorderPane();
        //mainContainer.setPrefSize(900, 600);
        main = FXMLLoader.load(Bank.class.getClassLoader().getResource("Login.fxml"));

        Scene scene = new Scene(main);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        LocationWindow.addCursorMove(main, stage);
        stage.show();


    }

    private static void setupSql() {

        String host = yamlFile.getString("mysql.host");
        String username = yamlFile.getString("mysql.username");
        String password = yamlFile.getString("mysql.password");
        String database = yamlFile.getString("mysql.database");
        String port = yamlFile.getString("mysql.port");

        boolean ssl = false;
        if (yamlFile.get("mysql.ssl") != null) {
            ssl = yamlFile.getBoolean("mysql.ssl");
        }

        sql = new Mysql(host, username, password, database, port, ssl);
    }

    private static void loadFile()
    {

        try {
            if (!yamlFile.exists()) {
                yamlFile.createNewFile();

                yamlFile.path("mysql.host").set("localhost");
                yamlFile.path("mysql.username").set("root");
                yamlFile.path("mysql.password").set("");
                yamlFile.path("mysql.database").set("bot");
                yamlFile.path("mysql.port").set("3306");
                yamlFile.path("mysql.ssl").set("false");
                yamlFile.save();
            } else {
                System.out.println("Wczytywanie configu...");
            }
            yamlFile.load();

        } catch (final Exception e) {
            e.printStackTrace();
        }

    }


    public static Parent getMain() {
        return main;
    }

    public static Stage getStage() {
        return stage;
    }

    public static Mysql getSql() {
        return sql;
    }

    public static Bank getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        launch();
    }

    public static BorderPane getBorderPane() {
        return borderPane;
    }

    public static BorderPane getMainContainer() {
        return mainContainer;
    }

    public static AnchorPane getMainBox() {
        return mainBox;
    }

    public static void setMainBox(AnchorPane mainBox) {
        Bank.mainBox = mainBox;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mainContainer = mainPane;
    }

    public void openRegister(javafx.scene.input.MouseEvent mouseEvent) {
        LoadFXML.loadRegister();
    }
}