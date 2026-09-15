package example.learning.lld.foundation.oop;

/**
 * Lesson 1.3 - Abstraction
 *
 * The caller knows WHAT can be done.
 * The caller does not need every internal detail of HOW it is done.
 */
public class AbstractionDemo {

    public static void main(String[] args) {
        Payment payment = new UpiPayment();
        payment.pay(500);
    }

    interface Payment {
        void pay(double amount);
    }

    static class UpiPayment implements Payment {

        @Override
        public void pay(double amount) {
            validateAmount(amount);
            connectToBank();
            System.out.println("Paid Rs." + amount + " using UPI");
        }

        private void validateAmount(double amount) {
            if (amount <= 0) {
                throw new IllegalArgumentException("Amount must be positive");
            }
        }

        private void connectToBank() {
            System.out.println("Internal bank communication completed");
        }
    }
}
