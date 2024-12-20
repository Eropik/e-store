package com.library.service;

import com.library.dto.CustomerDto;
import com.library.model.Customer;

public interface CustomerService {


    Customer save(CustomerDto customerDto);

    Customer findByUsername(String username);

    Customer update(CustomerDto customerDto);

    Customer changePass(CustomerDto customerDto);

    CustomerDto getCustomer(String username);
}
