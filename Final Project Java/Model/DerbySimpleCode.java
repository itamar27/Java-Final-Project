package Model;

import java.util.List;

public class DerbySimpleCode {
    public static void main(String[] args) throws CostManagerException {

        try {
            DerbyDBModel db = new DerbyDBModel();

            List<Category> categories = db.getAllCategory();
//            db.addCostItem(new CostItem("almost easy", 20.2, Currency.ILS, new Category("Food"), "2002-12-14"));
//            db.addCostItem(new CostItem("Very cheap", 4, Currency.NZD, new Category("Food"), "2021-12-14"));
//            new CostItem("Very pricey", 12.2, Currency.USD, new Category("Shopping"), "2000-12-14");

            List<CostItem> myCosts = db.getCostItemsBetweenDates("1990-01-01", "2021-12-31");

            myCosts.forEach(System.out::println);

            System.out.println("--------------------------------------");

//            categories.forEach(System.out::println);

            List<IModel.Pair> forPieChart = db.getCategorySumBetweenDates("1990-01-01", "2021-12-31");

            forPieChart.forEach(System.out::println);


            db.DerbyDBModelRelease();

        } catch (CostManagerException e) {
            System.out.println(e.getMessage());
            throw new CostManagerException("Could not create or connect to db");
        }


        System.out.print("The connection was created everything is working so far!");

    }
}
