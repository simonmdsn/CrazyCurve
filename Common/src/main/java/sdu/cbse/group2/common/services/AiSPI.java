package sdu.cbse.group2.common.services;

import sdu.cbse.group2.common.data.Entity;
import sdu.cbse.group2.common.data.World;

public interface AiSPI {

    void move(Entity enemy, World world, int searchRadius);
}
