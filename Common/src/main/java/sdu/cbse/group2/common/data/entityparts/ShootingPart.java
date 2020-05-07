package sdu.cbse.group2.common.data.entityparts;

import lombok.Getter;
import lombok.Setter;
import sdu.cbse.group2.common.data.Entity;
import sdu.cbse.group2.common.data.GameData;

@Getter
@Setter
public class ShootingPart implements EntityPart{

    private boolean shooting;
    private float coolDown;

    public boolean isShooting() {
        return shooting;
    }

    public void setShooting(boolean isShooting) {
        this.shooting = isShooting;
    }

    private void reduceCoolDown(float delta) {
        coolDown -= delta;
    }

    @Override
    public void process(GameData gameData, Entity entity) {
        reduceCoolDown(gameData.getDelta());
    }
}
