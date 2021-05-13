package az.company.card.domain;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @Author QasimovEY on 14.05.21
 */
@Data
public class CardOrderCommand {

    @NotEmpty
    private String uuid;

    @NotEmpty
    private String fin;

    @NotEmpty
    private String branchCode;

    private String email;

    private String phoneNumber;

    @NotEmpty
    private String cardHolderName;

    @NotNull
    private CardType cardType;

}
