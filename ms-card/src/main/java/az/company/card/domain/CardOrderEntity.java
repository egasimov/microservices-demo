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
public class CardOrderEntity {

    private String uuid;

    private String fin;

    private String branchCode;

    private String email;

    private String phoneNumber;

    private String cardHolderName;

    private CardType cardType;

    private String operationId;

}
