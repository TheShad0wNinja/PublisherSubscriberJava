package remote;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface SubscriberRemote extends Remote {
    void receiveNotification(String topic, String content) throws RemoteException;
}
