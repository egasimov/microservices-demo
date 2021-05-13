package az.company.card.application.in;

import az.company.card.domain.CardEntity;

/**
 * @Author QasimovEY on 14.05.21
 */
public interface CreateCardUseCase {
    boolean createCard(CardEntity entity);
}
