package com.arise.pharmacy.customer;

import com.arise.pharmacy.security.user.User;
import com.arise.pharmacy.security.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService{

    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;

    @Override
    public Optional<Customer> findCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    @Override
    public Optional<Customer> findCustomerByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    //customer -> information of user with role of SHOPPER
    //therefore customer.email must exist before saving their information
    @Override
    public void saveCustomer(CustomerRequest customer) throws UsernameNotFoundException {

        Optional<User> user = userRepository.findByEmail(customer.email());

        if(user.isEmpty()){
            throw new UsernameNotFoundException("Email doesn't exist");
        }

        Customer customer1 = Customer.builder()
                .user(user.get())
                .firstName(customer.firstName())
                .lastName(customer.lastName())
                .phoneNumber(customer.phone())
                .build();

         customerRepository.save(customer1);
    }

    @Override
    public void updateCustomer(CustomerRequest updatedCustomer) throws UsernameNotFoundException{

        Optional<Customer> customerOptional = customerRepository.findByEmail(updatedCustomer.email());

        if(customerOptional.isEmpty()){
            throw new UsernameNotFoundException("Email doesn't exist");
        }

        Customer customer = customerOptional.get();

        customer.setFirstName(updatedCustomer.firstName());
        customer.setLastName(updatedCustomer.lastName());
        customer.setPhoneNumber(updatedCustomer.phone());

        customerRepository.save(customer);
    }
}
