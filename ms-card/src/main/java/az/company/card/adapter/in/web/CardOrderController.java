package az.company.card.adapter.in.web;

import az.company.card.adapter.in.web.error.exception.InvalidInputException;
import az.company.card.adapter.in.web.error.exception.RecordNotFoundException;
import az.company.card.application.in.PlaceCardOrderUseCase;
import az.company.card.application.in.ViewCardOrderUseCase;
import az.company.card.domain.CardOrderCommand;
import az.company.card.domain.CardOrderEntity;
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

import java.util.HashMap;
import java.util.List;

/**
 * @Author QasimovEY on 14.05.21
 */
@Slf4j
@RestController
@RequestMapping("/api/card/order")
@RequiredArgsConstructor
@Validated
public class CardOrderController {

    private final PlaceCardOrderUseCase placeCardOrderUseCase;
    private final ViewCardOrderUseCase viewCardOrderUseCase;

    @PostMapping
    public ResponseEntity createCardOrder(@RequestBody CardOrderCommand command) {
        CardOrderEntity entity = CardOrderTransformer.toEntity(command);
        String operationId = placeCardOrderUseCase.placeCardOrder(entity);
        var response = new HashMap<>();
        response.put("operationId",operationId);
        response.put("status","IN_PROGRESS");
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/{operationId}/info")
    public List<Object> viewCardOrderEvents(@PathVariable String operationId) {
        if (StringUtils.isBlank(operationId)) {
            throw InvalidInputException.of("OPERATION ID MUST BE NOT EMPTY: Operation ID %s".formatted(operationId));
        }
        var result = viewCardOrderUseCase.viewCardOrderEvents(operationId);
        if (result.isEmpty()) {
            throw RecordNotFoundException.of("NO EVENTS FOUND FOR OPERATION ID: %s".formatted(operationId));
        }
        return viewCardOrderUseCase.viewCardOrderEvents(operationId);
    }

    @GetMapping(value = "/{operationId}/status")
    public Object viewCardOrderStatus(@PathVariable String operationId) {
        if (StringUtils.isBlank(operationId)) {
            throw InvalidInputException.of("OPERATION ID MUST BE NOT EMPTY: Operation ID %s".formatted(operationId));
        }
        var result = viewCardOrderUseCase.viewCardOrderStatus(operationId);
        if (result == null) {
            throw RecordNotFoundException.of("NO EVENTS FOUND FOR OPERATION ID: %s".formatted(operationId));
        }
        return viewCardOrderUseCase.viewCardOrderStatus(operationId);
    }

}
