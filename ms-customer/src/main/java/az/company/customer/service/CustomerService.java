package az.company.customer.service;

import az.company.customer.error.exception.InvalidInputException;
import az.company.customer.error.exception.RecordNotFoundException;
import az.company.customer.model.dto.CreateCustomerDto;
import az.company.customer.model.dto.CustomerDto;
import az.company.customer.model.entity.Customer;
import az.company.customer.repository.CustomerH2Repository;
import az.company.customer.util.Transformer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author QasimovEY on 12.05.21
 */
@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerH2Repository customerRepository;

    public CustomerDto findCustomerById(Integer id) {
        Customer customer = customerRepository.findById(id).orElse(null);
        if (customer == null) {
            throw RecordNotFoundException.of("Customer NOT FOUND %s ".formatted(id));
        }
        return Transformer.toDto(customer);
    }

    public CustomerDto searchCustomer(String fin) {
        Customer customer = customerRepository.findByFin(fin);
        if (customer == null) {
            throw RecordNotFoundException.of("Customer NOT FOUND: by %s".formatted(fin));
        }
        return Transformer.toDto(customer);
    }


    public CustomerDto createCustomer(CreateCustomerDto createCustomerDto) {

        Customer byFin = customerRepository.findByFin(createCustomerDto.getFin());
        if (byFin != null) {
            throw InvalidInputException.of("Duplicate FIN FOUND :  %s".formatted(createCustomerDto.getFin()));
        }
        var newEntity = new Customer();

        newEntity.setFirstName(createCustomerDto.getFirstName());
        newEntity.setLastName(createCustomerDto.getFirstName());
        newEntity.setFin(createCustomerDto.getFin());
        newEntity.setBirthDate(createCustomerDto.getBirthDate());
        newEntity.setAddress(createCustomerDto.getAddress());
        newEntity.setPhoneNumber(createCustomerDto.getPhoneNumber());

        newEntity = customerRepository.save(newEntity);

        return Transformer.toDto(newEntity);
    }

    public void deleteCustomer(Integer id) {
        customerRepository.deleteById(id);
    }

    public List<CustomerDto> getAllCustomers() {
        Collection<Customer> customers = customerRepository.findAll();
        return customers.stream()
                .map(i -> Transformer.toDto(i))
                .collect(Collectors.toList());
    }
}
