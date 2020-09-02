package top.gabin.patterns.observable;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public enum Event implements Observable {
    REGISTER,
    SHOPPING;
    private Set<Listener> invalidationListenerSet = new HashSet<>();

    @Override
    public void addListener(Listener listener) {
        invalidationListenerSet.add(listener);
    }

    @Override
    public void removeListener(Listener listener) {
        invalidationListenerSet.remove(listener);
    }

    @Override
    public void notifyAllObserver(EventData eventData) {
        invalidationListenerSet.stream().forEach(listener -> listener.handle(eventData));
    }


}
