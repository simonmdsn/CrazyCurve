package sdu.cbse.group2.gamestates.settings;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CrazyCurveModule {

    private final int id;
    private final String name;
    private boolean active;

    public CrazyCurveModule(final String entry) {
        // ID|State|Level|Name
        // 0  1     2     3
        String[] split = entry.replace(" ", "").split("\\|");
        id = Integer.parseInt(split[0]);
        active = split[1].equalsIgnoreCase("Active");
        // sdu.cbse.group2.<name>[sdu.cbse.group2](1.0.0.SNAPSHOT)) ---> <name>
        name = split[3].substring("sdu.cbse.group2.".length(), split[3].indexOf("["));
    }

    public void toggleActive() {
        active = !active;
    }
}
