package az.company.card.application.in;

import az.company.card.domain.CardOrderEntity;

/**
 * @Author QasimovEY on 14.05.21
 */
public interface PlaceCardOrderUseCase {
    boolean placeCardOrder(CardOrderEntity entity);
}
