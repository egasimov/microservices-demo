package az.company.card.shared;

import az.company.card.domain.CardType;

/**
 * @Author QasimovEY on 14.05.21
 */
public class Generator {
    public static String panGenerator(CardType cardType, Integer accountId) {
        var builder = new StringBuilder();
        builder.append(cardType.getPrefix());
        for (int i = String.valueOf(accountId).length(); i <= 8; i++) {
            builder.append("0");
        }
        builder.append(accountId);
        return builder.toString();
    }
}
