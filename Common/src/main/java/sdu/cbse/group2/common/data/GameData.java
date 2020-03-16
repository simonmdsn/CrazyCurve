package sdu.cbse.group2.common.data;

import lombok.Getter;
import lombok.Setter;
import sdu.cbse.group2.common.events.Event;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Getter
@Setter
public class GameData {

    private float delta;
    private int displayWidth, displayHeight;
    private final GameKeys keys = new GameKeys();
    private List<Event> events = new CopyOnWriteArrayList<>();

    public void addEvent(Event e) {
        events.add(e);
    }

    public void removeEvent(Event e) {
        events.remove(e);
    }

    public <E extends Event> List<Event> getEvents(Class<E> type, UUID sourceID) {
        return events.stream().filter(event -> event.getClass().equals(type) && event.getSource().getUuid().equals(sourceID)).collect(Collectors.toList());
    }
}
