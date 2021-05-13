package az.company.card.application.out;

import az.company.card.domain.CardEntity;

/**
 * @Author QasimovEY on 14.05.21
 */
public interface LoadCardInfoPort {
    CardEntity load(String operationId);
}
