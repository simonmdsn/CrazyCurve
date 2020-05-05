package sdu.cbse.group2.collision;

import lombok.Getter;
import sdu.cbse.group2.common.services.SoundSPI;

public class CollisionSound {

    @Getter
    private static SoundSPI soundSPI;

    public void setSoundSPI(SoundSPI soundSPI) {
        this.soundSPI = soundSPI;
    }
}
