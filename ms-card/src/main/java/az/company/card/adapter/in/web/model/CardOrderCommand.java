package az.company.card.adapter.in.web.model;

import az.company.card.domain.CardType;
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
