package az.company.cardstream.adapter.out.external.model;

import lombok.Data;

import java.time.LocalDate;

/**
 * @Author QasimovEY on 12.05.21
 */
@Data
public class CreateCustomerDto {

    private String firstName;
    private String lastName;
    private String fin;
    private LocalDate birthDate;
    private String address;
    private String phoneNumber;

}
