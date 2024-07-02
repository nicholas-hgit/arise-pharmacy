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
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @Mock
    CustomerRepository customerRepository;
    @Mock
    CustomerRequest request;
    @Mock
    UserRepository userRepository;

    CustomerService underTest;

    @BeforeEach
    void setUp(){
        underTest = new CustomerServiceImpl(customerRepository,userRepository);
    }

    @Test
    void findCustomerById(){

        //GIVEN
        Long id = 1234L;
        Customer customer = Customer.builder()
                .id(id)
                .firstName("nick")
                .lastName("cage")
                .phoneNumber(2767_1312_787L)
                .build();

        given(customerRepository.findById(id)).willReturn(Optional.of(customer));

        //WHEN
        Customer expected = underTest.findCustomerById(id);

        //THEN
        assertThat(customer).isEqualTo(expected);
    }

    @Test
    void findCustomerByIdThrows(){

        //GIVEN
        given(customerRepository.findById(anyLong())).willReturn(Optional.empty());

        //WHEN
        //THEN
        assertThatExceptionOfType(UsernameNotFoundException.class).isThrownBy(() -> underTest.findCustomerById(1L))
                .withMessage("user not found");
    }

    @Test
    void findCustomerByEmail(){

        //GIVEN
        String email = "customer@gmail.com";
        Customer customer = Customer.builder()
                .id(1L)
                .firstName("nick")
                .lastName("cage")
                .phoneNumber(2767_1312_787L)
                .build();

        given(customerRepository.findByEmail(email)).willReturn(Optional.of(customer));

        //WHEN
        Customer expected = underTest.findCustomerByEmail(email);

        //THEN
        assertThat(customer).isEqualTo(expected);
    }

    @Test
    void findCustomerByEmailThrows(){

        //GIVEN
        String email = "user@gmail.com";
        given(customerRepository.findByEmail(anyString())).willReturn(Optional.empty());

        //WHEN
        //THEN
        assertThatExceptionOfType(UsernameNotFoundException.class).isThrownBy(() -> underTest.findCustomerByEmail(email))
                .withMessage("user not found");
    }

    @Test
    void saveCustomer() {

        //GIVEN
        given(request.email()).willReturn("user@gmail.com");
        given(request.firstName()).willReturn("john");
        given(request.lastName()).willReturn("doe");
        given(request.phone()).willReturn(2786_345_6787L);

        User user = User.builder()
                .email(request.email())
                .build();

        given(userRepository.findByEmail(request.email())).willReturn(Optional.of(user));

        Customer customer = Customer.builder()
                .user(user)
                .firstName("john")
                .lastName("doe")
                .phoneNumber(2786_345_6787L)
                .build();
        
        //WHEN
        underTest.saveCustomer(request);

        //THEN
        ArgumentCaptor<Customer> captor = ArgumentCaptor.forClass(Customer.class);

        then(customerRepository).should().save(captor.capture());
        assertThat(captor.getValue()).hasFieldOrPropertyWithValue("firstName",customer.getFirstName())
                .hasFieldOrPropertyWithValue("lastName",customer.getLastName())
                .hasFieldOrPropertyWithValue("phoneNumber",customer.getPhoneNumber())
                .hasFieldOrPropertyWithValue("user",user);
    }

    @Test
    void saveCustomerThrowsException(){

        //Given
        given(request.email()).willReturn("user@gmail.com");
        given(userRepository.findByEmail(anyString())).willReturn(Optional.empty());

        //WHEN
        //THEN
        assertThatExceptionOfType(UsernameNotFoundException.class).isThrownBy(() -> underTest.saveCustomer(request))
                .withMessage("user account must exist");
    }

    @Test
    void updateCustomer() {

        //GIVEN
        Customer customer = Customer.builder()
                .id(1L)
                .firstName("nick")
                .lastName("cage")
                .phoneNumber(2786_345_6787L)
                .build();

        Customer updatedCustomer = Customer.builder()
                        .id(1L)
                        .firstName("john")
                        .lastName("doe")
                        .phoneNumber(customer.getPhoneNumber())
                        .build();

        given(request.email()).willReturn("user@gmail.com");
        given(request.firstName()).willReturn(updatedCustomer.getFirstName());
        given(request.lastName()).willReturn(updatedCustomer.getLastName());
        given(request.phone()).willReturn(updatedCustomer.getPhoneNumber());
        given(customerRepository.findByEmail(request.email())).willReturn(Optional.of(customer));

        //WHEN
        underTest.updateCustomer(request);

        //THEN
        ArgumentCaptor<Customer> captor = ArgumentCaptor.forClass(Customer.class);

        then(customerRepository).should().save(captor.capture());
        assertThat(captor.getValue()).isEqualTo(customer)
                .hasFieldOrPropertyWithValue("firstName",updatedCustomer.getFirstName())
                .hasFieldOrPropertyWithValue("lastName",updatedCustomer.getLastName())
                .hasFieldOrPropertyWithValue("phoneNumber",updatedCustomer.getPhoneNumber());

    }

    @Test
    void updateCustomerThrowsException(){

        //GIVEN
        given(request.email()).willReturn("user@gmail.com");
        given(customerRepository.findByEmail(anyString())).willReturn(Optional.empty());

        //WHEN
        //THEN
        assertThatExceptionOfType(UsernameNotFoundException.class).isThrownBy(() -> underTest.updateCustomer(request))
                .withMessage("user not found");

    }
}