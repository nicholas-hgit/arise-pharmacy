package com.arise.pharmacy.customer;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;


import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "api/v1/info/user")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<?> saveCustomer(@RequestBody CustomerRequest customer){

        Customer savedCustomer;
        try {
            savedCustomer = customerService.saveCustomer(customer);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(e.getMessage());
        }

        return ResponseEntity.status(CREATED).body(savedCustomer);
    }

    @GetMapping
    public ResponseEntity<?> getCustomerByEmail(@RequestParam String email){

        Customer customer;
        try {
            customer = customerService.findCustomerByEmail(email);
        }catch (UsernameNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(e.getMessage());
        }

        return ResponseEntity.status(OK).body(customer);
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable Long id){

        Customer customer;
        try {
            customer = customerService.findCustomerById(id);
        }catch (UsernameNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(e.getMessage());
        }

        return ResponseEntity.status(OK).body(customer);
    }

    @PutMapping(path = "update")
    public ResponseEntity<?> updateCustomer(@RequestBody CustomerRequest customer){

        Customer updatedCustomer;
        try {
            updatedCustomer = customerService.updateCustomer(customer);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(e.getMessage());
        }

        return ResponseEntity.status(OK).body(updatedCustomer);
    }
}
