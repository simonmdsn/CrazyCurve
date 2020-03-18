package sdu.cbse.group2.commonsnake;

import lombok.Getter;
import lombok.Setter;
import sdu.cbse.group2.common.data.Entity;
import sdu.cbse.group2.common.data.GameSprite;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class CommonSnake extends Entity {

    private Entity head;
    private List<Entity> tail;


    public CommonSnake(GameSprite gameSprite) {
        super(gameSprite);
    }

    public CommonSnake(UUID uuid, GameSprite gameSprite) {
        super(uuid, gameSprite);
    }

    //TODO implement holes in snake
    public boolean move() {
        tail.add(new Entity(head.getUuid(), getGameSprite()));
        return true;
    }
}
