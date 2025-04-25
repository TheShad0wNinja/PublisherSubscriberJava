package subscriber;

import remote.OutputHandler;
import remote.SubscriberRemote;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class SubscriberRemoteImpl extends UnicastRemoteObject implements SubscriberRemote {
    OutputHandler outputHandler;
    OutputHandler updateNotificationOptionsHandler;
    public SubscriberRemoteImpl(OutputHandler outputHandler, OutputHandler updateNotificationOptionsHandler) throws RemoteException {
        this.outputHandler = outputHandler;
        this.updateNotificationOptionsHandler = updateNotificationOptionsHandler;
    }

    @Override
    public void receiveNotification(String topic, String content) throws RemoteException {
        outputHandler.output(topic + ": " + content);
    }

    @Override
    public void updateNotificationOptions() throws RemoteException {
        updateNotificationOptionsHandler.output("");
    }
}
