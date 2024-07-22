import java.util.*;

class Car {
    private String carId;

    private String model;

    private String brand;

    private double basePricePerDay;

    private boolean isAvailable;

    public Car(String carId, String model, String brand, double basePricePerDay, boolean isAvailable){
        this.carId = carId;
        this.brand = brand;
        this.model = model;
        this.basePricePerDay = basePricePerDay;
        this.isAvailable = true;
    }

    public String getCarId() {
        return carId;
    }

    public String getBrand() {
        return brand;
    }
    
    public String getModel() {
        return model;
    }

    public double calculatePrice(int rentalDays){
        return basePricePerDay * rentalDays;
    }

    public boolean isAvailable(){
        return isAvailable;
    }

    public void rent(){
        isAvailable = false;
    }

    public void returnCar(){
        isAvailable = true;
    }
}
    class Customer {
        private String customerId;

        private String name;

        public Customer(String customerId, String name){
            this.customerId = customerId;
            this.name = name;
        }

        public String getCustomerId(){
            return customerId;
        }

        public String getName(){
            return name;
        }
    }

    class Rental {
        private Car car;

        private Customer customer;

        private int days;

        public Rental(Car car, Customer customer, int days){
            this.car = car;
            this.customer = customer;
            this.days = days;
        }

        public Car getCar(){
            return car;
        }

        public Customer getCustomer(){
            return customer;
        }

        public int getDays(){
            return days;
        }
    }

    class CarRentalSystem {
        private List<Car> cars;   // not in memory its just declaration
        private List<Customer> customers;
        private List<Rental> rentals;

        public CarRentalSystem(){
            cars = new ArrayList<>(); // formed in memory
            customers = new ArrayList<>();  // these are blank arrayList ready to input data
            rentals = new ArrayList<>();  // arraylist.add(5) is used to add element into arraylist i.e 5 into the array
        }

        public void addCar(Car car){
            cars.add(car);
        }

        public void addCustomer(Customer customer){
            customers.add(customer);
        }

        public void rentCar(Car car, Customer customer, int days){
            if(car.isAvailable()){
                car.rent();
                rentals.add(new Rental(car, customer, days));
            } else {
                System.out.println("Car is not available for rent");
            }
        }

        public void returnCar(Car car){
            car.returnCar();
            Rental rentalToRemove = null;
            for(Rental rental : rentals){  // phle apne data se dekh ki car rent pe di h ki nahi
                if(rental.getCar() == car){ // agr car mil gai data se 
                    rentalToRemove = rental; // to rented data se us car ko hta do
                    break;
                }
            }
            if(rentalToRemove != null){
                rentals.remove(rentalToRemove);
                // System.out.println("Car return successfully");
            } else {
                System.out.println("Car was not rented");
            }
        }

        public void menu(){
            Scanner scn = new Scanner (System.in);

            while (true) { 
                System.out.println("\n---- Car Rental System----\n");
                System.out.println("1. Rent a car");
                System.out.println("2. Return a car");
                System.out.println("3. Exit");
                System.out.print("Enter your choice: ");

                int choice = scn.nextInt();
                scn.nextLine();

                if(choice == 1){
                    System.out.println("\n---Rent a Car---\n");
                    System.out.print("Enter your name : ");
                    String customerName = scn.nextLine();

                    System.out.println("\n  Available Cars   \n");
                    for(Car car : cars){
                        if(car.isAvailable()){
                            System.out.println(car.getCarId() + " - " + car.getBrand() + " " + car.getModel());
                            }
                    }

                    System.out.print("\nEnter the car ID you want to rent : ");
                    String carId = scn.nextLine(); 

                    System.out.print("Enter the number of days for rental : ");
                    int rentalDays = scn.nextInt();
                    scn.nextLine();

                    Customer newCustomer = new Customer("CUS" + (customers.size()+1), customerName);
                    addCustomer(newCustomer);

                    Car selectedCar = null;
                    for(Car car : cars){
                        if(car.getCarId().equals(carId) && car.isAvailable()){
                            selectedCar = car;
                            break;
                        }
                    }

                    if(selectedCar != null){
                        double totalPrice = selectedCar.calculatePrice(rentalDays);
                        System.out.println("\n -- Rental Information -- \n");
                        System.out.println("Customer ID: "+ newCustomer.getCustomerId());
                        System.out.println("Customer Name: "+ newCustomer.getName());
                        System.out.println("Car: "+ selectedCar.getBrand() + " " + selectedCar.getModel());
                        System.out.println("Rental Days: " + rentalDays);
                        System.out.printf("Total price: $%.2f%n", totalPrice);

                        System.out.print("\nConfirm Rental (Y/N) : ");
                        String confirm = scn.nextLine();

                        if(confirm.equalsIgnoreCase("Y")){
                            rentCar(selectedCar, newCustomer, rentalDays); 
                            System.out.println("\nCar rented successfully\n");
                            System.out.println("Thanku for using the Car Rental System...");

                        } else{
                            System.out.println("\nRental cancelled");
                        }
                    } else {
                        System.out.println("\nInvalid car selection or car not available for rent");
                    }
                } else if (choice == 2){
                    System.out.println("\n -- Return a Car -- \n");
                    System.out.print("Enter the car Id you want to return: ");
                    String carId = scn.nextLine();

                    Car carToReturn = null;
                    for(Car car : cars){
                        if(car.getCarId().equals(carId) && !car.isAvailable()){
                            carToReturn = car;
                            break;
                        }
                    }

                    if(carToReturn != null){
                        Customer customer = null;
                        for(Rental rental : rentals){
                            if(rental.getCar() == carToReturn){
                                customer = rental.getCustomer();
                                break;
                            }
                        }

                        if(customer != null){
                            returnCar(carToReturn);
                            System.out.println("Car return successfully by "+customer.getName());
                            System.out.println("\n Thanku for using the Car Rental System...");

                        } else{
                            System.out.println("Car was not rented or rental information is missing");
                        }
                    } else {
                        System.out.println("Invalid car ID or car is not rented");
                    }
                } else if(choice == 3){
                    break;
                } else {
                    System.out.println("Invalid choice. Please enter a valid option");
                }
            }
        System.out.println("\n Thanku for using the Car Rental System...");
        }

    }

public class main{
    public static void main(String[] args) {
        CarRentalSystem rentalSystem = new CarRentalSystem();

        Car car1 = new Car("C001", "Toyota", "Fortuner", 100.0,true);
        Car car2 = new Car("C002", "Honda", "Accord", 80.0,true);
        Car car3 = new Car("C003", "Mahindra", "Thar", 120.0,true);
        rentalSystem.addCar(car1);
        rentalSystem.addCar(car2);
        rentalSystem.addCar(car3);

        rentalSystem.menu();
    }
}


