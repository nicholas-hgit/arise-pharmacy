package com.arise.pharmacy.profile;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile,Long> {

    @Query("SELECT c FROM Profile c WHERE c.user.email = :email")
    Optional<Profile> findByEmail(String email);
}
