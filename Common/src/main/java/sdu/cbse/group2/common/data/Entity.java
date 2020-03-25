package sdu.cbse.group2.common.data;

import lombok.Getter;
import lombok.Setter;
import sdu.cbse.group2.common.data.entityparts.EntityPart;

import java.io.Serializable;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@Setter
public class Entity implements Serializable {

    private final UUID uuid = UUID.randomUUID();
    private double radius = 9;

    private final GameSprite gameSprite;
    private final Map<Class<? extends EntityPart>, EntityPart> parts = new ConcurrentHashMap<>();

    public Entity(GameSprite gameSprite) {
        this.gameSprite = gameSprite;
    }

    public void add(EntityPart part) {
        parts.put(part.getClass(), part);
    }

    public void remove(Class partClass) {
        parts.remove(partClass);
    }

    public <E extends EntityPart> E getPart(Class<? extends EntityPart> partClass) {
        return (E) parts.get(partClass);
    }

}
