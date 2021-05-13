package az.company.event.account.model;

/**
 * @Author QasimovEY on 13.05.21
 */
public enum AccountType {
    CURR_ACCOUNT("4101001"),
    PERCENT_ACCOUNT("2501001"),
    FINE_ACCOUNT("25020002"),
    CRED_ACCOUNT("25040004");

    private String prefix;

    AccountType(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }
}
