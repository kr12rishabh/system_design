package example.learning.lld.foundation.oop;

/**
 * Lesson 1.8 - IS-A vs HAS-A
 *
 * IS-A usually means inheritance.
 * HAS-A usually means one object contains/uses another object.
 */
public class IsAHasADemo {

    public static void main(String[] args) {
        Engine engine = new Engine();
        Car car = new Car(engine);

        car.startCar();
    }

    static class Vehicle {
        void move() {
            System.out.println("Vehicle is moving");
        }
    }

    // Car IS-A Vehicle.
    static class Car extends Vehicle {
        private final Engine engine;

        // Car HAS-A Engine.
        Car(Engine engine) {
            this.engine = engine;
        }

        void startCar() {
            engine.start();
            move();
        }
    }

    static class Engine {
        void start() {
            System.out.println("Engine started");
        }
    }
}
