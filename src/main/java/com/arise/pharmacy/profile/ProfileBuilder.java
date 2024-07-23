package com.arise.pharmacy.profile;

import com.arise.pharmacy.exceptions.InvalidIdentityNumberException;
import com.arise.pharmacy.security.user.User;
import org.apache.commons.validator.routines.checkdigit.LuhnCheckDigit;

public final class ProfileBuilder {

    private Long id;
    private String identityNumber;
    private String firstName;
    private String lastName;
    private Long phoneNumber;
    private String image;

    private User user;

    public ProfileBuilder id(Long id){
        this.id = id;
        return this;
    }

    public ProfileBuilder user(User user){
        this.user = user;
        return this;
    }

    public ProfileBuilder identityNumber(String identityNumber){
        this.identityNumber = identityNumber;
        return this;
    }

    public ProfileBuilder firstName(String firstName){
        this.firstName = firstName;
        return this;
    }

    public ProfileBuilder lastName(String lastName){
        this.lastName = lastName;
        return this;
    }

    public ProfileBuilder phoneNumber(Long phoneNumber){
        this.phoneNumber = phoneNumber;
        return this;
    }

    public ProfileBuilder image(String image){
        this.image = image;
        return this;
    }

    public Profile build(){
        if(!isValidIdentityNumber(identityNumber)){
            throw new InvalidIdentityNumberException("invalid ID number");
        }
        return new Profile(id,identityNumber,firstName,lastName,phoneNumber,image,user);
    }

    private boolean isValidIdentityNumber(String id){
        return LuhnCheckDigit.LUHN_CHECK_DIGIT.isValid(id);
    }


}
