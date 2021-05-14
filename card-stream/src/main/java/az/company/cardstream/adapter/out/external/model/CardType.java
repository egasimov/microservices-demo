package az.company.cardstream.adapter.out.external.model;

import java.util.Arrays;
import java.util.Optional;

/**
 * @Author QasimovEY on 14.05.21
 */
public enum CardType {
    VISA("VISA"),
    MASTER("MASTER"),
    MAESTRO("MAESTRO");

    private final String name;

    CardType(String name) {
        this.name = name();
    }

    public static Optional<CardType> of(String name) {
        return Arrays.stream(CardType.values())
                .filter(item->item.name.equalsIgnoreCase(name))
                .findFirst();
    }

    public String getName() {
        return name;
    }
}
