package az.company.card.application;

import az.company.card.adapter.in.web.error.exception.CommonException;
import az.company.card.adapter.in.web.error.exception.InvalidInputException;
import az.company.card.adapter.out.external.EventPublisherClient;
import az.company.card.adapter.out.external.model.Event;
import az.company.card.application.in.PlaceCardOrderUseCase;
import az.company.card.application.in.ViewCardOrderUseCase;
import az.company.card.application.out.CheckOrderExistencePort;
import az.company.card.application.out.EnqueueOrderPort;
import az.company.card.domain.CardOrderEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @Author QasimovEY on 14.05.21
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CardOrderService implements PlaceCardOrderUseCase, ViewCardOrderUseCase {

    private final CheckOrderExistencePort checkOrderExistence;
    private final EnqueueOrderPort enqueueOrderPort;
    //    private final PublishEventPort publishEventPort;
    private final EventPublisherClient eventPublisherClient;

    @Override
    public boolean placeCardOrder(CardOrderEntity entity) {
        //check uuid duplicate occurrence
        boolean duplicateUuid = checkOrderExistence.exist(entity.getUuid());
        if (duplicateUuid) {
            throw InvalidInputException.of("Duplicate UUID entered: %s".formatted(entity.getUuid()));
        }
        //enqueue order
        boolean isSent = enqueueOrderPort.enqueue(entity);

        if (!isSent) {
            throw new CommonException(500, "Try a moment later");
        }

        //publish event
        String eventTime = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        var event = new Event(
                entity.getUuid(),
                "ORDER PLACED",
                "MS-CARD",
                entity.getOperationId(),
                eventTime);
        try {
            eventPublisherClient.placeEvent(event.getOperationId(), event).wait(10);
        } catch (Exception ex) {
            log.info("Unable to publish notification for {}", event);
        }
        return true;
    }

    @Override
    public List<Object> viewCardOrderEvents(String operationId) {
        return eventPublisherClient.getAllEvents(operationId);
    }

    @Override
    public Object viewCardOrderStatus(String operationId) {
        return eventPublisherClient.getOperationStatus(operationId);
    }

}
