package notification;

import remote.ServiceRemote;
import service.Service;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Notification {
    public static void main(String[] args) {
//        try{
//            Registry serviceRegistry = LocateRegistry.getRegistry("localhost", Service.PORT);
//            ServiceRemote service = (ServiceRemote) serviceRegistry.lookup("service");
//
//            Scanner sc = new Scanner(System.in);
//            System.out.println("Enter the category the notification is for: ");
//            String notificationOption = sc.nextLine().trim();
//
//            service.registerTopic(notificationOption);
//
//            String msg = "";
//            while (true){
//                System.out.println("Enter the message: ");
//                msg = sc.nextLine();
//                if (msg.trim().equalsIgnoreCase("exit"))
//                    break;
//
//                service.publish(notificationOption, msg.trim());
//            }
//
//            service.unregisterTopic(notificationOption);
//            System.out.println("Notification Option " + notificationOption + " unregistered");
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
    }
}
