package LearningJDBC.LibraryProject;
import java.util.*;
import java.sql.Date;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean isNotOver = true;
        while (isNotOver) {
            double LibBalance = DBHelper.getLibraryBalance();
            System.out.println("\nCurrent balance: " + LibBalance);
            System.out.println("1. Add book");
            System.out.println("2. Remove book");
            System.out.println("3. Show all books");
            System.out.println("4. Show one book");
            System.out.println("5. Buy book");
            System.out.println("6. Sell book");
            System.out.println("7. Show balance");
            System.out.println("8. Exit");
            System.out.print("Enter option: ");
            int option = sc.nextInt();
            sc.nextLine();

            switch (option) {
                case 1 -> { // Add book
                    int sno = DBHelper.getNextSerialNumber();
                    System.out.println("Serial number: " + sno);
                    System.out.print("Enter book name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter author: ");
                    String author = sc.nextLine();
                    System.out.print("Enter year (YYYY): ");
                    int yearInt = sc.nextInt();
                    System.out.print("Enter price: ");
                    double price = sc.nextDouble();
                    Date year = Date.valueOf(yearInt + "-01-01");
                    DBHelper.insertBook(new Books(sno, name, author, year, price));
                }
                case 2 -> { // Remove book
                    System.out.print("Enter serial number to remove: ");
                    int sno = sc.nextInt();
                    DBHelper.deleteBook(sno);
                }
                case 3 -> DBHelper.showAllBooks(); // Show all books
                case 4 -> { // Show one book
                    System.out.print("Enter serial number to show: ");
                    int sno = sc.nextInt();
                    DBHelper.showBook(sno);
                }

                case 5 -> { // Buy book
                    System.out.println("Current balance: " + LibBalance);
                    System.out.print("Enter price of book to buy: ");
                    double price = sc.nextInt();
                    sc.nextLine();
                    if (price <= LibBalance) {
                        double newBalance = LibBalance - price;
                        DBHelper.updateLibraryBalance(newBalance);
                        // add book to DB
                        int sno = DBHelper.getNextSerialNumber();
                        System.out.println("Serial number: " + sno);
                        System.out.print("Enter book name: ");
                        String name = sc.nextLine();
                        System.out.print("Enter author: ");
                        String author = sc.nextLine();
                        System.out.print("Enter year (YYYY): ");
                        int yearInt = sc.nextInt();
                        Date year = Date.valueOf(yearInt + "-01-01");
                        DBHelper.insertBook(new Books(sno, name, author, year, price));
                    } else {
                        System.out.println("Not enough balance.");
                    }
                }
                case 6 -> { // Sell book
                    System.out.println("Current balance: " + LibBalance);
                    System.out.print("Enter serial number to sell: ");
                    int sno = sc.nextInt();
                    double price = DBHelper.getBookPrice(sno);
                    if (price > 0) {
                        double newBalance = LibBalance + price;
                        DBHelper.updateLibraryBalance(newBalance);
                        DBHelper.deleteBook(sno);
                        System.out.println("Book sold for " + price + ". New balance: " + newBalance);
                    } else {
                        System.out.println("Book not found.");
                    }
                }
                case 7 -> System.out.println("Balance: " + LibBalance);
                case 8 -> {
                    System.out.println("Thank you for using the library");
                    isNotOver = false;
                }
            }
        }
    }
}
