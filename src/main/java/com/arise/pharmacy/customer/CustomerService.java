package com.arise.pharmacy.customer;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface CustomerService {

    Customer saveCustomer(CustomerRequest customer) throws UsernameNotFoundException;
    Customer updateCustomer(CustomerRequest updatedCustomer) throws UsernameNotFoundException;
    Customer findCustomerById(Long id) throws UsernameNotFoundException ;
    Customer findCustomerByEmail(String email) throws UsernameNotFoundException;
}
