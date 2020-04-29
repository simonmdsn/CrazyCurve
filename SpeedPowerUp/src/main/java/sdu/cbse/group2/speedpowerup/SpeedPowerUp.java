package sdu.cbse.group2.speedpowerup;

import sdu.cbse.group2.common.data.GameSprite;
import sdu.cbse.group2.common.data.entityparts.MovingPart;
import sdu.cbse.group2.common.data.entityparts.PositionPart;
import sdu.cbse.group2.commonpowerup.CommonPowerUp;
import sdu.cbse.group2.commonpowerup.PowerUpPart;
import sdu.cbse.group2.commonsnake.CommonSnake;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class SpeedPowerUp extends CommonPowerUp {

    private static final long DURATION = 3;
    private static final double SPEED_MULTIPLIER = 50;
    private ScheduledFuture<?> schedule;

    public SpeedPowerUp(int x, int y) {
        super(new GameSprite("textures/powerup/speed.png", 30, 30));
        this.add(new PositionPart(x, y, 0));
    }

    @Override
    public void applyPowerUp(CommonSnake commonSnake) {
        super.applyPowerUp(commonSnake);
        PowerUpPart powerUpPart = commonSnake.getPart(PowerUpPart.class);
        powerUpPart.getOfType(SpeedPowerUp.class).stream().map(SpeedPowerUp.class::cast).collect(Collectors.toList()).forEach(commonPowerUp -> commonPowerUp.removePowerUp(commonSnake));
        MovingPart movingPart = commonSnake.getPart(MovingPart.class);
        movingPart.setMaxSpeed((float) (movingPart.getMaxSpeed() + SPEED_MULTIPLIER));
        schedule = Executors.newSingleThreadScheduledExecutor().schedule(() -> removePowerUp(commonSnake), DURATION, TimeUnit.SECONDS);
        (powerUpPart).addPowerUp(this);
    }

    @Override
    public void removePowerUp(CommonSnake commonSnake) {
        schedule.cancel(true);
        ((PowerUpPart) commonSnake.getPart(PowerUpPart.class)).removePowerUp(this);
        MovingPart movingPart = commonSnake.getPart(MovingPart.class);
        movingPart.setMaxSpeed((float) (movingPart.getMaxSpeed() - SPEED_MULTIPLIER));
    }
}
