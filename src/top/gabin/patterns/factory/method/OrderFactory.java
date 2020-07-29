package top.gabin.patterns.factory.method;

public interface OrderFactory<T extends OrderForm> {
    Order createOrder(T form);
}
