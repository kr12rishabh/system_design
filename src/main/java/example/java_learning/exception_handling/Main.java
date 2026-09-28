package example.java_learning.exception_handling;

public class Main {
    static void main() throws ClassNotFoundException {
        Main sampleObject = new Main();
        sampleObject.method1();
    }

    private void method1() throws ClassNotFoundException {

            method2();


    }

    private void method2() throws ClassNotFoundException {
        method3();
    }

    private void method3() throws ClassNotFoundException {
        throw new ClassNotFoundException();
    }

//    String [] arr = new String[90000000*9000000000*9000000000];
}
