package Model;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Year;
import java.time.format.DateTimeFormatter;


public class CostItemDemo {

    public static void main(String args[]) throws CostManagerException, ParseException {

        Category shopping = new Category("Shopping");
        Category lifeStyle = new Category("Life Style", 1);
        Category food = new Category("Food", 2);

        CostItem item = new CostItem("nice carpet",990, Currency.USD,shopping, "11/15/2020");
        CostItem item1 = new CostItem("PS5",450, Currency.GBP, lifeStyle, "11/15/2020");
        CostItem item2 = new CostItem("Iphone1",5000, Currency.ILS, lifeStyle, "11/15/2020");
        CostItem item3 = new CostItem("Gum",1, Currency.NZD, food, "11/15/2020");

        /*
        System.out.println(item);
        System.out.println(item1);
        System.out.println(item2);
        System.out.println(item3);
        */
    }
}
