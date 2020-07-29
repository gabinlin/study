package top.gabin.patterns.observable;

import java.util.List;

public class Tests {
    public static void main(String[] args) {
        register();
        shopping();
    }

    private static void shopping() {
        Event shopping = Event.SHOPPING;
        // 假设现在需要多种发送消息的方式，这边采取简单的硬编码，实际这种类型的模块功能也不会一直变
        // 对单个事件监听再分发不同的任务，理论上也算是一种观察者模式
        // 日志写错了，是java8支持接口有静态方法
        List<MessageSend> messageSendList = List.of(msg -> {
            System.out.println("微信通知：" + msg);
            return true;
        }, msg -> {
            System.out.println("短信通知：" + msg);
            return true;
        });
        shopping.addListener(new Listener() {
            private List<MessageSend> messageSendListT = messageSendList;
            @Override
            public void handle(EventData eventData) {
                messageSendListT.forEach(messageSend -> messageSend.send(eventData.getData().toString()));
            }
        });
        shopping.addListener(eventData -> System.out.println("购物送积分"));

        EventData eventData = new EventData(shopping, "您已成功购买一台傻瓜机");
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
