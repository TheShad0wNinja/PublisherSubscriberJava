package notification;

import remote.OutputHandler;
import remote.NotificationRemote;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class NotificationRemoteImpl extends UnicastRemoteObject implements NotificationRemote {
    OutputHandler outputHandler;

    protected NotificationRemoteImpl(OutputHandler outputHandler) throws RemoteException {
        this.outputHandler = outputHandler;
    }

    @Override
    public void updateSubscriberCount(int count) throws RemoteException {
        outputHandler.output("Subscriber Count: " + count);
    }
}
