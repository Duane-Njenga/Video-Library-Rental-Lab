package vls;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class VLSServer {
    public static void main(String[] args) {
        try {
            VLSService service = new VLSServiceImpl();
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("VLSService", service);
            System.out.println("Server is running...");
        } catch (Exception e) {
            System.out.println("Server error: " + e.getMessage());
        }
    }
}