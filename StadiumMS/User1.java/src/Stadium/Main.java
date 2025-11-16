package Stadium;

import java.util.Map;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static HistoryStack historyStack = new HistoryStack();
    static UserService userService = new UserService(historyStack);
    static EventService eventService = new EventService(historyStack);
    static ConcertService concertService = new ConcertService(historyStack);
    static BookingService bookingService = new BookingService();
    static CategoryService categoryService = new CategoryService();  

    public static void main(String[] args) {
        while (true) {
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Book Ticket");
            System.out.println("4. Create Event");
            System.out.println("5. Create Concert");
            System.out.println("6. View All Users");
            System.out.println("7. View All Events");
            System.out.println("8. View All Concerts");
            System.out.println("9. View History");
            System.out.println("10. Add Category");  
            System.out.println("11. View All Categories");  
            System.out.println("12. Update Category");  
            System.out.println("13. Delete Category");  
            System.out.println("14. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine();  

            switch (choice) {
                case 1:
                    System.out.println("Enter name:");
                    String name = scanner.nextLine();
                    System.out.println("Enter email:");
                    String email = scanner.nextLine();
                    System.out.println("Enter password:");
                    String password = scanner.nextLine();
                    if (userService.registerUser(name, email, password)) {
                        System.out.println("Registration successful!");
                    } else {
                        System.out.println("Registration failed!");
                    }
                    break;
                case 2:
                    System.out.println("Enter Registered email:");
                    String loginEmail = scanner.nextLine();
                    System.out.println("Enter password:");
                    String loginPassword = scanner.nextLine();
                    if (userService.authenticateUser(loginEmail, loginPassword)) {
                        System.out.println("Login successful!");
                    } else {
                        System.out.println("Login failed!");
                    }
                    break;
                case 3:
                    System.out.println("Enter user ID:");
                    int userId = scanner.nextInt();
                    System.out.println("Require AC seat (true/false):");
                    boolean requireAc = scanner.nextBoolean();
                    bookingService.bookTicket(userId, requireAc);
                    break;
                case 4:
                    System.out.println("Enter event name:");
                    String eventName = scanner.nextLine();
                    System.out.println("Enter event date (YYYY-MM-DD):");
                    String eventDate = scanner.nextLine();
                    System.out.println("Enter event location:");
                    String eventLocation = scanner.nextLine();
                    eventService.createEvent(eventName, eventDate, eventLocation);
                    break;
                case 5:
                    System.out.println("Enter event ID:");
                    int eventIdForConcert = scanner.nextInt();
                    scanner.nextLine(); 
                    System.out.println("Enter artist name:");
                    String artist = scanner.nextLine();
                    System.out.println("Enter genre:");
                    String genre = scanner.nextLine();
                    concertService.createConcert(eventIdForConcert, artist, genre);
                    break;
                case 6:
                    Map<Integer, User> users = userService.viewAllUsers();
                    users.forEach((id, user) -> System.out.println(user));
                    break;
                case 7:
                    Map<Integer, Event> events = eventService.viewAllEvents();
                    events.forEach((id, event) -> System.out.println(event));
                    break;
                case 8:
                    Map<Integer, Concert> concerts = concertService.viewAllConcerts();
                    concerts.forEach((c_id, concert) -> System.out.println(concert));
                    break;
                case 9:
                    historyStack.viewAllHistory();
                    break;
                case 10:
                    addCategory(); 
                  break;
                case 11:
                    viewAllCategories();  
                    break;
                case 12:
                    updateCategory();  
                    break;
                case 13:
                    deleteCategory();  
                    break;
                case 14:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    
    private static void addCategory() {
        System.out.println("Enter category name:");
        String name = scanner.nextLine();
        System.out.println("Enter category price:");
        double price = scanner.nextDouble();
        System.out.println("Does it have AC? (true/false):");
        boolean hasAc = scanner.nextBoolean();
        scanner.nextLine();  

        if (categoryService.addCategory(name, price, hasAc)) {
            System.out.println("Category added successfully!");
        } else {
            System.out.println("Failed to add category.");
        }
    }


    private static void viewAllCategories() {
        Map<Integer, Category> categories = categoryService.viewAllCategories();
        if (categories.isEmpty()) {
            System.out.println("No categories found.");
        } else {
            categories.forEach((id, category) -> 
                System.out.println("ID: " + id + ", Name: " + category.getName() + ", Price: " + category.getPrice() + ", Has AC: " + category.isHasAc()));
        }
    }

    private static void updateCategory() {
        System.out.println("Enter category ID to update:");
        int id = scanner.nextInt();
        scanner.nextLine();  
        System.out.println("Enter new category name:");
        String name = scanner.nextLine();
        System.out.println("Enter new category price:");
        double price = scanner.nextDouble();
        System.out.println("Does it have AC? (true/false):");
        boolean hasAc = scanner.nextBoolean();
        scanner.nextLine();  

        if (categoryService.updateCategory(id, name, price, hasAc)) {
            System.out.println("Category updated successfully!");
        } else {
            System.out.println("Failed to update category.");
        }
    }

    private static void deleteCategory() {
        System.out.println("Enter category ID to delete:");
        int id = scanner.nextInt();
        scanner.nextLine();  
        if (categoryService.deleteCategory(id)) {
            System.out.println("Category deleted successfully!");
        } else {
            System.out.println("Failed to delete category.");
        }
    }
}



