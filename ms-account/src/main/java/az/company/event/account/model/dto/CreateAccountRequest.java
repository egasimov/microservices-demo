package az.company.event.account.model.dto;

import az.company.event.account.model.AccountType;
import lombok.Data;

/**
 * @Author QasimovEY on 13.05.21
 */
@Data
public class CreateAccountRequest {
    private Integer customerId;
    private AccountType accountType;
    private String branchCode;
    private String currency;
}
