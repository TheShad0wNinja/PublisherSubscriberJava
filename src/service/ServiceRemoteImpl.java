package service;

import remote.NotificationRemote;
import remote.OutputHandler;
import remote.ServiceRemote;
import remote.SubscriberRemote;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ServiceRemoteImpl extends UnicastRemoteObject implements ServiceRemote {
    protected static final long TTL = 20000;
    protected final ConcurrentMap<String, NotificationEntry> notifications;
    protected final ConcurrentMap<String, Queue<NotificationMessage>> notificationQueues;
    protected final ConcurrentMap<String, SubscriberEntry> subscribers;
    private final OutputHandler outputHandler;

    protected record NotificationMessage(String content, Set<String> receivedSubscribers, LocalDateTime timestamp) {}
    protected static class NotificationEntry {
        public NotificationRemote notificationRemote;
        public HashSet<String> subscribers;

        public NotificationEntry(NotificationRemote notificationRemote, HashSet<String> subscribers) {
            this.notificationRemote = notificationRemote;
            this.subscribers = subscribers;
        }
    }
    protected static class SubscriberEntry {
        public SubscriberRemote subscriberRemote;
        public HashSet<String> notifications;

        public SubscriberEntry(SubscriberRemote subscriberRemote, HashSet<String> notifications) {
            this.subscriberRemote = subscriberRemote;
            this.notifications = notifications;
        }
    }

    protected ServiceRemoteImpl(OutputHandler outputHandler) throws RemoteException {
        this.outputHandler = outputHandler;
        notifications = new ConcurrentHashMap<>();
        subscribers = new ConcurrentHashMap<>();
        notificationQueues = new ConcurrentHashMap<>();
    }

    @Override
    public void registerTopic(String name, NotificationRemote notificationRemote) throws RemoteException {
        if (notifications.containsKey(name)) {
            System.out.println("Registering exiting notification: " + name);
            notifications.get(name).notificationRemote = notificationRemote;
            return;
        }

        System.out.println("Registering notification: " + name);
        notifications.put(name, new NotificationEntry(notificationRemote, new HashSet<>()));
        notificationQueues.put(name, new LinkedList<>());
        outputHandler.output("");
    }

    @Override
    public void unregisterTopic(String name) throws RemoteException {
        System.out.println("Unregistering notification: " + name);
        for(SubscriberEntry subscriber : subscribers.values()) {
            subscriber.notifications.remove(name);
        }
        notifications.remove(name);
        notificationQueues.remove(name);
        outputHandler.output("");
    }

    @Override
    public void publish(String topic, String content) throws RemoteException {
        NotificationMessage newMessage = new NotificationMessage(content, new HashSet<>(), LocalDateTime.now());
        System.out.println("Pushing notification: " + topic + " -> content: " + content);
        for(String subscriber : notifications.get(topic).subscribers) {
            try {
                System.out.println("Pushing notification: " + topic + " -> content: " + content + " -> subscriber: " + subscriber);
                subscribers.get(subscriber).subscriberRemote.receiveNotification(topic, content);
                newMessage.receivedSubscribers.add(subscriber);
            }catch (RemoteException e) {
                System.out.println("Unable to push " + topic + " notification to " + subscriber );
            }
        }
        notificationQueues.get(topic).offer(newMessage);
        cleanupNotificationQueues();
        outputHandler.output("");
    }

    private void cleanupNotificationQueues() {
        System.out.println("Cleaning up notification queues");
        for (var entry : notificationQueues.entrySet()) {
            for (var notificationMessage : entry.getValue()) {
                if (Duration.between(notificationMessage.timestamp(), LocalDateTime.now()).toMillis() > TTL) {
                    System.out.println("Removing notification: " + entry.getKey() + " -> content: " + notificationMessage.content);
                    notificationQueues.get(entry.getKey()).remove(notificationMessage);
                }
            }
        }
    }

    @Override
    public ArrayList<String> getAvailableTopics() {
        return new ArrayList<>(notifications.keySet());
    }

    @Override
    public void registerSubscriber(String subscriber, SubscriberRemote subscriberRemote) throws RemoteException {
        if (subscribers.containsKey(subscriber)) {
            System.out.println("Registering existing subscriber: " + subscriber);
            subscribers.get(subscriber).subscriberRemote = subscriberRemote;
            return;
        }
        System.out.println("Registering subscriber: " + subscriber);
        SubscriberEntry newSubscriberEntry = new SubscriberEntry(subscriberRemote, new HashSet<>());
        subscribers.put(subscriber, newSubscriberEntry);
        outputHandler.output("");
    }

    @Override
    public void unregisterSubscriber(String subscriber) throws RemoteException {
        System.out.println("Unregistering subscriber: " + subscriber);
        for(String notification : subscribers.get(subscriber).notifications) {
            notifications.get(notification).subscribers.remove(subscriber);
        }
        subscribers.remove(subscriber);
        outputHandler.output("");
    }

    @Override
    public void subscribe(String subscriber, String topic) throws RemoteException {
        if (!notifications.containsKey(topic))
            return;

        System.out.println("Subscribing to notification: " + topic + " for " + subscriber);
        NotificationEntry notification =  notifications.get(topic);
        notification.subscribers.add(subscriber);
        notification.notificationRemote.updateSubscriberCount(notification.subscribers.size());
        subscribers.get(subscriber).notifications.add(topic);
        outputHandler.output("");
    }

    @Override
    public void unsubscribe(String subscriber, String topic) throws RemoteException {
        if (!notifications.containsKey(topic))
            return;

        System.out.println("Unsubscribing from notification: " + topic + " for " + subscriber);
        NotificationEntry notification =  notifications.get(topic);
        notification.subscribers.remove(subscriber);
        notification.notificationRemote.updateSubscriberCount(notification.subscribers.size());
        subscribers.get(subscriber).notifications.remove(topic);
        outputHandler.output("");
    }

    @Override
    public void resendMissedNotifications(String user) throws RemoteException {
        for (String topic : subscribers.get(user).notifications) {
            for (NotificationMessage missedNotification : notificationQueues.get(topic)) {
                if (!missedNotification.receivedSubscribers.contains(user)) {
                    System.out.println("Resending missed notification: " + topic + " -> content: " + missedNotification.content + " -> user: " + user);
                    subscribers.get(user).subscriberRemote.receiveNotification(topic, missedNotification.content);
                    missedNotification.receivedSubscribers.add(user);
                }
            }
        }
        System.out.println("Resending missed notifications for  " + user);
        outputHandler.output("");
    }

    @Override
    public Set<String> getSubscribedTopics(String user) throws RemoteException {
        System.out.println("Getting subscribed topics for " + user);
        return subscribers.get(user).notifications;
    }
}
