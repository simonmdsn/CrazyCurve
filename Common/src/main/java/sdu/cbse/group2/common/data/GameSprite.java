package sdu.cbse.group2.common.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class GameSprite implements Serializable {

    private final String imagePath;
    private final float height, width;
    private int layer;
}
