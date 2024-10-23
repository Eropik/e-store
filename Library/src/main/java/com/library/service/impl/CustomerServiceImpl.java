package com.library.service.impl;

import com.library.dto.CustomerDto;
import com.library.model.Customer;
import com.library.repository.CustomerRepository;
import com.library.repository.RoleRepository;
import com.library.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final RoleRepository roleRepository;

    @Override
    public CustomerDto save(CustomerDto customerDto) {
        Customer customer = new Customer();
        customer.setFirstName(customerDto.getFirstName());
        customer.setLastName(customerDto.getLastName());
        customer.setPassword(customerDto.getPassword());
        customer.setUsername(customerDto.getUsername());
        customer.setRoles(Arrays.asList(roleRepository.findByName("CUSTOMER")));
        return mappedDto(customerRepository.save(customer));


    }

    @Override
    public Customer findByUsername(String username) {
        return customerRepository.findByUsername(username);
    }

    @Override
    public Customer saveInfor(Customer customer) {
        Customer customerSave = customerRepository.findByUsername(customer.getUsername());
        customerSave.setAddress(customer.getAddress());
        customerSave.setPhoneNumber(customer.getPhoneNumber());
        customerSave.setCity(customer.getCity());
        customerSave.setCountry(customer.getCountry());
        return customerRepository.save(customerSave);
    }

    private CustomerDto mappedDto(Customer customer) {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setFirstName(customer.getFirstName());
        customerDto.setLastName(customer.getLastName());
        customerDto.setPassword(customer.getPassword());
        customerDto.setUsername(customer.getUsername());


        return customerDto;
    }
}
