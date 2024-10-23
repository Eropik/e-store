package com.library.service;

import com.library.dto.CustomerDto;
import com.library.model.Customer;

public interface CustomerService {


    CustomerDto save(CustomerDto customerDto);

    Customer findByUsername(String username);

    Customer saveInfor(Customer customer);
}
