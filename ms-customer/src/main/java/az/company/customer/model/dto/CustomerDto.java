package az.company.customer.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * @Author QasimovEY on 12.05.21
 */

@Data
@NoArgsConstructor
public class CustomerDto {

    private Integer id;
    private String firstName;
    private String lastName;
    private String state;
    private String fin;

    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate birthDate;

    private String address;
    private String phoneNumber;

}
