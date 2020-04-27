package sdu.cbse.group2.common.services;

import sdu.cbse.group2.common.data.Entity;
import sdu.cbse.group2.common.data.GameSprite;

import java.io.Serializable;

public interface ObstacleService extends Serializable {

    Entity create(float x, float y);

    GameSprite getGameSprite();

    String getObstacleName();
}
