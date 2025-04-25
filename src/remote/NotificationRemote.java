package remote;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface NotificationRemote extends Remote {
    void updateSubscriberCount(int count) throws RemoteException;
}
