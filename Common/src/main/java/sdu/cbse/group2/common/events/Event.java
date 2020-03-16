package sdu.cbse.group2.common.events;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import sdu.cbse.group2.common.data.Entity;

import java.io.Serializable;

@RequiredArgsConstructor
@Getter
public class Event implements Serializable {

    private final Entity source;
}
