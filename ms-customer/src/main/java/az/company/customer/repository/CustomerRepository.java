package az.company.customer.repository;

import az.company.customer.model.dto.CustomerDto;
import az.company.customer.model.entity.Customer;

import java.util.List;

/**
 * @Author QasimovEY on 12.05.21
 */
public interface CustomerRepository {

    Customer findById(Integer id);

    Customer searchByFin(String fin);

    void delete(Integer id);

    Customer create(Customer customer);

    List<Customer> loadAll();
}
