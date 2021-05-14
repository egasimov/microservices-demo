package az.company.cardstream.adapter.out.external;

import az.company.cardstream.adapter.out.external.exception.CommonClientException;
import az.company.cardstream.adapter.out.external.model.CreateCustomerDto;
import az.company.cardstream.adapter.out.external.model.CustomerDto;
import feign.error.ErrorHandling;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author QasimovEY on 14.05.21
 */
@FeignClient(name = "ms-customer-client", url = "${app.clients.ms-customer.url}")
public interface CustomerClient {

    @ErrorHandling(defaultException = CommonClientException.class)
    @GetMapping("/api/customer/{id}")
    CustomerDto getCustomer(@PathVariable Integer id);

    @ErrorHandling(defaultException = CommonClientException.class)
    @GetMapping("/api/customer/")
    List<CustomerDto> getAllCustomer();

    @ErrorHandling(defaultException = CommonClientException.class)
    @GetMapping("/api/customer/info")
    CustomerDto searchCustomer(@RequestParam String fin);

    @ErrorHandling(defaultException = CommonClientException.class)
    @PostMapping("/api/customer")
    Integer createCustomer(@RequestBody CreateCustomerDto createCustomerDto);

}
