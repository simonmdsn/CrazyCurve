package sdu.cbse.group2.common.data;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


public class World {

    private final Map<UUID, Entity> entityMap = new ConcurrentHashMap<>();

    public UUID addEntity(Entity entity) {
        entityMap.put(entity.getUuid(), entity);
        return entity.getUuid();
    }

    public void removeEntity(UUID uuid) {
        entityMap.remove(uuid);
    }

    public void removeEntity(Entity entity) {
        entityMap.remove(entity.getUuid());
    }
    
    public Collection<Entity> getEntities() {
        return entityMap.values();
    }

    public <E extends Entity> List<Entity> getEntities(Class<E>... entityTypes) {
        List<Entity> r = new ArrayList<>();
        for (Entity e : getEntities()) {
            for (Class<E> entityType : entityTypes) {
                if (entityType.equals(e.getClass())) {
                    r.add(e);
                }
            }
        }
        return r;
    }

    public Entity getEntity(UUID uuid) {
        return entityMap.get(uuid);
    }
}
