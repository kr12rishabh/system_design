package example.design_pattern.low_level_design.creational_design_patterns.prototype_design_pattern.network_connection_example;

public class ConnectionMain {
    static void main() throws InterruptedException {
        System.out.println("Creating Object using prototype design pattern");
        NetworkConnection networkConnection = new NetworkConnection();

        networkConnection.setIp("192.46.7.6");
        networkConnection.loadVeryImportantData();

//        System.out.println(networkConnection);
//        networkConnection.clone()


//        we want new object of NetworkObjectCOnnection

        try {

            NetworkConnection networkConnection1 = (NetworkConnection) networkConnection.clone();
            NetworkConnection networkConnection2 = (NetworkConnection) networkConnection.clone();
            System.out.println(networkConnection);
            networkConnection.getDomains().removeFirst();
            System.out.println(networkConnection);
            // this is example of shallow copy
            System.out.println(networkConnection1);
            System.out.println(networkConnection2);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }


    }
}
