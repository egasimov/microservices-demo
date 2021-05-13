package az.company.customer.controller;

import az.company.customer.model.dto.CreateCustomerDto;
import az.company.customer.model.dto.CustomerDto;
import az.company.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author QasimovEY on 12.05.21
 */
@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/{id}")
    public ResponseEntity getCustomer(@PathVariable Integer id) {
        CustomerDto customerDto = customerService.findCustomerById(id);
        return ResponseEntity.ok(customerDto);
    }

    @GetMapping()
    public ResponseEntity<List<CustomerDto>> getCustomers() {
        List<CustomerDto> dtoList = customerService.getAllCustomers();
        return ResponseEntity.ok(dtoList);
    }


    @GetMapping("/info")
    public ResponseEntity searchCustomer(@RequestParam String fin) {
        CustomerDto customerDto = customerService.searchCustomer(fin);
        return ResponseEntity.ok(customerDto);
    }

    @PostMapping
    public ResponseEntity createCustomer(@RequestBody CreateCustomerDto createCustomerDto) {
        CustomerDto dto = customerService.createCustomer(createCustomerDto);
        return ResponseEntity.ok(dto.getId());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteCustomer(@PathVariable Integer id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.ok("SUCCESS");
    }

}
