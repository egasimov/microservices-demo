package az.company.cardstream.adapter.out.external;

import az.company.cardstream.adapter.out.external.exception.CommonClientException;
import az.company.cardstream.adapter.out.external.model.CardEntity;
import az.company.cardstream.adapter.out.external.model.CreateCardRequest;
import feign.error.ErrorHandling;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Author QasimovEY on 14.05.21
 */
@FeignClient(name = "ms-card-client", url = "${app.clients.ms-card.url}")
public interface CardClient {

    @ErrorHandling(defaultException = CommonClientException.class)
    @PostMapping("/api/card")
    CardEntity createCard(@RequestBody CreateCardRequest createCardRequest);

    @ErrorHandling(defaultException = CommonClientException.class)
    @GetMapping("/api/card/{operationId}/info")
    CardEntity getCardInfo(@PathVariable String operationId);

}
