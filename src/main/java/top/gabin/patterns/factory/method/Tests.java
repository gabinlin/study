package top.gabin.patterns.factory.method;

import java.util.ServiceLoader;

public class Tests {

    public static void main(String[] args) {
        ServiceLoader<OrderFactory> load = ServiceLoader.load(OrderFactory.class);
        load.iterator().forEachRemaining(orderFactory -> System.out.println(orderFactory.toString()));
    }

}
