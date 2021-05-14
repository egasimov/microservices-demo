package az.company.card.application;

import az.company.card.adapter.in.web.error.exception.InvalidInputException;
import az.company.card.application.in.CreateCardUseCase;
import az.company.card.application.in.ViewCardUseCase;
import az.company.card.application.out.CreateCardPort;
import az.company.card.application.out.LoadCardInfoPort;
import az.company.card.domain.CardEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @Author QasimovEY on 14.05.21
 */
@Service
@RequiredArgsConstructor
public class CardService implements CreateCardUseCase, ViewCardUseCase {

    private final LoadCardInfoPort loadCardInfoPort;
    private final CreateCardPort createCardPort;

    public CardEntity getCardInfo(String operationId) {
        return loadCardInfoPort.load(operationId);
    }

    public CardEntity createCard(CardEntity entity) {
        //checkCardExistence with operation
        CardEntity result = loadCardInfoPort.load(entity.getOperationID());
        if (result != null) {
            throw InvalidInputException.of("Duplicate Operation ID %s".formatted(entity.getOperationID()));
        }
        //need to check customerId with ms-customer
        //need to check accountId with ms-account
        createCardPort.save(entity);
        return entity;
    }

}
