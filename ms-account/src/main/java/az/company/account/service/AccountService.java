package az.company.account.service;

import az.company.account.error.exception.RecordNotFoundException;
import az.company.account.model.State;
import az.company.account.model.dto.AccountDto;
import az.company.account.model.dto.CreateAccountRequest;
import az.company.account.model.entity.Account;
import az.company.account.repository.AccountRepository;
import az.company.account.util.Generator;
import az.company.account.util.Transformer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author QasimovEY on 13.05.21
 */
@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;

    public AccountDto getAccountById(Integer id) {
        Account entity = accountRepository.findById(id)
                .orElseThrow(() -> RecordNotFoundException.of("ACCOUNT NOT FOUND FOR : %s".formatted(id)));
        return Transformer.toDto(entity);
    }

    public Integer createAccount(CreateAccountRequest request) {
        Account entity = new Account();
        entity.setCurrency(request.getCurrency());
        entity.setAccountType(request.getAccountType());
        entity.setBranchCode(request.getBranchCode());

        entity.setRest(BigDecimal.ZERO);
        entity.setState(State.ACTIVE);
        String accNo = Generator.generateAccountNO(request.getCustomerId(),
                request.getAccountType(),
                request.getCurrency(),
                request.getBranchCode());
        entity.setAccountNumber(accNo);
        entity.setCustomerId(request.getCustomerId());
        entity = accountRepository.save(entity);
        return entity.getId();
    }

    public AccountDto searchAccount(String accountNo) {
        var entity = accountRepository.findByAccountNumber(accountNo)
                .orElseThrow(() -> RecordNotFoundException.of("ACCOUNT NOT FOUND FOR %s".formatted(accountNo)));
        return Transformer.toDto(entity);
    }

    public List<AccountDto> getAllAccounts(){
        List<Account> accounts = accountRepository.findAll();
        return accounts.stream()
                .map(item->Transformer.toDto(item))
                .collect(Collectors.toList());
    }
}
