package az.company.cardstream.adapter.out.external;

import az.company.cardstream.adapter.out.external.exception.CommonClientException;
import az.company.cardstream.adapter.out.external.model.AccountDto;
import az.company.cardstream.adapter.out.external.model.CreateAccountRequest;
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
@FeignClient(name = "ms-account-client", url = "${app.clients.ms-account.url}")
public interface AccountClient {

    @ErrorHandling(defaultException = CommonClientException.class)
    @GetMapping(value = "/api/account/{id}")
    AccountDto getAccountById(@PathVariable Integer id);

    @ErrorHandling(defaultException = CommonClientException.class)
    @GetMapping("/api/account/")
    List<AccountDto> getAllAccounts();

    @ErrorHandling(defaultException = CommonClientException.class)
    @GetMapping("/api/account//info")
    AccountDto searchAccount(@RequestParam("accNo") String accountNumber);

    @ErrorHandling(defaultException = CommonClientException.class)
    @PostMapping("/api/account/")
    Integer createAccount(@RequestBody CreateAccountRequest createAccountRequest);

}
