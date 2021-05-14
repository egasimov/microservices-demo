package az.company.cardstream.adapter.out.external.model;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author QasimovEY on 13.05.21
 */
@Data
public class AccountDto {
    private Integer id;
    private String accountNumber;
    private String accountType;
    private String currency;
    private String branchCode;
    private String state;
    private BigDecimal rest;
    private Integer customerId;
}
