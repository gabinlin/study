package top.gabin.patterns.observable;

public class Tests {
    public static void main(String[] args) {
        register();
        shopping();
    }

    private static void shopping() {
        Event shopping = Event.SHOPPING;
        shopping.addListener(eventData -> System.out.println("购物发送微信通知"));
        shopping.addListener(eventData -> System.out.println("购物发送短信通知"));
        EventData eventData = new EventData(shopping, 1);
        eventData.notifyAllObserver();
    }

    private static void register() {
        Event register = Event.REGISTER;
        register.addListener(eventData -> System.out.println("注册发送微信通知"));
        register.addListener(eventData -> System.out.println("注册发送短信通知"));
        EventData eventData = new EventData(register, 1);
        eventData.notifyAllObserver();
    }
}
