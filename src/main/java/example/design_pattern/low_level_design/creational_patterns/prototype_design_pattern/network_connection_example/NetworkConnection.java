package example.design_pattern.low_level_design.creational_patterns.prototype_design_pattern.network_connection_example;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class NetworkConnection implements Cloneable {
    private String ip;
    private String importantData;
    private List<String> domains = new ArrayList<>();

    public void loadVeryImportantData() throws InterruptedException {
        this.importantData = " This is very very important data :";
        domains.add("www.rishabh.com");
        domains.add("google.com");
        domains.add("aubank.com");
        domains.add("triquetra.com");


        Thread.sleep(5000);
    }

    @Override
    public String toString() {
        return this.ip + " : " + this.importantData + " : "+ this.domains;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        // logic for cloning
        NetworkConnection networkConnection = new NetworkConnection();
        networkConnection.setIp(this.getIp());
        networkConnection.setIp(this.getImportantData());
        for(String d : this.getDomains()){
            networkConnection.getDomains().add(d);
        }
        return networkConnection;
    }
}
