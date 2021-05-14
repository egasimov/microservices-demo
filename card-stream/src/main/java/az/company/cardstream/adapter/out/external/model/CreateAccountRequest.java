package az.company.cardstream.adapter.out.external.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author QasimovEY on 13.05.21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateAccountRequest {
    private Integer customerId;
    private AccountType accountType;
    private String branchCode;
    private String currency;
}
