package az.company.card.adapter.in.web;

import az.company.card.adapter.in.web.error.exception.InvalidInputException;
import az.company.card.application.in.CreateCardUseCase;
import az.company.card.application.in.ViewCardUseCase;
import az.company.card.domain.CardEntity;
import az.company.card.domain.CreateCardRequest;
import az.company.card.shared.transformer.CardOrderTransformer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author QasimovEY on 14.05.21
 */
@Slf4j
@RestController
@RequestMapping("/api/card")
@RequiredArgsConstructor
@Validated
public class CardController {

    private final CreateCardUseCase createCardUseCase;
    private final ViewCardUseCase viewCardUseCase;

    @GetMapping("/{operationId}/info")
    public ResponseEntity<CardEntity> getCardInfo(@PathVariable String operationId) {
        if (StringUtils.isBlank(operationId)) {
            throw InvalidInputException.of("OPERATION ID MUST BE NOT EMPTY: Operation ID %s".formatted(operationId));
        }
        return ResponseEntity.ok(viewCardUseCase.getCardInfo(operationId));
    }

    @PostMapping
    public ResponseEntity<String> createCard(@RequestBody CreateCardRequest createCardRequest) {
        CardEntity entity = CardOrderTransformer.toEntity(createCardRequest);
        String result = createCardUseCase.createCard(entity) ? "SUCCESS" : "FAILURE";
        return ResponseEntity.ok(result);
    }
}
