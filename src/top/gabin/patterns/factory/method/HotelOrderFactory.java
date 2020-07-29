package top.gabin.patterns.factory.method;

public class HotelOrderFactory implements OrderFactory<HotelOrderForm> {
    @Override
    public Order createOrder(HotelOrderForm form) {
        return new Order("酒店");
    }
}
