package az.company.card.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author QasimovEY on 14.05.21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardEntity {
    private Integer customerId;

    private Integer accountId;

    private String branchCode;

    private CardType cardType;

    private String operationID;
}
