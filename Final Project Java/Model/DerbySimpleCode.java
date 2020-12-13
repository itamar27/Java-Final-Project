package Model;

import java.sql.*;

public class DerbySimpleCode {
    public static String driver = "org.apache.derby.jdbc.EmbeddedDriver";
    public static String connectionString = "jdbc:derby:";
    public static void main(String[] args) {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        try {
            connection = null;
            //Instantiating the dirver class will indirectly register
            //this driver as an available driver for DriverManager
            Class.forName(driver);
            //Getting a connection by calling getConnection
            connection = DriverManager.getConnection(connectionString + "CostMangerDB;create=true");
            //connection = new org.apache.derby.jdbc.ClientDriver().connect(connectionString,null);
            statement = connection.createStatement();
            statement.execute("create table inventory(id int GENERATED ALWAYS AS IDENTITY, fee double)");
            statement.execute("insert into inventory(fee) values (2.5)");
            statement.execute("insert into inventory(fee) values (1.2)");
            statement.execute("insert into inventory(fee) values (2.2)");
            rs = statement.executeQuery("SELECT id,fee FROM inventory ORDER BY id");

            while (rs.next()) {
                System.out.println("id=" + rs.getInt("id")
                        + " fee=" + rs.getDouble("fee"));
            }
            statement.execute("DROP TABLE inventory");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) try {
                statement.close();
            } catch (Exception e) {
            }
            if (connection != null) try {
                connection.close();
            } catch (Exception e) {
            }
            if (rs != null) try {
                rs.close();
            } catch (Exception e) {
            }
        }
    }
}
