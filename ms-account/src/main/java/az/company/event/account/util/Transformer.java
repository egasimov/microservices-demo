package az.company.event.account.util;

import az.company.event.account.model.dto.AccountDto;
import az.company.event.account.model.entity.Account;

/**
 * @Author QasimovEY on 12.05.21
 */
public class Transformer {
    public static AccountDto toDto(Account entity) {
        AccountDto dto = new AccountDto();
        dto.setId(entity.getId());
        dto.setAccountNumber(entity.getAccountNumber());
        dto.setAccountType(entity.getAccountType().name());
        dto.setCurrency(entity.getCurrency());
        dto.setBranchCode(entity.getBranchCode());
        dto.setState(entity.getState().name());
        dto.setRest(entity.getRest());
        dto.setCustomerId(entity.getCustomerId());
        return dto;
    }
}
