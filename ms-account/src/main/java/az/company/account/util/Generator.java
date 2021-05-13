package az.company.account.util;

import az.company.account.model.AccountType;

/**
 * @Author QasimovEY on 13.05.21
 */
public class Generator {
    public static String generateAccountNO(Integer customerId,
                                           AccountType type, String currency, String branchCode) {
        var accNo = new StringBuilder();
        accNo.append(type.getPrefix());
        accNo.append(currency);

        for (int i = String.valueOf(customerId).length(); i <= 7; i++) {
            accNo.append('0');
        }

        accNo.append(customerId);
        accNo.append(branchCode);

        return accNo.toString();
    }
}
