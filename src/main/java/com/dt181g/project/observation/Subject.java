package com.dt181g.project.observation;

/**
 * The {@code Subject} interface defines the contract for objects that can be
 * observed by observers (implementing the {@link Observer} interface). It
 * provides methods to manage observers and notify them about changes in the
 * subject(Observable).
 * @author Daniel Jönsson
 */
public interface Subject {

    /**
     * Registers an observer to receive notifications from the subject.
     * @param observer The observer to register.
     */
    void addObserver(Observer observer);

    /**
     * Removes an observer from the list of observers.
     * @param observer The observer to remove.
     */
    void removeObserver(Observer observer);

    /**
     * Notifies all observers about a change in the subject.
     */
    void notifyObserver();
}
