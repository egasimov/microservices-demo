package az.company.cardstream.transformer;

import az.company.cardstream.adapter.out.external.model.CardEntity;
import az.company.cardstream.type.KCardEntity;

/**
 * @Author QasimovEY on 14.05.21
 */
public class CardTransformer {
    public static KCardEntity toKafkaEntity(CardEntity cardEntity) {
        var kEntity = new KCardEntity(cardEntity.getCustomerId(), cardEntity.getAccountId(), cardEntity.getBranchCode(),
                cardEntity.getCardType(), cardEntity.getOperationID());
        return kEntity;
    }
}
