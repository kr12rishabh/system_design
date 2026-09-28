package example.learning.lld.foundation.oop;

/**
 * Lesson 1.2 - Encapsulation
 *
 * Keep important data private and control how it changes.
 */
public class EncapsulationDemo {

    public static void main(String[] args) {
        BankAccount account = new BankAccount();

        account.deposit(1000);
        account.withdraw(250);
        account.deposit(-100); // rejected

        System.out.println("Final balance: Rs." + account.getBalance());
    }

    static class BankAccount {
        private double balance;

        void deposit(double amount) {
            if (amount <= 0) {
                System.out.println("Deposit amount must be positive");
                return;
            }

            balance += amount;
        }

        void withdraw(double amount) {
            if (amount <= 0 || amount > balance) {
                System.out.println("Invalid withdrawal");
                return;
            }

            balance -= amount;
        }

        double getBalance() {
            return balance;
        }
    }
}
