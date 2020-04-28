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
    @Getter
    private Tile[][] tiles;

    public World(GameData gameData) {
        fillTiles(gameData);
    }

    private void fillTiles(GameData gameData) {
        int numOfTilesInRow = (int) Math.ceil((float) gameData.getDisplayHeight() / (float) Tile.LENGTH);
        tiles = new Tile[numOfTilesInRow][numOfTilesInRow];
        for (int i = 0; i < numOfTilesInRow; i++) {
            for (int j = 0; j < numOfTilesInRow; j++) {
                Tile tile = new Tile(new PositionPart(i * Tile.LENGTH, j * Tile.LENGTH, 0));
                tiles[i][j] = tile;
            }
        }
    }

    public Tile getNearestTile(int x, int y) {
        try {
            return tiles[x / Tile.LENGTH][y / Tile.LENGTH];
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }

    public UUID addEntity(Entity entity) {
        entityMap.put(entity.getUuid(), entity);
        PositionPart part = entity.getPart(PositionPart.class);
        if (part != null) {
            getNearestTile((int) part.getX(), (int) part.getY()).getEntities().add(entity);
        }
        return entity.getUuid();
    }

    public void removeEntity(UUID uuid) {
        removeEntity(entityMap.get(uuid));
    }

    public void removeEntity(Entity entity) {
        if(entity == null) {
            return;
        }
        entityMap.remove(entity.getUuid());
        PositionPart part = entity.getPart(PositionPart.class);
        if (part != null) {
            getNearestTile((int) part.getX(), (int) part.getY()).getEntities().remove(entity);
        }
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
