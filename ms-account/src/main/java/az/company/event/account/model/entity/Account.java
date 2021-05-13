package az.company.event.account.model.entity;

import az.company.event.account.model.AccountType;
import az.company.event.account.model.State;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author QasimovEY on 13.05.21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "accounts")
public class Account implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ACCOUNT_NUMBER", nullable = false)
    private String accountNumber;

    @Column(name = "ACCOUNT_TYPE", nullable = false)
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @Column(name = "CURRENCY", nullable = false)
    private String currency;

    @Column(name = "BRANCH_CODE", nullable = false)
    private String branchCode;

    @Column(name = "STATUS", nullable = false)
    @Enumerated(EnumType.STRING)
    private State state;

    @Column(name = "REST")
    private BigDecimal rest;

    @Column(name = "CUSTOMER_ID", nullable = false)
    private Integer customerId;

}
