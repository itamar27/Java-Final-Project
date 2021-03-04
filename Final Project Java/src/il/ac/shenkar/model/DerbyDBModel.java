/**
 * @author Barak Daniel - 204594329
 * @author Itamar Yarden - 204289987
 */

package il.ac.shenkar.model;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * This class implements the IModel interface.
 * The DB used for this class is DerbyDB, the class creates the db,
 * making a connection to the db and also queries it.
 */
public class DerbyDBModel implements IModel {

    //The driver used in the forName() call in the constructor
    private static String driver = "org.apache.derby.jdbc.EmbeddedDriver";
    //The string used to create the connection to our DerbyDB
    private static String connectionString = "jdbc:derby:";

    //This members used to the queries from the DB
    private Connection connection = null;
    private Statement statement = null;


    /**
     * Calls forName() with our driver and then createDB
     * @throws CostManagerException
     */
    public DerbyDBModel() throws CostManagerException {
        try {
            //this driver as an available driver for DriverManager
            Class.forName(driver);
            createDB();
        } catch (CostManagerException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new CostManagerException("Could not create DerbyDBModel");
        }
    }

    /**
     * In the start of each query we create our connection using this method
     * @throws CostManagerException
     */
    @Override
    public void createConnection() throws CostManagerException {
        try {
            //Getting a connection by calling getConnection
            connection = DriverManager.getConnection(connectionString + "CostMangerDB;create=true");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new CostManagerException("Could not create a connection to derbyDB");
        }
    }

    /**
     * In the end of each query we close our connection using this method
     * @throws CostManagerException
     */
    public void closeConnection() throws CostManagerException {
        try {
            if (statement != null)
                statement.close();

            if (connection != null)
                connection.close();
        } catch (SQLException e) {
            throw new CostManagerException("Error closing DB");
        }
    }

    /**
     * Creates the DB if there isn't one yet, otherwise does nothing.
     * @throws CostManagerException
     */
    @Override
    public void createDB() throws CostManagerException {
        try {
            this.createConnection();
            this.statement = connection.createStatement();
        } catch (SQLException e) {
            throw new CostManagerException("Connection couldn't be made");
        }

        try {
            //using the JDBC MetaData to check if table already exist
            boolean categoryTableNotExist = true;
            boolean costItemTableNotExist = true;

            DatabaseMetaData meta = connection.getMetaData();
            ResultSet res = meta.getTables(null, null, null, new String[]{"TABLE"});
            //Check the names of the tables we got in our DB
            while (res.next()) {
                if (res.getString("TABLE_NAME").equals("CATEGORY")) {
                    categoryTableNotExist = false;
                }
                if (res.getString("TABLE_NAME").equals("COSTITEM")) {
                    costItemTableNotExist = false;
                }
            }
            //Create Table for Category if there isn't one
            if (categoryTableNotExist) {
                statement.execute("create table Category(id INT GENERATED ALWAYS AS IDENTITY NOT NULL," +
                        "name VARCHAR(30) NOT NULL, PRIMARY KEY(name))");
                addCategory(new Category("Shopping"));
                addCategory(new Category("Rent"));
                addCategory(new Category("Food"));
            }
            //Create Table for Cost Item if there isn't one
            if (costItemTableNotExist) {
                statement.execute("create table CostItem(id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY, " +
                        "category VARCHAR(30) REFERENCES Category(name) NOT NULL ," +
                        " amount DOUBLE NOT NULL, currency VARCHAR(5) NOT NULL, description VARCHAR(100), date DATE NOT NULL)");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new CostManagerException("Error with creating a table statement");
        }
        this.closeConnection();
    }

    /**
     * Add a CostItem to the Cost Item Table.
     * @param item - the CostItem to be added to the db
     * @throws CostManagerException
     */
    @Override
    public void addCostItem(CostItem item) throws CostManagerException {
        try {
            this.createConnection();
            this.statement = connection.createStatement();
        } catch (SQLException e) {
            throw new CostManagerException("Connection couldn't be made");
        }

        //Transform into it's SQL equivalent
        Date date = Date.valueOf(item.getDate());
        try {
            statement.execute("INSERT INTO CostItem(category, amount, currency, description, date)" +
                    " values('" + item.getCategory().getName() +
                    "'," + item.getAmount() +
                    ",'" + item.getCurrency().name() +
                    "','" + item.getDescription() + "','" + date + "')");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new CostManagerException("Could not add new CostItem");
        }
        this.closeConnection();
    }

    /**
     * Returns a List of CostItems between the given dates.
     * @param fromDate
     * @param toDate
     * @return List<CostItem> - the list of all the costs items between the given dates.
     * @throws CostManagerException
     */
    @Override
    public List<CostItem> getCostItemsBetweenDates(String fromDate, String toDate) throws CostManagerException {
        try {
            this.createConnection();
            this.statement = connection.createStatement();
        } catch (SQLException e) {
            throw new CostManagerException("Connection couldn't be made");
        }

        ResultSet rs = null;
        List<CostItem> costItems = new ArrayList<CostItem>();

        //Parsing the strings to create the dates as java.time.LocalDate
        DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate from_date = LocalDate.parse(fromDate, inputFormat);
        LocalDate to_date = LocalDate.parse(toDate, inputFormat);

        //Creating java.sql.Date from LocalDate to use it in the query
        Date fromDate_sql = Date.valueOf(from_date);
        Date toDate_sql = Date.valueOf(to_date);
        try {
            rs = statement.executeQuery("SELECT * FROM CostItem WHERE date between '" + fromDate_sql + "' and '" + toDate_sql + "'");

            //For every result in the set make a new CostItem
            while (rs.next()) {
                String description = rs.getString("description");
                Double amount = rs.getDouble("amount");
                String currency = rs.getString("currency");
                String category = rs.getString("category");
                Date date = rs.getDate("date");
                int id = rs.getInt("id");

                costItems.add(new CostItem(id,new Category(category), amount, Currency.valueOf(currency),description , date.toString()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new CostManagerException("Could not receive data");
        } finally {
            if (rs != null) try {
                rs.close();
            } catch (SQLException e) {
                throw new CostManagerException(e.getMessage());
            }
        }
        this.closeConnection();

        return costItems;
    }

    /**
     * Return a List of pairs containing a category and the sum of it's costs between the given dates
     * @param fromDate
     * @param toDate
     * @return - List<Pair> - list of pairs containing the name of the category and the sum between the given dates.
     * @throws CostManagerException
     */
    @Override
    public List<Pair> getCategorySumBetweenDates(String fromDate, String toDate) throws CostManagerException {
        try {
            createConnection();
            statement = connection.createStatement();
        } catch (SQLException e) {
            throw new CostManagerException("Connection couldn't be made");
        }

        ResultSet rs = null;

        //Pair implementation can be found inside the IModel Interface
        List<Pair> categoriesSum = new ArrayList<Pair>();

        //Parsing the strings to create the dates as java.time.LocalDate
        DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate from_date = LocalDate.parse(fromDate, inputFormat);
        LocalDate to_date = LocalDate.parse(toDate, inputFormat);

        //Creating java.sql.Date from LocalDate to use it in the query
        Date fromDate_sql = Date.valueOf(from_date);
        Date toDate_sql = Date.valueOf(to_date);
        try {
            rs = statement.executeQuery("SELECT category, SUM(amount) as amount FROM CostItem WHERE date between '" + fromDate_sql + "' and '" + toDate_sql + "' GROUP BY category");

            //For every result in the set add a new Pair
            while (rs.next()) {
                String name = rs.getString("category");
                Double amount = rs.getDouble("amount");

                categoriesSum.add(new Pair(name, amount));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new CostManagerException("Could not receive data");
        } finally {
            if (rs != null) try {
                rs.close();
            } catch (SQLException e) {
                throw new CostManagerException(e.getMessage());
            }
        }
        this.closeConnection();

        return categoriesSum;
    }

    /**
     * Add a new category to the DB, if the category name already exits it will not
     * be added and an exception will be thrown.
     * @param category
     * @throws CostManagerException
     */
    @Override
    public void addCategory(Category category) throws CostManagerException {
        try {
            createConnection();
            statement = connection.createStatement();
        } catch (SQLException e) {
            throw new CostManagerException("Connection couldn't be made");
        }

        try {
            statement.execute("INSERT INTO category(name) values('" + category.getName() + "')");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new CostManagerException("Could not add new category");
        }
        this.closeConnection();
    }

    /**
     * Return a List of all our categories in the DB.
     * @return List<Category> - all the categories in the DB under Category Table.
     * @throws CostManagerException
     */
    @Override
    public List<Category> getAllCategory() throws CostManagerException {
        try {
            createConnection();
            statement = connection.createStatement();
        } catch (SQLException e) {
            throw new CostManagerException("Connection couldn't be made");
        }

        List<Category> categories = new ArrayList<Category>();
        ResultSet rs = null;

        try {
            rs = statement.executeQuery("SELECT * FROM Category");

            //For every result in the set we will add a new category to the list
            while (rs.next()) {
                Category category = new Category(rs.getString("name"), rs.getInt("id"));
                categories.add(category);
            }
        } catch (SQLException e) {
            throw new CostManagerException(e.getMessage());
        } finally {
            if (rs != null) try {
                rs.close();
            } catch (SQLException e) {
                throw new CostManagerException(e.getMessage());
            }
        }
        this.closeConnection();

        return categories;
    }
}
