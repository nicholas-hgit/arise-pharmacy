package com.arise.pharmacy.customer;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "api/v1/info/user")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<?> saveCustomer(@RequestBody CustomerRequest customer){
        try {
            customerService.saveCustomer(customer);
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(e.getMessage());
        }
        return ResponseEntity.status(CREATED).body(customer);
    }

    @GetMapping
    public ResponseEntity<?> getCustomerByEmail(@RequestParam String email){
        Optional<Customer> customer = customerService.findCustomerByEmail(email);

        if(customer.isEmpty()){
            return ResponseEntity.status(NOT_FOUND).body("User not found");
        }

        return ResponseEntity.status(OK).body(customer.get());
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable Long id){
        Optional<Customer> customer = customerService.findCustomerById(id);

        if(customer.isEmpty()){
            return ResponseEntity.status(NOT_FOUND).body("User not found");
        }

        return ResponseEntity.status(OK).body(customer.get());
    }

    @PutMapping(path = "update")
    public ResponseEntity<?> updateCustomer(@RequestBody CustomerRequest customer){

        try {
            customerService.updateCustomer(customer);
        } catch (Exception e) {
            //customer not found
            return ResponseEntity.status(NOT_FOUND).body(e.getMessage());
        }

        return ResponseEntity.status(OK).body(customer);
    }
}
