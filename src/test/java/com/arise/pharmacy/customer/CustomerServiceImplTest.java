package com.arise.pharmacy.customer;

import com.arise.pharmacy.security.user.User;
import com.arise.pharmacy.security.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @Mock
    CustomerRepository customerRepository;
    @Mock
    CustomerRequest customerRequest;
    @Mock
    UserRepository userRepository;

    CustomerService underTest;

    @BeforeEach
    void setUp(){
        underTest = new CustomerServiceImpl(customerRepository,userRepository);
    }

    @Test
    void findCustomerById() {

        //GIVEN
        Long id = 1234L;

        //WHEN
        underTest.findCustomerById(id);

        //THEN
        ArgumentCaptor<Long> captor = ArgumentCaptor.forClass(Long.class);

        then(customerRepository).should().findById(captor.capture());
        assertThat(id).isEqualTo(captor.getValue());
    }

    @Test
    void findCustomerByEmail() {

        //GIVEN
        String email = "customer@gmail.com";

        //WHEN
        underTest.findCustomerByEmail(email);

        //THEN
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);

        then(customerRepository).should().findByEmail(captor.capture());
        assertThat(email).isEqualTo(captor.getValue());
    }

    @Test
    void saveCustomer() {

        //GIVEN
        given(customerRequest.email()).willReturn("user@gmail.com");
        given(customerRequest.firstName()).willReturn("john");
        given(customerRequest.lastName()).willReturn("doe");
        given(customerRequest.phone()).willReturn(2786_345_6787L);

        User user = User.builder()
                .email(customerRequest.email())
                .build();

        given(userRepository.findByEmail(customerRequest.email())).willReturn(Optional.of(user));

        Customer customer = Customer.builder()
                .user(user)
                .firstName("john")
                .lastName("doe")
                .phoneNumber(2786_345_6787L)
                .build();
        
        //WHEN
        try {
            underTest.saveCustomer(customerRequest);
        } catch (Exception ignored) {

        }

        //THEN
        ArgumentCaptor<Customer> captor = ArgumentCaptor.forClass(Customer.class);

        then(customerRepository).should().save(captor.capture());
        assertThat(customer).isEqualTo(captor.getValue());
    }

    @Test
    void saveCustomerThrowsException(){

        //Given
        given(customerRequest.email()).willReturn("user@gmail.com");
        given(userRepository.findByEmail(anyString())).willReturn(Optional.empty());

        //WHEN
        //THEN
        assertThatExceptionOfType(UsernameNotFoundException.class).isThrownBy(() -> underTest.saveCustomer(customerRequest))
                .withMessage("Email doesn't exist");
    }

    @Test
    void updateCustomer() {

        //GIVEN
        given(customerRequest.email()).willReturn("user@gmail.com");
        given(customerRequest.firstName()).willReturn("john");
        given(customerRequest.lastName()).willReturn("doe");
        given(customerRequest.phone()).willReturn(2786_345_6787L);

        Customer customer = Customer.builder()
                .id(1L)
                .firstName("nick")
                .lastName("cage")
                .phoneNumber(2786_345_6787L)
                .build();

        given(customerRepository.findByEmail(customerRequest.email())).willReturn(Optional.of(customer));

        //WHEN
        try {
            underTest.updateCustomer(customerRequest);
        } catch (Exception ignored) {

        }

        //THEN
        ArgumentCaptor<Customer> captor = ArgumentCaptor.forClass(Customer.class);

        then(customerRepository).should().save(captor.capture());
        assertThat(captor.getValue()).hasFieldOrPropertyWithValue("id",customer.getId())
                .hasFieldOrPropertyWithValue("firstName","john")
                .hasFieldOrPropertyWithValue("lastName","doe")
                .hasFieldOrPropertyWithValue("phoneNumber",customer.getPhoneNumber());
    }

    @Test
    void updateCustomerThrowsException(){

        //GIVEN
        given(customerRequest.email()).willReturn("user@gmail.com");
        given(customerRepository.findByEmail(anyString())).willReturn(Optional.empty());

        //WHEN
        //THEN
        assertThatExceptionOfType(UsernameNotFoundException.class).isThrownBy(() -> underTest.updateCustomer(customerRequest))
                .withMessage("Email doesn't exist");

    }
}