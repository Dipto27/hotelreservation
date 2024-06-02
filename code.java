import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

class Room {
    private int roomNumber;
    private String roomType;
    private double price;
    private boolean isBooked;

    public Room(int roomNumber, String roomType, double price) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.price = price;
        this.isBooked = false;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getRoomType() {
        return roomType;
    }

    public double getPrice() {
        return price;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void book() {
        this.isBooked = true;
    }

    public void setBooked(boolean isBooked) {
        this.isBooked = isBooked;
    }
}

class Guest {
    private String name;
    private String email;

    public Guest(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}

class Hotel {
    private String name;
    private ArrayList<Room> rooms;
    private HashMap<Integer, Guest> bookings;

    public Hotel(String name) {
        this.name = name;
        this.rooms = new ArrayList<>();
        this.bookings = new HashMap<>();
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }

    public ArrayList<Room> checkAvailability(String roomType) {
        ArrayList<Room> availableRooms = new ArrayList<>();
        for (Room room : rooms) {
            if (room.getRoomType().equalsIgnoreCase(roomType) && !room.isBooked()) {
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }

    public boolean bookRoom(Guest guest, int roomNumber) {
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber) {
                if (!room.isBooked()) {
                    room.book();
                    bookings.put(roomNumber, guest);
                    System.out.println("Room " + roomNumber + " booked successfully by " + guest.getName() + ".");
                    return true;
                } else {
                    System.out.println("Room " + roomNumber + " is already booked.");
                    return false;
                }
            }
        }
        System.out.println("Room " + roomNumber + " does not exist.");
        return false;
    }

    public void displayBookedRooms() {
        if (bookings.isEmpty()) {
            System.out.println("No rooms booked yet.");
        } else {
            for (int roomNumber : bookings.keySet()) {
                Guest guest = bookings.get(roomNumber);
                System.out.println("Room " + roomNumber + " is booked by " + guest.getName() + " (" + guest.getEmail() + ").");
            }
        }
    }
}

public class HotelReservationSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Hotel hotel = new Hotel("The Grand Hotel");

        // Adding some rooms to the hotel
        hotel.addRoom(new Room(101, "Single", 100));
        hotel.addRoom(new Room(102, "Double", 150));
        hotel.addRoom(new Room(103, "Suite", 200));

        while (true) {
            System.out.println("\nWelcome to The Grand Hotel Reservation System");
            System.out.println("1. Check room availability");
            System.out.println("2. Book a room");
            System.out.println("3. Display booked rooms");
            System.out.println("4. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter room type (Single/Double/Suite): ");
                    String roomType = scanner.nextLine();
                    ArrayList<Room> availableRooms = hotel.checkAvailability(roomType);
                    if (availableRooms.isEmpty()) {
                        System.out.println("No rooms available for the selected type.");
                    } else {
                        System.out.println("Available rooms:");
                        for (Room room : availableRooms) {
                            System.out.println("Room " + room.getRoomNumber() + " - " + room.getRoomType() + " - $" + room.getPrice());
                        }
                    }
                    break;

                case 2:
                    System.out.print("Enter guest name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter guest email: ");
                    String email = scanner.nextLine();
                    Guest guest = new Guest(name, email);
                    System.out.print("Enter room number to book: ");
                    int roomNumber = scanner.nextInt();
                    hotel.bookRoom(guest, roomNumber);
                    break;

                case 3:
                    hotel.displayBookedRooms();
                    break;

                case 4:
                    System.out.println("Thank you for using the hotel reservation system. Goodbye!");
                    scanner.close();
                    System.exit(0);

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}