package example.learning.lld.foundation.oop;

/**
 * Lesson 1.9 - Composition Over Inheritance
 *
 * Instead of making many subclasses only to change one behaviour,
 * give the object another object that represents that behaviour.
 */
public class CompositionOverInheritanceDemo {

    public static void main(String[] args) {
        Car normalCar = new Car(new NormalDrive());
        normalCar.drive();

        Car sportsCar = new Car(new SportsDrive());
        sportsCar.drive();
    }

    interface DriveBehaviour {
        void drive();
    }

    static class NormalDrive implements DriveBehaviour {

        @Override
        public void drive() {
            System.out.println("Normal driving");
        }
    }

    static class SportsDrive implements DriveBehaviour {

        @Override
        public void drive() {
            System.out.println("Sports driving");
        }
    }

    static class Car {
        private final DriveBehaviour driveBehaviour;

        Car(DriveBehaviour driveBehaviour) {
            this.driveBehaviour = driveBehaviour;
        }

        void drive() {
            driveBehaviour.drive();
        }
    }
}
