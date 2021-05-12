package az.company.customer.util;

import az.company.customer.model.dto.CustomerDto;
import az.company.customer.model.entity.Customer;

/**
 * @Author QasimovEY on 12.05.21
 */
public class Transformer {
    public static Customer toEntity(CustomerDto dto) {
        Customer entity = new Customer();
        entity.setId(dto.getId());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setFin(dto.getFin());
        entity.setBirthDate(dto.getBirthDate());
        entity.setAddress(dto.getAddress());
        entity.setPhoneNumber(dto.getPhoneNumber());
        return entity;
    }

    public static CustomerDto toDto(Customer entity) {
        CustomerDto dto = new CustomerDto();
        dto.setId(entity.getId());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setFin(entity.getFin());
        dto.setBirthDate(entity.getBirthDate());
        dto.setAddress(entity.getAddress());
        dto.setPhoneNumber(entity.getPhoneNumber());
        return dto;
    }
}
