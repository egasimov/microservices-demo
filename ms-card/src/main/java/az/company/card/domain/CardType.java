package az.company.card.domain;

/**
 * @Author QasimovEY on 14.05.21
 */
public enum CardType {
    VISA("VISA","41697414"),
    MASTER("MASTER","51030715"),
    MAESTRO("MAESTRO","67626121");

    private final String prefix;
    private final String name;

    CardType(String name, String prefix) {
        this.name = name;
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }
}
