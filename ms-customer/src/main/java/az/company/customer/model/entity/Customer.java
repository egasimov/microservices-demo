package az.company.customer.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * @Author QasimovEY on 12.05.21
 */
@Data
@NoArgsConstructor
public class Customer {

    private Integer id;
    private String firstName;
    private String lastName;
    private String state;
    private String fin;
    private LocalDate birthDate;
    private String address;
    private String phoneNumber;

}