package az.company.card.shared.transformer;

import az.company.card.domain.CardEntity;
import az.company.card.domain.CardOrderCommand;
import az.company.card.domain.CardOrderEntity;
import az.company.card.domain.CreateCardRequest;

import java.util.UUID;

/**
 * @Author QasimovEY on 14.05.21
 */
public class CardOrderTransformer {
    public static CardOrderEntity toEntity(CardOrderCommand command) {
        var entity = new CardOrderEntity();
        entity.setUuid(command.getUuid());
        entity.setOperationId(UUID.randomUUID().toString());
        entity.setCardType(command.getCardType());
        entity.setEmail(command.getEmail());
        entity.setBranchCode(command.getBranchCode());
        entity.setCardHolderName(command.getCardHolderName());
        entity.setPhoneNumber(command.getPhoneNumber());
        entity.setFin(command.getFin());
        return entity;
    }

    public static CardEntity toEntity(CreateCardRequest request) {
        var entity = new CardEntity();
        entity.setCardType(request.getCardType());
        entity.setOperationID(request.getOperationID());
        entity.setCustomerId(request.getCustomerId());
        entity.setBranchCode(request.getBranchCode());
        entity.setAccountId(request.getAccountId());
        return entity;
    }

}
