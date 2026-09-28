package example.learning.lld.foundation.oop;

/**
 * Lesson 1.1 - Class and Object
 *
 * A class is a blueprint.
 * An object is a real instance created from that blueprint.
 */
public class ClassAndObjectDemo {

    public static void main(String[] args) {
        Car bmw = new Car("BMW");
        Car audi = new Car("Audi");

        bmw.drive();
        audi.drive();
    }

    static class Car {
        private final String brand;

        Car(String brand) {
            this.brand = brand;
        }

        void drive() {
            System.out.println(brand + " is driving");
        }
    }
}
