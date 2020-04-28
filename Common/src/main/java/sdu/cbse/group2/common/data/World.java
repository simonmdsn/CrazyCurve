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
    private final Map<Tile, Entity> tilesEntityMap = new ConcurrentHashMap<>();
    //TODO should tile be list or 2d array?
    @Getter
    private Tile[][] tiles;
//    @Getter
//    private final List<Tile> tiles = new CopyOnWriteArrayList<>();

    public World(GameData gameData) {
        fillTiles(gameData);
    }

    private void fillTiles(GameData gameData) {
        int numOfTilesInRow = (int) Math.ceil((float) gameData.getDisplayHeight() / (float) Tile.length);
        tiles = new Tile[numOfTilesInRow][numOfTilesInRow];
        for (int i = 0; i < numOfTilesInRow; i++) {
            for (int j = 0; j < numOfTilesInRow; j++) {
                System.out.println(i * Tile.length + " " + j * Tile.length);
                Tile tile = new Tile(new PositionPart(i * Tile.length, j * Tile.length, 0));
                tiles[i][j] = tile;
            }
        }
    }

    public Tile getNearestTile(int x, int y, GameData gameData) {
        if(x < 0 && y < 0 && x > gameData.getDisplayWidth() && y > gameData.getDisplayWidth()) {
            return null;
        }
        return tiles[x / Tile.length][y / Tile.length];
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
