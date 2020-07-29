package top.gabin.patterns.factory.method;

public class Tests {

    public static void main(String[] args) {
        createFlight();
        createHotel();
    }

    private static void createFlight() {
        FlightOrderFactory flightOrderFactory = new FlightOrderFactory();
        Order order = flightOrderFactory.createOrder(new FlightOrderForm());
        System.out.println(order.getType());
    }

    private static void createHotel() {
        HotelOrderFactory hotelOrderFactory = new HotelOrderFactory();
        Order order = hotelOrderFactory.createOrder(new HotelOrderForm());
        System.out.println(order.getType());
    }

}
