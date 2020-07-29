package top.gabin.patterns.observable;

public interface Observable {
    void addListener(Listener listener);
    void removeListener(Listener listener);
    void notifyAllObserver(EventData eventData);
}
