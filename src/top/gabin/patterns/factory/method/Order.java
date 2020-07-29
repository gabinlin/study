package top.gabin.patterns.factory.method;

public class Order {
    private String type;

    public Order(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
