package sdu.cbse.group2.common.services;

import java.util.List;
import java.util.function.Consumer;

public interface TelnetSPI {
    void execute(String command, Consumer<List<String>> response);
}
