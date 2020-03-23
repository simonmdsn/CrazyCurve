package sdu.cbse.group2.common.data.entityparts;

import sdu.cbse.group2.common.data.Entity;
import sdu.cbse.group2.common.data.GameData;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ItemPart implements EntityPart {
    private Map<UUID, Entity> itemMap = new ConcurrentHashMap<>();

    @Override
    public void process(GameData gameData, Entity entity) {

    }

    public void addItem(Entity item) {
        itemMap.put(item.getUuid(), item);
    }

    public Collection<Entity> getItems() {
        return itemMap.values();
    }

    public <E extends Entity> List<Entity> getItems(Class<E>... entityTypes) {
        List<Entity> r = new ArrayList<>();
        for (Entity e : getItems()) {
            for (Class<E> entityType : entityTypes) {
                if (entityType.equals(e.getClass())) {
                    r.add(e);
                }
            }
        }
        return r;
    }

    public <E extends Entity> void removeItems(Class<E>... entityTypes) {
        for (Entity e : getItems()) {
            for (Class<E> entityType : entityTypes) {
                if (entityType.equals(e.getClass())) {
                    itemMap.remove(e.getUuid());
                }
            }
        }
    }

}
