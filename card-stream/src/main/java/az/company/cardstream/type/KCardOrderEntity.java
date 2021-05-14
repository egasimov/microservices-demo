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
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class KCardOrderEntity {

    private String uuid;

    private String fin;

    private String branchCode;

    private String email;

    private String phoneNumber;

    private String cardHolderName;

    private String cardType;

    private String operationId;

}
