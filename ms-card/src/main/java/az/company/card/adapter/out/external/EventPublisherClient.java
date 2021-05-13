package az.company.card.adapter.out.external;

import az.company.card.adapter.out.external.exception.CommonClientException;
import az.company.card.adapter.out.external.model.Event;
import az.company.card.application.out.LoadEventPort;
import az.company.card.application.out.PublishEventPort;
import feign.error.ErrorCodes;
import feign.error.ErrorHandling;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @Author QasimovEY on 14.05.21
 */
@FeignClient(name = "event-publisher-service",
        url = "${app.clients.event-service.url}")
public interface EventPublisherClient /*extends LoadEventPort, PublishEventPort */{

    @ErrorHandling(defaultException = CommonClientException.class)
    @GetMapping("/api/operation/{operationId}/event")
    List<Object> getAllEvents(@PathVariable String operationId);

    @ErrorHandling(defaultException = CommonClientException.class)
    @PostMapping("/api/operation/{operationId}/event")
    String placeEvent(@PathVariable("operationId") String operationId, @RequestBody Event event);

    @ErrorHandling(defaultException = CommonClientException.class)
    @GetMapping("/api/operation/{operationId}/status")
    Event getOperationStatus(@PathVariable String operationId);

}
