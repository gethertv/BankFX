package pl.edu.pwste.bank.storage;

import pl.edu.pwste.bank.data.Gender;
import pl.edu.pwste.bank.data.User;
import pl.edu.pwste.bank.data.UserManager;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;


public class Mysql {
    private String host;
    private String username;
    private String password;
    private String database;
    private String port;
    private boolean ssl;
    private boolean isFinished;
    private Connection connection;

    public Mysql(String host, String username, String password, String database, String port, boolean ssl) {
        this.host = host;
        this.username = username;
        this.password = password;
        this.database = database;
        this.port = port;
        this.ssl = ssl;

        openConnection();

        createTable();
    }

    private String getUsername() {
        return this.username;
    }

    private String getPassword() {
        return this.password;
    }

    private String getHost() {
        return this.host;
    }

    private String getPort() {
        return this.port;
    }

    private String getDatabase() {
        return this.database;
    }

    private boolean useSSL() {
        return this.ssl;
    }

    public boolean isConnected() {
        return (getConnection() != null);
    }

    public Connection getConnection() {
        validateConnection();
        return this.connection;
    }

    private void openConnection() {
        try {
            long l1 = System.currentTimeMillis();
            long l2 = 0L;

            //Class.forName("com.mysql.cj.jdbc.Driver");
            Class.forName("com.mysql.jdbc.Driver");
            Properties properties = new Properties();
            properties.setProperty("user", getUsername());
            properties.setProperty("password", getPassword());
            properties.setProperty("autoReconnect", "true");
            properties.setProperty("useSSL", String.valueOf(useSSL()));
            properties.setProperty("requireSSL", String.valueOf(useSSL()));
            properties.setProperty("verifyServerCertificate", "false");
            String str = "jdbc:mysql://" + getHost() + ":" + getPort() + "/" + getDatabase();
            this.connection = DriverManager.getConnection(str, properties);
            l2 = System.currentTimeMillis();
            this.isFinished = true;
            System.out.println("[mysql] Connected successfully");
        } catch (ClassNotFoundException classNotFoundException) {
            this.isFinished = false;
            System.out.println("[mysql] Check your configuration.");
        } catch (SQLException sQLException) {
            this.isFinished = false;
            System.out.println("[mysql] (" + sQLException.getLocalizedMessage() + "). Check your configuration.");
        }
    }

    private void validateConnection() {
        if (!this.isFinished)
            return;
        try {
            if (this.connection == null) {
                System.out.println("[mysql] aborted. Connecting again");
                reConnect();
            }
            if (!this.connection.isValid(4)) {
                System.out.println("[mysql] timeout.");
                reConnect();
            }
            if (this.connection.isClosed()) {
                System.out.println("[mysql] closed. Connecting again");
                reConnect();
            }
        } catch (Exception exception) {
        }
    }

    private void reConnect() {
        System.out.println("[mysql] connection again");
        openConnection();
    }


    public int checkExists(String str) {
        int i = 0;
        try {
            ResultSet resultSet = getResult(str);
            if (resultSet.next()) {
                i++;
                resultSet.close();
                return i;
            }
        } catch (SQLException sQLException) {
            return i;
        }
        return i;
    }


    public void update(String paramString) {
        try {
            Connection connection = getConnection();
            if (connection != null) {
                Statement statement = getConnection().createStatement();
                statement.executeUpdate(paramString);
            }
        } catch (SQLException sQLException) {
            System.out.println("[mysql] wrong update : '" + paramString + "'!");
        }
    }

    public ResultSet getResult(String paramString) {
        ResultSet resultSet = null;
        Connection connection = getConnection();
        try {
            if (connection != null) {
                Statement statement = getConnection().createStatement();
                resultSet = statement.executeQuery(paramString);
            }
        } catch (SQLException sQLException) {
            System.out.println("[mysql] wrong when want get result: '" + paramString + "'!");
        }
        return resultSet;
    }

    public void createTable() {
        String create = "CREATE TABLE IF NOT EXISTS `users` (id INT(10) AUTO_INCREMENT, PRIMARY KEY (id)," +
                "email VARCHAR(50) NOT NULL," +
                "balance DOUBLE(11,2) NOT NULL DEFAULT '0.00'," +
                "password VARCHAR(50) NOT NULL," +
                "name VARCHAR(50) NOT NULL," +
                "surname VARCHAR(50) NOT NULL," +
                "numberAccount VARCHAR(26) NOT NULL," +
                "pesel VARCHAR(11) NOT NULL," +
                "age int(11) NOT NULL," +
                "phone int(9) NOT NULL," +
                "city VARCHAR(50) NOT NULL," +
                "street VARCHAR(50) NOT NULL," +
                "postcode VARCHAR(10) NOT NULL," +
                "gender VARCHAR(11) NOT NULL," +
                "homeNr VARCHAR(10) NOT NULL)";

        //String data, String accountFrom, String accountTo, String money
        String transfer = "CREATE TABLE IF NOT EXISTS `transfer` (id INT(10) AUTO_INCREMENT, PRIMARY KEY (id)," +
                "data DATETIME NOT NULL, " +
                "numberTo VARCHAR(26) NOT NULL, " +
                "numberFrom VARCHAR(26) NOT NULL, " +
                "name VARCHAR(26) NOT NULL, " +
                "surname VARCHAR(26) NOT NULL, " +
                "title VARCHAR(50) NOT NULL, " +
                "money DOUBLE(11,2) NOT NULL)";
        update(create);
        update(transfer);
    }

    public boolean playerExists(String email, String password)
    {
        String sqlString = "SELECT id FROM `users` WHERE email = '"+email+"' AND password = '"+password+"'";
        try {
            ResultSet resultSet = getResult(sqlString);
            if (resultSet.next()) {
                resultSet.close();
                return true;
            }
        } catch (SQLException sQLException) {
            return false;
        }
        return false;
    }

    public boolean userExists(String email) {
        return (getUserID(email) != 0);
    }

    private int getUserID(String email) {
        return getInt("id", "SELECT id FROM `users` WHERE email='" + email + "'");
    }

    private int getInt(String paramString1, String paramString2) {
        try {
            ResultSet resultSet = getResult(paramString2);
            if (resultSet.next()) {
                int i = resultSet.getInt(paramString1);
                resultSet.close();
                return i;
            }
        } catch (SQLException sQLException) {
            return 0;
        }
        return 0;
    }

    public boolean numberExists(String number) {
        try {
            ResultSet resultSet = getResult("SELECT id FROM `users` WHERE numberAccount = '"+number+"'");
            if (resultSet.next()) {
                int i = resultSet.getInt("id");
                resultSet.close();
                return true;
            }
        } catch (SQLException sQLException) {
            return false;
        }
        return false;
    }



    public void getUser(String email) {
        String sqlString = "SELECT * FROM `users` WHERE email = '"+email+"'";
        try {
            ResultSet resultSet = getResult(sqlString);
            if (resultSet.next()) {
                User user = new User(
                        resultSet.getString("email"),
                        resultSet.getDouble("balance"),
                        resultSet.getString("name"),
                        resultSet.getString("surname"),
                        resultSet.getString("numberAccount"),
                        Long.parseLong(resultSet.getString("pesel")),
                        resultSet.getInt("age"),
                        resultSet.getInt("phone"),
                        resultSet.getString("city"),
                        resultSet.getString("street"),
                        resultSet.getString("postCode"),
                        resultSet.getString("homeNr"),
                        ((resultSet.getString("gender").equalsIgnoreCase("M")) ? Gender.M : Gender.K)
                );
                UserManager.setUser(user);
                resultSet.close();
            }
        } catch (SQLException sQLException) {

        }
    }

    public void loadTransferUser()
    {
        ResultSet result = getResult("SELECT * FROM transfer WHERE " +
                "numberFrom = '" + UserManager.getUser().getNumberAccount() + "' " +
                "OR " +
                "numberTo ='" + UserManager.getUser().getNumberAccount() + "' ORDER BY DATA DESC");
        try {
            String numberTo = null;
            String money = null;
            String name = null;
            String surname = null;
            String title = null;
            String data = null;
            if(result==null)
                return;

            while (result.next())
            {
                numberTo = result.getString("numberTo");
                money = result.getString("money");
                name = result.getString("name");
                surname = result.getString("surname");
                title = result.getString("title");
                data = result.getString("data");
                UserManager.addTransfer(UserManager.getUser(), numberTo, money, name, surname, title, data);
            }

        } catch (SQLException e) {

        }
    }
}

