package az.company.cardstream.application;

import az.company.cardstream.adapter.out.external.AccountClient;
import az.company.cardstream.adapter.out.external.CardClient;
import az.company.cardstream.adapter.out.external.CustomerClient;
import az.company.cardstream.adapter.out.external.EventClient;
import az.company.cardstream.adapter.out.external.model.AccountType;
import az.company.cardstream.adapter.out.external.model.CardEntity;
import az.company.cardstream.adapter.out.external.model.CardType;
import az.company.cardstream.adapter.out.external.model.CreateAccountRequest;
import az.company.cardstream.adapter.out.external.model.CreateCardRequest;
import az.company.cardstream.adapter.out.external.model.CustomerDto;
import az.company.cardstream.adapter.out.external.model.Event;
import az.company.cardstream.type.KCardOrderEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @Author QasimovEY on 14.05.21
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CardService {
    private final AccountClient accountClient;
    private final CustomerClient customerClient;
    private final CardClient cardClient;
    private final EventClient eventClient;

    public CardEntity onboardOrder(KCardOrderEntity kCardOrderEntity) throws InterruptedException{
        String operationId = kCardOrderEntity.getOperationId();
        String uuid = kCardOrderEntity.getUuid();

        //get Customer Info
        CustomerDto customerInfo = customerClient.searchCustomer(kCardOrderEntity.getFin());
        var event = generateEvent(kCardOrderEntity, "CARD-STREAM:MS-CUSTOMER",
                "CUSTOMER FOUND ID: %s".formatted(customerInfo.getId()));
        eventClient.placeEvent(operationId, event);

        //create Account for new Card
        CreateAccountRequest accountRequest = new CreateAccountRequest(customerInfo.getId(),
                AccountType.CURR_ACCOUNT,
                kCardOrderEntity.getBranchCode(),
                "944");
        Integer accountID = accountClient.createAccount(accountRequest);
        event = generateEvent(kCardOrderEntity, "CARD-STREAM:MS-ACCOUNT",
                "ACCOUNT CREATED, ID {}".formatted(accountID));
        eventClient.placeEvent(operationId, event);

        var cardRequest = new CreateCardRequest(customerInfo.getId(),
                accountID,
                kCardOrderEntity.getBranchCode(),
                CardType.of(kCardOrderEntity.getCardType()).orElse(CardType.MAESTRO),
                operationId);
        CardEntity result = cardClient.createCard(cardRequest);

        event = generateEvent(kCardOrderEntity, "CARD-STREAM:MS-ACCOUNT",
                "CARD CREATED: %s, ID {}".formatted(result, accountID));
        eventClient.placeEvent(operationId, event);

        return result;
    }

    public Event generateEvent(KCardOrderEntity kCardOrderEntity,
                               String source, String message) {
        var eventTime = DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(LocalDateTime.now());
        return new Event(kCardOrderEntity.getUuid(),
                message,
                source,
                kCardOrderEntity.getOperationId(),
                eventTime);
    }
}
