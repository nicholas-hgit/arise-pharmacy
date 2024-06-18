package com.arise.pharmacy.customer;

import com.arise.pharmacy.security.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "customers")
@Builder
@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private Long phoneNumber;

    @OneToOne
    @JoinColumn(name = "email", referencedColumnName = "email")
    @JsonIgnore
    private User user;

}
