package top.gabin.java.core.one.section2;

public class Manager extends Employee {
    private Employee secretary;

    public Employee getSecretary() {
        return secretary;
    }

    public void setSecretary(Employee secretary) {
        this.secretary = secretary;
    }
}
