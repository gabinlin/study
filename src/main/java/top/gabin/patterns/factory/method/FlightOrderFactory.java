package top.gabin.patterns.factory.method;

public class FlightOrderFactory implements OrderFactory<FlightOrderForm> {
    @Override
    public Order createOrder(FlightOrderForm form) {
        return new Order("机票");
    }
}
