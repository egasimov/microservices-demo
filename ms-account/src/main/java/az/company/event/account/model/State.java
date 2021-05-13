package az.company.event.account.model;

import java.util.Arrays;
import java.util.Optional;

/**
 * @Author QasimovEY on 13.05.21
 */
public enum State {

    ACTIVE("ACTIVE"),
    BLOCKED("BLOCKED");

    private String name;

    State(String name) {
        this.name = name;
    }

    public static Optional<State> of(String name) {
        return Arrays.stream(State.values())
                .filter(item -> item.name.equalsIgnoreCase(name))
                .findFirst();
    }
}
