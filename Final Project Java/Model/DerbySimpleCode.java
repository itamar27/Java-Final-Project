package Model;

import java.util.List;

public class DerbySimpleCode {
    public static void main(String[] args) throws CostManagerException {

        try {
            DerbyDBModel db = new DerbyDBModel();
            //db.addCategory(new Category("Pets"));
//            CostItem ci = new CostItem("super market", 2222, Currency.ILS,new Category("Food"),"2020-05-31");
//            db.addCostItem(ci);
            List<Category> categories = db.getAllCategory();

            List<CostItem> myCosts = db.getCostItemsBetweenDates("1990-01-01", "2020-12-31");

            myCosts.forEach(System.out::println);

            System.out.println("--------------------------------------");

            categories.forEach(System.out::println);


        }catch(CostManagerException e){
            System.out.println(e.getMessage());
            throw new CostManagerException("Could not create or connect to db");
        }

        System.out.print("The connection was created everything is working so far!");






        /*
        ResultSet rs = null;
        try {
            connection = null;
            //Instantiating the driver class will indirectly register
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
        }*/
    }
}
