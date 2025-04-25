package subscriber;

import remote.OutputHandler;
import remote.SubscriberRemote;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class SubscriberRemoteImpl extends UnicastRemoteObject implements SubscriberRemote {
    OutputHandler outputHandler;
    public SubscriberRemoteImpl(OutputHandler outputHandler) throws RemoteException {
        this.outputHandler = outputHandler;
    }

    @Override
    public void receiveNotification(String topic, String content) throws RemoteException {
        outputHandler.output(topic + ": " + content);
    }
}
