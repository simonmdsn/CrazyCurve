package sdu.cbse.group2.ai;

import lombok.Getter;
import sdu.cbse.group2.common.services.DrawSPI;

public class AiDrawer {

    @Getter
    private static DrawSPI drawSPI;

    public void setDrawSPI(final DrawSPI drawSPI) {
        AiDrawer.drawSPI = drawSPI;
    }
}
