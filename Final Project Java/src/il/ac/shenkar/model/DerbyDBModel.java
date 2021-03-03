package il.ac.shenkar.model;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
            //this driver as an available driver for DriverManager
            Class.forName(driver);
            createDB();

        } catch (CostManagerException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new CostManagerException("Could not create DerbyDBModel");
        }
    }

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


    /*
     * General DB query implementation
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
            while (res.next()) {

                if (res.getString("TABLE_NAME").equals("CATEGORY")) {
                    categoryTableNotExist = false;
                }
                if (res.getString("TABLE_NAME").equals("COSTITEM")) {
                    costItemTableNotExist = false;
                }
            }

            if (categoryTableNotExist) {
                statement.execute("create table Category(id INT GENERATED ALWAYS AS IDENTITY NOT NULL," +
                        "name VARCHAR(30) NOT NULL, PRIMARY KEY(name))");
                addCategory(new Category("Shopping"));
                addCategory(new Category("Rent"));
                addCategory(new Category("Food"));
            }
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


    /*
     * CostItem queries implementation
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

    /*
     * Category queries implementation
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
