package com.arise.pharmacy.profile;

import com.arise.pharmacy.security.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "profiles")
@Entity
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String identityNumber;
    private String firstName;
    private String lastName;
    private Long phoneNumber;
    private String image;

    @OneToOne
    @JoinColumn(name = "email", referencedColumnName = "email")
    @JsonIgnore
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Profile profile)) return false;
        return Objects.equals(getId(), profile.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    public static ProfileBuilder builder() {
        return new ProfileBuilder();
    }
}
