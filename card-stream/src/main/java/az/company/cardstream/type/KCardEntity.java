package az.company.cardstream.type;

import az.company.cardstream.adapter.out.external.model.CardType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Author QasimovEY on 14.05.21
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class KCardEntity {
    private Integer customerId;

    private Integer accountId;

    private String branchCode;

    private CardType cardType;

    private String operationID;
}
