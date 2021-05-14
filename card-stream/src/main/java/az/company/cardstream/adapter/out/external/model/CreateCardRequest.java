package az.company.cardstream.adapter.out.external.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @Author QasimovEY on 14.05.21
 */
@Data
@AllArgsConstructor
@NotNull
public class CreateCardRequest {
    @NotEmpty
    private Integer customerId;

    @NotEmpty
    private Integer accountId;

    @NotEmpty
    private String branchCode;

    @NotNull
    private CardType cardType;

    @NotNull
    private String operationID;
}
