package example.learning.lld.foundation.oop;

/**
 * Lesson 1.4 - Inheritance
 *
 * Inheritance is useful when the child truly IS-A parent.
 */
public class InheritanceDemo {

    public static void main(String[] args) {
        Car car = new Car();
        car.start();
        car.openBoot();
    }

    static class Vehicle {

        void start() {
            System.out.println("Vehicle started");
        }
    }

    static class Car extends Vehicle {

        void openBoot() {
            System.out.println("Car boot opened");
        }
    }
}
