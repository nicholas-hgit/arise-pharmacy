package com.arise.pharmacy.customer;

import java.util.Optional;

public interface CustomerService {

    void saveCustomer(CustomerRequest customer) throws Exception;
    void updateCustomer(CustomerRequest updatedCustomer) throws Exception;
    Optional<Customer> findCustomerById(Long id);
    Optional<Customer> findCustomerByEmail(String email);
}
