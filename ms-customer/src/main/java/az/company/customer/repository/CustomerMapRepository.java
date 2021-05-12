package az.company.customer.repository;

import az.company.customer.model.entity.Customer;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author QasimovEY on 12.05.21
 */
@Component
public class CustomerMapRepository {

    private Map<Integer, Customer> customerMap = new HashMap<>();

    @PostConstruct
    void setupMap() {
        var customer0 = new Customer();
        customer0.setId(1);
        customer0.setFirstName("Elchin");
        customer0.setLastName("Gasimov");
        customer0.setBirthDate(LocalDate.of(1999, 8, 13));
        customer0.setFin("1111111");
        customer0.setAddress("Baku, Azerbaijan");
        customer0.setPhoneNumber("+99450-000-00-00");

        customerMap.put(customer0.getId(), customer0);

        customer0 = new Customer();
        customer0.setId(2);
        customer0.setFirstName("Yashar");
        customer0.setLastName("Hasanov");
        customer0.setBirthDate(LocalDate.of(1989, 12, 03));
        customer0.setFin("2222222");
        customer0.setAddress("Ganja, Azerbaijan");
        customer0.setPhoneNumber("+99470-1111-22-33");

        customerMap.put(customer0.getId(), customer0);

        customer0 = new Customer();
        customer0.setId(3);
        customer0.setFirstName("Ali");
        customer0.setLastName("Aliyev");
        customer0.setBirthDate(LocalDate.of(1967, 8, 13));
        customer0.setFin("3333333");
        customer0.setAddress("Baku, Azerbaijan");
        customer0.setPhoneNumber("+99450-999-22-00");

        customerMap.put(customer0.getId(), customer0);
    }


    public Customer findById(Integer id) {
        return customerMap.get(id);
    }

    public Customer searchByFin(String fin) {
        return customerMap.keySet()
                .stream()
                .map(item -> customerMap.get(item))
                .filter(i -> i.getFin().equalsIgnoreCase(fin))
                .findFirst()
                .orElse(null);
    }

    public void delete(Integer id) {
        customerMap.remove(id);
    }

    public Customer create(Customer customer) {
        Integer maxId = customerMap.keySet()
                .stream()
                .max(Integer::compare)
                .orElse(0) + 1;
        customer.setId(maxId);
        customerMap.put(maxId, customer);

        return customer;
    }

    public List<Customer> loadAll() {
        return customerMap.values()
                .stream()
                .collect(Collectors.toList());
    }
}
