package sdu.cbse.group2.commonpowerup;

public interface PowerUpSPI {

    void register(Class<? extends CommonPowerUp> commonPowerUp);

    void unregister(Class<? extends CommonPowerUp> commonPowerUp);
}
