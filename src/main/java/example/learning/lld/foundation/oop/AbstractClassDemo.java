package example.learning.lld.foundation.oop;

/**
 * Lesson 1.7 - Abstract Class
 *
 * An abstract class can contain common state, common behaviour,
 * and abstract behaviour that child classes must implement.
 */
public class AbstractClassDemo {

    public static void main(String[] args) {
        Vehicle car = new Car("RJ14AB1234");
        car.printNumber();
        car.drive();
    }

    abstract static class Vehicle {
        protected final String number;

        Vehicle(String number) {
            this.number = number;
        }

        void printNumber() {
            System.out.println("Vehicle number: " + number);
        }

        abstract void drive();
    }

    static class Car extends Vehicle {

        Car(String number) {
            super(number);
        }

        @Override
        void drive() {
            System.out.println("Car is driving");
        }
    }
}
