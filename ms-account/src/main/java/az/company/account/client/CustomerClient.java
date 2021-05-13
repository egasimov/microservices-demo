package az.company.account.client;

import az.company.account.client.customer.CustomerDto;
import az.company.account.client.exception.CommonClientException;
import feign.error.ErrorCodes;
import feign.error.ErrorHandling;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author QasimovEY on 13.05.21
 */
@FeignClient(name = "customerClient", url = "${app.services.customer.url}")
public interface CustomerClient {


    @ErrorHandling(codeSpecific = {@ErrorCodes(codes = 404, generate = CommonClientException.class),
            @ErrorCodes(codes = 400, generate = CommonClientException.class)},
            defaultException = CommonClientException.class)
    @GetMapping("/api/customer/{id}")
    CustomerDto getCustomer(@PathVariable Integer id);

    @ErrorHandling(codeSpecific = {@ErrorCodes(codes = 404, generate = CommonClientException.class),
            @ErrorCodes(codes = 400, generate = CommonClientException.class)},
            defaultException = CommonClientException.class)
    @GetMapping("/api/customer/")
    List<CustomerDto> getAllCustomer();


    @ErrorHandling(codeSpecific = {@ErrorCodes(codes = 404, generate = CommonClientException.class),
            @ErrorCodes(codes = 400, generate = CommonClientException.class)},
            defaultException = CommonClientException.class)
    @GetMapping("/api/customer/info")
    CustomerDto searchCustomer(@RequestParam String fin);

}
