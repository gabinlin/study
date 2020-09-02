package top.gabin.java.core.one.section2;

import java.io.*;

public class ObjectStreamTest {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Employee secretary = new Employee();
        secretary.setName("gabin");
        Manager managerOne = new Manager();
        managerOne.setSecretary(secretary);
        Manager managerTwo = new Manager();
        managerTwo.setSecretary(secretary);
        String name = "Manager.txt";
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(name));
        Manager[] managers = new Manager[2];
        managers[0] = managerOne;
        managers[1] = managerTwo;
        objectOutputStream.writeObject(managers);
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(name));
        Manager[] managersTemp = (Manager[]) objectInputStream.readObject();

        // 序列化保证了反序列化的引用，还是同一个对象
        System.out.println(managersTemp[0].getSecretary() == managersTemp[1].getSecretary());
    }
}
