package sdu.cbse.group2.speedpowerup;

import sdu.cbse.group2.common.data.GameSprite;
import sdu.cbse.group2.common.data.entityparts.MovingPart;
import sdu.cbse.group2.commonpowerup.CommonPowerUp;
import sdu.cbse.group2.commonpowerup.PowerUpPart;
import sdu.cbse.group2.commonpowerup.PowerUpSPI;
import sdu.cbse.group2.commonsnake.CommonSnake;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class SpeedPowerUp extends CommonPowerUp {

    private static final long DURATION = 3;
    private static final double SPEED_MULTIPLIER = 2;
    private ScheduledFuture<?> schedule;
    private PowerUpSPI powerUpSPI;

    public SpeedPowerUp(GameSprite gameSprite) {
        super(gameSprite);
    }

    @Override
    public void applyPowerUp(CommonSnake commonSnake) {
        ((PowerUpPart) commonSnake.getPart(PowerUpPart.class)).addPowerUp(this);
        if(((PowerUpPart) commonSnake.getPart(PowerUpPart.class)).contains(SpeedPowerUp.class)) {
            schedule.cancel(true);
            removePowerUp(commonSnake);
        }
        MovingPart movingPart = commonSnake.getPart(MovingPart.class);
        movingPart.setMaxSpeed((float) (movingPart.getMaxSpeed() * SPEED_MULTIPLIER));
        schedule = Executors.newSingleThreadScheduledExecutor().schedule(() -> removePowerUp(commonSnake), DURATION, TimeUnit.SECONDS);
        powerUpSPI.register(this);
    }

    @Override
    public void removePowerUp(CommonSnake commonSnake) {
        ((PowerUpPart) commonSnake.getPart(PowerUpPart.class)).removePowerUp(this);
        MovingPart movingPart = commonSnake.getPart(MovingPart.class);
        movingPart.setMaxSpeed((float) (movingPart.getMaxSpeed() / SPEED_MULTIPLIER));
        powerUpSPI.unregister(this);
    }

    public void removePowerUpSPI() {
        powerUpSPI = null;
    }

    public void setPowerUpSPI(PowerUpSPI powerUpSPI) {
        this.powerUpSPI = powerUpSPI;
    }
}
