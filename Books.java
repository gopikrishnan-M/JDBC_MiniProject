package LearningJDBC.LibraryProject;

import java.sql.Date;

public class Books {
    int serialNumber;
    String name;
    String author;
    Date year;  //stored as SQL Date
    double price;

    public Books(int serialNumber, String name, String author, Date year, double price) {
        this.serialNumber = serialNumber;
        this.name = name;
        this.author = author;
        this.year = year;
        this.price = price;
    }
}
