package remote;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public interface ServiceRemote extends Remote {
    void registerTopic(String name, NotificationRemote notification) throws RemoteException;
    void unregisterTopic(String name) throws RemoteException;
    void publish(String topic, String content) throws RemoteException;
    ArrayList<String> getAvailableTopics() throws RemoteException;

    void registerSubscriber(String subscriber, SubscriberRemote subscriberRemote) throws RemoteException;
    void unregisterSubscriber(String subscriber) throws RemoteException;
    void subscribe(String user, String topic) throws RemoteException;
    void unsubscribe(String user, String topic) throws RemoteException;
    Set<String> getSubscribedTopics(String user) throws RemoteException;

    void resendMissedNotifications(String user) throws RemoteException;
}
