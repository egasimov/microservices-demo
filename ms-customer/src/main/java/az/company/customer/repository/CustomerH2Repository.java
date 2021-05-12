package az.company.customer.repository;

import az.company.customer.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author QasimovEY on 12.05.21
 */
@Repository
public interface CustomerH2Repository extends JpaRepository<Customer, Integer> {
    Customer findByFin(String fin);
}
