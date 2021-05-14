package az.company.cardstream.adapter.out.external;

import az.company.cardstream.adapter.out.external.exception.CommonClientException;
import az.company.cardstream.adapter.out.external.model.Event;
import feign.error.ErrorHandling;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @Author QasimovEY on 14.05.21
 */
@FeignClient(name = "ms-event-client", url = "${app.clients.ms-event.url}")
public interface EventClient {
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
