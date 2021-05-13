package az.company.card.application.out;

import az.company.card.domain.CardOrderEntity;

/**
 * @Author QasimovEY on 14.05.21
 */
public interface EnqueueOrderPort {
    boolean enqueue(CardOrderEntity entity);
}
