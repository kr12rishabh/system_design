package example.learning.lld.foundation.oop;

/**
 * First LLD learning example.
 *
 * Goal:
 * Understand abstraction, interface, polymorphism and loose coupling
 * using a very small payment example.
 */
public class OopPaymentDemo {

    public static void main(String[] args) {

        PaymentService paymentService = new PaymentService();

        // Same Payment interface, but UPI implementation.
        Payment upiPayment = new UpiPayment();
        paymentService.makePayment(upiPayment, 500.0);

        // Same Payment interface, but Credit Card implementation.
        Payment creditCardPayment = new CreditCardPayment();
        paymentService.makePayment(creditCardPayment, 1200.0);
    }
}

/**
 * Payment is a contract.
 *
 * PaymentService does not need to know HOW UPI or Credit Card works.
 * It only knows that every Payment implementation must provide pay().
 */
interface Payment {

    void pay(double amount);
}

/**
 * One implementation of Payment.
 */
class UpiPayment implements Payment {

    @Override
    public void pay(double amount) {
        System.out.println("Paid Rs." + amount + " using UPI");
    }
}

/**
 * Another implementation of Payment.
 */
class CreditCardPayment implements Payment {

    @Override
    public void pay(double amount) {
        System.out.println("Paid Rs." + amount + " using Credit Card");
    }
}

/**
 * Notice something important:
 *
 * PaymentService depends on Payment interface.
 * It does not depend directly on UpiPayment or CreditCardPayment.
 *
 * Because of this, tomorrow we can create:
 *
 * NetBankingPayment
 * WalletPayment
 * DebitCardPayment
 *
 * without changing this class.
 */
class PaymentService {

    public void makePayment(Payment payment, double amount) {
        payment.pay(amount);
    }
}
