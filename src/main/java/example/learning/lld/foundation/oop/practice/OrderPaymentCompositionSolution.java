package example.learning.lld.foundation.oop.practice;

/**
 * Lesson 1 Practice Solution
 *
 * Problem:
 * Design an Order that HAS-A Payment object.
 *
 * Concepts used:
 * - HAS-A relationship
 * - composition
 * - interface
 * - polymorphism
 */
public class OrderPaymentCompositionSolution {

    public static void main(String[] args) {
        Order order = new Order(101, 1499.0, new UpiPayment());
        order.checkout();
    }

    interface Payment {
        void pay(double amount);
    }

    static class UpiPayment implements Payment {

        @Override
        public void pay(double amount) {
            System.out.println("Paid Rs." + amount + " using UPI");
        }
    }

    static class CardPayment implements Payment {

        @Override
        public void pay(double amount) {
            System.out.println("Paid Rs." + amount + " using Card");
        }
    }

    static class Order {
        private final int orderId;
        private final double totalAmount;
        private final Payment payment;

        Order(int orderId, double totalAmount, Payment payment) {
            this.orderId = orderId;
            this.totalAmount = totalAmount;
            this.payment = payment;
        }

        void checkout() {
            System.out.println("Checking out order: " + orderId);
            payment.pay(totalAmount);
        }
    }
}
