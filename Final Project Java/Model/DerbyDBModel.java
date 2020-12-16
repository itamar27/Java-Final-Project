package Model;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DerbyDBModel implements IModel {

    private static String driver = "org.apache.derby.jdbc.EmbeddedDriver";
    private static String connectionString = "jdbc:derby:";
    private Connection connection = null;
    private Statement statement = null;


    /*
     * DerbyDbModel constructor
     */

    public DerbyDBModel() throws CostManagerException {

        try {
            createConnection();
            statement = connection.createStatement();
            createDB();

        }catch (SQLException | CostManagerException e){
            e.printStackTrace();
            throw new CostManagerException("Could not create DerbyDBModel");
        }
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
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new CostManagerException("Could not create a connection to derbyDB");
        }
    }

    @Override
    public void createDB() throws CostManagerException {
        try {

            //using the JDBC MetaData to check if table already exist
            boolean categoryTableNotExist = true;
            boolean costItemTableNotExist = true;

            DatabaseMetaData meta = connection.getMetaData();
            ResultSet res = meta.getTables(null, null, null,new String[] {"TABLE"});
            while (res.next()) {

                if(res.getString("TABLE_NAME").equals("CATEGORY")){
                        categoryTableNotExist = false;
                }
                if(res.getString("TABLE_NAME").equals("COSTITEM")){
                    costItemTableNotExist = false;
                }
            }

            if(categoryTableNotExist)  {
                statement.execute("create table Category(id INT GENERATED ALWAYS AS IDENTITY NOT NULL," +
                        "name CHAR(30) NOT NULL, PRIMARY KEY(name))");
                addCategory(new Category("Shopping"));
                addCategory(new Category("Rent"));
                addCategory(new Category("Food"));
            }
            if(costItemTableNotExist) {
                statement.execute("create table CostItem(id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY, " +
                        "category CHAR(30) REFERENCES Category(name) NOT NULL ," +
                        " amount DOUBLE NOT NULL, currency CHAR(5) NOT NULL, description CHAR(100), date DATE NOT NULL)");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new CostManagerException("Error with creating a table statement");
        }
    }


    /*
     * CostItem queries implementation
     */

    @Override
    public void addCostItem(CostItem item) throws CostManagerException {

        //Transform into it's SQL equivalent
        Date date = Date.valueOf(item.getDate());

        try {
            statement.execute("INSERT INTO CostItem(category, amount, currency, description, date)" +
                                    " values('" + item.getCategory().getName() +
                                    "'," + item.getAmount() +
                                    ",'"+ item.getCurrency().name() +
                                    "','"+ item.getDescription()+"','"+ date +"')");
        }catch(SQLException e){
            e.printStackTrace();
            throw new CostManagerException("Could not add new CostItem");
        }


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

        try {
            statement.execute("INSERT INTO category(name) values('" + category.getName() +"')");
        }catch(SQLException e){
            e.printStackTrace();
            throw new CostManagerException("Could not add new category");
        }

    }

    @Override
    public List<Category> getAllCategory() throws CostManagerException {

        List<Category> categories = new ArrayList<Category>();
        ResultSet rs = null;

        try {
             rs = statement.executeQuery("SELECT * FROM Category");
            while(rs.next()){

                Category category = new Category( rs.getString("name"),rs.getInt("id"));
                categories.add(category);

            }
        }catch(SQLException e){
            throw new CostManagerException(e.getMessage());
        }

        return categories;
    }
}
