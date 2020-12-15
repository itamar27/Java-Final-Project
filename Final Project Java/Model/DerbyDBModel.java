package Model;

import java.sql.*;

public class DerbyDBModel implements IModel {

    private static String driver = "org.apache.derby.jdbc.EmbeddedDriver";
    private static String connectionString = "jdbc:derby:";
    private Connection connection = null;

    public DerbyDBModel(){

    }
    /*
     * General DB query implementation
     */

    @Override
    public void createConnection() throws CostManagerException {

        try {
            //this driver as an available driver for DriverManager
            Class.forName(driver);
            //Getting a connection by calling getConnection
            connection = DriverManager.getConnection(connectionString + "CostMangerDB;create=true");
        }catch(ClassNotFoundException | SQLException e){
            e.printStackTrace();
            throw new CostManagerException("Could not create a connection to derbyDB");
        }
    }

    @Override
    public void createDB() throws CostManagerException{
        Statement statement = null;

        try{
            statement = connection.createStatement();
            statement.execute("create table CostItem(id INT GENERATED ALWAYS AS IDENTITY PK_STATUS PRIMARY KEY, " +
                                  "category CHAR(30) NOT NULL FOREIGN KEY," +
                                  " sum DOUBLE NOT NULL, currency CHAR(5) NOT NULL, description CHAR(100), date DATE NOT NULL)");

            statement.execute("create table Category(id INT GENERATED ALWAYS AS IDENTITY NOT NULL," +
                                   "name CHAR(30) NOT NULL, PRIMARY KEY(id, name))");
        }catch(SQLException e){
            e.printStackTrace();
            throw new CostManagerException("Error with creating a table statement");
        }
    }


    /*
     * CostItem queries implementation
     */

    @Override
    public void addCostItem(CostItem item) throws CostManagerException {


    }

    @Override
    public void deleteCostItem(CostItem item) throws CostManagerException {

    }

    @Override
    public CostItem[] getCostItemsBetweenDates(String fromDate, String toDate) throws CostManagerException {
        return new CostItem[0];
    }

    /*
     * Category queries implementation
     */

    @Override
    public void addCategory(Category category) throws CostManagerException {

    }

    @Override
    public Category[] getAllCategory() throws CostManagerException {
        return new Category[0];
    }
}
