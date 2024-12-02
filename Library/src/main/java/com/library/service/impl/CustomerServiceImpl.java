package com.library.service.impl;

import com.library.dto.CustomerDto;
import com.library.model.Customer;
import com.library.repository.CustomerRepository;
import com.library.repository.RoleRepository;
import com.library.service.CountryService;
import com.library.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final RoleRepository roleRepository;
    private final CountryService countryService;

    @Override
    public Customer save(CustomerDto customerDto) {
        Customer customer = new Customer();
        customer.setFirstName(customerDto.getFirstName());
        customer.setLastName(customerDto.getLastName());
        customer.setPassword(customerDto.getPassword());
        customer.setUsername(customerDto.getUsername());
        customer.setRoles(Collections.singletonList(roleRepository.findByName("CUSTOMER")));
        return customerRepository.save(customer);
    }

    @Override
    public Customer findByUsername(String username) {
        try {
            return customerRepository.findByUsername(username);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public Customer update(CustomerDto customerDto) {
        Customer customer = findByUsername(customerDto.getUsername());
        customer.setAddress(customerDto.getAddress());
        customer.setCity(customerDto.getCity());



        customer.setCountry(customerDto.getCountry());
        customer.setPhoneNumber(customerDto.getPhoneNumber());
        return customerRepository.save(customer);
    }

    @Override
    public Customer changePass(CustomerDto customerDto) {
        Customer customer = customerRepository.findByUsername(customerDto.getUsername());
        customer.setPassword(customerDto.getPassword());
        return customerRepository.save(customer);
    }

    @Override
    public CustomerDto getCustomer(String username) {
        CustomerDto customerDto = new CustomerDto();
        Customer customer = customerRepository.findByUsername(username);
        customerDto.setFirstName(customer.getFirstName());
        customerDto.setLastName(customer.getLastName());
        customerDto.setPassword(customer.getPassword());
        customerDto.setUsername(customer.getUsername());
        customerDto.setAddress(customer.getAddress());
        customerDto.setPhoneNumber(customer.getPhoneNumber());
        customerDto.setCity(customer.getCity());
        customerDto.setCountry(customer.getCountry());
        return customerDto;
    }




}
