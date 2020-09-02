package top.gabin.patterns.observable;

public class EventData<T> {
    private Event event;
    private T data;

    public EventData(Event event, T data) {
        this.event = event;
        this.data = data;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void notifyAllObserver() {
        event.notifyAllObserver(this);
    }
}
