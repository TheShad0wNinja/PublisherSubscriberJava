package subscriber;

import remote.ServiceRemote;
import remote.outputdata.ConsoleOutput;
import service.Service;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Scanner;

public class Subscriber {
    public static void main(String[] args) {
//        try {
//            Registry registry = LocateRegistry.getRegistry("localhost", Service.PORT);
//            ServiceRemote service = (ServiceRemote) registry.lookup("service");
//
//            Scanner sc = new Scanner(System.in);
//            System.out.println("Enter your name: ");
//            String subscriberName = sc.nextLine().trim();
//
//            System.out.println("BUE Events Notifications System");
//            System.out.println("List of Notification Options:");
//
//            ArrayList<String> notificationOptions = service.getAvailableTopics();
//            for (int i = 0; i < notificationOptions.size(); i++) {
//                System.out.println("[" + (i + 1) + "]: " + notificationOptions.get(i));
//            }
//            int choice = sc.nextInt() - 1;
//
//            SubscriberRemoteImpl subscriberRemote = new SubscriberRemoteImpl(new ConsoleOutput());
//            service.subscribe(subscriberName, notificationOptions.get(choice), subscriberRemote);
//
//            while(true){
//                for (int i = 0; i < notificationOptions.size(); i++) {
//                    System.out.println("[" + (i + 1) + "]: " + notificationOptions.get(i));
//                }
//                choice = sc.nextInt() - 1;
//                if (choice == - 1)
//                    break;
//
//                if (choice < 0 || choice >= notificationOptions.size())
//                    continue;
//                service.subscribe(subscriberName, notificationOptions.get(choice), subscriberRemote);
//            }
//
////            service.unsubscribe(subscriberName, );
//        } catch (RemoteException | NotBoundException  e) {
//            System.out.println("Error: " + e.getMessage());
//        }

    }
}
