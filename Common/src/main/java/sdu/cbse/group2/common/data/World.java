package sdu.cbse.group2.common.data;

import lombok.Getter;
import sdu.cbse.group2.common.data.entityparts.PositionPart;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;


public class World {

    private final Map<UUID, Entity> entityMap = new ConcurrentHashMap<>();
    @Getter
    private final List<Text> textList = new CopyOnWriteArrayList<>();

    //TODO should tile be list or 2d array?
    //private Tile[][] tiles;
    @Getter
    private final List<Tile> tiles = new ArrayList<>();

    public World(GameData gameData) {
        fillTiles(gameData);
    }

    private void fillTiles(GameData gameData) {
        int height = gameData.getDisplayHeight();
        for (int i = 0; i < height; i += Tile.length) {
            for (int j = 0; j < height; j += Tile.length) {
                Tile tile = new Tile(new PositionPart(i,j, 0));
                tiles.add(tile);
            }
        }
    }

    public Tile getNearestTile(int x, int y) {
        for (Tile tile : tiles) {
            if (tile.getPositionPart().getX() > x - Tile.length && tile.getPositionPart().getY() > y - Tile.length && tile.getPositionPart().getX() < x && tile.getPositionPart().getY() < y) {
                return tile;
            }
        }
        return null;
    }

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

    public <E extends Entity> List<Entity> getBoundedEntities(Class<E>... entityTypes) {
        List<Entity> r = new ArrayList<>();
        for (Entity e : getEntities()) {
            for (Class<E> entityType : entityTypes) {
                if (entityType.isAssignableFrom(e.getClass())) {
                    r.add(e);
                }
            }
        }
        return r;
    }

    public Entity getEntity(UUID uuid) {
        return entityMap.get(uuid);
    }

    public void addText(Text text) {
        textList.add(text);
    }

    public void removeText(Text text) {
        textList.remove(text);
    }

    public <E extends Text> void boundedTextClear(Class<E>... textTypes) {
        for (Text text : textList) {
            for (Class<E> subText : textTypes) {
                if (subText.isAssignableFrom(text.getClass())) {
                    textList.remove(text);
                }
            }
        }
    }
}
