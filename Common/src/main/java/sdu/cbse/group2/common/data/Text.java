package sdu.cbse.group2.common.data;

import lombok.Data;

@Data
public class Text {

    private String text;
    private float x,y;

    public Text(String text, float x, float y) {
        this.text = text;
        this.x = x;
        this.y = y;
    }
}
