package service;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Service {
    public static final int PORT = 8008;
    public static void main(String[] args) {
        try {
            ServiceRemoteImpl service = new ServiceRemoteImpl();

            Registry registry = LocateRegistry.createRegistry(PORT);

            registry.rebind("service", service);

            System.out.println("Service is ready & registered on port: " + PORT);
        } catch (RemoteException e) {
            System.out.println("Service Error: " + e.getMessage());
        }

    }
}
